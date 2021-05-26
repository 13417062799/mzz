package com.distlab.hqsms.strategy;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.stream.Stream;

@ApiModel("策略事件信息")
@Entity
@Table(name = "eg_rule_event")
public class RuleEvent {
  @ApiModelProperty("ID")
  @Id
  private BigInteger id;
  @ApiModelProperty("策略ID")
  private BigInteger ruleId;
  @ApiModelProperty("设备数据ID")
  private BigInteger deviceLogId;
  @ApiModelProperty("设备数据类型")
  private Rule.RuleDeviceLogType deviceLogType;
  @ApiModelProperty("事件等级")
  private Rule.RuleLevel level;
  @ApiModelProperty("事件名称")
  private Rule.RuleName name;
  @ApiModelProperty("经度")
  private Float latitude;
  @ApiModelProperty("纬度")
  private Float longitude;
  private RuleEventDone isDone;
  private Date createdAt;

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public BigInteger getRuleId() {
    return ruleId;
  }

  public void setRuleId(BigInteger ruleId) {
    this.ruleId = ruleId;
  }

  public BigInteger getDeviceLogId() {
    return deviceLogId;
  }

  public void setDeviceLogId(BigInteger deviceLogId) {
    this.deviceLogId = deviceLogId;
  }

  public Float getLatitude() {
    return latitude;
  }

  public void setLatitude(Float latitude) {
    this.latitude = latitude;
  }

  public Float getLongitude() {
    return longitude;
  }

  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }

  public RuleEventDone getIsDone() {
    return isDone;
  }

  public void setIsDone(RuleEventDone isDone) {
    this.isDone = isDone;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Rule.RuleDeviceLogType getDeviceLogType() {
    return deviceLogType;
  }

  public void setDeviceLogType(Rule.RuleDeviceLogType deviceLogType) {
    this.deviceLogType = deviceLogType;
  }

  public Rule.RuleLevel getLevel() {
    return level;
  }

  public void setLevel(Rule.RuleLevel level) {
    this.level = level;
  }

  public Rule.RuleName getName() {
    return name;
  }

  public void setName(Rule.RuleName name) {
    this.name = name;
  }

  public enum RuleEventDone {
    NO("0"),
    YES("1");

    private final String code;

    private RuleEventDone(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

}

@Converter(autoApply = true)
class RuleEventDoneConvertor implements AttributeConverter<RuleEvent.RuleEventDone, String> {
  @Override
  public String convertToDatabaseColumn(RuleEvent.RuleEventDone value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public RuleEvent.RuleEventDone convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(RuleEvent.RuleEventDone.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

