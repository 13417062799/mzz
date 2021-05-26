//package com.distlab.hqsms.municipal;
//
//import com.distlab.hqsms.edge.DeviceLog;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//@ApiModel("市政垃圾桶设备数据")
//@Entity
////@Table(name = "eg_municipal_trashcan")
//public class MunicipalTrashcan extends DeviceLog {
//  /* 型号：CS-iTRF-02 */
//
//  @ApiModelProperty(name = "0 正常报文，2 告警报文")
//  private String messageType;
//  @ApiModelProperty(name = "消息序号")
//  private String messageNumber;
//  @ApiModelProperty(name = "终端状态")
//  private String terminalStatus;
//  @ApiModelProperty(name = "桶A与垃圾表面距离，单位cm")
//  private int deepA;
//  @ApiModelProperty(name = "桶B与垃圾表面距离，单位cm")
//  private int deepB;
//  @ApiModelProperty(name = "剩余电量")
//  private double battery;
//  @ApiModelProperty(name = "信号强度")
//  private int signal;
//  @ApiModelProperty(name = "告警状态")
//  private String alarmType;
//  /* 告警类型：
//   * lba 低电量告警
//   * acka 云端无应答告警
//   * wma 无线模块出错告警
//   * sf 设备撤防
//   * barrelA 桶A告警
//   * barrelB 桶B告警
//   * */
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
//  public int getDeepA() {
//    return deepA;
//  }
//
//  public void setDeepA(int deepA) {
//    this.deepA = deepA;
//  }
//
//  public int getDeepB() {
//    return deepB;
//  }
//
//  public void setDeepB(int deepB) {
//    this.deepB = deepB;
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
