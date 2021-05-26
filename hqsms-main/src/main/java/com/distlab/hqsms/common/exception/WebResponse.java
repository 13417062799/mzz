package com.distlab.hqsms.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebResponse<T> implements Serializable {
  public static final long serialVersionUID = 42L;

  private int code;
  private String message;
  private T data;

  /**
   * 成功，不返回数据
   *
   * @param <T> 需要返回的数据的类型
   * @return    响应
   */
  public static <T> WebResponse<T> success() {
    WebResponse<T> webResponse = new WebResponse<>();
    webResponse.setCode(WebResponseEnum.SUCCESS.getCode());
    webResponse.setMessage(WebResponseEnum.SUCCESS.getMessage());
    return webResponse;
  }

  /**
   * 成功，返回数据
   *
   * @param data  需要返回的数据
   * @param <T>   需要返回的数据类型
   * @return      响应
   */
  public static <T> WebResponse<T> success(T data) {
    WebResponse<T> webResponse = new WebResponse<>();
    webResponse.setCode(WebResponseEnum.SUCCESS.getCode());
    webResponse.setMessage(WebResponseEnum.SUCCESS.getMessage());
    webResponse.setData(data);
    return webResponse;
  }

  /**
   * 失败，返回粗略错误消息
   *
   * @param webResponseEnum 枚举，包含错误码及粗略错误消息
   * @param <T>             需要返回的数据类型
   * @return                响应
   */
  public static <T> WebResponse<T> fail(WebResponseEnum webResponseEnum) {
    WebResponse<T> webResponse = new WebResponse<>();
    webResponse.setCode(webResponseEnum.getCode());
    webResponse.setMessage(webResponseEnum.getMessage());
    return webResponse;
  }

  /**
   * 失败，返回详细错误消息
   *
   * @param webResponseEnum 枚举，包含错误码及粗略错误消息
   * @param message         详细错误消息
   * @param <T>             需要返回的数据类型
   * @return                响应
   */
  public static <T> WebResponse<T> fail(WebResponseEnum webResponseEnum, String message) {
    WebResponse<T> webResponse = new WebResponse<>();
    webResponse.setCode(webResponseEnum.getCode());
    webResponse.setMessage(String.format("%s [%s]", webResponseEnum.getMessage(), message));
    return webResponse;
  }

  public static <T> WebResponse<T> fail(int code, String message) {
    WebResponse<T> webResponse = new WebResponse<>();
    webResponse.setCode(code);
    webResponse.setMessage(message);
    return webResponse;
  }


  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
