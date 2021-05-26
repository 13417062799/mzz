package com.distlab.hqsms.wifi;

import com.distlab.hqsms.common.CustomTaskScheduler;
import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.edge.*;
import com.distlab.hqsms.common.net.SnmpUtil;
import com.distlab.hqsms.strategy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@EnableScheduling
public class WifiService {

  private static final Logger logger = LoggerFactory.getLogger(WifiService.class);

  @Value("${hqsms.edge.wifi.enable}")
  private Boolean edgeWifiEnable;
  @Value("${hqsms.edge.wifi.ac.ruijie.snmp.community}")
  private String acCommunity;
  @Value("${hqsms.edge.wifi.ac.ruijie.snmp.get.host}")
  private String acGetHost;
  @Value("${hqsms.edge.wifi.ac.ruijie.snmp.trap.host}")
  private String acTrapHost;
  @Value("${hqsms.edge.wifi.ac.ruijie.snmp.get.port}")
  private int acGetPort;
  @Value("${hqsms.edge.wifi.ac.ruijie.snmp.trap.port}")
  private int acTrapPort;

  @Autowired
  WifiAPRepository wifiAPRepository;
  @Autowired
  WifiLogRepository wifiLogRepository;
  @Autowired
  DeviceService deviceService;
  @Autowired
  CustomTaskScheduler scheduler;
  @Autowired
  StrategyService strategyService;
  @Autowired
  RabbitMQSender rabbitMQSender;

  /**
   * 启动服务，开启SNMP监听，记录无线用户上下线
   *
   */
  @PostConstruct
  public void startService() {
    if (!edgeWifiEnable) {
      logger.info(GlobalConstant.MSG_WIFI_UNABLE);
      return;
    }
    // 获取trap参数
    if (acTrapHost == null) {
      logger.error("host for trap not found");
      return;
    }
    if (acTrapPort == 0) {
      logger.error("port for trap not found");
      return;
    }
    // 开启snmp trap监听
    SnmpUtil snmpUtil = new SnmpUtil();
    String addressTrap = String.format("%s/%d", acTrapHost, acTrapPort);
    snmpUtil.setAddressTrap(addressTrap);
    try {
      snmpUtil.initSnmpTrap();
      TrapReceiver trapReceiver = new TrapReceiver();
      snmpUtil.snmp.addCommandResponder(trapReceiver);
      logger.debug("edge ac trap start listening...");
    } catch (IOException e) {
      logger.error("failed to listen trap: " + e.getMessage());
    }
  }

  /**
   * 定时更新数据，包括：
   *  上行速率、下行速率
   *  总上行流量、总下行流量
   *  用户名（可能变更）
   *  客户端类型及用户上线时间（若上线时更新失败）
   *
   */
  @Scheduled(cron = "0 0/1 * * * ?")
  private void updateGeneralData() {
    if (!edgeWifiEnable) {
      logger.info(GlobalConstant.MSG_WIFI_UNABLE);
      // 设备未启用，取消定时任务
      String className = Thread.currentThread().getStackTrace()[1].getClassName();
      String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
      scheduler.cancelTask(String.format("%s.%s", className, methodName));
      return;
    }
    // 定时更新在线用户数据
    List<WifiLog> onlineUsers = wifiLogRepository.findByOffType(1);
    Map<String, List<VariableBinding>> dataMap = getDataMap();
    for (WifiLog onlineUser : onlineUsers) {
      transferOtherDataAndSave(dataMap, onlineUser);
      wifiLogRepository.save(onlineUser);

      uploadStrategy(onlineUser);
    }
//    logger.debug("update online user");
  }

