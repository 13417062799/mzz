package com.distlab.hqsms.strategy;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.stream.Stream;

@ApiModel("策略信息")
@Entity
@Table(name = "eg_rule")
public class Rule {
  @ApiModelProperty("ID")
  @Id
  private BigInteger id;
  @ApiModelProperty("设备ID")
  private BigInteger deviceId;
  @ApiModelProperty("设备类型")
  private RuleDeviceType deviceType;
  @ApiModelProperty("设备数据类型")
  private RuleDeviceLogType deviceLogType;
  @ApiModelProperty(value = "策略名称")
  private RuleName name;
  @ApiModelProperty("JSON格式的参考值。每当接收到设备数据，系统根据策略名称尝试以设备数据匹配参考值，如果匹配成功，则会创建策略事件。\n" +
    "策略名称不同，参考值字段也不同: \n" +
    "CAMERA_HUMAN_APPEAR: { \"codeDistanceMin\": 0.6, \"codeDistanceMax\": 1.0, \"code\": \"[...]\", \"faceScoreMin\": 0.6, \"faceScoreMax\": 1.0 }\n" +
    "CAMERA_VEHICLE_APPEAR: { \"plate\": \"粤A12345\", \"plateScoreMin\": 0.6, \"plateScoreMax\": 1.0 }\n")
  private String reference;
  @ApiModelProperty("策略等级")
  private RuleLevel level;
  @ApiModelProperty("创建时间")
  private Date createdAt;

  /**
   * 从枚举值别名获取枚举值的通用方法
   *
   * @param <T>  枚举值
   * @param c    枚举值类型
   * @param code 枚举值别名
   * @return corresponding enum, or null
   */
  public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String code) {
    if (c != null && code != null) {
      try {
        return Enum.valueOf(c, code.trim().toUpperCase());
      } catch (IllegalArgumentException ex) {
        ex.printStackTrace();
        return null;
      }
    }
    return null;
  }

  public RuleDeviceLogType getDeviceLogType() {
    return deviceLogType;
  }

  public void setDeviceLogType(RuleDeviceLogType deviceLogType) {
    this.deviceLogType = deviceLogType;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public RuleDeviceType getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(RuleDeviceType deviceType) {
    this.deviceType = deviceType;
  }

  public RuleLevel getLevel() {
    return level;
  }

  public void setLevel(RuleLevel level) {
    this.level = level;
  }

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public BigInteger getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(BigInteger deviceId) {
    this.deviceId = deviceId;
  }

  public RuleName getName() {
    return name;
  }

  public void setName(RuleName name) {
    this.name = name;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public enum RuleName {
    CAMERA_HUMAN_APPEAR("1"),
    CAMERA_VEHICLE_APPEAR("2"),
    CAMERA_OFFLINE("3");

    private final String code;

    RuleName(String code) {
      this.code = code;
    }

    public static RuleName fromCode(String code) {
      return getEnumFromString(RuleName.class, code);
    }

    public String getCode() {
      return code;
    }
  }

  public enum RuleDeviceType {
    CHARGER("1"),
    WIFI("2"),
    SCREEN("3"),
    ALARM("4"),
    LIGHT("5"),
    WEATHER("6"),
    BROADCAST("7"),
    CAMERA("8"),
    ENERGY("9"),
    METER("10"),
    POLE("11"),
    SERVER("12");

    private final String code;

    RuleDeviceType(String code) {
      this.code = code;
    }

    public static RuleDeviceType fromCode(String code) {
      return getEnumFromString(RuleDeviceType.class, code);
    }

    public String getCode() {
      return code;
    }
  }

  public enum RuleLevel {
    LEVEL1_1("0"),
    LEVEL1_2("1"),
    LEVEL1_3("2"),
    LEVEL2("3"),
    LEVEL3("4");

    private final String code;

    private RuleLevel(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum RuleDeviceLogType {
    RAW("0"),
    VEHICLE("1"),
    HUMAN("2"),
    ORDER("3");

    private final String code;

    private RuleDeviceLogType(String code) {
      this.code = code;
    }

    public static RuleDeviceLogType fromCode(String code) {
      return getEnumFromString(RuleDeviceLogType.class, code);
    }

    public String getCode() {
      return code;
    }
  }

}

@Converter(autoApply = true)
class RuleNameConvertor implements AttributeConverter<Rule.RuleName, String> {
  @Override
  public String convertToDatabaseColumn(Rule.RuleName value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public Rule.RuleName convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(Rule.RuleName.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class RuleDeviceTypeConvertor implements AttributeConverter<Rule.RuleDeviceType, String> {
  @Override
  public String convertToDatabaseColumn(Rule.RuleDeviceType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public Rule.RuleDeviceType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(Rule.RuleDeviceType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class RuleLevelConvertor implements AttributeConverter<Rule.RuleLevel, String> {
  @Override
  public String convertToDatabaseColumn(Rule.RuleLevel value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public Rule.RuleLevel convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(Rule.RuleLevel.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class RuleDeviceLogTypeConvertor implements AttributeConverter<Rule.RuleDeviceLogType, String> {
  @Override
  public String convertToDatabaseColumn(Rule.RuleDeviceLogType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public Rule.RuleDeviceLogType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(Rule.RuleDeviceLogType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}