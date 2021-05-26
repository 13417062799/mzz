package com.distlab.hqsms.camera;

import com.distlab.hqsms.cloud.MediaFile;
import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.sdk.HCNetSDK;
import com.distlab.hqsms.common.sdk.SDKUtil;
import com.distlab.hqsms.edge.*;
import com.distlab.hqsms.cloud.MediaFileService;
import com.distlab.hqsms.strategy.*;
import com.distlab.hqsms.edge.Server;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class CameraHVService {
  private static final Logger logger = LoggerFactory.getLogger(CameraHVService.class);
  public static FMSGCallBack alarmCallback = null;
  public static FRealDataCallBack playCallback = null;
  private static final Map<BigInteger, Map<String, Object>> handles = new HashMap<>();  // 设备句柄，用于控制设备
  private final String DELAY_VEHICLE_QUEUE = "vehicle";  // 车辆视频下载延时任务队列
  private final String DELAY_HUMAN_QUEUE = "human";  // 人员视频下载延时任务队列
  private Thread delayThread = null; // 延迟任务线程
  private Thread clearThread = null;  // 文件清理线程
  @Autowired
  private CameraRepository cameraRepository;
  @Autowired
  private CameraVehicleRepository cameraVehicleRepository;
  @Autowired
  private CameraHumanRepository cameraHumanRepository;
  @Autowired
  private CameraLogRepository cameraLogRepository;
  @Autowired
  private StrategyService strategyService;
  @Autowired
  private MediaFileService mediaFileService;
  @Autowired
  private DeviceService deviceService;
  @Autowired
  private RabbitMQSender rabbitMQSender;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private FaceService faceService;
  @Value("${hqsms.edge.camera.enable}")
  private Boolean edgeCameraEnable;
  @Value("${hqsms.cloud.server.host}")
  String cloudServerHost;
  @Value("${hqsms.cloud.srs.api.port}")
  String cloudSrsApiPort;
  @Value("${hqsms.cloud.srs.hls.port}")
  String cloudSrsHlsPort;
  @Value("${hqsms.edge.camera.record.ttl}")
  Integer edgeCameraRecordTtl;
  @Value("${hqsms.edge.camera.record.duration}")
  Integer edgeCameraRecordDuration;

  @PostConstruct
  void postConstruct() {
    if (edgeCameraEnable) {
      if (!SDKUtil.HCNetSDK.NET_DVR_Init()) {
        logger.error("camera initial failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
        return;
      }
      startService();
    }
  }

  @PreDestroy
  void preDestroy() {
    if (edgeCameraEnable) {
      endService();
      if (!SDKUtil.HCNetSDK.NET_DVR_Cleanup()) {
        logger.error("cleanup failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      }
    }
  }

  /**
   * 启动摄像头服务，监听摄像头事件，将事件保存到数据库
   */
  public void startService() {
    Iterable<Camera> cameras = cameraRepository.findAll();
    for (Camera camera : cameras) {
      if (camera.getIsOn().equals(Device.DeviceOn.NO)) {
        continue;
      }
      // 登录摄像头
      int userHandle = login(camera.getIp(), "8000", camera.getUsername(), camera.getPassword());
      if (userHandle == -1) {
        logger.error("camera login failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
        continue;
      }
      // 设置事件监听
      Pointer dataPointer = new Memory(20);
      dataPointer.setString(0, camera.getId().toString());
      int alarmHandle = setup(userHandle, dataPointer);
      if (alarmHandle == -1) {
        logger.error("camera setup failed: " + camera.getId());
        if (logout(userHandle) == -1) {
          logger.error("camera logout failed: " + camera.getId());
        }
        continue;
      }
      // 启动录像任务
      DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(camera.getId(), Rule.RuleDeviceType.CAMERA);
      if (hierarchy == null) {
        logger.error("camera hierarchy missing: " + camera.getId());
        continue;
      }
      Thread thread = newRecordTask(userHandle, hierarchy);
      Map<String, Object> handle = new HashMap<>();
      handle.put("user", userHandle);
      handle.put("alarm", alarmHandle);
      handle.put("data", dataPointer);
      handle.put("thread", thread);
      handles.put(camera.getId(), handle);
    }
    delayThread = poolDelayTask();
    clearThread = poolClearTask();

    logger.info("camera service start successfully");
  }

  /**
   * 停止摄像头服务
   */
  public void endService() {
    try {
      clearThread.interrupt();
      delayThread.interrupt();
    } catch (Exception ex) {
      logger.error("camera service interrupt demon threads failed");
    }
    for (BigInteger cameraId : handles.keySet()) {
      Map<String, Object> handle = handles.get(cameraId);
      Thread thread = (Thread) handle.get("thread");
      Integer alarmHandle = (Integer) handle.get("alarm");
      Integer userHandle = (Integer) handle.get("user");
      try {
        thread.interrupt();
      } catch (Exception ex) {
        logger.error("camera interrupt failed: " + cameraId);
      }
      if (close(alarmHandle) == -1) {
        logger.error("camera close failed: " + cameraId);
      }
      if (logout(userHandle) == -1) {
        logger.error("camera logout failed: " + cameraId);
      }
    }
    logger.info("camera service stop successfully");
  }

  /**
   * 登录摄像头
   *
   * @param ip       摄像头IP地址
   * @param port     摄像头管理端口，一般为 554
   * @param username 摄像头用户名
   * @param password 摄像头密码
   * @return 0，操作成功；-1，未知错误
   */
  public int login(String ip, String port, String username, String password) {
    HCNetSDK.NET_DVR_USER_LOGIN_INFO loginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();    //  设备登录信息
    HCNetSDK.NET_DVR_DEVICEINFO_V40 deviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();     //  设备信息
    int userHandle;  // 用户句柄
    loginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
    System.arraycopy(ip.getBytes(), 0, loginInfo.sDeviceAddress, 0, ip.length());
    loginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
    System.arraycopy(username.getBytes(), 0, loginInfo.sUserName, 0, username.length());
    loginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
    System.arraycopy(password.getBytes(), 0, loginInfo.sPassword, 0, password.length());
    loginInfo.wPort = (short) Integer.parseInt(port);
    loginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是
    loginInfo.write();
    userHandle = SDKUtil.HCNetSDK.NET_DVR_Login_V40(loginInfo, deviceInfo);
    if (userHandle == -1) {
      logger.error("camera register failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      return -1;
    }
    return userHandle;
  }

  /**
   * 设置事件监听
   *
   * @param userHandle  摄像头句柄，登录后获得
   * @param dataPointer 回调函数用户数据，一般传设备ID
   * @return 0，操作成功；-1，未知错误
   */
  public int setup(int userHandle, Pointer dataPointer) {
    if (alarmCallback == null) {
      alarmCallback = new FMSGCallBack();
    }
    if (!SDKUtil.HCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(alarmCallback, dataPointer)) {
      logger.error("camera setup alarm Callback failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      return -1;
    }
    HCNetSDK.NET_DVR_SETUPALARM_PARAM alarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();  //  布防信息
    int alarmHandle;    //报警布防句柄

    alarmInfo.dwSize = alarmInfo.size();
    alarmInfo.byLevel = 1;
    alarmInfo.byAlarmInfoType = 1;
    alarmInfo.byDeployType = 1;
    alarmInfo.write();
    alarmHandle = SDKUtil.HCNetSDK.NET_DVR_SetupAlarmChan_V41(userHandle, alarmInfo);
    if (alarmHandle == -1) {
      logger.error("camera setup play failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      return -1;
    }

    return alarmHandle;
  }

  /**
   * 开始录像
   *
   * @param userHandle 摄像头句柄，登录后获得
   * @param dataPointer 回调函数用户数据，一般传设备ID
   * @return 0，操作成功；-1，未知错误
   */
  public int start(int userHandle, Pointer dataPointer) {
    if (playCallback == null) {
      playCallback = new FRealDataCallBack();
    }
    HCNetSDK.NET_DVR_PREVIEWINFO previewInfo = new HCNetSDK.NET_DVR_PREVIEWINFO();
    int playHandle; // 预览句柄

    previewInfo.hPlayWnd = null;
    previewInfo.lChannel = 1;
//    previewInfo.dwStreamType = 0;
//    previewInfo.dwLinkMode = 0;
    previewInfo.bBlocked = true;

    previewInfo.byProtoType = 0;
    previewInfo.dwDisplayBufNum = 1;
    previewInfo.write();
    playHandle = SDKUtil.HCNetSDK.NET_DVR_RealPlay_V40(userHandle, previewInfo, null, dataPointer);
    if (playHandle == -1) {
      logger.error("camera preview failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      return -1;
    }

    return playHandle;
  }

  /**
   * 停止录像
   *
   * @param playHandle 摄像头预览句柄，启动预览后获得
   * @return 0，操作成功；-1，未知错误
   */
  public int stop(int playHandle) {
    if (!SDKUtil.HCNetSDK.NET_DVR_StopRealPlay(playHandle)) {
      logger.error("camera stop preview failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      return -1;
    }
    return 0;
  }

  /**
   * 关闭监听
   *
   * @param alarmHandle 摄像头监听句柄，启动监听后获得
   * @return 0，操作成功；-1，未知错误
   */
  public int close(int alarmHandle) {
    if (!SDKUtil.HCNetSDK.NET_DVR_CloseAlarmChan_V30(alarmHandle)) {
      logger.error("camera close play failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      return -1;
    }
    return 0;
  }

  /**
   * 退出登录
   *
   * @param userHandle 摄像头句柄，登录后获得
   * @return 0，操作成功；-1，未知错误
   */
  public int logout(int userHandle) {
    if (!SDKUtil.HCNetSDK.NET_DVR_Logout(userHandle)) {
      logger.error("logout failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      return -1;
    }
    return 0;
  }

  /**
   * 清除文件目录
   * @param hierarchy 设备架构
   * @param source 文件来源
   * @param datetime 文件创建时间
   */
  public void clearTask(DeviceHierarchy<Server, Pole, Device> hierarchy, MediaFile.MediaFileSource source, Date datetime) {
    String localPath = MediaFileService.getLocalPath(hierarchy, source, datetime);

    if (!StringUtils.isEmpty(localPath)) {
      FileSystemUtils.deleteRecursively(new File(localPath));
    }
  }

  /**
   * 清除大于90天的录像、图片和视频片段
   * @return 线程
   */
  public Thread poolClearTask() {
    int intervalByMinute = 60 * 24;
    // 获取90天之前的时间戳
    Date now = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(now);
    calendar.add(Calendar.DATE, -edgeCameraRecordTtl);
    Date datetime = calendar.getTime();

    Thread thread = new Thread(() -> {
      while (true) {
        Iterable<Camera> cameras = cameraRepository.findAll();
        for (Camera camera : cameras) {
          DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(camera.getId(), Rule.RuleDeviceType.CAMERA);
          if (hierarchy == null) {
            continue;
          }

          clearTask(hierarchy, MediaFile.MediaFileSource.CAMERA_RAW_VIDEO, datetime);
          clearTask(hierarchy, MediaFile.MediaFileSource.CAMERA_HUMAN_CLIP, datetime);
          clearTask(hierarchy, MediaFile.MediaFileSource.CAMERA_HUMAN_PANORAMA, datetime);
          clearTask(hierarchy, MediaFile.MediaFileSource.CAMERA_HUMAN_SNAPSHOT, datetime);
          clearTask(hierarchy, MediaFile.MediaFileSource.CAMERA_HUMAN_FACE, datetime);
          clearTask(hierarchy, MediaFile.MediaFileSource.CAMERA_VEHICLE_CLIP, datetime);
          clearTask(hierarchy, MediaFile.MediaFileSource.CAMERA_VEHICLE_PANORAMA, datetime);
          clearTask(hierarchy, MediaFile.MediaFileSource.CAMERA_VEHICLE_SNAPSHOT, datetime);
        }
        try {
          TimeUnit.MINUTES.sleep(intervalByMinute);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });
    thread.setDaemon(true);
    thread.start();

    return thread;
  }

  /**
   * 并发方式保存录像
   * @return 线程
   */
  public Thread newRecordTask(int userHandle, DeviceHierarchy<Server, Pole, Device> hierarchy) {
    Thread thread = new Thread(() -> {
      while (true) {

        Pointer dataPointer = new Memory(1024);
        dataPointer.setString(0, hierarchy.getDevice().getId().toString());
        // 打开预览
        int playHandle = start(userHandle, dataPointer);
        if (playHandle == -1) {
          logger.error("camera start previewing failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
          continue;
        }
        // 构建文件名
        Date startedAt = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startedAt);
        calendar.add(Calendar.MINUTE, edgeCameraRecordDuration);
        Date endedAt = calendar.getTime();
        String localVideoName = MediaFileService.getLocalName(hierarchy, MediaFile.MediaFileSource.CAMERA_RAW_VIDEO, startedAt, MediaFileService.VIDEO_MP4);
        if (StringUtils.isEmpty(localVideoName)) {
          logger.error("camera record name is empty");
          continue;
        }
        // 开始保存文件
        if (!SDKUtil.HCNetSDK.NET_DVR_SaveRealData_V30(playHandle, 2, localVideoName)) {
          logger.error("camera start recording failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
          continue;
        }
        try {
          TimeUnit.MINUTES.sleep(edgeCameraRecordDuration);
          // 异步执行后续操作，缓解录像延迟问题
          Thread newThread = new Thread(() -> {
            // 停止保存
            if (!SDKUtil.HCNetSDK.NET_DVR_StopSaveRealData(playHandle)) {
              logger.error("camera stop recording failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
              return;
            }
            // 关闭预览
            if (stop(playHandle) == -1) {
              logger.error("camera stop previewing failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
              return;
            }
            CameraLog log = new CameraLog();
            // 获取分布式ID
            BigInteger id = deviceService.getLeafId(DeviceService.LeafType.CAMERA_LOG);
            if (id.equals(BigInteger.valueOf(-1))) {
              return;
            }
            // 不上传到云端，等云端请求缓存后再上传
            if (mediaFileService.recordRemoteFile(localVideoName, MediaFile.MediaFileSource.CAMERA_RAW_VIDEO, id) != 0) {
              return;
            }
            log.setId(id);
            log.setDeviceId(hierarchy.getDevice().getId());
            log.setLongitude(hierarchy.getPole().getLongitude());
            log.setLatitude(hierarchy.getPole().getLatitude());
            log.setStartedAt(startedAt);
            log.setEndedAt(endedAt);
            log.setCreatedAt(new Date());
            cameraLogRepository.save(log);

            // 匹配策略和事件上报
            StrategyParameter input = new StrategyParameter();
            input.setServerId(hierarchy.getPole().getServerId());
            input.setPoleId(hierarchy.getPole().getId());
            input.setDeviceType(Rule.RuleDeviceType.CAMERA);
            input.setDeviceId(hierarchy.getDevice().getId());
            input.setDeviceLogType(Rule.RuleDeviceLogType.RAW);
            input.setDeviceLogId(log.getId());
            input.setDeviceLogLongitude(log.getLongitude());
            input.setDeviceLogLatitude(log.getLatitude());
            Map<String, String> values = new HashMap<>();

            rabbitMQSender.sendCameraLog(input, log);
          });
          newThread.start();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });
    thread.setDaemon(true);
    thread.start();
    return thread;
  }

  /**
   * 开始延时任务，利用 Redis 的 ZSet 实现延时任务，用于事件触发之后，视频延时下载
   * @return 线程
   */
  public Thread poolDelayTask() {
    Thread thread = new Thread(() -> {
      while (true) {
        long now = System.currentTimeMillis();
        Set<String> vehicleIds = redisTemplate.opsForZSet().rangeByScore(DELAY_VEHICLE_QUEUE, 0, now);
        if (vehicleIds != null && vehicleIds.size() != 0) {
          redisTemplate.opsForZSet().removeRangeByScore(DELAY_VEHICLE_QUEUE, 0, now);
          for (String id : vehicleIds) {
            if (saveVehicleVideo(BigInteger.valueOf(Long.parseLong(id))) != 0) {
              logger.trace("delay save vehicle video failed: " + id);
            }
          }
        }
        Set<String> humanIds = redisTemplate.opsForZSet().rangeByScore(DELAY_HUMAN_QUEUE, 0, now);
        if (humanIds != null && humanIds.size() != 0) {
          redisTemplate.opsForZSet().removeRangeByScore(DELAY_HUMAN_QUEUE, 0, now);
          for (String id : humanIds) {
            if (saveHumanVideo(BigInteger.valueOf(Long.parseLong(id))) != 0) {
              logger.trace("delay save human video failed: " + id);
            }
          }
        }
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    thread.setDaemon(true);
    thread.start();

    return thread;
  }

  /**
   * 保存车辆事件视频，默认截取事件触发前两秒和后一秒的视频片段
   *
   * @param cameraVehicleId 摄像头车辆数据ID
   * @return 0，操作成功
   */
  public int saveVehicleVideo(BigInteger cameraVehicleId) {
    try {
      Optional<CameraVehicle> optionalVehicle = cameraVehicleRepository.findById(cameraVehicleId);
      if (!optionalVehicle.isPresent()) {
        return -1;
      }
      CameraVehicle vehicle = optionalVehicle.get();
      DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(vehicle.getDeviceId(), Rule.RuleDeviceType.CAMERA);
      if (hierarchy == null) {
        return -1;
      }
      // 保存视频到本地
      Date capturedAt = vehicle.getCapturedAt();
      String localVideoName = MediaFileService.getLocalName(hierarchy, MediaFile.MediaFileSource.CAMERA_VEHICLE_CLIP, capturedAt, MediaFileService.VIDEO_MP4);
      if (StringUtils.isEmpty(localVideoName)) {
        return -1;
      }
      if (extractFromVideoLog(hierarchy, localVideoName, capturedAt) != 0) {
        // 保存失败，则延迟重试
//        redisTemplate.opsForZSet().add(DELAY_VEHICLE_QUEUE, vehicle.getId().toString(), System.currentTimeMillis() + 5000);
        return -1;
      }
      // 上传视频文件
      if (mediaFileService.reportRemoteFile(localVideoName, MediaFile.MediaFileSource.CAMERA_VEHICLE_CLIP, vehicle.getId()) != 0) {
        return -1;
      }
      return 0;
    } catch (Exception ex) {
      ex.printStackTrace();
      return -1;
    }
  }

  /**
   * 保存人员事件视频，默认截取事件触发前两秒和后一秒的视频片段
   *
   * @param cameraHumanId 摄像头人员数据ID
   * @return 0，操作成功
   */
  public int saveHumanVideo(BigInteger cameraHumanId) {
    try {
      Optional<CameraHuman> optionalHuman = cameraHumanRepository.findById(cameraHumanId);
      if (!optionalHuman.isPresent()) {
        return -1;
      }
      CameraHuman human = optionalHuman.get();
      DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(human.getDeviceId(), Rule.RuleDeviceType.CAMERA);
      if (hierarchy == null) {
        return -1;
      }
      // 保存视频到本地
      Date capturedAt = human.getCapturedAt();
      String localVideoName = MediaFileService.getLocalName(hierarchy, MediaFile.MediaFileSource.CAMERA_HUMAN_CLIP, capturedAt, MediaFileService.VIDEO_MP4);
      if (StringUtils.isEmpty(localVideoName)) {
        return -1;
      }
      if (extractFromVideoLog(hierarchy, localVideoName, capturedAt) != 0) {
        // 保存失败，则延迟重试
//        redisTemplate.opsForZSet().add(DELAY_HUMAN_QUEUE, human.getId().toString(), System.currentTimeMillis() + 5000);
        return -1;
      }
      // 上传视频文件到云端
      if (mediaFileService.reportRemoteFile(localVideoName, MediaFile.MediaFileSource.CAMERA_HUMAN_CLIP, human.getId()) != 0) {
        return -1;
      }
      return 0;
    } catch (Exception ex) {
      ex.printStackTrace();
      return -1;
    }
  }

  /**
   * 上报人脸数据
   * @param hierarchy 设备架构
   * @param human 人脸数据
   * @return 0，操作成功；-1，未知错误
   */
  public int reportHumanLog(DeviceHierarchy<Server, Pole, Device> hierarchy, CameraHuman human) {
    // 匹配策略和事件上报
    StrategyParameter input = new StrategyParameter();
    input.setServerId(hierarchy.getPole().getServerId());
    input.setPoleId(hierarchy.getPole().getId());
    input.setDeviceType(Rule.RuleDeviceType.CAMERA);
    input.setDeviceId(hierarchy.getDevice().getId());
    input.setDeviceLogType(Rule.RuleDeviceLogType.HUMAN);
    input.setDeviceLogId(human.getId());
    input.setDeviceLogLongitude(human.getLongitude());
    input.setDeviceLogLatitude(human.getLatitude());
    Map<String, String> values = new HashMap<>();
    values.put("faceScore", human.getFaceScore().toString());
    values.put("faceCode", human.getFaceCode());
    input.setValues(values);
    List<RuleEvent> events = strategyService.execute(input, new ArrayList<>());
    human.setRuleEvents(events);
    rabbitMQSender.sendCameraHuman(input, human);

    return 0;
  }

  /**
   * 上报车辆数据
   * @param hierarchy 设备架构
   * @param vehicle 车牌数据
   * @return 0，操作成功；-1，未知错误
   */
  public int reportVehicleLog(DeviceHierarchy<Server, Pole, Device> hierarchy, CameraVehicle vehicle) {
    // 匹配策略和事件上报
    StrategyParameter input = new StrategyParameter();
    input.setServerId(hierarchy.getPole().getServerId());
    input.setPoleId(hierarchy.getPole().getId());
    input.setDeviceType(Rule.RuleDeviceType.CAMERA);
    input.setDeviceId(hierarchy.getDevice().getId());
    input.setDeviceLogType(Rule.RuleDeviceLogType.VEHICLE);
    input.setDeviceLogId(vehicle.getId());
    input.setDeviceLogLongitude(vehicle.getLongitude());
    input.setDeviceLogLatitude(vehicle.getLatitude());
    Map<String, String> values = new HashMap<>();
    values.put("plateEntireScore", vehicle.getPlateEntireScore().toString());
    values.put("plateChars", vehicle.getPlateChars());
    input.setValues(values);
    List<RuleEvent> events = strategyService.execute(input, new ArrayList<>());
    vehicle.setRuleEvents(events);
    rabbitMQSender.sendCameraVehicle(input, vehicle);

    return 0;
  }

  /**
   * 从本地录像文件截取事件前后数秒的视频片段到本地
   *
   * @param hierarchy 设备架构
   * @param localName 视频片段本地路径
   * @param capturedAt 事件抓取时间
   * @return 0，操作成功；-1，未知错误
   */
  public int extractFromVideoLog(DeviceHierarchy<Server, Pole, Device> hierarchy, String localName, Date capturedAt) {
    BigInteger cameraId = hierarchy.getDevice().getId();
    Date startedAt = new Date();
    startedAt.setTime(capturedAt.getTime() - 2000);
    Date endedAt = new Date();
    endedAt.setTime(capturedAt.getTime() + 1000);

    List<CameraLog> cameraLogs = cameraLogRepository.findByDeviceIdAndStartedAtBeforeAndEndedAtAfter(cameraId, startedAt, endedAt);
    if (cameraLogs.isEmpty()) {
      return -1;
    }
    CameraLog cameraLog = cameraLogs.get(0);
    String sourceName = MediaFileService.getLocalName(hierarchy, MediaFile.MediaFileSource.CAMERA_RAW_VIDEO, cameraLog.getStartedAt(), MediaFileService.VIDEO_MP4);
    if (StringUtils.isEmpty(sourceName)) {
      return -1;
    }

    double offset = (startedAt.getTime() - cameraLog.getStartedAt().getTime()) / 1000.0;
    ProcessBuilder processBuilder = new ProcessBuilder();
    List<String> list = new ArrayList<>();
    list.add("ffmpeg");
    list.add("-ss");
    list.add(String.valueOf(offset));
    list.add("-t");
    list.add("3");
    list.add("-i");
    list.add(sourceName);
    list.add("-c:v");
    list.add("copy");
    list.add("-c:a");
    list.add("aac");
    list.add(localName);
    processBuilder.command(list);

    try {
      Process process = processBuilder.start();
      int exit = process.waitFor();
      if (exit != 0) {
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while(true) {
          String text = error.readLine();
          if (text == null) break;
          logger.error("ffmpeg extract failed: " + sourceName + " " + text);
        }
        return -1;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return -1;
    }
    return 0;
  }

  /**
   * 控制摄像头移动，改变摄像头焦距
   *
   * @param cameraId 设备ID
   * @param command  控制命令，参考开发文档 NET_DVR_PTZControlWithSpeed_Other() 说明
   * @param duration 控制持续时间
   * @param speed    控制速度
   * @return 0，操作成功；-1，未知错误
   */
  public int controlPTZ(BigInteger cameraId, int command, int duration, int speed) {
    Map<String, Object> handle = handles.get(cameraId);
    if (handle == null || handle.isEmpty()) {
      logger.error("camera not exists: " + cameraId);
      return -1;
    }
    if (!SDKUtil.HCNetSDK.NET_DVR_PTZControlWithSpeed_Other((Integer) handle.get("user"), 1, command, 0, speed)) {
      logger.error("camera control ptz start failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      return -1;
    }
    SDKUtil.setTimeout(() -> {
      if (!SDKUtil.HCNetSDK.NET_DVR_PTZControlWithSpeed_Other((Integer) handle.get("user"), 1, command, 1, speed)) {
        logger.error("camera control ptz stop failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      }
    }, duration);
    return 0;
  }

  /**
   * 设置预置点
   * @param cameraId 设备ID
   * @param command 控制命令，参考开发文档 NET_DVR_PTZPreset_Other() 说明
   * @param index 预置位序号
   * @return  0，操作成功；-1，未知错误
   */
  public int presetPTZ(BigInteger cameraId, int command, int index) {
    Map<String, Object> handle = handles.get(cameraId);
    if (handle == null || handle.isEmpty()) {
      logger.error("camera not exists: " + cameraId);
      return -1;
    }
    if (!SDKUtil.HCNetSDK.NET_DVR_PTZPreset_Other((Integer) handle.get("user"), 1, command, index)) {
      logger.error("camera set ptz failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      return -1;
    }
    return 0;
  }

  /**
   * 设置预置点
   * @param cameraId 设备ID
   * @return  0，操作成功；-1，未知错误
   */
  public int getPTZPreset(BigInteger cameraId) {
    Map<String, Object> handle = handles.get(cameraId);
    if (handle == null || handle.isEmpty()) {
      logger.error("camera not exists: " + cameraId);
      return -1;
    }
    HCNetSDK.NET_DVR_PRESET_NAME presetCfg = new HCNetSDK.NET_DVR_PRESET_NAME();
    presetCfg.dwSize = presetCfg.size();
    presetCfg.write();
    IntByReference retRef = new IntByReference(0);//获取预置点信息
    if (!SDKUtil.HCNetSDK.NET_DVR_GetDVRConfig(
      (Integer) handle.get("user"),
      SDKUtil.HCNetSDK.NET_DVR_GET_PRESET_NAME,
      1,
      presetCfg.getPointer(),
      presetCfg.size(),
      retRef
    )) {
      logger.error("camera get ptz preset configuration failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      return -1;
    }
    presetCfg.read();

    return 0;
  }

  /**
   * 获取GB28181配置信息，用于实时预览
   *
   * @param cameraId 设备ID
   * @return {szSipAuthenticateID}@{szSipAuthenticateID}
   */
  public String getGB28181Address(BigInteger cameraId) {
    Map<String, Object> handle = handles.get(cameraId);
    if (handle == null || handle.isEmpty()) {
      logger.error("camera not exists: " + cameraId);
      return null;
    }
    HCNetSDK.NET_DVR_GBT28181_ACCESS_CFG accessCfg = new HCNetSDK.NET_DVR_GBT28181_ACCESS_CFG();
    accessCfg.dwSize = accessCfg.size();
    accessCfg.write();
    IntByReference retRef = new IntByReference(0);//获取IP接入配置参数
    if (!SDKUtil.HCNetSDK.NET_DVR_GetDVRConfig(
      (Integer) handle.get("user"),
      SDKUtil.HCNetSDK.NET_DVR_GET_GBT28181_ACCESS_CFG,
      1,
      accessCfg.getPointer(),
      accessCfg.size(),
      retRef
    )) {
      logger.error("camera get gb28181 configuration failed: " + SDKUtil.HCNetSDK.NET_DVR_GetErrorMsg(new IntByReference(SDKUtil.HCNetSDK.NET_DVR_GetLastError())));
      return null;
    }
    accessCfg.read();
    String authID = new String(accessCfg.szSipAuthenticateID, StandardCharsets.UTF_8).trim();
    String url = UriComponentsBuilder.fromHttpUrl(String.format("http://%s:%s/api/v1/gb28181", cloudServerHost, cloudSrsApiPort))
      .queryParam("action", "query_channel")
      .queryParam("id", String.format("%s@%s", authID, authID))
      .build(true)
      .toUriString();
    try {
      String response = restTemplate.getForObject(url, String.class);
      ObjectMapper mapper = new ObjectMapper();
      GB28181QueryChannelResponse result = mapper.readValue(response, GB28181QueryChannelResponse.class);
      GB28181QueryChannelResponseData data = result.getData();
      if (data == null) {
        return null;
      }
      List<GB28181QueryChannelResponseDataChannel> channels = data.getChannels();
      if (channels.isEmpty()) {
        return null;
      }
      // 替换端口
      UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(channels.get(0).getFlv_url());
      return builder.port(cloudSrsHlsPort).toUriString();
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /**
   * 监听事件回调函数，当摄像头上报事件，会触发此函数
   */
  public class FMSGCallBack implements HCNetSDK.FMSGCallBack_V31 {
    public boolean invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int bufLen, Pointer dataPointer) {
      // 获取自定义参数
      String type = Integer.toHexString(lCommand);
      String ip = new String(pAlarmer.sDeviceIP).split("\0", 2)[0];
      BigInteger deviceId = new BigInteger(dataPointer.getString(0));

      try {
        switch (lCommand) {
          //  人脸抓拍结果信息
          case HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT: {
            logger.debug("command=0x" + type + ", ip=" + ip + ", device=" + deviceId);
            // 获取图片信息
            HCNetSDK.NET_VCA_FACESNAP_RESULT faceSnapInfo = new HCNetSDK.NET_VCA_FACESNAP_RESULT();
            faceSnapInfo.write();
            faceSnapInfo.getPointer().write(0, pAlarmInfo.getByteArray(0, faceSnapInfo.size()), 0, faceSnapInfo.size());
            faceSnapInfo.read();
            // 获取截图时间
            Date capturedAt = SDKUtil.parseIntAbsTime(faceSnapInfo.dwAbsTime);
            DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(deviceId, Rule.RuleDeviceType.CAMERA);
            if (hierarchy == null) {
              return true;
            }

            // 保存大图
            String localPanoramaName = MediaFileService.getLocalName(hierarchy, MediaFile.MediaFileSource.CAMERA_HUMAN_PANORAMA, capturedAt, MediaFileService.IMAGE_JPG);
            if (StringUtils.isEmpty(localPanoramaName) || !SDKUtil.saveImage(localPanoramaName, faceSnapInfo.pBuffer2, faceSnapInfo.dwBackgroundPicLen)) {
              logger.warn("save human panorama image to local file system failed: " + localPanoramaName);
              return true;
            }
            // 保存子图
            String localSnapshotName = MediaFileService.getLocalName(hierarchy, MediaFile.MediaFileSource.CAMERA_HUMAN_SNAPSHOT, capturedAt, MediaFileService.IMAGE_JPG);
            if (StringUtils.isEmpty(localSnapshotName) || !SDKUtil.saveImage(localSnapshotName, faceSnapInfo.pBuffer1, faceSnapInfo.dwFacePicLen)) {
              logger.warn("save human face image to local file system failed: " + localSnapshotName);
              return true;
            }
            // 保存人脸识别
            List<FaceService.FaceCode> codes = faceService.faceEncode(localSnapshotName);
            String faceCode = null;
            if (codes.size() > 0) {
              // 选取第一个人脸编码作为指定编码
              faceCode = codes.get(0).getCode().toString();
            }
            String localFaceName = null;
            if (!StringUtils.isEmpty(faceCode)) {
              localFaceName = MediaFileService.getLocalName(hierarchy, MediaFile.MediaFileSource.CAMERA_HUMAN_FACE, capturedAt, MediaFileService.RAW_TEXT);
              if (StringUtils.isEmpty(localFaceName)) {
                logger.warn("save human face image to local file system failed: " + localSnapshotName);
                return true;
              }
              Files.write(Paths.get(localFaceName), faceCode.getBytes());
            }
            // 获取分布式ID
            BigInteger id = deviceService.getLeafId(DeviceService.LeafType.CAMERA_HUMAN);
            if (id.equals(BigInteger.valueOf(-1))) {
              return true;
            }
            // 上传文件
            if (mediaFileService.reportRemoteFile(localPanoramaName, MediaFile.MediaFileSource.CAMERA_HUMAN_PANORAMA, id) != 0) {
              logger.warn("report human panorama image failed: " + localPanoramaName);
              return true;
            }
            if (mediaFileService.reportRemoteFile(localSnapshotName, MediaFile.MediaFileSource.CAMERA_HUMAN_SNAPSHOT, id) != 0) {
              logger.warn("report human snapshot image failed: " + localSnapshotName);
              return true;
            }
            if (!StringUtils.isEmpty(localFaceName) && mediaFileService.reportRemoteFile(localFaceName, MediaFile.MediaFileSource.CAMERA_HUMAN_FACE, id) != 0) {
              logger.warn("report human face code failed: " + localFaceName);
              return true;
            }
            // 保存图片信息
            CameraHuman human = new CameraHuman();
            human.setId(id);
            human.setDeviceId(hierarchy.getDevice().getId());
            human.setLongitude(hierarchy.getPole().getLongitude());
            human.setLatitude(hierarchy.getPole().getLatitude());
            human.setCapturedAt(capturedAt);
            human.setCreatedAt(new Date());
            human.setFaceScore((float) faceSnapInfo.dwFaceScore / 100);
            human.setFaceRectX(faceSnapInfo.struRect.fX);
            human.setFaceRectY(faceSnapInfo.struRect.fY);
            human.setFaceRectW(faceSnapInfo.struRect.fWidth);
            human.setFaceRectH(faceSnapInfo.struRect.fHeight);
            if (!StringUtils.isEmpty(faceCode)) {
              human.setFaceCode(faceCode);
            }
            cameraHumanRepository.save(human);
            // 上报人脸数据
            reportHumanLog(hierarchy, human);
            // 添加截取视频延时任务
            redisTemplate.opsForZSet().add(DELAY_HUMAN_QUEUE, human.getId().toString(), System.currentTimeMillis() + edgeCameraRecordDuration * 60 * 1000);
            break;
          }
          case HCNetSDK.COMM_ITS_PLATE_RESULT: {
            logger.debug("command=0x" + type + ", ip=" + ip + ", device=" + deviceId);
            // 获取图片信息
            HCNetSDK.NET_ITS_PLATE_RESULT itsPlateResult = new HCNetSDK.NET_ITS_PLATE_RESULT();
            itsPlateResult.write();
            itsPlateResult.getPointer().write(0, pAlarmInfo.getByteArray(0, itsPlateResult.size()), 0, itsPlateResult.size());
            itsPlateResult.read();

            // 获取绝对时间
            Date capturedAt = SDKUtil.parseStrutAbsTime(itsPlateResult.struSnapFirstPicTime);
            DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(deviceId, Rule.RuleDeviceType.CAMERA);
            if (hierarchy == null) {
              return true;
            }
            // 保存大图
            String localPanoramaName = MediaFileService.getLocalName(hierarchy, MediaFile.MediaFileSource.CAMERA_VEHICLE_PANORAMA, capturedAt, MediaFileService.IMAGE_JPG);
            if (StringUtils.isEmpty(localPanoramaName) || !SDKUtil.saveImage(localPanoramaName, itsPlateResult.struPicInfo[1].pBuffer, itsPlateResult.struPicInfo[1].dwDataLen)) {
              logger.warn("save vehicle panorama image failed: " + localPanoramaName);
              return true;
            }
            // 保存子图
            String localSnapshotName = MediaFileService.getLocalName(hierarchy, MediaFile.MediaFileSource.CAMERA_VEHICLE_SNAPSHOT, capturedAt, MediaFileService.IMAGE_JPG);
            if (!SDKUtil.saveImage(localSnapshotName, itsPlateResult.struPicInfo[0].pBuffer, itsPlateResult.struPicInfo[0].dwDataLen)) {
              logger.warn("save vehicle plate image failed: " + localSnapshotName);
              return true;
            }

            // 获取分布式ID
            BigInteger id = deviceService.getLeafId(DeviceService.LeafType.CAMERA_VEHICLE);
            if (id.equals(BigInteger.valueOf(-1))) {
              return true;
            }
            if (mediaFileService.reportRemoteFile(localPanoramaName, MediaFile.MediaFileSource.CAMERA_VEHICLE_PANORAMA, id) != 0) {
              logger.warn("report vehicle panorama image failed: " + localPanoramaName);
              return true;
            }
            if (mediaFileService.reportRemoteFile(localSnapshotName, MediaFile.MediaFileSource.CAMERA_VEHICLE_SNAPSHOT, id) != 0) {
              logger.warn("report vehicle snapshot image failed: " + localSnapshotName);
              return true;
            }
            // 保存图片信息
            CameraVehicle vehicle = new CameraVehicle();
            vehicle.setId(id);
            vehicle.setDeviceId(hierarchy.getDevice().getId());
            vehicle.setLongitude(hierarchy.getPole().getLongitude());
            vehicle.setLatitude(hierarchy.getPole().getLatitude());
            vehicle.setCapturedAt(capturedAt);
            vehicle.setCreatedAt(new Date());
            vehicle.setPlateType(String.valueOf(itsPlateResult.struPlateInfo.byPlateType));
            vehicle.setPlateColor(String.valueOf(itsPlateResult.struPlateInfo.byColor));
            vehicle.setPlateRectX(itsPlateResult.struPlateInfo.struPlateRect.fX);
            vehicle.setPlateRectY(itsPlateResult.struPlateInfo.struPlateRect.fY);
            vehicle.setPlateRectW(itsPlateResult.struPlateInfo.struPlateRect.fWidth);
            vehicle.setPlateRectH(itsPlateResult.struPlateInfo.struPlateRect.fHeight);
            vehicle.setPlateBright((int) itsPlateResult.struPlateInfo.byBright);
            vehicle.setPlateCharsLength((int) itsPlateResult.struPlateInfo.byLicenseLen);
            vehicle.setPlateChars(new String(itsPlateResult.struPlateInfo.sLicense, "GBK").substring(1));
            vehicle.setPlateCharsScore(Arrays.toString(itsPlateResult.struPlateInfo.byBelieve));
            vehicle.setPlateEntireScore((float) itsPlateResult.struPlateInfo.byEntireBelieve / 100);
            vehicle.setVehicleType(String.valueOf(itsPlateResult.struVehicleInfo.byVehicleType));
            vehicle.setVehicleColor(String.valueOf(itsPlateResult.struVehicleInfo.byColor));
            vehicle.setVehicleColorDepth(String.valueOf(itsPlateResult.struVehicleInfo.byColorDepth));
            vehicle.setVehicleLogoRecog(String.valueOf(itsPlateResult.struVehicleInfo.byVehicleLogoRecog));
            vehicle.setVehicleSubLogoRecog(String.valueOf(itsPlateResult.struVehicleInfo.byVehicleSubLogoRecog));
            vehicle.setVehicleModel(String.valueOf(itsPlateResult.struVehicleInfo.byVehicleModel));
            cameraVehicleRepository.save(vehicle);
            // 上报车牌数据
            reportVehicleLog(hierarchy, vehicle);
            // 添加截取视频延时任务
            redisTemplate.opsForZSet().add(DELAY_VEHICLE_QUEUE, vehicle.getId().toString(), System.currentTimeMillis() + edgeCameraRecordDuration * 60 * 1000);
            break;
          }
          default:
            break;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return true;
    }
  }

  /**
   * 监听实时视频流
   */
  public class FRealDataCallBack implements HCNetSDK.FRealDataCallBack_V30 {
    public void invoke(int playHandle, int dataType, Pointer buffer, int bufSize, Pointer dataPointer) {
//      switch (dataType)
//      {
//        case HCNetSDK.NET_DVR_SYSHEAD: //系统头
//        case HCNetSDK.NET_DVR_STREAMDATA:   //码流数据
//        case HCNetSDK.NET_DVR_AUDIOSTREAMDATA:
//        case HCNetSDK.NET_DVR_PRIVATE_DATA:
//        default:
//      }
      // 获取自定义参数
      String localVideoName = dataPointer.getString(0);
      try {
        FileOutputStream playStream = new FileOutputStream(localVideoName, true);
        long offset = 0;
        ByteBuffer buffers = buffer.getByteBuffer(offset, bufSize);
        byte [] bytes = new byte[bufSize];
        buffers.rewind();
        buffers.get(bytes);
        playStream.write(bytes);
        playStream.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

}


class GB28181QueryChannelResponse {
  private String code;
  private GB28181QueryChannelResponseData data;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public GB28181QueryChannelResponseData getData() {
    return data;
  }

  public void setData(GB28181QueryChannelResponseData data) {
    this.data = data;
  }
}

class GB28181QueryChannelResponseData {
  private List<GB28181QueryChannelResponseDataChannel> channels;

  public List<GB28181QueryChannelResponseDataChannel> getChannels() {
    return channels;
  }

  public void setChannels(List<GB28181QueryChannelResponseDataChannel> channels) {
    this.channels = channels;
  }
}

class GB28181QueryChannelResponseDataChannel {
  private String id;
  private String ip;
  private int rtmp_port;
  private String app;
  private String stream;
  private String rtmp_url;
  private String flv_url;
  private String hls_url;
  private String webrtc_url;
  private int ssrc;
  private int rtp_port;
  private String port_mode;
  private int rtp_peer_port;
  private String rtp_peer_ip;
  private long recv_time;
  private String recv_time_str;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public int getRtmp_port() {
    return rtmp_port;
  }

  public void setRtmp_port(int rtmp_port) {
    this.rtmp_port = rtmp_port;
  }

  public String getStream() {
    return stream;
  }

  public void setStream(String stream) {
    this.stream = stream;
  }

  public String getApp() {
    return app;
  }

  public void setApp(String app) {
    this.app = app;
  }

  public String getRtmp_url() {
    return rtmp_url;
  }

  public void setRtmp_url(String rtmp_url) {
    this.rtmp_url = rtmp_url;
  }

  public String getFlv_url() {
    return flv_url;
  }

  public void setFlv_url(String flv_url) {
    this.flv_url = flv_url;
  }

  public String getHls_url() {
    return hls_url;
  }

  public void setHls_url(String hls_url) {
    this.hls_url = hls_url;
  }

  public String getWebrtc_url() {
    return webrtc_url;
  }

  public void setWebrtc_url(String webrtc_url) {
    this.webrtc_url = webrtc_url;
  }

  public int getSsrc() {
    return ssrc;
  }

  public void setSsrc(int ssrc) {
    this.ssrc = ssrc;
  }

  public int getRtp_port() {
    return rtp_port;
  }

  public void setRtp_port(int rtp_port) {
    this.rtp_port = rtp_port;
  }

  public String getPort_mode() {
    return port_mode;
  }

  public void setPort_mode(String port_mode) {
    this.port_mode = port_mode;
  }

  public int getRtp_peer_port() {
    return rtp_peer_port;
  }

  public void setRtp_peer_port(int rtp_peer_port) {
    this.rtp_peer_port = rtp_peer_port;
  }

  public String getRtp_peer_ip() {
    return rtp_peer_ip;
  }

  public void setRtp_peer_ip(String rtp_peer_ip) {
    this.rtp_peer_ip = rtp_peer_ip;
  }

  public long getRecv_time() {
    return recv_time;
  }

  public void setRecv_time(long recv_time) {
    this.recv_time = recv_time;
  }

  public String getRecv_time_str() {
    return recv_time_str;
  }

  public void setRecv_time_str(String recv_time_str) {
    this.recv_time_str = recv_time_str;
  }
}
