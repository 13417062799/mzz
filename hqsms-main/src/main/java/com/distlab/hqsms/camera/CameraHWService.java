package com.distlab.hqsms.camera;

import com.distlab.hqsms.common.sdk.HWPuSDK;
import com.distlab.hqsms.common.sdk.SDKUtil;
import com.distlab.hqsms.edge.Pole;
import com.distlab.hqsms.edge.PoleRepository;
import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.PointerByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


@Service
public class CameraHWService {
  private static final Logger logger = LoggerFactory.getLogger(CameraHWService.class);
  public static EventStatesCallback esCallback = null;

  // 测试代码
  int testUserHandle;
  int testPlayHandle;
  @Autowired
  private CameraRepository cameraRepository;
  @Autowired
  private PoleRepository poleRepository;
  @Autowired
  private CameraVehicleRepository cameraVehicleRepository;
  @Autowired
  private CameraHumanRepository cameraHumanRepository;

  @PostConstruct
  void postConstruct() throws InterruptedException, IOException {
//    startTest();
  }

  @PreDestroy
  void preDestroy() {
//    endTest();
  }

  public void startTest() throws InterruptedException {
    HWPuSDK.PU_CERT_FILE_PATH_PARA_S.ByReference filePath = new HWPuSDK.PU_CERT_FILE_PATH_PARA_S.ByReference();
    ByteBuffer buff = ByteBuffer.allocate(512);
    buff.put((SDKUtil.certPath + File.separator + "cacert.cer").getBytes());
    ByteBuffer buff2 = ByteBuffer.allocate(512);
    buff2.put((SDKUtil.certPath + File.separator + "cert.pem").getBytes());
    ByteBuffer buff3 = ByteBuffer.allocate(512);
    buff3.put((SDKUtil.certPath + File.separator + "key.pem").getBytes());

    ByteBuffer buff4 = ByteBuffer.allocate(512);
    buff4.put(HWPuSDK.pathCryptStr.getBytes());
    filePath.szCACertFilePath = buff.array();
    filePath.szCertFilePath = buff2.array();
    filePath.szKeyFilePath = buff3.array();
    filePath.szKeyPasswd = buff4.array();

    if (!SDKUtil.HWPuSDK.IVS_PU_InitEx(new WinDef.ULONG(HWPuSDK.PU_LINK_MODE.PU_MANUALLOGIN_MODE), "", new WinDef.ULONG(HWPuSDK.gLocalSSLPort), new WinDef.ULONG(HWPuSDK.gLocalSSLPort), filePath)) {
      logger.error("camera initial failed: " + SDKUtil.HWPuSDK.IVS_PU_GetErrorMsg(SDKUtil.HWPuSDK.IVS_PU_GetLastError()));
      return;
    }
    testUserHandle = login("192.168.2.115", "6060", "admin", "Hg123_123");
    if (testUserHandle == -1) {
      return;
    }
    Pointer dataPointer = new Memory(20);
    dataPointer.setInt(0, 1);
    dataPointer.setInt(4, testUserHandle);
    testPlayHandle = setup(testUserHandle, dataPointer);
    if (testPlayHandle == -1) {
      logout(testUserHandle);
    }
  }

  public void endTest() {
    if (testUserHandle > 0 && testPlayHandle > 0) {
      if (close(testUserHandle, testPlayHandle) == -1) {
        logger.error("close failed: " + testUserHandle + " | " + testPlayHandle);
      }
    } else {
      logger.warn("skip close: " + testUserHandle + " | " + testPlayHandle);
    }
    if (testUserHandle > 0) {
      if (logout(testUserHandle) == -1) {
        logger.error("unregister failed: " + testUserHandle);
      }
    } else {
      logger.warn("skip unregister: " + testUserHandle);
    }
  }

