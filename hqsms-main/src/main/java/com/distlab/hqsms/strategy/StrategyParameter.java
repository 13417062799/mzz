package com.distlab.hqsms.strategy;

import java.math.BigInteger;
import java.util.Map;

public class StrategyParameter {
  // 边缘服务器ID
  BigInteger serverId;
  // 灯杆ID
  BigInteger poleId;
  // 设备类型
  Rule.RuleDeviceType deviceType;
  // 设备ID
  BigInteger deviceId;
  // 设备日志类型
  Rule.RuleDeviceLogType deviceLogType;
  // 设备日志ID
  BigInteger deviceLogId;
  // 设备日志经度
  Float deviceLogLongitude;
  // 设备日志纬度
  Float deviceLogLatitude;
  // 设备日志匹配值
  Map<String, String> values;

  public BigInteger getServerId() {
    return serverId;
  }

  public void setServerId(BigInteger serverId) {
    this.serverId = serverId;
  }

  public BigInteger getPoleId() {
    return poleId;
  }

  public void setPoleId(BigInteger poleId) {
    this.poleId = poleId;
  }

  public Rule.RuleDeviceType getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(Rule.RuleDeviceType deviceType) {
    this.deviceType = deviceType;
  }

  public BigInteger getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(BigInteger deviceId) {
    this.deviceId = deviceId;
  }

  public Rule.RuleDeviceLogType getDeviceLogType() {
    return deviceLogType;
  }

  public void setDeviceLogType(Rule.RuleDeviceLogType deviceLogType) {
    this.deviceLogType = deviceLogType;
  }

  public BigInteger getDeviceLogId() {
    return deviceLogId;
  }

  public void setDeviceLogId(BigInteger deviceLogId) {
    this.deviceLogId = deviceLogId;
  }

  public Float getDeviceLogLongitude() {
    return deviceLogLongitude;
  }

  public void setDeviceLogLongitude(Float deviceLogLongitude) {
    this.deviceLogLongitude = deviceLogLongitude;
  }

  public Float getDeviceLogLatitude() {
    return deviceLogLatitude;
  }

  public void setDeviceLogLatitude(Float deviceLogLatitude) {
    this.deviceLogLatitude = deviceLogLatitude;
  }

  public Map<String, String> getValues() {
    return values;
  }

  public void setValues(Map<String, String> values) {
    this.values = values;
  }
}
