//package com.distlab.hqsms.municipal;
//
//import com.distlab.hqsms.edge.DeviceLog;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//@ApiModel("市政液位设备数据")
//@Entity
////@Table(name = "eg_municipal_liquid")
//public class MunicipalLiquid extends DeviceLog {
//  /* 型号：CS-iTLLS-05 */
//  @ApiModelProperty(name = "市政终端Id")
//  private String municipalDevId;
//  @ApiModelProperty(name = "0 正常报文，2 告警报文")
//  private String messageType;
//  @ApiModelProperty(name = "消息序号")
//  private String messageNumber;
//  @ApiModelProperty(name = "终端状态")
//  private String terminalStatus;
//  @ApiModelProperty(name = "液位深度，单位mm，0为可能没有连接传感器")
//  private int liquidLevel;
//  @ApiModelProperty(name = "剩余电量")
//  private double battery;
//  @ApiModelProperty(name = "信号强度")
//  private int signal;
//  @ApiModelProperty(name = "告警状态")
//  private String alarmType;
//  /* 告警类型：
//   * voltage 低电量告警
//   * ack 云端无应答告警
//   * fault 无线模块出错告警
//   * sf 设备撤防
//   * upperLimit 液位井盖上限告警
//   * lowerLimit 液位井盖下限告警
//   * ep 外部电源不工作告警
//   * */
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
//  public int getLiquidLevel() {
//    return liquidLevel;
//  }
//
//  public void setLiquidLevel(int liquidLevel) {
//    this.liquidLevel = liquidLevel;
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
//  public String getAlarmType() {
//    return alarmType;
//  }
//
//  public void setAlarmType(String alarmType) {
//    this.alarmType = alarmType;
//  }
//}
