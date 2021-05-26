//package com.distlab.hqsms.municipal;
//
//import com.distlab.hqsms.edge.DeviceLog;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//@ApiModel("市政井盖设备数据")
//@Entity
////@Table(name = "eg_municipal_manhole")
//public class MunicipalManhole extends DeviceLog {
//  /* 型号：CS-iTWM-05LW */
//  @ApiModelProperty(name = "市政终端Id")
//  private String municipalDevId;
//  @ApiModelProperty(name = "0 正常报文，2 告警报文")
//  private String messageType;
//  @ApiModelProperty(name = "消息序号")
//  private String messageNumber;
//  @ApiModelProperty(name = "终端状态")
//  private String terminalStatus;
//  @ApiModelProperty(name = "基准角度，单位°")
//  private int baseAngle;
//  @ApiModelProperty(name = "当前角度，单位°")
//  private int currentAngle;
//  @ApiModelProperty(name = "剩余电量")
//  private double battery;
//  @ApiModelProperty(name = "信号强度")
//  private int signal;
//  @ApiModelProperty(name = "布防状态，0 布防，1 撤防")
//  private String sfStatus;
//  @ApiModelProperty(name = "告警状态")
//  private String alarmType;
//  /* 告警类型：
//  * voltage 低电量告警
//  * angle 角度告警
//  * ack 云端无应答告警
//  * waterLevel 水位告警
//  * fault 故障告警
//  * gas 天然气浓度告警
//  * sf 设备撤防
//  * sa 终端定期更新背景角度
//  * */
//
//  public String getMunicipalDevId() {
//    return municipalDevId;
//  }
//
//  public void setMunicipalDevId(String municipalDevId) {
//    this.municipalDevId = municipalDevId;
//  }
//
//  public String getMessageType() {
//    return messageType;
//  }
//
//  public void setMessageType(String messageType) {
//    this.messageType = messageType;
//  }
//
//  public String getMessageNumber() {
//    return messageNumber;
//  }
//
//  public void setMessageNumber(String messageNumber) {
//    this.messageNumber = messageNumber;
//  }
//
//  public String getTerminalStatus() {
//    return terminalStatus;
//  }
//
//  public void setTerminalStatus(String terminalStatus) {
//    this.terminalStatus = terminalStatus;
//  }
//
//  public int getBaseAngle() {
//    return baseAngle;
//  }
//
//  public void setBaseAngle(int baseAngle) {
//    this.baseAngle = baseAngle;
//  }
//
//  public int getCurrentAngle() {
//    return currentAngle;
//  }
//
//  public void setCurrentAngle(int currentAngle) {
//    this.currentAngle = currentAngle;
//  }
//
//  public double getBattery() {
//    return battery;
//  }
//
//  public void setBattery(double battery) {
//    this.battery = battery;
//  }
//
//  public int getSignal() {
//    return signal;
//  }
//
//  public void setSignal(int signal) {
//    this.signal = signal;
//  }
//
//  public String getSfStatus() {
//    return sfStatus;
//  }
//
//  public void setSfStatus(String sfStatus) {
//    this.sfStatus = sfStatus;
//  }
//
//  public String getAlarmType() {
//    return alarmType;
//  }
//
//  public void setAlarmType(String alarmType) {
//    this.alarmType = alarmType;
//  }
//}