  /**
   * 匹配策略和事件上报
   *
   * @param log 设备数据
   */
  private void uploadStrategy(WifiLog log) {
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(log.getDeviceId(), Rule.RuleDeviceType.WIFI);
    StrategyParameter input = new StrategyParameter();
    input.setServerId(hierarchy.getPole().getServerId());
    input.setPoleId(hierarchy.getPole().getId());
    input.setDeviceType(Rule.RuleDeviceType.WIFI);
    input.setDeviceId(hierarchy.getDevice().getId());
    input.setDeviceLogType(Rule.RuleDeviceLogType.RAW);
    input.setDeviceLogId(log.getId());
    input.setDeviceLogLongitude(hierarchy.getPole().getLongitude());
    input.setDeviceLogLatitude(hierarchy.getPole().getLatitude());
    Map<String, String> values = new HashMap<>();
    input.setValues(values);
    List<RuleEvent> events = strategyService.execute(input, new ArrayList<>());
    log.setRuleEvents(events);
    log.setUsername("cloud");
    rabbitMQSender.sendWifiLog(input, log);
  }

  /**
   * Trap回调函数
   *
   */
  public class TrapReceiver implements CommandResponder {

    /**
     * 处理PDU
     *
     * @param commandResponderEvent 获取的事件
     */
    @Override
    public void processPdu(CommandResponderEvent commandResponderEvent) {
//      logger.debug("receive a trap");
      try {
        // trap丢包
        if (commandResponderEvent == null || commandResponderEvent.getPDU() == null) {
          return;
        }
        // 用户上下线
        if (VariableTypes.TRAP_STA_OPERATE.getOid().equals(commandResponderEvent.getPDU().getVariableBindings().elementAt(1).getVariable().toString())){
          // 处理用户数据
          logHandler(commandResponderEvent);
          }
      } catch (Exception e) {
        logger.error(e.getMessage());
      }
    }
  }

  /**
   * 用户数据处理
   *  判断用户数据是否存在于数据库：
   *    1.存在，更新用户数据
   *    2.不存在，添加新的用户数据
   *
   * @param commandResponderEvent 用户上下线的事件
   */
  private void logHandler(CommandResponderEvent commandResponderEvent) {
    List<VariableBinding> list = commandResponderEvent.getPDU().getBindingList(new OID(VariableTypes.TRAP_STA_ENTITY_PREFIX.getOid()));

    for (VariableBinding vb : list) {
      VariableTypes variableType = VariableTypes.getByOid(vb.getOid().toString());
      if (!VariableTypes.TRAP_USER_MAC.equals(variableType)) {
        continue;
      }
      // 获取用户MAC
      String userMac = formatMac(vb.getVariable().toString());
      // 获取用户数据对象列表
      List<WifiLog> logs = wifiLogRepository.findByUserMac(userMac);
      boolean offline = true;
      // 用户在线
      for (WifiLog previousLog : logs) {
        if (previousLog.getLogout() != null) {
          continue;
        }
        // 用户在线，更新用户数据，2种情况：1.用户数据更新（例如IP变更）；2.用户离线
        offline = false;
        updateLog(list, previousLog);
        break;
      }
      // 用户离线，新建用户数据
      if (offline) {
        WifiLog log = new WifiLog();
        // 获取分布式ID
        BigInteger id = deviceService.getLeafId(DeviceService.LeafType.WIFI_LOG);
        if (id.equals(BigInteger.valueOf(-1))) {
          return;
        }
        log.setId(id);
        log.setCreatedAt(new Date());
        updateLog(list, log);
      }
      break;
    }
  }

  /**
   * 更新用户数据，如需添加新用户数据，需在此基础上新建用户数据对象，添加ID、创建时间，再将对象传入
   *
   * @param list  用户上下线事件中包含的数据列表
   * @param log   需要更新的用户数据
   */
  private void updateLog(List<VariableBinding> list, WifiLog log) {
    // 1.添加上下线事件中包含的数据
    for (VariableBinding vb : list) {
      transferEventsData2Log(vb, log);
    }
    // 判断AP是否存在
    BigInteger apId = log.getDeviceId();
    if (apId.equals(BigInteger.valueOf(-1))) {
      logger.error(GlobalConstant.MSG_DEVICE_NOT_FOUND);
      return;
    }
    // 2.添加其他数据
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(log.getDeviceId(), Rule.RuleDeviceType.WIFI);
    log.setLatitude(hierarchy.getPole().getLatitude());
    log.setLongitude(hierarchy.getPole().getLongitude());
    Map<String, List<VariableBinding>> dataMap = getDataMap();
    transferOtherDataAndSave(dataMap, log);
    // 3.用户上线，抹除总流量误差
    if (log.getOffType().equals(1)) {
      logger.debug("user login: " + log.getUserMac());
      log.setTotalUp(0L);
      log.setTotalDown(0L);
    }else {
      logger.debug("user logout: " + log.getUserMac());
    }
    // 3.保存数据
    wifiLogRepository.save(log);

    uploadStrategy(log);
  }

