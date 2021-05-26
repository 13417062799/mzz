package com.distlab.hqsms.common.exception;

public enum WebResponseEnum {

  /**************** 粗略消息 **************/
  // 失败
  UNKNOWN_ERROR(-1, "未知错误"),
  // 成功
  SUCCESS(200, "操作成功"),


  /**************** 外部错误 **************/
  // 301 参数错误
  OUT_PARAM_VALUE_ERROR(301001, "参数值错误"),
  OUT_PARAM_TYPE_ERROR(301002, "参数类型错误"),
  OUT_PARAM_FORMAT_ERROR(301003, "参数格式错误"),
  // 302 请求方法错误
  OUT_REQUEST_METHOD_ERROR(302001, "请求方法错误"),


  /**************** 设备错误 **************/
  // 401 网络错误
  DEV_CONNECT_TIMEOUT(401001, "设备连接超时"),
  // 402 业务错误
  DEV_LOGIN_FAILED(402001, "设备登陆失败"),
  DEV_UNKNOWN_ERROR(402002, "设备未知错误"),


  /**************** 内部错误 **************/
  // 501 异步操作错误
  SYS_GEN_ID_FAILED(501001, "获取分布式ID失败"),
  SYS_SAVE_DATA_FAILED(501002, "数据保存失败"),
  SYS_GET_DATA_FAILED(501003, "数据查询失败"),
  SYS_REMOTE_OPERATE_FAILED(501004, "远程操作失败"),
  // 502 系统错误
  SYS_PROPERTY_ERROR(502001, "配置错误"),
  SYS_INNER_ERROR(502002, "系统内部错误"),

  ;


  private Integer code;
  private String message;

  WebResponseEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
