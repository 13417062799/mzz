package com.distlab.hqsms.common;

import com.distlab.hqsms.common.sdk.HWPuSDK;

public class GlobalConstant {

  /***************** Message *****************/
  public static final String MSG_MUST_NULL = "must be null";
  public static final String MSG_NOT_NULL = "must not be null";
  public static final String MSG_NOT_BLANK = "must not be blank";
  public static final String MSG_MAY_BE_INCORRECT = "may be incorrect";

  public static final String MSG_DATETIME_FORMAT = "format or value may be incorrect 'yyyy-MM-dd HH:mm:ss'";
  public static final String MSG_DATE_FORMAT = "must be 'yyyy-MM-dd'";
  public static final String MSG_DATABASE_ERROR = "database error: ";
  public static final String MSG_DATA_FORMAT_ERROR = "data format error: ";
  public static final String MSG_NETWORK_ERROR = "network error: ";
  public static final String MSG_PARSE_ERROR = "parse error: ";

  public static final String MSG_DEVICE_NOT_FOUND = "device not found";
  public static final String MSG_RESULT_NOT_FOUND = "result not found";
  public static final String MSG_IP_NOT_FOUND = "ip not found";
  public static final String MSG_PORT_NOT_FOUND = "port not found";

  public static final String MSG_SCREEN_UNABLE = "screen unable";
  public static final String MSG_BROADCAST_UNABLE = "broadcast unable";
  public static final String MSG_METER_UNABLE = "meter unable";
  public static final String MSG_CHARGER_UNABLE = "charger unable";
  public static final String MSG_WIFI_UNABLE = "wifi unable";
  public static final String MSG_WEATHER_UNABLE = "weather unable";

  public static final String MSG_BY_FIELD_TYPE = "字段类型：1-supplier, 2-isOn(0/1), 3-code, 4-model, 5-name, 可模糊查询";
  public static final String MSG_BY_FIELD_KEYWORD = "字段关键字";
  public static final String MSG_BY_TIME_START = "区间起点，格式：yyyy-MM-dd HH:mm:ss";
  public static final String MSG_BY_TIME_END = "区间终点，格式：yyyy-MM-dd HH:mm:ss";

  /***************** Regexp *****************/
  public static final String RGP_DATETIME_FORMAT = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29) ([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
  public static final String RGP_DATE_FORMAT = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)";

  /***************** Format *****************/
  public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
  public static final String FORMAT_DATE = "yyyy-MM-dd";
}
