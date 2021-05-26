package com.distlab.hqsms.light;

import com.distlab.hqsms.edge.DeviceLog;
import com.distlab.hqsms.strategy.RuleEvent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigInteger;
import java.util.List;

@ApiModel("灯控数据信息")
@Entity
@Table(name = "eg_light_log")
public class LightLog extends DeviceLog {

  @Transient
  List<RuleEvent> ruleEvents;
  @ApiModelProperty("温度 单位1℃")
  private Integer temperature;
  @ApiModelProperty("输入电压 单位0.1V")
  private Float inVoltage;
  @ApiModelProperty("输入电流 单位0.001A")
  private Float inCurrent;
  @ApiModelProperty("输出电压 单位0.1V")
  private Float outVoltage;
  @ApiModelProperty("输出电流 单位0.001A")
  private Float outCurrent;
  @ApiModelProperty("有功功率 单位0.1W")
  private Float power;
  @ApiModelProperty("功率因数")
  private Float factor;
  @ApiModelProperty("总能耗 单位0.1WH")
  private Float energy;
  @ApiModelProperty("亮度")
  private Integer brightness;
  @ApiModelProperty("温度过高 0-正常 1-异常")
  private Boolean overTemperature;
  @ApiModelProperty("温度过低 0-正常 1-异常")
  private Boolean lowTemperature;
  @ApiModelProperty("无法启动 0-正常 1-异常")
  private Boolean unStart;
  @ApiModelProperty("短路 0-正常 1-异常")
  private Boolean shortCircuit;
  @ApiModelProperty("开路 0-正常 1-异常")
  private Boolean openLoop;
  @ApiModelProperty("功率过高 0-正常 1-异常")
  private Boolean overPower;
  @ApiModelProperty("电压过高 0-正常 1-异常")
  private Boolean overVoltage;
  @ApiModelProperty("电压过低 0-正常 1-异常")
  private Boolean underVoltage;

  public List<RuleEvent> getRuleEvents() {
    return ruleEvents;
  }

  public void setRuleEvents(List<RuleEvent> ruleEvents) {
    this.ruleEvents = ruleEvents;
  }

  public Integer getTemperature() {
    return temperature;
  }

  public void setTemperature(Integer temperature) {
    this.temperature = temperature;
  }

  public Float getInVoltage() {
    return inVoltage;
  }

  public void setInVoltage(Float inVoltage) {
    this.inVoltage = inVoltage;
  }

  public Float getInCurrent() {
    return inCurrent;
  }

  public void setInCurrent(Float inCurrent) {
    this.inCurrent = inCurrent;
  }

  public Float getOutVoltage() {
    return outVoltage;
  }

  public void setOutVoltage(Float outVoltage) {
    this.outVoltage = outVoltage;
  }

  public Float getOutCurrent() {
    return outCurrent;
  }

  public void setOutCurrent(Float outCurrent) {
    this.outCurrent = outCurrent;
  }

  public Float getPower() {
    return power;
  }

  public void setPower(Float power) {
    this.power = power;
  }

  public Float getFactor() {
    return factor;
  }

  public void setFactor(Float factor) {
    this.factor = factor;
  }

  public Float getEnergy() {
    return energy;
  }

  public void setEnergy(Float energy) {
    this.energy = energy;
  }

  public Integer getBrightness() {
    return brightness;
  }

  public void setBrightness(Integer brightness) {
    this.brightness = brightness;
  }

  public Boolean getOverTemperature() {
    return overTemperature;
  }

  public void setOverTemperature(Boolean overTemperature) {
    this.overTemperature = overTemperature;
  }

  public Boolean getLowTemperature() {
    return lowTemperature;
  }

  public void setLowTemperature(Boolean lowTemperature) {
    this.lowTemperature = lowTemperature;
  }

  public Boolean getUnStart() {
    return unStart;
  }

  public void setUnStart(Boolean unStart) {
    this.unStart = unStart;
  }

  public Boolean getShortCircuit() {
    return shortCircuit;
  }

  public void setShortCircuit(Boolean shortCircuit) {
    this.shortCircuit = shortCircuit;
  }

  public Boolean getOpenLoop() {
    return openLoop;
  }

  public void setOpenLoop(Boolean openLoop) {
    this.openLoop = openLoop;
  }

  public Boolean getOverPower() {
    return overPower;
  }

  public void setOverPower(Boolean overPower) {
    this.overPower = overPower;
  }

  public Boolean getOverVoltage() {
    return overVoltage;
  }

  public void setOverVoltage(Boolean overVoltage) {
    this.overVoltage = overVoltage;
  }

  public Boolean getUnderVoltage() {
    return underVoltage;
  }

  public void setUnderVoltage(Boolean underVoltage) {
    this.underVoltage = underVoltage;
  }
}
