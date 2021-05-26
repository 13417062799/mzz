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

@ApiModel("摄像头设备人员数据")
@Entity
@Table(name = "eg_camera_human")
public class CameraHuman extends DeviceLog {
  @Transient
  List<RuleEvent> ruleEvents;
  @ApiModelProperty("截图时间，设备上的时间，非系统的时间")
  private Date capturedAt;
  @ApiModelProperty("人脸置信度")
  private Float faceScore;
  @ApiModelProperty("人脸坐标X")
  private Float faceRectX;
  @ApiModelProperty("人脸坐标Y")
  private Float faceRectY;
  @ApiModelProperty("人脸坐标W")
  private Float faceRectW;
  @ApiModelProperty("人脸坐标H")
  private Float faceRectH;
  @ApiModelProperty("人员性别，测试设备不支持，忽略")
  private CameraHumanSexType humanSex;
  @ApiModelProperty("人员是否戴眼镜，测试设备不支持，忽略")
  private CameraHumanGlassType humanGlass;
  @ApiModelProperty("人员是否戴口罩，测试设备不支持，忽略")
  private CameraHumanMaskType humanMask;
  @ApiModelProperty("人员是否微笑，测试设备不支持，忽略")
  private CameraHumanSmileType humanSmile;
  @ApiModelProperty("人员是否留胡子，测试设备不支持，忽略")
  private CameraHumanBeardType humanBeard;
  @ApiModelProperty("人员是否戴帽子，测试设备不支持，忽略")
  private CameraHumanHatType humanHat;
  @ApiModelProperty("文件信息")
  @Transient
  private List<String> files;
  @Transient
  private String faceCode;

  public String getFaceCode() {
    return faceCode;
  }

  public void setFaceCode(String faceCode) {
    this.faceCode = faceCode;
  }

  public List<String> getFiles() {
    return files;
  }

  public void setFiles(List<String> files) {
    this.files = files;
  }

  public Float getFaceScore() {
    return faceScore;
  }

  public void setFaceScore(Float faceScore) {
    this.faceScore = faceScore;
  }

  public Float getFaceRectX() {
    return faceRectX;
  }

  public void setFaceRectX(Float faceRectX) {
    this.faceRectX = faceRectX;
  }

  public Float getFaceRectY() {
    return faceRectY;
  }

  public void setFaceRectY(Float faceRectY) {
    this.faceRectY = faceRectY;
  }

  public Float getFaceRectW() {
    return faceRectW;
  }

  public void setFaceRectW(Float faceRectW) {
    this.faceRectW = faceRectW;
  }

  public Float getFaceRectH() {
    return faceRectH;
  }

  public void setFaceRectH(Float faceRectH) {
    this.faceRectH = faceRectH;
  }

  public CameraHumanSexType getHumanSex() {
    return humanSex;
  }

  public void setHumanSex(CameraHumanSexType humanSex) {
    this.humanSex = humanSex;
  }

  public CameraHumanGlassType getHumanGlass() {
    return humanGlass;
  }

  public void setHumanGlass(CameraHumanGlassType humanGlass) {
    this.humanGlass = humanGlass;
  }

  public CameraHumanMaskType getHumanMask() {
    return humanMask;
  }

  public void setHumanMask(CameraHumanMaskType humanMask) {
    this.humanMask = humanMask;
  }

  public CameraHumanSmileType getHumanSmile() {
    return humanSmile;
  }

  public void setHumanSmile(CameraHumanSmileType humanSmile) {
    this.humanSmile = humanSmile;
  }

  public CameraHumanBeardType getHumanBeard() {
    return humanBeard;
  }

  public void setHumanBeard(CameraHumanBeardType humanBeard) {
    this.humanBeard = humanBeard;
  }

  public CameraHumanHatType getHumanHat() {
    return humanHat;
  }

  public void setHumanHat(CameraHumanHatType humanHat) {
    this.humanHat = humanHat;
  }

  public Date getCapturedAt() {
    return capturedAt;
  }

  public void setCapturedAt(Date capturedAt) {
    this.capturedAt = capturedAt;
  }

  public List<RuleEvent> getRuleEvents() {
    return ruleEvents;
  }

  public void setRuleEvents(List<RuleEvent> ruleEvents) {
    this.ruleEvents = ruleEvents;
  }

  public enum CameraHumanBeardType {
    UNKNOWN("0"),
    NO_HAS("1"),
    HAS("2"),
    OTHER("255");

    private final String code;

    private CameraHumanBeardType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum CameraHumanGlassType {
    NO_WEARING("1"),
    WEARING("2"),
    OTHER("255");

    private final String code;

    private CameraHumanGlassType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum CameraHumanHatType {
    UNKNOWN("0"),
    NO_HAS("1"),
    HAS("2"),
    OTHER("255");

    private final String code;

    CameraHumanHatType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum CameraHumanMaskType {
    UNKNOWN("0"),
    NO_WEARING("1"),
    WEARING("2"),
    OTHER("255");

    private final String code;

    CameraHumanMaskType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum CameraHumanSexType {
    UNKNOWN("0"),
    MALE("1"),
    FEMALE("2"),
    OTHER("255");

    private final String code;

    CameraHumanSexType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum CameraHumanSmileType {
    UNKNOWN("0"),
    NO_SMILING("1"),
    SMILING("2"),
    OTHER("255");

    private final String code;

    CameraHumanSmileType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }
}

@Converter(autoApply = true)
class CameraHumanBeardConvertor implements AttributeConverter<CameraHuman.CameraHumanBeardType, String> {
  @Override
  public String convertToDatabaseColumn(CameraHuman.CameraHumanBeardType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public CameraHuman.CameraHumanBeardType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(CameraHuman.CameraHumanBeardType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class CameraHumanGlassConvertor implements AttributeConverter<CameraHuman.CameraHumanGlassType, String> {
  @Override
  public String convertToDatabaseColumn(CameraHuman.CameraHumanGlassType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public CameraHuman.CameraHumanGlassType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(CameraHuman.CameraHumanGlassType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class CameraHumanHatConvertor implements AttributeConverter<CameraHuman.CameraHumanHatType, String> {
  @Override
  public String convertToDatabaseColumn(CameraHuman.CameraHumanHatType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public CameraHuman.CameraHumanHatType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(CameraHuman.CameraHumanHatType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class CameraHumanMaskConvertor implements AttributeConverter<CameraHuman.CameraHumanMaskType, String> {
  @Override
  public String convertToDatabaseColumn(CameraHuman.CameraHumanMaskType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public CameraHuman.CameraHumanMaskType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(CameraHuman.CameraHumanMaskType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class CameraHumanSexConvertor implements AttributeConverter<CameraHuman.CameraHumanSexType, String> {
  @Override
  public String convertToDatabaseColumn(CameraHuman.CameraHumanSexType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public CameraHuman.CameraHumanSexType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(CameraHuman.CameraHumanSexType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class CameraHumanSmileConvertor implements AttributeConverter<CameraHuman.CameraHumanSmileType, String> {
  @Override
  public String convertToDatabaseColumn(CameraHuman.CameraHumanSmileType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public CameraHuman.CameraHumanSmileType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(CameraHuman.CameraHumanSmileType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}