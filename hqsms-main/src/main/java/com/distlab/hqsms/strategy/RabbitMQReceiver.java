package com.distlab.hqsms.strategy;


import com.distlab.hqsms.alarm.AlarmLog;
import com.distlab.hqsms.alarm.AlarmLogRepository;
import com.distlab.hqsms.camera.*;
import com.distlab.hqsms.camera.CameraHVService;
import com.distlab.hqsms.charger.ChargerLog;
import com.distlab.hqsms.charger.ChargerLogRepository;
import com.distlab.hqsms.charger.ChargerOrder;
import com.distlab.hqsms.charger.ChargerOrderRepository;
import com.distlab.hqsms.edge.*;
import com.distlab.hqsms.cloud.MediaFileService;
import com.distlab.hqsms.edge.Server;
import com.distlab.hqsms.meter.MeterLog;
import com.distlab.hqsms.meter.MeterLogRepository;
import com.distlab.hqsms.weather.WeatherLog;
import com.distlab.hqsms.weather.WeatherLogRepository;
import com.distlab.hqsms.wifi.WifiLog;
import com.distlab.hqsms.wifi.WifiLogRepository;
import com.sankuai.inf.leaf.service.SegmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RabbitMQReceiver {
  private static final Logger logger = LoggerFactory.getLogger(RabbitMQReceiver.class);
  @Autowired
  PoleRepository poleRepository;
  @Autowired
  CameraRepository cameraRepository;
  @Autowired
  CameraHumanRepository cameraHumanRepository;
  @Autowired
  CameraVehicleRepository cameraVehicleRepository;
  @Autowired
  CameraLogRepository cameraLogRepository;
  @Autowired
  RuleRepository ruleRepository;
  @Autowired
  RuleEventRepository ruleEventRepository;
  @Autowired
  StrategyService strategyService;
  @Autowired
  SegmentService segmentService;
  @Autowired
  CameraHVService cameraService;
  @Autowired
  MediaFileService mediaFileService;
  @Autowired
  DeviceService deviceService;
  @Autowired
  RestTemplate restTemplate;
  @Autowired
  WeatherLogRepository weatherLogRepository;
  @Autowired
  AlarmLogRepository alarmLogRepository;
  @Autowired
  ChargerLogRepository chargerLogRepository;
  @Autowired
  ChargerOrderRepository chargerOrderRepository;
  @Autowired
  MeterLogRepository meterLogRepository;
  @Autowired
  WifiLogRepository wifiLogRepository;
  @Value("${hqsms.cloud.server.enable}")
  boolean isCloudServerEnable;
  @Autowired
  RabbitTemplate rabbitTemplate;

  public String getRoutingKey(StrategyParameter param, RuleEvent event) {
    if (!isCloudServerEnable) {
      return null;
    }
    return String.format(
      "cloud.%d.%d.%s.%d.%s.%d.%d.%d",
      param.getServerId(),
      param.getPoleId(),
      param.getDeviceType().getCode(),
      param.getDeviceId(),
      param.getDeviceLogType().getCode(),
      event.getRuleId(),
      param.getDeviceLogId(),
      event.getId()
    );
  }

  public void publish(StrategyParameter param, List<RuleEvent> events) {
    events.forEach(event -> {
      String routingKey = getRoutingKey(param, event);
      if (StringUtils.isEmpty(routingKey)) {
        return;
      }
      rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_RULE_EVENT, routingKey, event);
    });
  }

  public StrategyParameter prepare(DeviceHierarchy<Server, Pole, Device> hierarchy, Rule.RuleDeviceLogType logType) {
    StrategyParameter param = new StrategyParameter();
    param.setServerId(hierarchy.getServer().getId());
    param.setPoleId(hierarchy.getPole().getId());
    param.setDeviceType(hierarchy.getDeviceType());
    param.setDeviceId(hierarchy.getDevice().getId());
    param.setDeviceLogType(logType);
    param.setDeviceLogId(hierarchy.getDevice().getId());
    param.setDeviceLogLongitude(hierarchy.getPole().getLongitude());
    param.setDeviceLogLatitude(hierarchy.getPole().getLatitude());

    return param;
  }

  @RabbitListener(queues = {RabbitMQConfig.QUEUE_WEATHER_LOG}, id = "weather_log", autoStartup = "${hqsms.cloud.server.enable}")
  public void receiveWeatherLog(WeatherLog log, Message message) {
    logger.debug("receive weather log: " + log.getId());
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(log.getDeviceId(), Rule.RuleDeviceType.WEATHER);
    if (hierarchy == null) {
      return;
    }

    // 保存设备日志
    weatherLogRepository.save(log);
//    // 匹配策略
//    StrategyParameter param = prepare(hierarchy, Rule.RuleDeviceLogType.RAW);
//    Map<String, String> values = new HashMap<>();
//    param.setValues(values);
//    List<RuleEvent> events = strategyService.execute(param, log.getRuleEvents());
//    // 广播策略事件
//    publish(param, events);
  }

  @RabbitListener(queues = {RabbitMQConfig.QUEUE_ALARM_LOG}, id = "alarm_log", autoStartup = "${hqsms.cloud.server.enable}")
  public void receiveAlarmLog(AlarmLog log, Message message) {
    logger.debug("receive alarm log: " + log.getId());
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(log.getDeviceId(), Rule.RuleDeviceType.ALARM);
    if (hierarchy == null) {
      return;
    }

    // 保存设备日志
    alarmLogRepository.save(log);
//    // 匹配策略
//    StrategyParameter param = prepare(hierarchy, Rule.RuleDeviceLogType.RAW);
//    Map<String, String> values = new HashMap<>();
//    param.setValues(values);
//    List<RuleEvent> events = strategyService.execute(param, log.getRuleEvents());
//    // 广播策略事件
//    publish(param, events);
  }

  @RabbitListener(queues = {RabbitMQConfig.QUEUE_CHARGER_LOG}, id = "charger_log", autoStartup = "${hqsms.cloud.server.enable}")
  public void receiveChargerLog(ChargerLog log, Message message) {
    logger.debug("receive charger log: " + log.getId());
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(log.getDeviceId(), Rule.RuleDeviceType.CHARGER);
    if (hierarchy == null) {
      return;
    }

    // 保存设备日志
    chargerLogRepository.save(log);
//    // 匹配策略
//    StrategyParameter param = prepare(hierarchy, Rule.RuleDeviceLogType.RAW);
//    Map<String, String> values = new HashMap<>();
//    param.setValues(values);
//    List<RuleEvent> events = strategyService.execute(param, log.getRuleEvents());
//    // 广播策略事件
//    publish(param, events);
  }

  @RabbitListener(queues = {RabbitMQConfig.QUEUE_CHARGER_ORDER}, id = "charger_order", autoStartup = "${hqsms.cloud.server.enable}")
  public void receiveChargerOrder(ChargerOrder order, Message message) {
    logger.debug("receive charger order: " + order.getId());
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(order.getDeviceId(), Rule.RuleDeviceType.CHARGER);
    if (hierarchy == null) {
      return;
    }

    // 保存设备日志
    chargerOrderRepository.save(order);
//    // 匹配策略
//    StrategyParameter param = prepare(hierarchy, Rule.RuleDeviceLogType.ORDER);
//    Map<String, String> values = new HashMap<>();
//    param.setValues(values);
//    List<RuleEvent> events = strategyService.execute(param, order.getRuleEvents());
//    // 广播策略事件
//    publish(param, events);
  }

  @RabbitListener(queues = {RabbitMQConfig.QUEUE_METER_LOG}, id = "meter_log", autoStartup = "${hqsms.cloud.server.enable}")
  public void receiveMeterLog(MeterLog log, Message message) {
    logger.debug("receive meter log: " + log.getId());
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(log.getDeviceId(), Rule.RuleDeviceType.METER);
    if (hierarchy == null) {
      return;
    }

    // 保存设备日志
    meterLogRepository.save(log);
//    // 匹配策略
//    StrategyParameter param = prepare(hierarchy, Rule.RuleDeviceLogType.RAW);
//    Map<String, String> values = new HashMap<>();
//    param.setValues(values);
//    List<RuleEvent> events = strategyService.execute(param, log.getRuleEvents());
//    // 广播策略事件
//    publish(param, events);
  }

  @RabbitListener(queues = {RabbitMQConfig.QUEUE_WIFI_LOG}, id = "wifi_log", autoStartup = "${hqsms.cloud.server.enable}")
  public void receiveWifiLog(WifiLog log, Message message) {
    logger.debug("receive wifi log: " + log.getId());
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(log.getDeviceId(), Rule.RuleDeviceType.WIFI);
    if (hierarchy == null) {
      return;
    }

    // 保存设备日志
    wifiLogRepository.save(log);
//    // 匹配策略
//    StrategyParameter param = prepare(hierarchy, Rule.RuleDeviceLogType.RAW);
//    Map<String, String> values = new HashMap<>();
//    param.setValues(values);
//    List<RuleEvent> events = strategyService.execute(param, log.getRuleEvents());
//    // 广播策略事件
//    publish(param, events);
  }

  @RabbitListener(queues = {RabbitMQConfig.QUEUE_CAMERA_LOG}, id = "camera_log", autoStartup = "${hqsms.cloud.server.enable}")
  public void receiveCameraLog(CameraLog log, Message message) {
    logger.debug("receive camera log: " + log.getId());
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(log.getDeviceId(), Rule.RuleDeviceType.CAMERA);
    if (hierarchy == null) {
      return;
    }

    // 保存设备日志
    cameraLogRepository.save(log);
  }

  @RabbitListener(queues = {RabbitMQConfig.QUEUE_CAMERA_VEHICLE}, id = "camera_vehicle", autoStartup = "${hqsms.cloud.server.enable}")
  public void receiveCameraVehicle(CameraVehicle vehicle, Message message) {
    logger.debug("receive camera vehicle: " + vehicle.getId());
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(vehicle.getDeviceId(), Rule.RuleDeviceType.CAMERA);
    if (hierarchy == null) {
      return;
    }

    // 保存设备日志
    cameraVehicleRepository.save(vehicle);
    // 匹配策略
    StrategyParameter param = prepare(hierarchy, Rule.RuleDeviceLogType.VEHICLE);
    Map<String, String> values = new HashMap<>();
    // VEHICLE_PLATE_SCORE_IN_RANGE
    values.put("plateEntireScore", vehicle.getPlateEntireScore().toString());
    // VEHICLE_PLATE_MATCH_BY_REGEXP
    values.put("plateChars", vehicle.getPlateChars());
    param.setValues(values);
    List<RuleEvent> events = strategyService.execute(param, vehicle.getRuleEvents());
    // 广播策略事件
    publish(param, events);
  }

  @RabbitListener(queues = {RabbitMQConfig.QUEUE_CAMERA_HUMAN}, id = "camera_human", autoStartup = "${hqsms.cloud.server.enable}")
  public void receiveCameraHuman(CameraHuman human, Message message) {
    logger.debug("receive camera human: " + human.getId());
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(human.getDeviceId(), Rule.RuleDeviceType.CAMERA);
    if (hierarchy == null) {
      return;
    }

    // 保存设备日志
    cameraHumanRepository.save(human);
    // 匹配策略
    StrategyParameter param = prepare(hierarchy, Rule.RuleDeviceLogType.HUMAN);
    Map<String, String> values = new HashMap<>();
    // HUMAN_FACE_SCORE_IN_RANGE
    values.put("faceScore", human.getFaceScore().toString());
    // HUMAN_FACE_MATCH_IN_RANGE
    values.put("faceCode", human.getFaceCode());
    param.setValues(values);
    List<RuleEvent> events = strategyService.execute(param, human.getRuleEvents());
    // 广播策略事件
    publish(param, events);
  }

}
