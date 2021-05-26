//package com.distlab.hqsms.alarm;
//
//import com.distlab.hqsms.edge.*;
//import com.distlab.hqsms.common.sdk.HCNetSDK;
//import com.distlab.hqsms.common.sdk.SDKUtil;
//import com.distlab.hqsms.edge.DeviceService;
//import com.distlab.hqsms.strategy.Rule;
//import com.distlab.hqsms.edge.Server;
//import com.sun.jna.Memory;
//import com.sun.jna.Pointer;
//import com.sun.jna.ptr.IntByReference;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.math.BigInteger;
//import java.util.*;
//
//
//@Service
//public class AlarmHVService {
//  private static final Logger logger = LoggerFactory.getLogger(AlarmHVService.class);
//  public static FMSGCallBack callback = null;
//  private static final Map<BigInteger, Map<String, Object>> handles = new HashMap<>();  // 设备句柄，用于控制设备
//  @Autowired
//  private AlarmLogRepository alarmLogRepository;
//  @Autowired
//  private AlarmRepository alarmRepository;
//  @Autowired
//  private DeviceService deviceService;
//  @Value("${hqsms.edge.alarm.enable}")
//  private Boolean edgeAlarmEnable;
//
//  @PostConstruct
//  void postConstruct() {
//    if (edgeAlarmEnable) {
//      startService();
//    }
//  }
//
//  @PreDestroy
//  void preDestroy() {
//    if (edgeAlarmEnable) {
//      endService();
//    }
//  }
//
//  /**
//   * 启动报警器服务，监听报警器事件，将事件保存到数据库
//   */
//  public void startService() {
//    Iterable<Alarm> alarms = alarmRepository.findAll();
//    for (Alarm alarm : alarms) {
//      int userHandle = login(alarm.getIp(), "8000", alarm.getUsername(), alarm.getPassword());
//      if (userHandle == -1) {
//        logger.error("alarm login failed: " + alarm.getId());
//        return;
//      }
//      Pointer dataPointer = new Memory(20);
//      dataPointer.setString(0, alarm.getId().toString());
//      int alarmHandle = setup(userHandle, dataPointer);
//      if (alarmHandle == -1) {
//        logger.error("alarm setup failed: " + alarm.getId());
//        if (logout(userHandle) == -1) {
//          logger.error("alarm logout failed: " + alarm.getId());
//        }
//        return;
//      }
//      Map<String, Object> handle = new HashMap<>();
//      handle.put("user", userHandle);
//      handle.put("alarm", alarmHandle);
//      handle.put("data", dataPointer);
//      handles.put(alarm.getId(), handle);
//    }
//    logger.info("alarm service start successfully");
//  }
//
//  /**
//   * 停止报警器服务
//   */
//  public void endService() {
//    for (BigInteger alarmId : handles.keySet()) {
//      Map<String, Object> handle = handles.get(alarmId);
//      Integer alarmHandle = (Integer) handle.get("alarm");
//      Integer userHandle = (Integer) handle.get("user");
//      if (close(alarmHandle) == -1) {
//        logger.error("alarm close failed: " + alarmId);
//      }
//      if (logout(userHandle) == -1) {
//        logger.error("alarm logout failed: " + alarmId);
//      }
//    }
//    logger.info("alarm service stop successfully");
//  }
//
//  /**
//   * 登录报警器
//   *
//   * @param ip       rIP地址
//   * @param port     报警器管理端口，一般为 554
//   * @param username 报警器用户名
//   * @param password 报警器密码
//   * @return 0，操作成功；-1，未知错误
//   */
//  public int login(String ip, String port, String username, String password) {
//    if (!SDKUtil.HCNetSDK.NET_DVR_Init()) {
//      logger.error("alarm initial failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
//      return -1;
//    }
//    HCNetSDK.NET_DVR_USER_LOGIN_INFO loginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();    //  设备登录信息
//    HCNetSDK.NET_DVR_DEVICEINFO_V40 deviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();     //  设备信息
//    int userHandle;  // 用户句柄
//    loginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
//    System.arraycopy(ip.getBytes(), 0, loginInfo.sDeviceAddress, 0, ip.length());
//    loginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
//    System.arraycopy(username.getBytes(), 0, loginInfo.sUserName, 0, username.length());
//    loginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
//    System.arraycopy(password.getBytes(), 0, loginInfo.sPassword, 0, password.length());
//    loginInfo.wPort = (short) Integer.parseInt(port);
//    loginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是
//    loginInfo.write();
//    userHandle = SDKUtil.HCNetSDK.NET_DVR_Login_V40(loginInfo, deviceInfo);
//    if (userHandle == -1) {
//      logger.error("alarm register failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
//      return -1;
//    }
//    return userHandle;
//  }
//
//  /**
//   * 设置事件监听
//   *
//   * @param userHandle  报警器句柄，登录后获得
//   * @param dataPointer 回调函数用户数据，一般传设备ID
//   * @return 0，操作成功；-1，未知错误
//   */
//  public int setup(int userHandle, Pointer dataPointer) {
//    if (callback == null) {
//      callback = new FMSGCallBack();
//    }
//    if (!SDKUtil.HCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(callback, dataPointer)) {
//      logger.error("alarm setup callback failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
//      return -1;
//    }
//    HCNetSDK.NET_DVR_SETUPALARM_PARAM alarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();  //  布防信息
//    int alarmHandle;    //报警布防句柄
//
//    alarmInfo.dwSize = alarmInfo.size();
//    alarmInfo.byLevel = 1;
//    alarmInfo.byAlarmInfoType = 1;
//    alarmInfo.byDeployType = 1;
//    alarmInfo.write();
//    alarmHandle = SDKUtil.HCNetSDK.NET_DVR_SetupAlarmChan_V41(userHandle, alarmInfo);
//    if (alarmHandle == -1) {
//      logger.error("alarm setup play failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
//      return -1;
//    }
//
//    return alarmHandle;
//  }
//
//  /**
//   * 关闭监听
//   *
//   * @param alarmHandle 报警器监听句柄，启动监听后获得
//   * @return 0，操作成功；-1，未知错误
//   */
//  public int close(int alarmHandle) {
//    if (!SDKUtil.HCNetSDK.NET_DVR_CloseAlarmChan_V30(alarmHandle)) {
//      logger.error("alarm close play failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
//      return -1;
//    }
//    return 0;
//  }
//
//  /**
//   * 退出登录
//   *
//   * @param userHandle 报警器句柄，登录后获得
//   * @return 0，操作成功；-1，未知错误
//   */
//  public int logout(int userHandle) {
//    if (!SDKUtil.HCNetSDK.NET_DVR_Logout(userHandle)) {
//      System.out.println("logout failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
//      return -1;
//    }
//
//    if (!SDKUtil.HCNetSDK.NET_DVR_Cleanup()) {
//      System.out.println("cleanup failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
//      return -1;
//    }
//    return 0;
//  }
//
//  /**
//   * 监听事件回调函数，当报警器上报事件，会触发此函数
//   */
//  public class FMSGCallBack implements HCNetSDK.FMSGCallBack_V31 {
//    public boolean invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen,
//                          Pointer dataPointer) {
//      // 获取自定义参数
//      String type = Integer.toHexString(lCommand);
//      String ip = new String(pAlarmer.sDeviceIP).split("\0", 2)[0];
//      BigInteger deviceId = new BigInteger(dataPointer.getString(0));
//
//      try {
//        switch (lCommand) {
//          case HCNetSDK.COMM_ALARMHOST_CID_ALARM: {
//            logger.debug("command=0x" + type + ", ip=" + ip + ", device=" + deviceId);
//            DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(deviceId, Rule.RuleDeviceType.ALARM);
//            if (hierarchy == null) {
//              return true;
//            }
//            HCNetSDK.NET_DVR_CID_ALARM cidAlarm = new HCNetSDK.NET_DVR_CID_ALARM();
//            cidAlarm.write();
//            cidAlarm.getPointer().write(0, pAlarmInfo.getByteArray(0, cidAlarm.size()), 0, cidAlarm.size());
//            cidAlarm.read();
//
//
//            // 获取分布式ID
//            BigInteger id = deviceService.getLeafId(DeviceService.LeafType.ALARM_LOG);
//            if (id.equals(BigInteger.valueOf(-1))) {
//              return true;
//            }
//            AlarmLog log = new AlarmLog();
//            log.setId(id);
//            log.setDeviceId(deviceId);
//            log.setLongitude(hierarchy.getPole().getLongitude());
//            log.setLatitude(hierarchy.getPole().getLatitude());
//            log.setCreatedAt(new Date());
//            alarmLogRepository.save(log);
//            break;
//          }
//          default:
//            break;
//        }
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//      return true;
//    }
//  }
//
//}
