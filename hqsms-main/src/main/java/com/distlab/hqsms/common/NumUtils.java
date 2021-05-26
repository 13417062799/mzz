package com.distlab.hqsms.common;

import com.distlab.hqsms.screen.device.ScreenSetterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumUtils {

  private static final Logger logger = LoggerFactory.getLogger(NumUtils.class);

  /**
   * bytes to long
   *
   * @param data  bytes
   * @param len   length
   * @return      long
   */
  public static long[] bytes2Longs(byte[] data, int len) {
    if (len > 8) {
      logger.error("len must less than 8");
      return null;
    }
    if (data.length%len != 0) {
      logger.error("data incorrect");
      return null;
    }
    long[] result = new long[data.length/len];
    for (int i = 0, k = 0; i < data.length; i++) {
      long tData = Byte.toUnsignedInt(data[i]);
      for (int j = 1; j < len; j++) {
        tData = tData << 8 | Byte.toUnsignedInt(data[++i]);
      }
      result[k] = tData;
      k++;
    }
    return result;
  }

  /**
   * bytes to long
   * @param src bytes
   * @return    long
   */
  public static long bytes2Long(byte[] src) {
    if (src.length == 0) {
      logger.error("data error");
      return -1;
    }
    long num = Byte.toUnsignedInt(src[0]);
    for (int i = 1; i < src.length; i++) {
      num = num << 8 | Byte.toUnsignedInt(src[i]);
    }
    return num;
  }

  /**
   * byte to binary
   *
   * @param data byte
   * @return     binary in string
   */
  public static String byte2Binary(byte data) {
    return String.format("%8s", Integer.toBinaryString(data & 0xFF)).replace(' ', '0');
  }

  /**
   * string to boolean, just for string "0" or "1"
   * @param str "0" or "1"
   * @return    boolean
   */
  public static Boolean string2Boolean(String str) {
    if ("1".equals(str)) {
      return true;
    }
    if ("0".equals(str)) {
      return false;
    }
    logger.error("argument error.");
    return null;
  }
}
