package com.distlab.hqsms.common.exception;

import com.distlab.hqsms.screen.device.ScreenSetterInfo;

import java.math.BigInteger;

public class MessageUtil {

  public static final String REMOTE_RESULTS_NOT_FOUND = "remote results not found";

  /**
   * 拼接生成ID失败的详细消息
   *
   * @param messageEnum 消息类型
   * @return            详细消息
   */
  public static String genIdFailed(MessageEnum messageEnum) {
    return String.format("failed to generate %s Id", messageEnum.getMessageType());
  }

  /**
   * 拼接保存数据失败的详细消息
   *
   * @param messageEnum 消息类型
   * @param id          数据主键
   * @return            详细消息
   */
  public static String saveDataFailed(MessageEnum messageEnum, BigInteger id) {
    if (id != null) {
      return String.format("failed to save %s data: %d", messageEnum.getMessageType(), id);
    } else {
      return String.format("failed to save %s data", messageEnum.getMessageType());
    }
  }

  /**
   * 拼接获取数据失败的详细消息
   *
   * @param messageEnum 消息类型
   * @param id          数据主键
   * @return            详细消息
   */
  public static String notFound(MessageEnum messageEnum, BigInteger id) {
    if (id != null) {
      return String.format("%s not found: %d", messageEnum.getMessageType(), id);
    } else {
      return String.format("%s not found", messageEnum.getMessageType());
    }
  }

  /**
   * 拼接获取远程数据失败的详细消息
   * @param messageEnum 消息类型
   * @param id          数据主键
   * @return            详细消息
   */
  public static String remoteDataNotFound(MessageEnum messageEnum, BigInteger id) {
    if (id != null) {
      return String.format("remote %s not found: %d", messageEnum.getMessageType(), id);
    } else {
      return String.format("remote %s not found", messageEnum.getMessageType());
    }
  }

  /**
   * 拼接设备不可用的详细消息
   *
   * @param messageEnum 消息类型
   * @return            详细消息
   */
  public static String deviceUnable(MessageEnum messageEnum) {
    return String.format("%s unable", messageEnum.getMessageType());
  }

  /**
   * 拼接资源为空的详细消息
   *
   * @param messageEnum 消息类型
   * @param id          数据主键
   * @return            详细消息
   */
  public static String isEmpty(MessageEnum messageEnum, BigInteger id) {
    if (id != null) {
      return String.format("%s is empty: %d", messageEnum.getMessageType(), id);
    } else {
      return String.format("%s is empty", messageEnum.getMessageType());
    }
  }

}
