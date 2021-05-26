package com.distlab.hqsms.strategy;

import com.distlab.hqsms.alarm.AlarmLog;
import com.distlab.hqsms.camera.CameraHuman;
import com.distlab.hqsms.camera.CameraLog;
import com.distlab.hqsms.camera.CameraVehicle;
import com.distlab.hqsms.charger.ChargerLog;
import com.distlab.hqsms.charger.ChargerOrder;
import com.distlab.hqsms.meter.MeterLog;
import com.distlab.hqsms.weather.WeatherLog;
import com.distlab.hqsms.wifi.WifiLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class RabbitMQSender {
  private static final Logger logger = LoggerFactory.getLogger(RabbitMQSender.class);
  @Autowired
  RabbitTemplate rabbitTemplate;
  @Value("${hqsms.edge.server.enable}")
  boolean isEdgeServerEnable;

  public String getRoutingKey(StrategyParameter input) {
    if (!isEdgeServerEnable) {
      return null;
    }
    return String.format(
      "edge.%d.%d.%s.%d.%s.%d",
      input.getServerId(),
      input.getPoleId(),
      input.getDeviceType().getCode(),
      input.getDeviceId(),
      input.getDeviceLogType().getCode(),
      input.getDeviceLogId()
    );
  }

  public void sendWeatherLog(StrategyParameter input, WeatherLog message) {
    String routingKey = getRoutingKey(input);
    if (StringUtils.isEmpty(routingKey)) {
      return;
    }
    logger.debug("send weather log: " + routingKey);
    // 通过MQ分发消息 @param1交换机 @param2路由key/queue队列名称 @param3消息内容
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DEVICE_LOG, routingKey, message);
  }

  public void sendAlarmLog(StrategyParameter input, AlarmLog message) {
    String routingKey = getRoutingKey(input);
    if (StringUtils.isEmpty(routingKey)) {
      return;
    }
    logger.debug("send alarm log: " + routingKey);
    // 通过MQ分发消息 @param1交换机 @param2路由key/queue队列名称 @param3消息内容
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DEVICE_LOG, routingKey, message);
  }

  public void sendChargerLog(StrategyParameter input, ChargerLog message) {
    String routingKey = getRoutingKey(input);
    if (StringUtils.isEmpty(routingKey)) {
      return;
    }
    logger.debug("send charger log: " + routingKey);
    // 通过MQ分发消息 @param1交换机 @param2路由key/queue队列名称 @param3消息内容
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DEVICE_LOG, routingKey, message);
  }

  public void sendChargerOrder(StrategyParameter input, ChargerOrder message) {
    String routingKey = getRoutingKey(input);
    if (StringUtils.isEmpty(routingKey)) {
      return;
    }
    logger.debug("send charger order: " + routingKey);
    // 通过MQ分发消息 @param1交换机 @param2路由key/queue队列名称 @param3消息内容
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DEVICE_LOG, routingKey, message);
  }

  public void sendMeterLog(StrategyParameter input, MeterLog message) {
    String routingKey = getRoutingKey(input);
    if (StringUtils.isEmpty(routingKey)) {
      return;
    }
    logger.debug("send meter log: " + routingKey);
    // 通过MQ分发消息 @param1交换机 @param2路由key/queue队列名称 @param3消息内容
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DEVICE_LOG, routingKey, message);
  }

  public void sendWifiLog(StrategyParameter input, WifiLog message) {
    String routingKey = getRoutingKey(input);
    if (StringUtils.isEmpty(routingKey)) {
      return;
    }
    logger.debug("send wifi log: " + routingKey);
    // 通过MQ分发消息 @param1交换机 @param2路由key/queue队列名称 @param3消息内容
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DEVICE_LOG, routingKey, message);
  }

  public void sendCameraLog(StrategyParameter input, CameraLog message) {
    String routingKey = getRoutingKey(input);
    if (StringUtils.isEmpty(routingKey)) {
      return;
    }
    logger.debug("send camera log: " + routingKey);
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DEVICE_LOG, routingKey, message);
  }

  public void sendCameraHuman(StrategyParameter input, CameraHuman message) {
    String routingKey = getRoutingKey(input);
    if (StringUtils.isEmpty(routingKey)) {
      return;
    }
    logger.debug("send camera human: " + routingKey);
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DEVICE_LOG, routingKey, message);
  }

  public void sendCameraVehicle(StrategyParameter input, CameraVehicle message) {
    String routingKey = getRoutingKey(input);
    if (StringUtils.isEmpty(routingKey)) {
      return;
    }
    logger.debug("send camera vehicle: " + routingKey);
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DEVICE_LOG, routingKey, message);
  }

}