  public int login(String ip, String port, String username, String password) {
    if (esCallback == null) {
      esCallback = new EventStatesCallback();
    }
    if (!SDKUtil.HWPuSDK.IVS_PU_EventStatesCallBack(esCallback)) {
      logger.error("camera setting event callback failed: " + SDKUtil.HWPuSDK.IVS_PU_GetErrorMsg(SDKUtil.HWPuSDK.IVS_PU_GetLastError()));
      return -1;
    }
    int userHandle = (int) SDKUtil.HWPuSDK.IVS_PU_Login(ip, new WinDef.ULONG(Integer.parseInt(port)), username, password);  // 用户句柄
    if (userHandle <= 0) {
      logger.error("camera login failed: " + SDKUtil.HWPuSDK.IVS_PU_GetErrorMsg(SDKUtil.HWPuSDK.IVS_PU_GetLastError()));
      return -1;
    }
    logger.info("camera register success: " + userHandle);
    return userHandle;
  }

  public int setup(int userHandle, Pointer dataPointer) {
    HWPuSDK.PU_REAL_PLAY_INFO_S playInfo = new HWPuSDK.PU_REAL_PLAY_INFO_S();
    playInfo.ulChannelId = new WinDef.ULONG(101);
    playInfo.hPlayWnd = null;
    playInfo.enStreamType = HWPuSDK.PU_STREAM_TYPE.PU_VIDEO_MAIN_STREAM;
    playInfo.enVideoType = HWPuSDK.PU_VIDEO_TYPE.PU_VIDEO_TYPE_META;
    playInfo.enProtocolType = HWPuSDK.PU_PROTOCOL_TYPE.PU_PROTOCOL_TYPE_TCP;
    playInfo.enMediaCallbackType = HWPuSDK.PU_MEDIA_CALLBACK_TYPE.PU_MEDIA_CALLBACK_TYPE_META_FRAME;
    playInfo.bKeepLive = true;
    playInfo.szReserved[HWPuSDK.PU_EVENT_TYPE_MAX] = HWPuSDK.PU_IGT_DATA_TYPE.META_TYPE;
    FaceRecognitionCallback frCallback = new FaceRecognitionCallback();
    int playHandle = SDKUtil.HWPuSDK.IVS_PU_RealPlay(new WinDef.ULONG(userHandle), playInfo, frCallback, dataPointer);
    if (playHandle <= 0) {
      logger.error("camera setup play failed: " + SDKUtil.HWPuSDK.IVS_PU_GetErrorMsg(SDKUtil.HWPuSDK.IVS_PU_GetLastError()));
      return -1;
    }

    logger.info("camera setup success: " + playHandle);
    return playHandle;
  }

  public int close(int userHandle, int playHandle) {
    if (!SDKUtil.HWPuSDK.IVS_PU_StopRealPlay(new WinDef.ULONG(userHandle), playHandle)) {
      logger.error("camera close play failed: " + userHandle + " " + playHandle + " " + SDKUtil.HWPuSDK.IVS_PU_GetErrorMsg(SDKUtil.HWPuSDK.IVS_PU_GetLastError()));
      return -1;
    }
    logger.info("camera close success");
    return 0;
  }

  public int logout(int userHandle) {
    if (!SDKUtil.HWPuSDK.IVS_PU_Logout(userHandle)) {
      logger.error("logout failed: " + SDKUtil.HWPuSDK.IVS_PU_GetErrorMsg(SDKUtil.HWPuSDK.IVS_PU_GetLastError()));
      return -1;
    }

    if (!SDKUtil.HWPuSDK.IVS_PU_Cleanup()) {
      logger.error("cleanup failed: " + SDKUtil.HWPuSDK.IVS_PU_GetErrorMsg(SDKUtil.HWPuSDK.IVS_PU_GetLastError()));
      return -1;
    }
    logger.info("camera unregister success");
    return 0;
  }

