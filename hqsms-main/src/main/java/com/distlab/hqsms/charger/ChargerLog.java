package com.distlab.hqsms.charger;

import com.alibaba.fastjson.annotation.JSONField;
import com.distlab.hqsms.edge.DeviceLog;
import com.distlab.hqsms.strategy.RuleEvent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "eg_charger_log")
@ApiModel("充电桩状态数据")
public class ChargerLog extends DeviceLog {

  private static final Logger logger = LoggerFactory.getLogger(ChargerLog.class);
  private static final SimpleDateFormat STATE_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

  @Transient
  List<RuleEvent> ruleEvents;

  @ApiModelProperty(name = "输出电压")
  @JSONField(name = "SCDY")
  private Float outVoltage;

  @ApiModelProperty(name = "输出电流")
  @JSONField(name = "SCDL")
  private Float outCurrent;

  @ApiModelProperty(name = "已充电量")
  @JSONField(name = "YCDL")
  private Float totalEnergy;

  @ApiModelProperty(name = "工作状态")
  @JSONField(name = "GZZT")
  private Integer workStatus;

  @ApiModelProperty(name = "充电时间")
  @JSONField(name = "CDSJ")
  private Integer chargingTime;

  @ApiModelProperty(name = "剩余时间")
  @JSONField(name = "SYSJ")
  private Integer residualTime;

  @ApiModelProperty(name = "SOC")
  @JSONField(name = "SOC")
  private Integer soc;

  @ApiModelProperty(name = "电池最高温度")
  @JSONField(name = "DCZGWD")
  private Float maxBatteryTemp;

  @ApiModelProperty(name = "远程采集时间")
  @JSONField(name = "CJSJ")
  private Date remoteTime;

  public List<RuleEvent> getRuleEvents() {
    return ruleEvents;
  }

  public void setRuleEvents(List<RuleEvent> ruleEvents) {
    this.ruleEvents = ruleEvents;
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

  public Float getTotalEnergy() {
    return totalEnergy;
  }

  public void setTotalEnergy(Float totalEnergy) {
    this.totalEnergy = totalEnergy;
  }

  public Integer getWorkStatus() {
    return workStatus;
  }

  public void setWorkStatus(Integer workStatus) {
    this.workStatus = workStatus;
  }

  public Integer getChargingTime() {
    return chargingTime;
  }

  public void setChargingTime(Integer chargingTime) {
    this.chargingTime = chargingTime;
  }

  public Integer getResidualTime() {
    return residualTime;
  }

  public void setResidualTime(Integer residualTime) {
    this.residualTime = residualTime;
  }

  public Integer getSoc() {
    return soc;
  }

  public void setSoc(Integer soc) {
    this.soc = soc;
  }

  public Float getMaxBatteryTemp() {
    return maxBatteryTemp;
  }

  public void setMaxBatteryTemp(Float maxBatteryTemp) {
    this.maxBatteryTemp = maxBatteryTemp;
  }

  public Date getRemoteTime() {
    return remoteTime;
  }

  /**
   * 字符串转DATE
   *
   * @param remoteTime  远程采集时间，格式：yyyy/MM/dd HH:mm:ss
   */
  public void setRemoteTime(String remoteTime) {
    try {
      this.remoteTime = STATE_DATE_FORMAT.parse(remoteTime);
    } catch (ParseException e) {
      logger.error("remote time parse error: " + e.getMessage());
      this.remoteTime = null;
    }
  }
}
