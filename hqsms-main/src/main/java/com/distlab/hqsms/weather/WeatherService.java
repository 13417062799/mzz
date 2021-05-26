package com.distlab.hqsms.weather;

import com.distlab.hqsms.common.CustomTaskScheduler;
import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.edge.*;
import com.distlab.hqsms.strategy.*;
import com.distlab.hqsms.edge.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;

@Service
@EnableScheduling
public class WeatherService {
  private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
  @Autowired
  private WeatherLogRepository weatherLogRepository;
  @Autowired
  private DeviceService deviceService;
  @Autowired
  private StrategyService strategyService;
  @Autowired
  private RabbitMQSender rabbitMQSender;
  @Autowired
  private WeatherRepository weatherRepository;
  @Autowired
  CustomTaskScheduler scheduler;

  @Value("${hqsms.edge.weather.enable}")
  private Boolean edgeWeatherEnable;

  /*
   * 获取并处理数据
   * */
  private int[] getData(Socket socket) {
    byte[] senCmd = new byte[]{0x01, 0x03, 0x00, 0x00, 0x00, 0x0a, (byte) 0xc5, (byte) 0xcd}; // 设定获取命令
    byte[] temp1 = new byte[18];

    try {
      InputStream is = socket.getInputStream();
      OutputStream os = socket.getOutputStream();
      os.write(senCmd); // 写入命令
      os.flush(); // 强制发送缓冲区数据，刷新

      byte[] rowData = new byte[21];  // 临时存储
      is.read(rowData); // 获取返回的原生数据存入rowData
      temp1 = Arrays.copyOfRange(rowData, 3, 21);

    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    //负数转正
    int[] temp2 = new int[18];
    for (int i = 0; i < temp1.length; i++) {
      int num = temp1[i];
      num = (num < 0 ? 256 + num : num);
      temp2[i] = num;
    }

    //两字节数据合并
    int[] data = new int[9];
    for (int i = 0, j = 0; i < temp2.length; i += 2) {
      data[j] = (temp2[i] << 8) + temp2[i + 1];
      j++;
    }

    return data;
  }

  /*
   * 保存数据
   * */
  private int saveData(BigInteger deviceId, int[] data) {
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(deviceId, Rule.RuleDeviceType.WEATHER);
    if (hierarchy == null) {
      return -1;
    }

    // 获取分布式ID
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.WEATHER_LOG);
    if (id.equals(BigInteger.valueOf(-1))) {
      return -1;
    }
    // 保存数据
    WeatherLog log = new WeatherLog();
    log.setId(id);
    log.setDeviceId(deviceId);
    log.setLongitude(hierarchy.getPole().getLongitude());
    log.setLatitude(hierarchy.getPole().getLatitude());
    log.setDn(data[0]);
    log.setDm(data[1]);
    log.setDx(data[2]);
    log.setSn((float) data[3]);
    log.setSm((float) data[4]);
    log.setSx((float) data[5]);
    log.setTa((float) data[6] / 10);
    log.setUa((float) data[7] / 10);
    log.setPa((float) data[8] / 100);
    log.setCreatedAt(new Date());
    try {
      weatherLogRepository.save(log);
//      logger.info("weather schedule task: " + log.getId());
    } catch (Exception e) {
      logger.info("weather save data failed: " + deviceId + ", "+ e.getMessage());
      return -1;
    }

    // 匹配策略和事件上报
    StrategyParameter input = new StrategyParameter();
    input.setServerId(hierarchy.getPole().getServerId());
    input.setPoleId(hierarchy.getPole().getId());
    input.setDeviceType(Rule.RuleDeviceType.WEATHER);
    input.setDeviceId(hierarchy.getDevice().getId());
    input.setDeviceLogType(Rule.RuleDeviceLogType.RAW);
    input.setDeviceLogId(log.getId());
    input.setDeviceLogLongitude(log.getLongitude());
    input.setDeviceLogLatitude(log.getLatitude());
    Map<String, String> values = new HashMap<>();
    input.setValues(values);
    List<RuleEvent> events = strategyService.execute(input, new ArrayList<>());
    log.setRuleEvents(events);
    rabbitMQSender.sendWeatherLog(input, log);

    return 0;
  }
  @Scheduled(cron = "0 0/30 * * * ?")
  private void startService() {
    if (!edgeWeatherEnable) {
      logger.info(GlobalConstant.MSG_WEATHER_UNABLE);
      // 设备未启用，取消定时任务
      String className = Thread.currentThread().getStackTrace()[1].getClassName();
      String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
      scheduler.cancelTask(String.format("%s.%s", className, methodName));
      return;
    }
    Iterable<Weather> weathers = weatherRepository.findAll();
    for (Weather weather : weathers) {
      Socket socket = new Socket();
      String ip = weather.getIp();
      if (ip == null) {
        logger.info("weather ip not found: " + weather.getId());
        continue;
      }
      Integer port = weather.getPort();
      if (port == null) {
        logger.info("weather port not found: " + weather.getId());
        continue;
      }
      SocketAddress address = new InetSocketAddress(ip, port);
      try {
        socket.connect(address, 30000);
      } catch (IOException e) {
        logger.warn("weather socket connect failed: " + weather.getId() + ", "+ e.getMessage());
        continue;
      }
      int[] data = getData(socket);
      if (data == null) {
        logger.warn("weather get data failed: " + weather.getId());
        continue;
      }
      if (socket.isConnected()) {
        try {
          socket.close();
        } catch (IOException e) {
          logger.warn("weather socket close failed: " + weather.getId() + ", "+ e.getMessage());
        }
      }
      int flag = saveData(weather.getId(), data);
      if (flag == -1) {
        logger.warn("weather schedule task failed: " + weather.getId());
      }
    }
  }

}
