package com.distlab.hqsms.strategy;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
  // 声明策略事件上报的交换器和队列, 队列路由格式 cloud.{serverId}.{poleId}.{deviceCode}.{deviceId}.{logCode}.{ruleId}.{logId}.{ruleEventId}
  public static final String EXCHANGE_RULE_EVENT = "exchange_rule_event";
  public static final String QUEUE_RULE_EVENT = "queue_rule_event";
  public static final String ROUTING_KEY_RULE_EVENT = String.format("cloud.*.*.*.*.*.*.*.*");

  // 声明数据上报的交换器和队列, 队列路由格式 edge.{serverId}.{poleId}.{deviceCode}.{deviceId}.{logCode}.{logId}
  public static final String EXCHANGE_DEVICE_LOG = "exchange_device_log";
  public static final String QUEUE_WEATHER_LOG = "queue_weather_log";
  public static final String QUEUE_ALARM_LOG = "queue_alarm_log";
  public static final String QUEUE_CHARGER_LOG = "queue_charger_log";
  public static final String QUEUE_CHARGER_ORDER = "queue_charger_order";
  public static final String QUEUE_METER_LOG = "queue_meter_log";
  public static final String QUEUE_WIFI_LOG = "queue_wifi_log";
  public static final String QUEUE_CAMERA_LOG = "queue_camera_log";
  public static final String QUEUE_CAMERA_HUMAN = "queue_camera_human";
  public static final String QUEUE_CAMERA_VEHICLE = "queue_camera_vehicle";
  public static final String ROUTING_KEY_WEATHER_LOG = String.format("edge.*.*.%s.*.%s.*", Rule.RuleDeviceType.WEATHER.getCode(), Rule.RuleDeviceLogType.RAW.getCode());
  public static final String ROUTING_KEY_ALARM_LOG = String.format("edge.*.*.%s.*.%s.*", Rule.RuleDeviceType.ALARM.getCode(), Rule.RuleDeviceLogType.RAW.getCode());
  public static final String ROUTING_KEY_CHARGER_LOG = String.format("edge.*.*.%s.*.%s.*", Rule.RuleDeviceType.CHARGER.getCode(), Rule.RuleDeviceLogType.RAW.getCode());
  public static final String ROUTING_KEY_CHARGER_ORDER = String.format("edge.*.*.%s.*.%s.*", Rule.RuleDeviceType.CHARGER.getCode(), Rule.RuleDeviceLogType.ORDER.getCode());
  public static final String ROUTING_KEY_METER_LOG = String.format("edge.*.*.%s.*.%s.*", Rule.RuleDeviceType.METER.getCode(), Rule.RuleDeviceLogType.RAW.getCode());
  public static final String ROUTING_KEY_WIFI_LOG = String.format("edge.*.*.%s.*.%s.*", Rule.RuleDeviceType.WIFI.getCode(), Rule.RuleDeviceLogType.RAW.getCode());
  public static final String ROUTING_KEY_CAMERA_LOG = String.format("edge.*.*.%s.*.%s.*", Rule.RuleDeviceType.CAMERA.getCode(), Rule.RuleDeviceLogType.RAW.getCode());
  public static final String ROUTING_KEY_CAMERA_HUMAN = String.format("edge.*.*.%s.*.%s.*", Rule.RuleDeviceType.CAMERA.getCode(), Rule.RuleDeviceLogType.HUMAN.getCode());
  public static final String ROUTING_KEY_CAMERA_VEHICLE = String.format("edge.*.*.%s.*.%s.*", Rule.RuleDeviceType.CAMERA.getCode(), Rule.RuleDeviceLogType.VEHICLE.getCode());

  // 策略事件消息队列
  @Bean(QUEUE_RULE_EVENT)
  Queue QUEUE_RULE_EVENT() {
    return new Queue(QUEUE_RULE_EVENT, false);
  }

  // 策略事件交换器
  @Bean(EXCHANGE_RULE_EVENT)
  TopicExchange EXCHANGE_RULE_EVENT() {
    return new TopicExchange(QUEUE_RULE_EVENT);
  }

  // 绑定策略事件消息队列到策略事件交换器
  @Bean
  Binding BINDING_QUEUE_RULE_EVENT(
    @Qualifier(QUEUE_RULE_EVENT) Queue queue,
    @Qualifier(EXCHANGE_RULE_EVENT) TopicExchange exchange
  ) {
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_RULE_EVENT);
  }

  // 气象数据消息队列
  @Bean(QUEUE_WEATHER_LOG)
  Queue QUEUE_WEATHER_LOG() {
    return new Queue(QUEUE_WEATHER_LOG, false);
  }

  // 报警器数据消息队列
  @Bean(QUEUE_ALARM_LOG)
  Queue QUEUE_ALARM_LOG() {
    return new Queue(QUEUE_ALARM_LOG, false);
  }

  // 充电桩数据消息队列
  @Bean(QUEUE_CHARGER_LOG)
  Queue QUEUE_CHARGER_LOG() {
    return new Queue(QUEUE_CHARGER_LOG, false);
  }

  // 充电桩订单数据消息队列
  @Bean(QUEUE_CHARGER_ORDER)
  Queue QUEUE_CHARGER_ORDER() {
    return new Queue(QUEUE_CHARGER_ORDER, false);
  }

  // 电能表数据消息队列
  @Bean(QUEUE_METER_LOG)
  Queue QUEUE_METER_LOG() {
    return new Queue(QUEUE_METER_LOG, false);
  }

  // 无线网络数据消息队列
  @Bean(QUEUE_WIFI_LOG)
  Queue QUEUE_WIFI_LOG() {
    return new Queue(QUEUE_WIFI_LOG, false);
  }

  // 录像文件消息队列
  @Bean(QUEUE_CAMERA_LOG)
  Queue QUEUE_CAMERA_LOG() {
    return new Queue(QUEUE_CAMERA_LOG, false);
  }

  // 人员数据消息队列
  @Bean(QUEUE_CAMERA_HUMAN)
  Queue QUEUE_CAMERA_HUMAN() {
    return new Queue(QUEUE_CAMERA_HUMAN, false);
  }

  // 车辆数据消息队列
  @Bean(QUEUE_CAMERA_VEHICLE)
  Queue QUEUE_CAMERA_VEHICLE() {
    return new Queue(QUEUE_CAMERA_VEHICLE, false);
  }

  // 设备数据交换器
  @Bean(EXCHANGE_DEVICE_LOG)
  TopicExchange EXCHANGE_DEVICE_LOG() {
    return new TopicExchange(EXCHANGE_DEVICE_LOG);
  }

  // 绑定气象数据消息队列到设备数据交换器
  @Bean
  Binding BINDING_QUEUE_WEATHER_LOG(
    @Qualifier(QUEUE_WEATHER_LOG) Queue queue,
    @Qualifier(EXCHANGE_DEVICE_LOG) TopicExchange exchange
  ) {
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_WEATHER_LOG);
  }

  // 绑定报警器数据消息队列到设备数据交换器
  @Bean
  Binding BINDING_QUEUE_ALARM_LOG(
    @Qualifier(QUEUE_ALARM_LOG) Queue queue,
    @Qualifier(EXCHANGE_DEVICE_LOG) TopicExchange exchange
  ) {
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_ALARM_LOG);
  }

  // 绑定充电桩数据消息队列到设备数据交换器
  @Bean
  Binding BINDING_QUEUE_CHARGER_LOG(
    @Qualifier(QUEUE_CHARGER_LOG) Queue queue,
    @Qualifier(EXCHANGE_DEVICE_LOG) TopicExchange exchange
  ) {
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_CHARGER_LOG);
  }

  // 绑定充电桩订单数据消息队列到设备数据交换器
  @Bean
  Binding BINDING_QUEUE_CHARGER_ORDER(
    @Qualifier(QUEUE_CHARGER_ORDER) Queue queue,
    @Qualifier(EXCHANGE_DEVICE_LOG) TopicExchange exchange
  ) {
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_CHARGER_ORDER);
  }

  // 绑定电能表数据消息队列到设备数据交换器
  @Bean
  Binding BINDING_QUEUE_METER_LOG(
    @Qualifier(QUEUE_METER_LOG) Queue queue,
    @Qualifier(EXCHANGE_DEVICE_LOG) TopicExchange exchange
  ) {
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_METER_LOG);
  }

  // 绑定无线网络数据消息队列到设备数据交换器
  @Bean
  Binding BINDING_QUEUE_WIFI_LOG(
    @Qualifier(QUEUE_WIFI_LOG) Queue queue,
    @Qualifier(EXCHANGE_DEVICE_LOG) TopicExchange exchange
  ) {
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_WIFI_LOG);
  }

  // 绑定录像文件消息队列到设备数据交换器
  @Bean
  Binding BINDING_QUEUE_CAMERA_LOG(
    @Qualifier(QUEUE_CAMERA_LOG) Queue queue,
    @Qualifier(EXCHANGE_DEVICE_LOG) TopicExchange exchange
  ) {
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_CAMERA_LOG);
  }

  // 绑定人员数据消息队列到设备数据交换器
  @Bean
  Binding BINDING_QUEUE_CAMERA_HUMAN(
    @Qualifier(QUEUE_CAMERA_HUMAN) Queue queue,
    @Qualifier(EXCHANGE_DEVICE_LOG) TopicExchange exchange
  ) {
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_CAMERA_HUMAN);
  }

  // 绑定车辆数据消息队列到设备数据交换器
  @Bean
  Binding BINDING_QUEUE_CAMERA_VEHICLE(
    @Qualifier(QUEUE_CAMERA_VEHICLE) Queue queue,
    @Qualifier(EXCHANGE_DEVICE_LOG) TopicExchange exchange
  ) {
    return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_CAMERA_VEHICLE);
  }

  @Bean
  Jackson2JsonMessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }
}
