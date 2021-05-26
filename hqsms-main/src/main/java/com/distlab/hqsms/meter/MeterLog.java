package com.distlab.hqsms.meter;

import com.distlab.hqsms.edge.DeviceLog;
import com.distlab.hqsms.strategy.RuleEvent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigInteger;
import java.util.List;

@ApiModel("电能表数据信息")
@Entity
@Table(name = "eg_meter_log")
public class MeterLog extends DeviceLog {

  @Transient
  List<RuleEvent> ruleEvents;
  @ApiModelProperty("A相电压")
  private Float aVoltage;
  @ApiModelProperty("B相电压")
  private Float bVoltage;
  @ApiModelProperty("C相电压")
  private Float cVoltage;
  @ApiModelProperty("A-B线电压")
  private Float abVoltage;
  @ApiModelProperty("C-B线电压")
  private Float cbVoltage;
  @ApiModelProperty("A-C线电压")
  private Float acVoltage;
  @ApiModelProperty("A相电流")
  private Float aCurrent;
  @ApiModelProperty("B相电流")
  private Float bCurrent;
  @ApiModelProperty("C相电流")
  private Float cCurrent;
  @ApiModelProperty("剩余电流")
  private Float residualCurrent;
  @ApiModelProperty("A相电能")
  private Float aEnergy;
  @ApiModelProperty("B相电能")
  private Float bEnergy;
  @ApiModelProperty("C相电能")
  private Float cEnergy;
  @ApiModelProperty("总电能")
  private Float totalEnergy;
  @ApiModelProperty("A相功率")
  private Float aPower;
  @ApiModelProperty("B相功率")
  private Float bPower;
  @ApiModelProperty("C相功率")
  private Float cPower;
  @ApiModelProperty("总功率")
  private Float totalPower;
  @ApiModelProperty("A相功率因素")
  private Float aFactor;
  @ApiModelProperty("B相功率因素")
  private Float bFactor;
  @ApiModelProperty("C相功率因素")
  private Float cFactor;
  @ApiModelProperty("总功率因素")
  private Float totalFactor;
  @ApiModelProperty("电网频率")
  private Float frequency;

  public List<RuleEvent> getRuleEvents() {
    return ruleEvents;
  }

  public void setRuleEvents(List<RuleEvent> ruleEvents) {
    this.ruleEvents = ruleEvents;
  }

  public Float getaVoltage() {
    return aVoltage;
  }

  public void setaVoltage(Float aVoltage) {
    this.aVoltage = aVoltage;
  }

  public Float getbVoltage() {
    return bVoltage;
  }

  public void setbVoltage(Float bVoltage) {
    this.bVoltage = bVoltage;
  }

  public Float getcVoltage() {
    return cVoltage;
  }

  public void setcVoltage(Float cVoltage) {
    this.cVoltage = cVoltage;
  }

  public Float getAbVoltage() {
    return abVoltage;
  }

  public void setAbVoltage(Float abVoltage) {
    this.abVoltage = abVoltage;
  }

  public Float getCbVoltage() {
    return cbVoltage;
  }

  public void setCbVoltage(Float cbVoltage) {
    this.cbVoltage = cbVoltage;
  }

  public Float getAcVoltage() {
    return acVoltage;
  }

  public void setAcVoltage(Float acVoltage) {
    this.acVoltage = acVoltage;
  }

  public Float getaCurrent() {
    return aCurrent;
  }

  public void setaCurrent(Float aCurrent) {
    this.aCurrent = aCurrent;
  }

  public Float getbCurrent() {
    return bCurrent;
  }

  public void setbCurrent(Float bCurrent) {
    this.bCurrent = bCurrent;
  }

  public Float getcCurrent() {
    return cCurrent;
  }

  public void setcCurrent(Float cCurrent) {
    this.cCurrent = cCurrent;
  }

  public Float getResidualCurrent() {
    return residualCurrent;
  }

  public void setResidualCurrent(Float residualCurrent) {
    this.residualCurrent = residualCurrent;
  }

  public Float getaEnergy() {
    return aEnergy;
  }

  public void setaEnergy(Float aEnergy) {
    this.aEnergy = aEnergy;
  }

  public Float getbEnergy() {
    return bEnergy;
  }

  public void setbEnergy(Float bEnergy) {
    this.bEnergy = bEnergy;
  }

  public Float getcEnergy() {
    return cEnergy;
  }

  public void setcEnergy(Float cEnergy) {
    this.cEnergy = cEnergy;
  }

  public Float getTotalEnergy() {
    return totalEnergy;
  }

  public void setTotalEnergy(Float totalEnergy) {
    this.totalEnergy = totalEnergy;
  }

  public Float getaPower() {
    return aPower;
  }

  public void setaPower(Float aPower) {
    this.aPower = aPower;
  }

  public Float getbPower() {
    return bPower;
  }

  public void setbPower(Float bPower) {
    this.bPower = bPower;
  }

  public Float getcPower() {
    return cPower;
  }

  public void setcPower(Float cPower) {
    this.cPower = cPower;
  }

  public Float getTotalPower() {
    return totalPower;
  }

  public void setTotalPower(Float totalPower) {
    this.totalPower = totalPower;
  }

  public Float getaFactor() {
    return aFactor;
  }

  public void setaFactor(Float aFactor) {
    this.aFactor = aFactor;
  }

  public Float getbFactor() {
    return bFactor;
  }

  public void setbFactor(Float bFactor) {
    this.bFactor = bFactor;
  }

  public Float getcFactor() {
    return cFactor;
  }

  public void setcFactor(Float cFactor) {
    this.cFactor = cFactor;
  }

  public Float getTotalFactor() {
    return totalFactor;
  }

  public void setTotalFactor(Float totalFactor) {
    this.totalFactor = totalFactor;
  }

  public Float getFrequency() {
    return frequency;
  }

  public void setFrequency(Float frequency) {
    this.frequency = frequency;
  }
}
