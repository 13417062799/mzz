package com.distlab.hqsms.alarm;

import com.distlab.hqsms.edge.*;
import com.distlab.hqsms.common.sdk.HCNetSDK;
import com.distlab.hqsms.common.sdk.SDKUtil;
import com.distlab.hqsms.strategy.*;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.math.BigInteger;
import java.util.*;

@Service
public class AlarmService {

  private static final int CID_TYPE_EMERGENCE_CALL_HELP = 1129; //紧急求助报警

  private static final Logger logger = LoggerFactory.getLogger(AlarmService.class);
  private static final Map<BigInteger, Map<String, Object>> handles = new HashMap<>();  // 设备句柄
  public static FMSGCallBack callBack = null;  // 报警回调函数实现

  @Value("${hqsms.edge.alarm.enable}")
  private Boolean edgeAlarmEnable;

  @Autowired
  AlarmRepository alarmRepository;
  @Autowired
  DeviceService deviceService;
  @Autowired
  AlarmLogRepository alarmLogRepository;
  @Autowired
  StrategyService strategyService;
  @Autowired
  RabbitMQSender rabbitMQSender;


  /**
   * 设备ID提示，用于log记录
   *
   * @param alarmId 设备ID
   * @return        提示字符串
   */
  private static String tips(BigInteger alarmId) {
    return String.format("[alarmId: %d] ", alarmId);
  }

