package com.distlab.hqsms.common.sdk;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class SDKUtil {
  private static final Logger logger = LoggerFactory.getLogger(SDKUtil.class);
  public static HCNetSDK HCNetSDK = null;
  public static HWPuSDK HWPuSDK = null;
  public static String classPath;
  public static String certPath;
  public static String sdkRoot;

  static {
    try {
      sdkRoot = System.getProperty("user.dir") + File.separator + "sdk";
      String hvDir = sdkRoot + File.separator + (Platform.isWindows() ? "win32-x86-64" : "linux-x86-64") + File.separator + "hikvision" + File.separator;
      String hwDir = sdkRoot + File.separator + (Platform.isWindows() ? "win32-x86-64" : "linux-x86-64") + File.separator + "huawei" + File.separator;
      // 动态加载SDK相关文件
      if (Platform.isWindows()) {
        // 海康威视Windows平台SDK
        HCNetSDK = Native.loadLibrary(hvDir + "HCNetSDK.dll", HCNetSDK.class);

        // 华为Windows平台SDK
        HWPuSDK = Native.loadLibrary(hwDir + "HWPuSDK.dll", HWPuSDK.class);
        certPath = hwDir + "cert";
      } else if (Platform.isLinux()) {
        // 海康威视Linux平台SDK
        HCNetSDK = Native.loadLibrary(hvDir + "libhcnetsdk.so", HCNetSDK.class);

        // 设置HCNetSDKCom所在路径，必须是绝对路径
        HCNetSDK.NET_DVR_LOCAL_SDK_PATH struComPath = new HCNetSDK.NET_DVR_LOCAL_SDK_PATH();
        System.arraycopy(hvDir.getBytes(), 0, struComPath.sPath, 0, hvDir.length());
        struComPath.write();
        HCNetSDK.NET_DVR_SetSDKInitCfg(2, struComPath.getPointer());

        // 设置日志路径
        if (SDKUtil.HCNetSDK.NET_DVR_Init()) {
          String logRoot = System.getProperty("user.dir") + File.separator + "log";
          File logDir = new File(logRoot + File.separator + "hikvision");
          if (!logDir.isDirectory() && !logDir.mkdirs()) {
            logger.error("create hikvision log directory failed: " + logDir);
          }
          if (!SDKUtil.HCNetSDK.NET_DVR_SetLogToFile(1, logDir.toString(), true)) {
            logger.error("set hikvision log directory failed: " + SDKUtil.HCNetSDK.NET_DVR_GetLastError());
          }
        }
        //设置libcrypto.so所在路径，必须是绝对路径
        HCNetSDK.BYTE_ARRAY ptrByteArrayCrypto = new HCNetSDK.BYTE_ARRAY(256);
        String strPathCrypto = hvDir + "libcrypto.so";
        System.arraycopy(strPathCrypto.getBytes(), 0, ptrByteArrayCrypto.byValue, 0, strPathCrypto.length());
        ptrByteArrayCrypto.write();
        HCNetSDK.NET_DVR_SetSDKInitCfg(3, ptrByteArrayCrypto.getPointer());
        //设置libssl.so所在路径，必须是绝对路径
        HCNetSDK.BYTE_ARRAY ptrByteArraySsl = new HCNetSDK.BYTE_ARRAY(256);
        String strPathSsl = hvDir + "libssl.so";
        System.arraycopy(strPathSsl.getBytes(), 0, ptrByteArraySsl.byValue, 0, strPathSsl.length());
        ptrByteArraySsl.write();
        HCNetSDK.NET_DVR_SetSDKInitCfg(4, ptrByteArraySsl.getPointer());

        // 华为Linux平台SDK
        HWPuSDK = Native.loadLibrary(hwDir + "libhwpusdk.so", HWPuSDK.class);
        certPath = hwDir + "cert";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * TODO:海康威视特有的时间转换方法，将int型绝对时间转为Date对象
   *
   * @param absTime 待转换的int型时间
   * @return 转换后的Date对象
   */
  public static Date parseIntAbsTime(int absTime) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, (absTime >> 26) + 2000);
    calendar.set(Calendar.MONTH, ((absTime >> 22) & 15) - 1);
    calendar.set(Calendar.DAY_OF_MONTH, (absTime >> 17) & 31);
    calendar.set(Calendar.HOUR_OF_DAY, (absTime >> 12) & 31);
    calendar.set(Calendar.MINUTE, (absTime >> 6) & 63);
    calendar.set(Calendar.SECOND, (absTime) & 63);

    return new Date(calendar.getTimeInMillis());
  }

  /**
   * TODO:海康威视特有的时间转换方法，将byte数组绝对时间转为Date对象
   *
   * @param absTime 待转换的byte数组时间
   * @return 转换后的Date对象
   */
  public static Date parseByteAbsTime(byte[] absTime) {
    Calendar calendar = Calendar.getInstance();
    String year = "" + (char) absTime[0] + (char) absTime[1] + (char) absTime[2] + (char) absTime[3];
    calendar.set(Calendar.YEAR, Integer.parseInt(year));
    String month = "" + (char) absTime[4] + (char) absTime[5];
    calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
    String day = "" + (char) absTime[6] + (char) absTime[7];
    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
    String hour = "" + (char) absTime[8] + (char) absTime[9];
    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
    String minute = "" + (char) absTime[10] + (char) absTime[11];
    calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
    String second = "" + (char) absTime[12] + (char) absTime[13];
    calendar.set(Calendar.SECOND, Integer.parseInt(second));
    String millSecond = "" + (char) absTime[14] + (char) absTime[15] + (char) absTime[16];
    calendar.set(Calendar.MILLISECOND, Integer.parseInt(millSecond));

    return new Date(calendar.getTimeInMillis());
  }

  /**
   * TODO:海康威视特有的时间转换方法，将结构体绝对时间转为Date对象，精度为毫秒
   *
   * @param absTime 待转换的结构体时间
   * @return 转换后的Date对象
   */
  public static Date parseStrutAbsTime(HCNetSDK.NET_DVR_TIME_V30 absTime) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, absTime.wYear);
    calendar.set(Calendar.MONTH, absTime.byMonth - 1);
    calendar.set(Calendar.DAY_OF_MONTH, absTime.byDay);
    calendar.set(Calendar.HOUR_OF_DAY, absTime.byHour);
    calendar.set(Calendar.MINUTE, absTime.byMinute);
    calendar.set(Calendar.SECOND, absTime.bySecond);
    calendar.set(Calendar.MILLISECOND, absTime.wMilliSec);
    return new Date(calendar.getTimeInMillis());
  }

  /**
   * TODO:海康威视特有的时间转换方法，将结构体绝对时间转为Date对象，精度为秒
   *
   * @param absTime 待转换的结构体时间
   * @return 转换后的Date对象
   */
  public static Date parseStrutAbsTimeEx(HCNetSDK.NET_DVR_TIME_EX absTime) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, absTime.wYear);
    calendar.set(Calendar.MONTH, absTime.byMonth - 1);
    calendar.set(Calendar.DAY_OF_MONTH, absTime.byDay);
    calendar.set(Calendar.HOUR_OF_DAY, absTime.byHour);
    calendar.set(Calendar.MINUTE, absTime.byMinute);
    calendar.set(Calendar.SECOND, absTime.bySecond);
    return new Date(calendar.getTimeInMillis());
  }

  /**
   * TODO:将byte数组MAC地址转为字符串
   *
   * @param macAddrBytes 待转换的byte数组MAC地址
   * @return 转换后的字符串MAC地址
   */
  public static String parseMacAddr(byte[] macAddrBytes) {
    StringBuilder sb = new StringBuilder(18);
    for (byte b : macAddrBytes) {
      if (sb.length() > 0)
        sb.append(':');
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }

  /**
   * TODO:将图片保存到指定本地路径
   *
   * @param path   保存到的本地路径
   * @param buffer 待保存的图片缓冲区指针
   * @param len    待保存的图片缓冲区长度
   * @return 保存结果
   */
  public static boolean saveImage(String path, Pointer buffer, int len) throws FileNotFoundException {
    if (len <= 0) return false;
    FileOutputStream fos = new FileOutputStream(path);
    try {
      fos.write(buffer.getByteArray(0, len), 0, len);
      fos.close();
    } catch (IOException ex) {
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * 延迟执行任务
   *
   * @param runnable 任务函数
   * @param delay    延时时间，单位毫秒
   */
  public static void setTimeout(Runnable runnable, int delay) {
    new Thread(() -> {
      try {
        Thread.sleep(delay);
        runnable.run();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }).start();
  }
}
