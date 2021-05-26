package com.distlab.hqsms.common.exception;

public enum MessageEnum {

  /*********** DEVICE **********/
  ALARM("alarm"),
  BROADCAST("broadcast"),
  BROADCAST_ID("broadcast Id"),
  BROADCAST_CONTENT("broadcast content"),
  BROADCAST_PLAN("broadcast plan"),
  CHARGER("charger"),
  CHARGER_ID("charger Id"),
  CHARGER_LOG("charger log"),
  CHARGER_ORDER("charger order"),
  LIGHT("light"),
  LIGHT_ID("light id"),
  LIGHT_LOG("light log"),
  METER("meter"),
  METER_ID("meter Id"),
  METER_LOG("meter log"),
  SCREEN("screen"),
  SCREEN_ID("screen Id"),
  SCREEN_CONTENT("screen content"),
  SCREEN_PLAN("screen plan"),
  CAMERA("camera"),
  CAMERA_ID("camera id"),
  CAMERA_LOG("camera log"),
  CAMERA_HUMAN("camera human"),
  CAMERA_VEHICLE("camera vehicle"),
  WEATHER("weather"),
  WEATHER_LOG("weather log"),
  WIFI("wifi"),
  WIFI_LOG("wifi log"),
  POLE("pole"),
  SERVER("server"),
  RULE("rule"),
  RULE_EVENT("rule event"),


  /*********** SOURCE **********/
  FILE("file"),

  /************ OTHER *********/
  SERVER_IP("server ip"),
  IP("ip"),
  MEDIA_FILE("media file"),
  ;

  private final String messageType;

  MessageEnum(String messageType) {
    this.messageType = messageType;
  }

  public String getMessageType() {
    return messageType;
  }
}
