package com.distlab.hqsms.meter;

import com.distlab.hqsms.common.CustomTaskScheduler;
import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.edge.*;
import com.distlab.hqsms.common.modbus.ModbusRTUCMD;
import com.distlab.hqsms.strategy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.*;

@Service
public class MeterService {

  private static final Logger logger = LoggerFactory.getLogger(MeterService.class);

  @Autowired
  MeterRepository meterRepository;
  @Autowired
  MeterLogRepository meterLogRepository;
  @Autowired
  DeviceService deviceService;
  @Autowired
  CustomTaskScheduler scheduler;
  @Autowired
  StrategyService strategyService;
  @Autowired
  RabbitMQSender rabbitMQSender;

  @Value("${hqsms.edge.meter.enable}")
  private Boolean edgeMeterEnable;

  /**
   * 合并多个字节为无符号整数
   *
   * @param data      需要合并的字节数组
   * @param numberLen 目标数字的长度（单位：个字节，不可超过7字节）
   *                  numberLen必须是字节数组长度的因数
   * @return          无符号整数数组
   */
  public long[] byte2Long(byte[] data, int numberLen) {
    if (numberLen > 8) {
      logger.error("len must less than 8");
      return null;
    }
    if (data.length%numberLen != 0) {
      logger.error("data incorrect");
      return null;
    }
    long[] result = new long[data.length/numberLen];
    for (int i = 0, k = 0; i < data.length; i++) {
      long tData = Byte.toUnsignedInt(data[i]);
      for (int j = 1; j < numberLen; j++) {
        tData = tData << 8 | Byte.toUnsignedInt(data[++i]);
      }
      result[k] = tData;
      k++;
    }
    return result;
  }

  /**
   * 获取RTU格式指令，发送指令，获取数据
   * 对数据进行处理：需明确一个数字由多少字节组成，即numberLen
   *
   * @param os            输出流
   * @param is            输入流
   * @param incompleteCMD 指令（不含CRC校验码）
   * @param numberLen     数字长度（单位：字节）
   * @return              无符号整数数组
   * @throws IOException  IO异常
   */
  public long[] receiveData(OutputStream os, InputStream is, byte[] incompleteCMD, int numberLen) throws IOException{
    ModbusRTUCMD mbrCMD = new ModbusRTUCMD();
    mbrCMD.setIncompleteCMD(incompleteCMD);
    byte[] cmd = mbrCMD.getCMD();

    // 输出流写入命令，读取输入流
    byte[] tData = new byte[cmd[5] * 2 + 5];
    os.write(cmd);
    int read = is.read(tData);
    os.flush();
//    if (read != cmd[5] * 2 + 5) {
//      logger.error("read inputStream error");
//      return null;
//    }

    // 校验数据
    byte[] tCMD = new byte[read - 2];
    System.arraycopy(tData, 0, tCMD, 0, tCMD.length);
    mbrCMD.setIncompleteCMD(tCMD);
    byte[] reCMD = mbrCMD.getCMD();
    if (!Arrays.equals(reCMD, tData)){
      logger.error("data crc error");
      return null;
    }

    // 处理数据
    byte[] data = new byte[tData.length - 5];
    System.arraycopy(tData, 3, data, 0, tData.length - 5);    // 剔除头尾，获取数据段
    return byte2Long(data, numberLen);
  }

