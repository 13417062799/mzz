package com.distlab.hqsms.charger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.distlab.hqsms.common.CustomTaskScheduler;
import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.net.OkHttpService;
import com.distlab.hqsms.edge.*;
import com.distlab.hqsms.strategy.*;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class ChargerService {

  private static final Logger logger = LoggerFactory.getLogger(ChargerService.class);
  private final MediaType mediaType = MediaType.parse("application/json;charset=utf-8");

  /********** API返回的字段名称 *********/
  private static final String QUERY_STATE = "query_devicezt_if";
  private static final String QUERY_ORDER = "query_charge_record_if";
  private static final String STATION_ID = "ZDBH";
  private static final String MERCHANT_CHARGER_ID = "CJBH";
  private static final String START_TIME = "KSRQ";
  private static final String END_TIME = "JSRQ";
  private static final String CHARGER_TYPE = "SBLX";
  private static final String STATE_DETAILS = "SbztDetails";
  private static final String ORDER_DETAILS = "ChargeDetails";

  @Autowired
  OkHttpService okHttpService;
  @Autowired
  ChargerRepository chargerRepository;
  @Autowired
  ChargerOrderRepository chargerOrderRepository;
  @Autowired
  ChargerLogRepository chargerLogRepository;
  @Autowired
  DeviceService deviceService;
  @Autowired
  CustomTaskScheduler scheduler;
  @Autowired
  StrategyService strategyService;
  @Autowired
  RabbitMQSender rabbitMQSender;

  @Value("${hqsms.edge.charger.enable}")
  private Boolean edgeChargerEnable;


  /**
   * 定时采集充电桩状态数据
   * 采集间隔：每小时的 0 5 10 15 20 25 30 35 40 45 50 55
   *
   */
  @Scheduled(cron = "0 0/10 * * * ?")
  public void stateSchedule() {
    if (!edgeChargerEnable) {
      logger.info(GlobalConstant.MSG_CHARGER_UNABLE);
      // 设备未启用，取消定时任务
      String className = Thread.currentThread().getStackTrace()[1].getClassName();
      String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
      scheduler.cancelTask(String.format("%s.%s", className, methodName));
      return;
    }
    startService(ServiceType.LOG);
  }

  /**
   * 定时采集充电桩订单数据
   * 采集间隔：每天0点采集一次
   *
   * @apiNote 未测试（设备无法产生订单）
   */
  @Scheduled(cron = "0 0 0 * * ?")
  public void orderSchedule() {
    if (!edgeChargerEnable) {
      logger.info(GlobalConstant.MSG_CHARGER_UNABLE);
      // 设备未启用，取消定时任务
      String className = Thread.currentThread().getStackTrace()[1].getClassName();
      String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
      scheduler.cancelTask(String.format("%s.%s", className, methodName));
      return;
    }
    startService(ServiceType.ORDER);
  }

  /**
   * 启动服务
   *
   */
  private void startService(ServiceType serviceType) {
    Iterable<Charger> chargers = chargerRepository.findAll();
    for (Charger charger : chargers) {
      DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(charger.getId(), Rule.RuleDeviceType.CHARGER);
      if (hierarchy == null) {
        logger.error(GlobalConstant.MSG_DEVICE_NOT_FOUND);
        return;
      }
      JSONArray details = receiveData(charger, serviceType);
      if (details == null) {
        logger.error("details not found");
        return;
      }
      Iterator<Object> iterator = details.stream().iterator();
      while (iterator.hasNext()) {
        String detail = iterator.next().toString();
        saveData(serviceType, charger, hierarchy, detail);
      }
    }
  }

  /**
   * 远程发送指令，采集数据
   *
   * @param charger     充电桩设备数据
   * @param serviceType 采集的数据类型：state、order
   * @return            包含多条状态数据的JSON数组
   */
  private JSONArray receiveData(Charger charger, ServiceType serviceType) {
    String urlPrefix = charger.getUrl();
    if (urlPrefix == null) {
      logger.error(GlobalConstant.MSG_RESULT_NOT_FOUND);
      return null;
    }
    String jChargerId = charger.getjChargerId();
    if (jChargerId == null) {
      logger.error(GlobalConstant.MSG_RESULT_NOT_FOUND);
      return null;
    }
    Integer stationId = charger.getStationId();
    if (stationId == null) {
      logger.error(GlobalConstant.MSG_RESULT_NOT_FOUND);
      return null;
    }
    JSONObject outBody = new JSONObject();
    switch (serviceType) {
      case LOG: {
        outBody.put(STATION_ID, stationId);
        outBody.put(MERCHANT_CHARGER_ID, jChargerId);
        RequestBody requestBody = RequestBody.create(mediaType, outBody.toJSONString());
        String url = String.format("%s/%s", urlPrefix, QUERY_STATE);
        String bodyInString = okHttpService.postRequestAndGetBody(url, requestBody);
        JSONObject body = JSON.parseObject(bodyInString);
        if (body == null) {
          logger.error(GlobalConstant.MSG_RESULT_NOT_FOUND);
          return null;
        }
        return body.getJSONArray(STATE_DETAILS);
      }
      case ORDER: {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, -1);
        String today = sdf.format(new Date());
        String yesterday = sdf.format(now.getTime());
        outBody.put(STATION_ID, stationId);
        outBody.put(MERCHANT_CHARGER_ID, jChargerId);
        outBody.put(START_TIME, yesterday);
        outBody.put(END_TIME, today);
        outBody.put(CHARGER_TYPE, 1);
        RequestBody requestBody = RequestBody.create(mediaType, outBody.toJSONString());
        String url = String.format("%s/%s", urlPrefix, QUERY_ORDER);
        String bodyInString = okHttpService.postRequestAndGetBody(url, requestBody);
        JSONObject body = JSON.parseObject(bodyInString);
        if (body == null) {
          logger.error(GlobalConstant.MSG_RESULT_NOT_FOUND);
          return null;
        }
        return body.getJSONArray(ORDER_DETAILS);
      }
    }
    return null;
  }

  /**
   * 保存数据
   *
   * @param serviceType 服务类型
   * @param charger     充电桩设备数据
   * @param hierarchy   层级数据
   * @param detail      采集的数据
   */
  private void saveData(ServiceType serviceType, Charger charger, DeviceHierarchy<Server, Pole, Device> hierarchy, String detail) {
    switch (serviceType) {
      case LOG: {
        ChargerLog log;
        try {
          log = JSON.parseObject(detail, ChargerLog.class);
        } catch (Exception e) {
          logger.error(GlobalConstant.MSG_PARSE_ERROR + e.getMessage());
          return;
        }
        BigInteger id = deviceService.getLeafId(DeviceService.LeafType.CHARGER_LOG);
        if (id.equals(BigInteger.valueOf(-1))) {
          return;
        }
        log.setId(id);
        log.setDeviceId(charger.getId());
        log.setLatitude(hierarchy.getPole().getLatitude());
        log.setLongitude(hierarchy.getPole().getLongitude());
        log.setCreatedAt(new Date());
        try {
          chargerLogRepository.save(log);
//          logger.debug("save charger state: " + id);

          // 匹配策略和事件上报
          StrategyParameter input = new StrategyParameter();
          input.setServerId(hierarchy.getPole().getServerId());
          input.setPoleId(hierarchy.getPole().getId());
          input.setDeviceType(Rule.RuleDeviceType.CHARGER);
          input.setDeviceId(hierarchy.getDevice().getId());
          input.setDeviceLogType(Rule.RuleDeviceLogType.RAW);
          input.setDeviceLogId(log.getId());
          input.setDeviceLogLongitude(hierarchy.getPole().getLongitude());
          input.setDeviceLogLatitude(hierarchy.getPole().getLatitude());
          Map<String, String> values = new HashMap<>();
          input.setValues(values);
          List<RuleEvent> events = strategyService.execute(input, new ArrayList<>());
          log.setRuleEvents(events);
          rabbitMQSender.sendChargerLog(input, log);
          break;
        } catch (Exception e) {
          logger.error(GlobalConstant.MSG_DATABASE_ERROR + e.getMessage());
          return;
        }
      }
      case ORDER: {
        ChargerOrder order;
        try {
          order = JSON.parseObject(detail, ChargerOrder.class);
        } catch (Exception e) {
          logger.error(GlobalConstant.MSG_PARSE_ERROR + e.getMessage());
          return;
        }
        BigInteger id = deviceService.getLeafId(DeviceService.LeafType.CHARGER_ORDER);
        if (id.equals(BigInteger.valueOf(-1))) {
          return;
        }
        order.setId(id);
        order.setDeviceId(charger.getId());
        order.setLatitude(hierarchy.getPole().getLatitude());
        order.setLongitude(hierarchy.getPole().getLongitude());
        order.setCreatedAt(new Date());
        try {
          chargerOrderRepository.save(order);
//          logger.debug("save charger order: " + id);

          // 匹配策略和事件上报
          StrategyParameter input = new StrategyParameter();
          input.setServerId(hierarchy.getPole().getServerId());
          input.setPoleId(hierarchy.getPole().getId());
          input.setDeviceType(Rule.RuleDeviceType.WIFI);
          input.setDeviceId(hierarchy.getDevice().getId());
          input.setDeviceLogType(Rule.RuleDeviceLogType.ORDER);
          input.setDeviceLogId(order.getId());
          input.setDeviceLogLongitude(hierarchy.getPole().getLongitude());
          input.setDeviceLogLatitude(hierarchy.getPole().getLatitude());
          Map<String, String> values = new HashMap<>();
          input.setValues(values);
          List<RuleEvent> events = strategyService.execute(input, new ArrayList<>());
          order.setRuleEvents(events);
          rabbitMQSender.sendChargerOrder(input, order);
          break;
        } catch (Exception e) {
          logger.error(GlobalConstant.MSG_DATABASE_ERROR + e.getMessage());
        }
      }
    }
  }


  /******* 枚举服务类型 *******/
  public enum ServiceType {
    LOG("1"),
    ORDER("2");

    private final String code;

    ServiceType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }
}