  /**
   * 获取错误提示
   *
   * @return  操作设备返回的错误消息
   */
  private String getErrors() {
    return SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError()));
  }

  /**
   * 用户注册
   *
   * @param ip        ip地址
   * @param port      端口
   * @param username  用户名
   * @param password  密码
   * @return          用户ID（-1，登录失败；>-1，登录成功）
   */
  public int login(String ip, String port, String username, String password) {
//    if (!SDKUtil.HCNetSDK.NET_DVR_Init()) {
//      log.error("SDK initial failed: " + getErrors());
//      return -1;
//    }
    HCNetSDK.NET_DVR_USER_LOGIN_INFO loginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();
    HCNetSDK.NET_DVR_DEVICEINFO_V40 deviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();

    loginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];  // 用户名
    System.arraycopy(username.getBytes(), 0, loginInfo.sUserName, 0, username.length());

    loginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];    // 密码
    System.arraycopy(password.getBytes(), 0, loginInfo.sPassword, 0, password.length());

    loginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];// IP地址
    System.arraycopy(ip.getBytes(), 0, loginInfo.sDeviceAddress, 0, ip.length());

    loginInfo.wPort = (short) Integer.parseInt(port); // 端口

    loginInfo.bUseAsynLogin = false;  //同步

    loginInfo.write();

    return SDKUtil.HCNetSDK.NET_DVR_Login_V40(loginInfo, deviceInfo);
  }

  /**
   * 报警布防
   *
   * @param userID    用户ID
   * @return          布防句柄（-1，布防失败；>-1，布防成功）
   */
  public int setupDefense(int userID) {
    HCNetSDK.NET_DVR_SETUPALARM_PARAM alarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
    alarmInfo.dwSize = alarmInfo.size();
    alarmInfo.write();
    return SDKUtil.HCNetSDK.NET_DVR_SetupAlarmChan_V41(userID, alarmInfo);
  }

  /**
   * 触发报警后，对报警信息的处理
   * @param lCommand    控制命令
   * @param deviceId     设备ID
   */
  public void AlarmDataHandle(int lCommand, BigInteger deviceId) {
    try {
      // 报警主机CID报告
      if (lCommand == HCNetSDK.COMM_ALARMHOST_CID_ALARM) {
        DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(deviceId, Rule.RuleDeviceType.ALARM);
        if (hierarchy == null) {
          logger.error("hierarchy not found");
          return;
        }

        // 获取分布式ID
        BigInteger id = deviceService.getLeafId(DeviceService.LeafType.ALARM_LOG);
        if (id.equals(BigInteger.valueOf(-1))) {
          return;
        }
        AlarmLog log = new AlarmLog();
        log.setId(id);
        log.setDeviceId(deviceId);
        log.setLongitude(hierarchy.getPole().getLongitude());
        log.setLatitude(hierarchy.getPole().getLatitude());
        log.setCreatedAt(new Date());
        alarmLogRepository.save(log);

        // 匹配策略和事件上报
        StrategyParameter input = new StrategyParameter();
        input.setServerId(hierarchy.getPole().getServerId());
        input.setPoleId(hierarchy.getPole().getId());
        input.setDeviceType(Rule.RuleDeviceType.ALARM);
        input.setDeviceId(hierarchy.getDevice().getId());
        input.setDeviceLogType(Rule.RuleDeviceLogType.RAW);
        input.setDeviceLogId(log.getId());
        input.setDeviceLogLongitude(hierarchy.getPole().getLongitude());
        input.setDeviceLogLatitude(hierarchy.getPole().getLatitude());
        Map<String, String> values = new HashMap<>();
        input.setValues(values);
        List<RuleEvent> events = strategyService.execute(input, new ArrayList<>());
        log.setRuleEvents(events);
        rabbitMQSender.sendAlarmLog(input, log);
      } else {
        logger.error("another alarm type");
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * 启动服务
   *
   */
  @PostConstruct
  public void startService() {
    if (!edgeAlarmEnable) {
      logger.info("edgeAlarm unable");
      return;
    }

    // 初始化SDK
    if (!SDKUtil.HCNetSDK.NET_DVR_Init()) {
      logger.error("SDK initial failed: " + getErrors());
      return;
    }
    Iterable<Alarm> alarms = alarmRepository.findAll();
    for (Alarm alarm : alarms) {
      // 用户注册
      int userID = login(alarm.getIp(), "8000", alarm.getUsername(), alarm.getPassword());
      if (userID < 0) {
        logger.error(tips(alarm.getId()) + "login failed: " + getErrors());
        return;
      }
      // 设置报警回调函数
      if (callBack == null) {
        callBack = new FMSGCallBack();
      }
      Pointer alarmIdInPointer = new Memory(20);
      alarmIdInPointer.setString(0, alarm.getId().toString());
      boolean isEnableCallBack = SDKUtil.HCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(callBack, alarmIdInPointer);
      if (!isEnableCallBack) {
        logger.error(tips(alarm.getId()) + "setup callback failed: " + getErrors());
        return;
      }
      // 报警布防
      int alarmHandle = setupDefense(userID);
      if (alarmHandle < 0) {
        logger.error(tips(alarm.getId()) + "setup defense failed: " + getErrors());
        return;
      }
      // 缓存数据
      Map<String, Object> handle = new HashMap<>();
      handle.put("userID", userID);
      handle.put("alarmHandle", alarmHandle);
      handles.put(alarm.getId(), handle);
      // 成功提示
      logger.info(tips(alarm.getId()) + "start service successfully");
    }
  }

  /**
   * 停止服务
   *
   */
  @PreDestroy
  public void stopService() {
    if (!edgeAlarmEnable) {
      logger.info("edgeAlarm unable");
      return;
    }

    for (BigInteger alarmId : handles.keySet()) {
      Map<String, Object> handle = handles.get(alarmId);
      // 报警撤防
      Integer alarmHandle = (Integer)handle.get("alarmHandle");
      boolean isClosed = SDKUtil.HCNetSDK.NET_DVR_CloseAlarmChan_V30(alarmHandle);
      if (!isClosed) {
        logger.error(tips(alarmId) + "close defense failed: " + getErrors());
      }
      // 注销用户
      Integer userID = (Integer)handle.get("userID");
      boolean isLoggedOut = SDKUtil.HCNetSDK.NET_DVR_Logout(userID);
      if (!isLoggedOut) {
        logger.error(tips(alarmId) + "logout failed: " + getErrors());
      }
      // 成功提示
      logger.info(tips(alarmId) + "stop service successfully");
    }
    // 释放SDK资源
    boolean isCleaned = SDKUtil.HCNetSDK.NET_DVR_Cleanup();
    if (!isCleaned) {
      logger.error("close SDK failed: " + getErrors());
    }
  }




  public class FMSGCallBack implements HCNetSDK.FMSGCallBack_V31 {

    // 报警信息回调函数
    @Override
    public boolean invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {

      BigInteger alarmId = new BigInteger(pUser.getString(0));
      AlarmDataHandle(lCommand, alarmId);
      return true;
    }
  }

}
