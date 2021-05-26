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
@Table(name = "eg_charger_order")
@ApiModel("充电桩订单数据")
public class ChargerOrder extends DeviceLog {

  private static final Logger logger = LoggerFactory.getLogger(ChargerLog.class);
  private static final SimpleDateFormat ORDER_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Transient
  List<RuleEvent> ruleEvents;
  @ApiModelProperty("开始充电时间")
  @JSONField(name = "StartTime")
  private Date startTime;
  @ApiModelProperty("结束充电时间")
  @JSONField(name = "EndTime")
  private Date endTime;
  @ApiModelProperty("充电量")
  @JSONField(name = "TotalPower")
  private Float totalPower;
  @ApiModelProperty("总金额")
  @JSONField(name = "TotalMoney")
  private Float totalMoney;

  public List<RuleEvent> getRuleEvents() {
    return ruleEvents;
  }

  public void setRuleEvents(List<RuleEvent> ruleEvents) {
    this.ruleEvents = ruleEvents;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    try {
      this.startTime = ORDER_DATE_FORMAT.parse(startTime);
    } catch (ParseException e) {
      logger.error("remote time parse error: " + e.getMessage());
      this.startTime = null;
    }
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    try {
      this.endTime = ORDER_DATE_FORMAT.parse(endTime);
    } catch (ParseException e) {
      logger.error("remote time parse error: " + e.getMessage());
      this.endTime = null;
    }
  }

  public Float getTotalPower() {
    return totalPower;
  }

  public void setTotalPower(Float cumulativePower) {
    this.totalPower = cumulativePower;
  }

  public Float getTotalMoney() {
    return totalMoney;
  }

  public void setTotalMoney(Float totalMoney) {
    this.totalMoney = totalMoney;
  }
}