  /**
   * 启动服务
   *
   * MODBUS RTU报文格式：从机地址，功能码，寄存器起始地址高位，低位，寄存器读取个数高位，低位，CRC高位，低位
   *
   */
  public void startService() {
    Iterable<Meter> meters = meterRepository.findAll();
    for (Meter meter : meters) {
      DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(meter.getId(), Rule.RuleDeviceType.METER);
      Integer slaveId = meter.getSlaveId();
      if (slaveId == null) {
        logger.error("slaveId not found");
        return;
      }
      String ip = meter.getIp();
      if (ip == null) {
        logger.error(GlobalConstant.MSG_IP_NOT_FOUND);
        return;
      }
      Integer port = meter.getPort();
      if (port == null) {
        logger.error(GlobalConstant.MSG_PORT_NOT_FOUND);
        return;
      }
      try {
        Socket socket = new Socket(ip, port);
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        // ABC三相电压及A-B、C-B、A-C线电压：007AH~007FH（按顺序存储），长度均为2字节
        long[] voltages = receiveData(os, is, new byte[]{slaveId.byteValue(), 0x03, 0x00, 0x7A, 0x00, 0x06}, 2);
        if (voltages == null) {
          socket.close();
          logger.error("voltage data error");
          return;
        }
        // ABC三相电流及剩余电流：0080H~0083H（按顺序），长度均为2字节
        long[] currents = receiveData(os, is, new byte[]{slaveId.byteValue(), 0x03, 0x00, (byte) 0x80, 0x00, 0x04}, 2);
        if (currents == null) {
          socket.close();
          logger.error("current data error");
          return;
        }
        // ABC三相正向有功电能：0048H~004DH，长度4字节
        long[] phaseEnergies = receiveData(os, is, new byte[]{slaveId.byteValue(), 0x03, 0x00, 0x48, 0x00, 0x06}, 4);
        if (phaseEnergies == null) {
          socket.close();
          logger.error("phase energy data error");
          return;
        }
        // 正向有功总电能，长度4字节
        long[] totalEnergy = receiveData(os, is, new byte[]{slaveId.byteValue(), 0x03, 0x00, 0x40, 0x00, 0x02}, 4);
        if (totalEnergy == null) {
          socket.close();
          logger.error("total energy data error");
          return;
        }
        // 总有功功率及ABC三相有功功率：0084H~0087H（按顺序），长度均为2字节
        long[] powers = receiveData(os, is, new byte[]{slaveId.byteValue(), 0x03, 0x00, (byte) 0x84, 0x00, 0x04}, 2);
        if (powers == null) {
          socket.close();
          logger.error("power data error");
          return;
        }
        // 总功率因数及ABC三相功率因数：0090H~0093H（按顺序），长度均为2字节
        long[] factors = receiveData(os, is, new byte[]{slaveId.byteValue(), 0x03, 0x00, (byte) 0x90, 0x00, 0x04}, 2);
        if (factors == null) {
          socket.close();
          logger.error("factor data error");
          return;
        }
        // 电网频率：0094H，长度2字节
        long[] frequency = receiveData(os, is, new byte[]{slaveId.byteValue(), 0x03, 0x00, (byte) 0x94, 0x00, 0x01}, 2);
        if (frequency == null) {
          socket.close();
          logger.error("frequency data error");
          return;
        }
        socket.close();

        // 获取分布式ID
        BigInteger id = deviceService.getLeafId(DeviceService.LeafType.METER_LOG);
        if (id.equals(BigInteger.valueOf(-1))) {
          return;
        }
        MeterLog log = new MeterLog();
        log.setId(id);
        log.setDeviceId(meter.getId());
        log.setaVoltage((float)(voltages[0]/100.0));
        log.setbVoltage((float)(voltages[1]/100.0));
        log.setcVoltage((float)(voltages[2]/100.0));
        log.setAbVoltage((float)(voltages[3]/100.0));
        log.setCbVoltage((float)(voltages[4]/100.0));
        log.setAcVoltage((float)(voltages[5]/100.0));
        log.setaCurrent((float)(currents[0]/1000.0));
        log.setbCurrent((float)(currents[1]/1000.0));
        log.setcCurrent((float)(currents[2]/1000.0));
        log.setResidualCurrent((float)(currents[3]/1000.0));
        log.setaEnergy((float)(phaseEnergies[0]/100.0));
        log.setbEnergy((float)(phaseEnergies[1]/100.0));
        log.setcEnergy((float)(phaseEnergies[2]/100.0));
        log.setTotalEnergy((float)(totalEnergy[0]/100.0));
        log.setTotalPower((float)(powers[0]/1000.0));
        log.setaPower((float)(powers[1]/1000.0));
        log.setbPower((float)(powers[2]/1000.0));
        log.setcPower((float)(powers[3]/1000.0));
        log.setTotalFactor((float)(factors[0]/100.0));
        log.setaFactor((float)(factors[1]/100.0));
        log.setbFactor((float)(factors[2]/100.0));
        log.setcFactor((float)(factors[3]/100.0));
        log.setFrequency((float)(frequency[0]/10.0));
        log.setLatitude(hierarchy.getPole().getLatitude());
        log.setLongitude(hierarchy.getPole().getLongitude());
        log.setCreatedAt(new Date());
        meterLogRepository.save(log);
//        logger.debug("save data: " + id);

        // 匹配策略和事件上报
        StrategyParameter input = new StrategyParameter();
        input.setServerId(hierarchy.getPole().getServerId());
        input.setPoleId(hierarchy.getPole().getId());
        input.setDeviceType(Rule.RuleDeviceType.METER);
        input.setDeviceId(hierarchy.getDevice().getId());
        input.setDeviceLogType(Rule.RuleDeviceLogType.RAW);
        input.setDeviceLogId(log.getId());
        input.setDeviceLogLongitude(hierarchy.getPole().getLongitude());
        input.setDeviceLogLatitude(hierarchy.getPole().getLatitude());
        Map<String, String> values = new HashMap<>();
        input.setValues(values);
        List<RuleEvent> events = strategyService.execute(input, new ArrayList<>());
        log.setRuleEvents(events);
        rabbitMQSender.sendMeterLog(input, log);
      } catch (Exception e) {
        logger.error(e.getMessage());
        return;
      }

    }
  }

  /**
   * 定时采集
   *
   * 采集间隔：每小时30分整采集一次
   *
   */
  @Scheduled(cron = "0 0/30 * * * ?")
  public void schedule() {
    if (!edgeMeterEnable) {
      logger.info(GlobalConstant.MSG_METER_UNABLE);
      // 设备未启用，取消定时任务
      String className = Thread.currentThread().getStackTrace()[1].getClassName();
      String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
      scheduler.cancelTask(String.format("%s.%s", className, methodName));
      return;
    }
    startService();
  }

}