  public boolean saveImage(String path, Pointer buffer, int len) throws FileNotFoundException {
    if (len <= 0) return false;
    FileOutputStream fos = new FileOutputStream(path);
    try {
      fos.write(buffer.getByteArray(0, len), 0, len);
      fos.flush();
      fos.close();
    } catch (IOException ex) {
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  public String getEventMsg(int eventType) {
    String t = Long.toHexString(eventType).toUpperCase();

    logger.info("layer three event type: 0x" + (t.length() <= 7 ? "0" : "") + t);
    return t;
  }

  public class EventStatesCallback implements HWPuSDK.pfGetEventInfoCallBack {
    public void eventStatus(Pointer args) {
      try {
        long ulLoginId = 0;
        HWPuSDK.PU_EVENT_COMMON stPuEventCommon = Structure.newInstance(HWPuSDK.PU_EVENT_COMMON.class, args);
        stPuEventCommon.read();

        logger.info("event status: " + stPuEventCommon.enEventType + " " + stPuEventCommon.ulIdentifyID);
        switch (stPuEventCommon.enEventType) {
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_CONNCET:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_REGISTER:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_UNREGISTER:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_DISCONNECT: {
//            ulLoginId = stPuEventCommon.ulIdentifyID.longValue();
//            long faceReHnd = faceReRealPlayHnd;
//            if (ulLoginId > 0) {
//              if (faceReHnd > 0) {
//                stopReconginTion();
//              }
//              logout();
//              long login = login();
//              if(login > 0){
//                if (faceReHnd > 0) {
//                  faceRecoginRealPlay();
//                }
//              }else{
//                return;
//              }
//            }
            break;
          }
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_SEND_RECV_ERROR: {//Failed to send or receive
            break;
          }
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_KEEPLIVE_FAIL: {//Front-end survival failure
            break;
          }
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_STREAM_PACKAGE_CHANGE:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_WATERMARK_ERR:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_UPLOAD_IMAGE_URL:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_UPLOAD_IMAGE_COMP_NOTIFY:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_TRANSPARENT_CHANNEL_NOTIFY:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_REALPALY_ERROR:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_REPORT_VISUAL_INFO:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_PUPU_INFO:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_RECORD_COMP_NOTIFY:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_SLAVE_DEVICE_ADD:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_SLAVE_DEVICE_MODIFY:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_SLAVE_DEVICE_DELETE:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_SLAVE_DEVICE_ONLINE:
            break;
          case HWPuSDK.PU_EVENT_TYPE.PU_EVENT_TYPE_SLAVE_DEVICE_OFFLINE:
            break;
          default:
            break;
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public class FaceRecognitionCallback implements HWPuSDK.pfRealDataCallBack {

    public Pointer invoke(Pointer buffer, NativeLong size, Pointer dataPointer) {
      if (buffer == null) {
        return null;
      }
      // 获取二层数据
      HWPuSDK.PU_META_DATA ppstMetaData = new HWPuSDK.PU_META_DATA();
      PointerByReference metaDataPointer = new PointerByReference(ppstMetaData.getPointer());
      if (!SDKUtil.HWPuSDK.IVS_User_GetMetaData(buffer, new WinDef.ULONG(size.longValue()), HWPuSDK.LAYER_TWO_TYPE.TARGET, metaDataPointer)) {
        logger.error("get metadata failed: " + SDKUtil.HWPuSDK.IVS_PU_GetErrorMsg(SDKUtil.HWPuSDK.IVS_PU_GetLastError()));
        return null;
      }
      // 解析二层数据
      HWPuSDK.PU_META_DATA metaData = new HWPuSDK.PU_META_DATA();
      metaData.getPointer().write(0, metaDataPointer.getValue().getByteArray(0, metaData.size()), 0, metaData.size());
      metaData.read();
      // 获取文件保存路径
      Optional<Camera> oCamera = cameraRepository.findById(BigInteger.valueOf(dataPointer.getInt(0)));
      if (!oCamera.isPresent()) {
        logger.warn("camera not exists: " + BigInteger.valueOf(dataPointer.getInt(0)));
        return null;
      }
      Camera camera = oCamera.get();
      Optional<Pole> oPole = poleRepository.findById(camera.getPoleId());
      if (!oPole.isPresent()) {
        logger.warn("pole not exists: " + camera.getPoleId());
        return null;
      }
      Pole pole = oPole.get();
      String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
      Path path = Paths.get(System.getProperty("user.dir"), "tmp", "plate", pole.getName(), camera.getName(), date);
      File dir = path.toFile();
      if (!dir.isDirectory()) {
        if (!dir.mkdirs()) {
          logger.warn("create face directory failed: " + dir);
          return null;
        }
      }
      // 三层数据属性
      Pointer facePanoramaBuffer = null;
      int facePanoramaLength = 0;
      int facePanoramaSize = 0;

      Pointer facePictureBuffer = null;
      int facePictureLength = 0;
      int facePictureSize = 0;
      long facePictureTime = 0;
      long facePictureTimezone = 0;
      // 解析三层数据
      HWPuSDK.PU_UserData[] userData = (HWPuSDK.PU_UserData[]) metaData.pstMetaUserData.toArray(metaData.usValidNumber.intValue());
      logger.info("process user data: " + userData.length);
      for (HWPuSDK.PU_UserData userDatum : userData) {
        String t = Long.toHexString(userDatum.eType).toUpperCase();
        logger.info("layer three event type: 0x" + (t.length() <= 7 ? "0" : "") + t);
        switch (userDatum.eType) {

          case HWPuSDK.LAYER_THREE_TYPE.PTS: {
            // 时间戳
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.ITGT_TYPE: {
            // 智能类型
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.IMG_WIDTH: {
            // 处理图片宽
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.IMG_HEIGHT: {
            // 处理图片高
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_SCORE: {
            // 人脸置信度
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_ANGLE: {
            // 人脸角度
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_ID: {
            // 人脸ID
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_PANOPIC_SIZE: {
            // 人脸全景图片大小
            facePanoramaSize = userDatum.unMetaData.uIntValue;
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_FACEPIC_SIZE: {
            // 人脸抠图图片大小
            facePictureSize = userDatum.unMetaData.uIntValue;
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_PIC_TIME: {
            // 人脸抠图产生时间
            facePictureTime = userDatum.unMetaData.longlongValue;
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_PIC_TZONE: {
            // 人脸抠图设备时区(单位ms 东区为+ 西区为-)
            facePictureTimezone = userDatum.unMetaData.longlongValue;
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.HUMAN_FEATURE: {
            // 人体属性
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_FEATURE: {
            // 人脸属性
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.PANORAMA_PIC: {
            // 全景图片
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_PIC: {
            // 人脸抠图
            facePictureLength = userDatum.unMetaData.stBinay.ulBinaryLenth.intValue();
            facePictureBuffer = userDatum.unMetaData.stBinay.pBinaryData;
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_PIC_KPS: {
            // 人脸抠图kps质量过滤标志位
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.HUMAN_PIC: {
            // 人体抠图
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.HUMAN_PIC_KPS: {
            // 人体抠图kps质量过滤标志位
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.HUMAN_PIC_ROI: {
            // 人体抠图中的人体目标框
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_PANORAMA: {
            // 人脸全景
            facePanoramaLength = userDatum.unMetaData.stBinay.ulBinaryLenth.intValue();
            facePanoramaBuffer = userDatum.unMetaData.stBinay.pBinaryData;
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_PIC_POSITION: {
            // 人脸抠图小框位置
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_POS: {
            // 人脸位置(实时位置框)
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.HUMAN_RECT: {
            // 人体位置(实时位置框)
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.HUMAN_RECT_POSITION: {
            // 人体抠图小框位置
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_MATCH: {
            // 人脸数据库中匹配图片
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACELIB_RECORDID: {
            // 名单库中的人脸ID，用来维持特征 record的一致性
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_MATCHRATE: {
            // 人脸匹配率
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_INFO: {
            // 人脸信息,对应数据库中信息
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_LIB_TYPE: {
            // 名单库类型
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_LIB_NAME: {
            // 名单库名字
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.TARGET_TYPE: {
            // target类型，当前用于区分人脸后处理抠图和人脸识别以及人脸识别多机协同
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.FACE_LIB_ID: {
            // 名单库ID
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.MMC_FACE_COMPARE_NUM_MAX: {
            //多机协同算法参数- 人脸比对数据
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.MMC_FACE_WARNING_RECALL_RATE_MAX: {
            //多机协同算法参数- 人脸上报告警的召回率
            break;
          }
          case HWPuSDK.LAYER_THREE_TYPE.MMC_FACE_WARNING_RECALL_RATE_MIN: {
            //多机协同算法参数- 人脸预警率的下限值
            break;
          }
          default:
            break;
        }
      }

      logger.info("process user data end");
      if (!SDKUtil.HWPuSDK.IVS_User_FreeMetaData(metaDataPointer)) {
        logger.error("free metadata failed: " + SDKUtil.HWPuSDK.IVS_PU_GetErrorMsg(SDKUtil.HWPuSDK.IVS_PU_GetLastError()));
        return null;
      }
      return null;
    }

  }
}