  /**
   * 将上下线事件中的可用数据添加到Log对象
   *  上下线事件中包含的数据：
   *  SSID, OFF_TYPE,
   *  AP_MAC, AP_IP,
   *  USER_MAC, USER_IP,
   *  LOGIN, LOGOUT
   *
   * @param variableBinding 与OID绑定的数据
   * @param log             log对象
   */
  private void transferEventsData2Log(VariableBinding variableBinding, WifiLog log) {
    VariableTypes variableTypes = VariableTypes.getByOid(variableBinding.getOid().toString());
    if (variableTypes == null) {
      return;
    }
    switch (variableTypes) {
      case TRAP_SSID: {
        log.setSsid(variableBinding.getVariable().toString());
        break;
      }
      case TRAP_OFF_REASON: {
        OffTypes offTypes = OffTypes.getByValue(variableBinding.getVariable().toString());
        if (offTypes != null) {
          // 离线类型：1-上线，负数-离线
          log.setOffType(offTypes.getCode());
          if (!OffTypes.ONLINE.equals(offTypes)) {
            // 记录离线时间
            log.setLogout(new Date());
          }
        }
        break;
      }
      case TRAP_AP_MAC: {
        String apMac = formatMac(variableBinding.getVariable().toString());
        BigInteger apId = getAPIdByApMac(apMac);
        log.setDeviceId(apId);
        break;
      }
      case TRAP_USER_MAC:{
        log.setUserMac(formatMac(variableBinding.getVariable().toString()));
        break;
      }
      case TRAP_USER_IP: {
        log.setUserIp(variableBinding.getVariable().toString());
        break;
      }
      default:
        break;
    }
  }

  /**
   * 添加用户其他数据到Log对象
   *  数据包括：
   *    上行速率、下行速率
   *    IPv4上行总流量、IPv4下行总流量
   *    用户名
   *
   * @param log log对象
   */
  private void transferOtherDataAndSave(Map<String, List<VariableBinding>> dataMap, WifiLog log) {
    // 1.获取数据
    // 获取用户MAC，根据MAC获取OID后缀，以获取其他数据
    String oidSuffix = null;
    List<VariableBinding> userMacs = dataMap.get(VariableTypes.GET_USER_MACS.getOid());
    if (userMacs.isEmpty()) {
      logger.error("user mac list is empty");
      return;
    }
    for (VariableBinding userMac : userMacs) {
      String mac = formatMac(userMac.getVariable().toString());
      // 匹配MAC
      if (!mac.equals(log.getUserMac())) {
        continue;
      }
      // 获取后缀
      oidSuffix = userMac.getOid().toString().substring(VariableTypes.GET_USER_MACS.getOid().length());
    }
    if (oidSuffix == null) {
      // 未获取到用户其他数据，原因：1.用户离线；2.网络原因
      // 直接保存用户数据
//      wifiLogRepository.save(log);
      return;
    }
    // 2.添加数据
    List<VariableBinding> userUpRates = dataMap.get(VariableTypes.GET_USER_UP_RATES.getOid());
    Integer userUpRate = getDataByOIDSuffix(Integer.class, oidSuffix, userUpRates); // 上行速率
    if (userUpRate != null) {
      log.setUp(userUpRate);
    }
    List<VariableBinding> userDownRates = dataMap.get(VariableTypes.GET_USER_DOWN_RATES.getOid());
    Integer userDownRate = getDataByOIDSuffix(Integer.class, oidSuffix, userDownRates); // 下行速率
    if (userDownRate != null) {
      log.setDown(userDownRate);
    }
    List<VariableBinding> userTotalUps = dataMap.get(VariableTypes.GET_USER_TOTAL_UPS.getOid());
    Long userTotalUp = getDataByOIDSuffix(Long.class, oidSuffix, userTotalUps); // 总上行流量
    if (userTotalUp != null) {
      log.setTotalUp(userTotalUp);
    }
    List<VariableBinding> userTotalDowns = dataMap.get(VariableTypes.GET_USER_TOTAL_DOWNS.getOid());
    Long usrTotalDown = getDataByOIDSuffix(Long.class, oidSuffix, userTotalDowns);  // 总下行流量
    if (usrTotalDown != null) {
      log.setTotalDown(usrTotalDown);
    }
    List<VariableBinding> userNames = dataMap.get(VariableTypes.GET_USER_NAMES.getOid());
    String userName = getDataByOIDSuffix(String.class, oidSuffix, userNames); // 用户名
    if (userName != null) {
      log.setUsername(userName);
    }
    if (log.getLogin() == null) {  // 上线时间
      List<VariableBinding> userLogins = dataMap.get(VariableTypes.GET_USER_LOGIN.getOid());
      Date login = getDataByOIDSuffix(Date.class, oidSuffix, userLogins);
      if (login != null) {
        log.setLogin(login);
      }
    }
    if (log.getClientType() == null) { // 客户端类型
      List<VariableBinding> clientTypes = dataMap.get(VariableTypes.GET_USER_TERMINAL_TYPE.getOid());
      String clientType = getDataByOIDSuffix(String.class, oidSuffix, clientTypes);
      if (clientType != null) {
        log.setClientType(clientType);
      }
    }
    // 3.更新数据
//    wifiLogRepository.save(log);
  }

