package com.distlab.hqsms.camera;


import com.distlab.hqsms.edge.DeviceLog;
import com.distlab.hqsms.strategy.RuleEvent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@ApiModel("摄像头设备车辆数据")
@Entity
@Table(name = "eg_camera_vehicle")
public class CameraVehicle extends DeviceLog {
  @Transient
  List<RuleEvent> ruleEvents;
  @ApiModelProperty("截图时间，设备上的时间，非系统的时间")
  private Date capturedAt;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("车牌类型")
  private String plateType;
  @ApiModelProperty("车牌颜色")
  private String plateColor;
  @ApiModelProperty("车牌坐标X")
  private Float plateRectX;
  @ApiModelProperty("车牌坐标Y")
  private Float plateRectY;
  @ApiModelProperty("车牌坐标W")
  private Float plateRectW;
  @ApiModelProperty("车牌坐标H")
  private Float plateRectH;
  @ApiModelProperty("车牌亮度")
  private Integer plateBright;
  @ApiModelProperty("车牌字符长度")
  private Integer plateCharsLength;
  @ApiModelProperty("车牌字符串")
  private String plateChars;
  @ApiModelProperty("车牌字符串置信度")
  private String plateCharsScore;
  @ApiModelProperty("车牌置信度")
  private Float plateEntireScore;
  @ApiModelProperty("车辆类型")
  private String vehicleType;
  @ApiModelProperty("车辆颜色")
  private String vehicleColor;
  @ApiModelProperty("车辆颜色深度")
  private String vehicleColorDepth;
  @ApiModelProperty("车辆品牌")
  private String vehicleLogoRecog;
  @ApiModelProperty("车辆子品牌")
  private String vehicleSubLogoRecog;
  @ApiModelProperty("车辆型号")
  private String vehicleModel;
  @ApiModelProperty("文件信息")
  @Transient
  private List<String> files;

  public List<String> getFiles() {
    return files;
  }

  public void setFiles(List<String> files) {
    this.files = files;
  }

  public String getPlateType() {
    return plateType;
  }

  public void setPlateType(String plateType) {
    this.plateType = plateType;
  }

  public String getPlateColor() {
    return plateColor;
  }

  public void setPlateColor(String plateColor) {
    this.plateColor = plateColor;
  }

  public Float getPlateRectX() {
    return plateRectX;
  }

  public void setPlateRectX(Float plateRectX) {
    this.plateRectX = plateRectX;
  }

  public Float getPlateRectY() {
    return plateRectY;
  }

  public void setPlateRectY(Float plateRectY) {
    this.plateRectY = plateRectY;
  }

  public Float getPlateRectW() {
    return plateRectW;
  }

  public void setPlateRectW(Float plateRectW) {
    this.plateRectW = plateRectW;
  }

  public Float getPlateRectH() {
    return plateRectH;
  }

  public void setPlateRectH(Float plateRectH) {
    this.plateRectH = plateRectH;
  }

  public Integer getPlateBright() {
    return plateBright;
  }

  public void setPlateBright(Integer plateBright) {
    this.plateBright = plateBright;
  }

  public Integer getPlateCharsLength() {
    return plateCharsLength;
  }

  public void setPlateCharsLength(Integer plateCharsLength) {
    this.plateCharsLength = plateCharsLength;
  }

  public String getPlateChars() {
    return plateChars;
  }

  public void setPlateChars(String plateChars) {
    this.plateChars = plateChars;
  }

  public String getPlateCharsScore() {
    return plateCharsScore;
  }

  public void setPlateCharsScore(String plateCharsScore) {
    this.plateCharsScore = plateCharsScore;
  }

  public Float getPlateEntireScore() {
    return plateEntireScore;
  }

  public void setPlateEntireScore(Float plateEntireScore) {
    this.plateEntireScore = plateEntireScore;
  }

  public String getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(String vehicleType) {
    this.vehicleType = vehicleType;
  }

  public String getVehicleColor() {
    return vehicleColor;
  }

  public void setVehicleColor(String vehicleColor) {
    this.vehicleColor = vehicleColor;
  }

  public String getVehicleColorDepth() {
    return vehicleColorDepth;
  }

  public void setVehicleColorDepth(String vehicleColorDepth) {
    this.vehicleColorDepth = vehicleColorDepth;
  }

  public String getVehicleLogoRecog() {
    return vehicleLogoRecog;
  }

