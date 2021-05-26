package com.distlab.hqsms.light;

import com.distlab.hqsms.common.NumUtils;
import com.distlab.hqsms.edge.DeviceService;
import com.distlab.hqsms.edge.Device;
import com.distlab.hqsms.edge.DeviceHierarchy;
import com.distlab.hqsms.edge.Pole;
import com.distlab.hqsms.edge.Server;
import com.distlab.hqsms.strategy.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Date;
import java.util.Optional;

@Service
public class LightService {

  private static final Logger logger = LoggerFactory.getLogger(LightService.class);

  @Autowired
  LightRepository lightRepository;
  @Autowired
  LightLogRepository lightLogRepository;
  @Autowired
  DeviceService deviceService;

  @Value("${hqsms.edge.Light.enable}")
  private boolean edgeLightEnable;

  private static final byte[] INIT485 = new byte[] {0x1B, 0x67, 0x72, 0x1D, (byte) 0xC0, 0x6D, 0x6D, 0x3A, 0x2F, (byte) 0xB7, 0x06, 0x15, 0x01, 0x01, 0x11, 0x01};

  /**
   * 初始化灯控设备
   *
   */
  @PostConstruct
  private void initServer() {
    if (!edgeLightEnable) {
      logger.info("edge light unable.");
      return;
    }
    Iterable<Light> lights = lightRepository.findAll();
    for (Light light : lights) {
      String ip = light.getIp();
      Integer port = light.getPort();
      if (ip == null || ip.trim().length() == 0 || port == null) {
        logger.info("ip or port not found, Id: " + light.getId());
        return;
      }
      // 建立连接，初始化
      Socket socket = null;
      try {
        socket = new Socket(ip, port);
        OutputStream os = socket.getOutputStream();
        os.write(INIT485);
        os.flush();
        logger.debug("light initialize..., Id: " + light.getId());
      } catch (IOException e) {
        logger.error(e.getMessage());
      } finally {
        try {
          if (socket != null) socket.close();
        } catch (IOException e) {
          logger.error(e.getMessage());
        }
      }
    }
    // 延迟3S
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * 关灯
   *
   * @param id  灯控Id
   * @return    -1失败 0成功
   */
  public LightLog controller(BigInteger id, int cmdCode) {
    Optional<Light> tempLight = lightRepository.findById(id);
    if (!tempLight.isPresent()) {
      logger.error("device not exist, Id: " + id);
      return null;
    }
    Light light = tempLight.get();
    String ip = light.getIp();
    Integer port = light.getPort();
    if (ip == null || ip.trim().length() == 0 || port == null) {
      logger.error("ip or port not found, Id: " + id);
      return null;
    }

    LightInfo lightInfo = new LightInfo();
    // connect
    Socket socket = null;
    try {
      socket = new Socket(ip, port);
      OutputStream os = socket.getOutputStream();
      InputStream is = socket.getInputStream();
      OperationTypes operationType = OperationTypes.getByCode(cmdCode);
      if (operationType == null) {
        logger.error("cmd code error");
        return null;
      }
      os.write(operationType.getCmd());
      int read = is.read(lightInfo.receiver);
//      BigInteger logId = BigInteger.valueOf(-1);
      os.flush(); // 不知是否出错！！！！！
      if (read == lightInfo.receiver.length) {
        return saveLog(lightInfo, id);
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
      return null;
    } finally {
      // close
      try {
        if (socket != null) socket.close();
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    }
    return null;
  }

  /**
   * 保存数据
   *
   * @param info    数据信息
   * @param deviceId 灯控ID
   */
  private LightLog saveLog(LightInfo info, BigInteger deviceId) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.LIGHT_LOG);
    if (id.equals(BigInteger.valueOf(-1))) {
      logger.info("failed to get leaf id.");
      return null;
    }
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(deviceId, Rule.RuleDeviceType.LIGHT);
    LightLog log = new LightLog();
    log.setId(id);
    log.setDeviceId(deviceId);
    log.setTemperature(info.getTemp());
    log.setInVoltage(info.getInVol());
    log.setInCurrent(info.getInCur());
    log.setOutVoltage(info.getOutVol());
    log.setOutCurrent(info.getOutCur());
    log.setPower(info.getPower());
    log.setFactor(info.getFactor());
    log.setEnergy(info.getTotalEnergy());
    log.setBrightness(info.getBrightness());
    log.setOverTemperature(NumUtils.string2Boolean(info.getStatus()[0]));
    log.setLowTemperature(NumUtils.string2Boolean(info.getStatus()[1]));
    log.setUnStart(NumUtils.string2Boolean(info.getStatus()[2]));
    log.setShortCircuit(NumUtils.string2Boolean(info.getStatus()[3]));
    log.setOpenLoop(NumUtils.string2Boolean(info.getStatus()[4]));
    log.setOverPower(NumUtils.string2Boolean(info.getStatus()[5]));
    log.setOverVoltage(NumUtils.string2Boolean(info.getStatus()[6]));
    log.setUnderVoltage(NumUtils.string2Boolean(info.getStatus()[7]));
    log.setLongitude(hierarchy.getPole().getLongitude());
    log.setLatitude(hierarchy.getPole().getLatitude());
    log.setCreatedAt(new Date());

    return lightLogRepository.save(log);
  }



  /**
   * 数据处理
   *
   */
  private class LightInfo {
    private final byte[] receiver = new byte[21];
//    private byte temp;  // 电源内部温度 单位1摄氏度
//    private byte[] outVol;  // 电源输出电压 单位0.1V
//    private byte[] outCur;  // 电源输出电流 单位1mA
//    private byte[] inVol;   // 电源输入电压 单位0.1V
//    private byte[] inCur;   // 电源输入电流 单位1mA
//    private byte[] power;   // 电源有功功率 单位0.1W
//    private byte factor;  // 电源功率因数 单位1%
//    private byte[] status;  // 电源故障状态 7-OT 6-LT 5-US 4-SC 3-OL 2-OP 1-OV 0-UV (0正常 1异常)
//    private byte brightness;  // 亮度参数，0x00为0%亮度，0xC8为100%亮度
//    private byte[] totalEnergy; // 总能耗 单位0.1WH
//    private byte checksum;  // 校验和

    public int getTemp() {  // 温度 单位1℃
      return receiver[1] & 0xFF;
    }

    public float getOutVol() {  // 输出电压 单位0.1V
      byte[] outVol = new byte[2];
      outVol[0] = receiver[3];  // 高位
      outVol[1] = receiver[2];  // 低位
      return (float) (NumUtils.bytes2Long(outVol) / 10.0);
    }

    public float getOutCur() { // 输出电流 单位0.001A
      byte[] outCur = new byte[2];
      outCur[0] = receiver[5];
      outCur[1] = receiver[4];
      return (float) (NumUtils.bytes2Long(outCur) / 1000.0);
    }

    public float getInVol() {  // 输入电压 单位0.1V
      byte[] inVol = new byte[2];
      inVol[0] = receiver[7];
      inVol[1] = receiver[6];
      return (float) (NumUtils.bytes2Long(inVol) / 10.0);
    }

    public float getInCur() {  // 输入电流 单位0.001mA
      byte[] inCur = new byte[2];
      inCur[0] = receiver[9];
      inCur[1] = receiver[8];
      return (float) (NumUtils.bytes2Long(inCur) / 1000.0);
    }

    public float getPower() {  // 有功功率 0.1W
      byte[] power = new byte[2];
      power[0] = receiver[11];
      power[1] = receiver[10];
      return (float) (NumUtils.bytes2Long(power) / 10.0);
    }

    public float getFactor() {  // 功率因数
      return (float) ((receiver[12] & 0xFF) / 100.0);
    }

    public String[] getStatus() { // 电源故障状态 只看低8位 0-正常 1-异常
      byte status = receiver[13];
      return NumUtils.byte2Binary(status).split("");
    }

    public int getBrightness() {  // 亮度
      return receiver[15] & 0xFF;
    }

    public float getTotalEnergy() {  // 总能耗 单位0.1WH
      byte[] totalEnergy = new byte[4];
      totalEnergy[0] = receiver[20];
      totalEnergy[1] = receiver[19];
      totalEnergy[2] = receiver[18];
      totalEnergy[3] = receiver[17];
      return (float) (NumUtils.bytes2Long(totalEnergy) / 10.0);
    }

    public byte getChecksum() { // 校验和
      return receiver[21];
    }
  }

  /**
   * 操作类型
   *
   */
  private enum OperationTypes {
    TURN_OFF(0, new byte[]{0x17, 0x00, 0x17}),
    TURN_ON_25(1, new byte[] {0x17, 0x32, 0x49}),
    TURN_ON_50(2, new byte[] {0x17, 0x64, 0x7B}),
    TURN_ON_75(3, new byte[] {0x17, (byte) 0x96, (byte) 0xAD}),
    TURN_ON_100(4, new byte[] {0x17, (byte) 0xC8, (byte) 0xDF})
//    INIT485(5, new byte[] {0x1B, 0x67, 0x72, 0x1D, (byte) 0xC0, 0x6D, 0x6D, 0x3A, 0x2F, (byte) 0xB7, 0x06, 0x15, 0x01, 0x01, 0x11, 0x01})
    ;

    private final int code;
    private final byte[] cmd;

    OperationTypes(int code, byte[] cmd) {
      this.code = code;
      this.cmd = cmd;
    }

    public byte[] getCmd() {
      return cmd;
    }

    public int getCode() {
      return code;
    }

    public static OperationTypes getByCode(int code) {
      for (OperationTypes value : values()) {
        if (value.getCode() == code) {
          return value;
        }
      }
      return null;
    }
  }
}