  /**
   * 根据OID后缀获取特定用户的数据：
   *  上、下行速率；上、下行总流量；用户名
   *
   * @param type      返回的数据类型
   * @param oidSuffix oid后缀（指定用户）
   * @param vbs       单属性数据列表
   * @param <T>       返回的数据类型
   * @return          数据
   */
  private <T> T getDataByOIDSuffix (Class<T> type, String oidSuffix, List<VariableBinding> vbs) {
    for (VariableBinding vb : vbs) {
      String oid = vb.getOid().toString();
      if (!oid.endsWith(oidSuffix)) {
        continue;
      }
      try {
        ParamTypes paramType = ParamTypes.getByTypeName(type.getTypeName());
        if (paramType == null) {
          return null;
        }
        switch (paramType) {
          case DATE: {
            String sDate = vb.getVariable().toString().replaceAll("\n", "");
            return type.cast(new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.ENGLISH).parse(sDate));
          }
          case LONG: {
            return type.cast(vb.getVariable().toLong());
          }
          case STRING: {
            return type.cast(vb.getVariable().toString());
          }
          case INTEGER: {
            return type.cast(vb.getVariable().toInt());
          }
          default: {
            return null;
          }
        }
      } catch (Exception e) {
        logger.error(e.getMessage());
        return null;
      }

    }
    return null;
  }

  /**
   * 获取所有用户数据：
   *  上、下行速率；上、下行总流量；用户名
   *
   * @return  map
   */
  public Map<String, List<VariableBinding>> getDataMap() {
    SnmpUtil snmpUtil = new SnmpUtil();
    String addressGet = String.format("%s/%d", acGetHost, acGetPort);
    snmpUtil.setAddressGet(addressGet);
    snmpUtil.setCommunityGet(acCommunity);
    Map<String, List<VariableBinding>> map = new HashMap<>();
    try {
      List<VariableBinding> userMacs = snmpUtil.snmpWalk(VariableTypes.GET_USER_MACS.getOid());
      map.put(VariableTypes.GET_USER_MACS.getOid(), userMacs);
      List<VariableBinding> userUpRates = snmpUtil.snmpWalk(VariableTypes.GET_USER_UP_RATES.getOid());
      map.put(VariableTypes.GET_USER_UP_RATES.getOid(), userUpRates);
      List<VariableBinding> userDownRates = snmpUtil.snmpWalk(VariableTypes.GET_USER_DOWN_RATES.getOid());
      map.put(VariableTypes.GET_USER_DOWN_RATES.getOid(), userDownRates);
      List<VariableBinding> userTotalUps = snmpUtil.snmpWalk(VariableTypes.GET_USER_TOTAL_UPS.getOid());
      map.put(VariableTypes.GET_USER_TOTAL_UPS.getOid(), userTotalUps);
      List<VariableBinding> userTotalDowns = snmpUtil.snmpWalk(VariableTypes.GET_USER_TOTAL_DOWNS.getOid());
      map.put(VariableTypes.GET_USER_TOTAL_DOWNS.getOid(), userTotalDowns);
      List<VariableBinding> userNames = snmpUtil.snmpWalk(VariableTypes.GET_USER_NAMES.getOid());
      map.put(VariableTypes.GET_USER_NAMES.getOid(), userNames);
      List<VariableBinding> userLogins = snmpUtil.snmpWalk(VariableTypes.GET_USER_LOGIN.getOid());
      map.put(VariableTypes.GET_USER_LOGIN.getOid(), userLogins);
      List<VariableBinding> clientTypes = snmpUtil.snmpWalk(VariableTypes.GET_USER_TERMINAL_TYPE.getOid());
      map.put(VariableTypes.GET_USER_TERMINAL_TYPE.getOid(), clientTypes);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return map;
  }

  /**
   * 根据AP获取AC信息
   *
   * @param apMac ap mac地址
   * @return      ac id
   */
  private BigInteger getAPIdByApMac(String apMac) {
    Optional<WifiAP> tempAp = wifiAPRepository.findByMac(apMac);
    if (tempAp.isPresent()) {
      return tempAp.get().getId();
    }
    return BigInteger.valueOf(-1);
  }

  /**
   * 格式化MAC地址
   * 格式：XX-XX-XX-XX-XX-XX
   *
   * @param unformattedMac  未格式化的MAC地址
   * @return                MAC地址
   */
  public String formatMac(String unformattedMac) {
    unformattedMac = unformattedMac.toUpperCase();
//    char divisionChar = '-';
    try {
      return unformattedMac.replaceAll(":", "-").substring(0, 17);
    } catch (Exception e) {
      logger.error("format mac error: " + e.getMessage());
      return unformattedMac;
    }
  }

  public Specification<WifiLog> searchLog(DeviceAndLogFilter filter) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Specification<WifiLog> specification = new Specification<WifiLog>() {
      @Override
      public Predicate toPredicate(Root<WifiLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String inStartInStr = filter.getInStart();
        String inEndInStr = filter.getInEnd();
        int offType = filter.getOffType();

        Date inStart = null, inEnd = null;
        try {
          if (inStartInStr != null) {
            inStart = sdf.parse(inStartInStr);
          }
          if (inEndInStr != null) {
            inEnd = sdf.parse(inEndInStr);
          }
        } catch (ParseException e) {
          inStart = null;
          inEnd = null;
        }

        List<Predicate> predicates = new ArrayList<>();

        if (inStart != null && inEnd != null) {
          Predicate predicate = cb.between(root.get("login"), inStart, inEnd);
          predicates.add(predicate);
        }
        if (inStart != null && inEnd == null) {
          Predicate predicate = cb.between(root.get("login"), inStart, new Date());
          predicates.add(predicate);
        }
        if (offType != 0) {
          Predicate predicate = cb.equal(root.get("offType"), offType);
          predicates.add(predicate);
        }

        if (predicates.size() == 0) {
          return null;
        }

        Predicate[] p = new Predicate[predicates.size()];
        return cb.and(predicates.toArray(p));
      }
    };

    return deviceService.searchLog(specification, filter);
  }

  /**
   * TRAP变量类型枚举
   *
   */
  public enum VariableTypes {
    /********** RUIJIE MIB **********/
    TRAP_STA_OPERATE("1.3.6.1.4.1.4881.1.1.10.2.56.6.2.2"), // 无线用户加入、删除、IP地址变化时的TRAP
    TRAP_STA_ENTITY_PREFIX("1.3.6.1.4.1.4881.1.1.10.2.56.6.1"),  // 无线用户属性OID前缀

    TRAP_AP_MAC("1.3.6.1.4.1.4881.1.1.10.2.56.6.1.1.0"),  //TRAP中AP的MAC地址
    TRAP_USER_MAC("1.3.6.1.4.1.4881.1.1.10.2.56.6.1.2.0"),  //TRAP中用户MAC地址
//    TRAP_AP_IP("1.3.6.1.4.1.4881.1.1.10.2.56.6.1.3.0"), //TRAP中AP的IP地址
    TRAP_USER_IP("1.3.6.1.4.1.4881.1.1.10.2.56.6.1.4.0"), //TRAP中用户的IP地址
    TRAP_USER_OPERATE_TYPE("1.3.6.1.4.1.4881.1.1.10.2.56.6.1.5.0"), //TRAP中用户的操作类型
    TRAP_SSID("1.3.6.1.4.1.4881.1.1.10.2.56.6.1.21.0"), //TRAP中无线网络的名称
    TRAP_OFF_REASON("1.3.6.1.4.1.4881.1.1.10.2.56.6.1.26.0"), //TRAP中用户的离线原因

    GET_USER_NAMES("1.3.6.1.4.1.4881.1.1.10.2.56.5.1.1.1.22"),  // 用户名称
    GET_USER_MACS("1.3.6.1.4.1.4881.1.1.10.2.81.10.7.1.1.1"), // 用户的MAC地址
//    GET_USER_LAST_ONLINE_DURATION("1.3.6.1.4.1.4881.1.1.10.2.81.10.7.1.1.2"), // 用户上次在线时长
    GET_USER_UP_RATES("1.3.6.1.4.1.4881.1.1.10.2.81.10.7.1.1.12"),  // 用户上行速率
    GET_USER_DOWN_RATES("1.3.6.1.4.1.4881.1.1.10.2.81.10.7.1.1.13"),  // 用户下行速率
    GET_USER_TOTAL_UPS("1.3.6.1.4.1.4881.1.1.10.2.81.10.7.1.1.6"), // 用户总上行流量，单位Byte
    GET_USER_TOTAL_DOWNS("1.3.6.1.4.1.4881.1.1.10.2.81.10.7.1.1.8"),  // 用户总下行流量，单位Byte
    GET_USER_TERMINAL_TYPE("1.3.6.1.4.1.4881.1.1.10.2.56.5.1.1.1.23"),  // 用户终端类型
    GET_USER_LOGIN("1.3.6.1.4.1.4881.1.1.10.2.56.5.1.1.1.24"), // 用户上线时间

    GET_STA_INFO("1.3.6.1.4.1.4881.1.1.10.2.81.6.1.1")  //STA信息集
    ;

    private final String oid;

    VariableTypes(String oid) {
      this.oid = oid;
    }

    public String getOid() {
      return oid;
    }

    public static VariableTypes getByOid(String oid) {
      for (VariableTypes variableTypes : values()) {
        if (variableTypes.getOid().equals(oid)) {
          return variableTypes;
        }
//        if (oid.startsWith(variableTypes.oid)) {
//          return variableTypes;
//        }
      }
      return null;
    }
  }

  /**
   * 用户下线原因枚举
   *  用户上线：code = 1
   *
   */
  public enum OffTypes{
    ONLINE(" ", 1),
    ACTIVE_OFFLINE("Sta Offline", -1)
    ;

    private final String value;
    private final int code;

    OffTypes(String type, int code) {
      this.value = type;
      this.code = code;
    }

    public String getValue() {
      return value;
    }

    public int getCode() {
      return code;
    }

    public static OffTypes getByValue(String value) {
      for (OffTypes offTypes : values()) {
        if (offTypes.getValue().equals(value)) {
          return offTypes;
        }
      }
      return null;
    }
  }

  /**
   * 参数类型枚举
   *
   */
  public enum ParamTypes {
    STRING("String"),
    INTEGER("Integer"),
    LONG("Long"),
    DATE("Date")
    ;

    private final String value;

    ParamTypes(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public static ParamTypes getByTypeName(String typeName) {
      for (ParamTypes paramType : values()) {
        if (typeName.endsWith(paramType.getValue())) {
          return paramType;
        }
      }
      return null;
    }
  }
}