  public void setVehicleLogoRecog(String vehicleLogoRecog) {
    this.vehicleLogoRecog = vehicleLogoRecog;
  }

  public String getVehicleSubLogoRecog() {
    return vehicleSubLogoRecog;
  }

  public void setVehicleSubLogoRecog(String vehicleSubLogoRecog) {
    this.vehicleSubLogoRecog = vehicleSubLogoRecog;
  }

  public String getVehicleModel() {
    return vehicleModel;
  }

  public void setVehicleModel(String vehicleModel) {
    this.vehicleModel = vehicleModel;
  }

  public Date getCapturedAt() {
    return capturedAt;
  }

  public void setCapturedAt(Date capturedAt) {
    this.capturedAt = capturedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public List<RuleEvent> getRuleEvents() {
    return ruleEvents;
  }

  public void setRuleEvents(List<RuleEvent> ruleEvents) {
    this.ruleEvents = ruleEvents;
  }

  public enum CameraVehicleColorDepthType {
    DARK("0"),
    LIGHT("1");

    private final String code;

    private CameraVehicleColorDepthType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum CameraVehicleColorType {
    UNKNOWN("0"),
    WHITE("1"),
    SILVER("2"),
    GREY("3"),
    BLACK("4"),
    RED("5"),
    DARK_BLUE("6"),
    BLUE("7"),
    YELLOW("8"),
    GREEN("9"),
    BROWN("10"),
    PINK("11"),
    PURPLE("12"),
    DEEP_GREY("13"),
    CYAN("14"),
    OTHER("255");

    private final String code;

    private CameraVehicleColorType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum CameraVehiclePlateColorType {
    BLUE("0"),
    YELLOW("1"),
    WHITE("2"),
    BLACK("3"),
    GREEN("4"),
    AVIATION_BLACK("5"),
    OTHER("255");

    private final String code;

    private CameraVehiclePlateColorType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum CameraVehiclePlateType {
    STANDARD_CAR("0"),
    TYPE02_CAR("1"),
    ARMED_POLICE_CAR("2"),
    POLICE_CAR("3"),
    DOUBLE_ROW_CAR("4"),
    EMBASSY_CAR("5"),
    AGRICULTURAL_CAR("6"),
    MOTORBIKE("7"),
    NEW_ENERGY_CAR("8"),
    OTHER("255");

    private final String code;

    private CameraVehiclePlateType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum CameraVehicleType {
    UNKNOWN("0"),
    SMALL_CAR("1"),
    BIG_CAR("2"),
    PEDESTRIAN("3"),
    TWO_WHEEL("4"),
    THREE_WHEEL("5"),
    AGRICULTURAL_CAR("6"),
    OTHER("255");

    private final String code;

    private CameraVehicleType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

}

@Converter(autoApply = true)
class CameraVehicleColorDepthConvertor implements AttributeConverter<CameraVehicle.CameraVehicleColorDepthType, String> {
  @Override
  public String convertToDatabaseColumn(CameraVehicle.CameraVehicleColorDepthType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public CameraVehicle.CameraVehicleColorDepthType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(CameraVehicle.CameraVehicleColorDepthType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class CameraVehicleColorTypeConvertor implements AttributeConverter<CameraVehicle.CameraVehicleColorType, String> {
  @Override
  public String convertToDatabaseColumn(CameraVehicle.CameraVehicleColorType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public CameraVehicle.CameraVehicleColorType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(CameraVehicle.CameraVehicleColorType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class CameraVehiclePlateColorTypeConvertor implements AttributeConverter<CameraVehicle.CameraVehiclePlateColorType, String> {
  @Override
  public String convertToDatabaseColumn(CameraVehicle.CameraVehiclePlateColorType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public CameraVehicle.CameraVehiclePlateColorType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(CameraVehicle.CameraVehiclePlateColorType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class CameraVehiclePlateTypeConvertor implements AttributeConverter<CameraVehicle.CameraVehiclePlateType, String> {
  @Override
  public String convertToDatabaseColumn(CameraVehicle.CameraVehiclePlateType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public CameraVehicle.CameraVehiclePlateType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(CameraVehicle.CameraVehiclePlateType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class CameraVehicleTypeConvertor implements AttributeConverter<CameraVehicle.CameraVehicleType, String> {
  @Override
  public String convertToDatabaseColumn(CameraVehicle.CameraVehicleType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public CameraVehicle.CameraVehicleType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(CameraVehicle.CameraVehicleType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}