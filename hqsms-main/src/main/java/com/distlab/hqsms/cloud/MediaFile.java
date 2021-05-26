package com.distlab.hqsms.cloud;


import com.distlab.hqsms.camera.CameraHuman;
import com.distlab.hqsms.camera.CameraLog;
import com.distlab.hqsms.camera.CameraVehicle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.WhereJoinTable;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.stream.Stream;

@ApiModel("媒体文件信息")
@Entity
@Table(name = "tb_media_file")
public class MediaFile {
  @ApiModelProperty("ID")
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private BigInteger id;
  @ApiModelProperty("文件来源ID")
  private BigInteger sourceId;
  @ApiModelProperty("文件来源")
  private MediaFileSource source;
  @ApiModelProperty("文件名称")
  private String name;
  @ApiModelProperty("创建时间")
  private Date createdAt;

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public BigInteger getSourceId() {
    return sourceId;
  }

  public void setSourceId(BigInteger sourceId) {
    this.sourceId = sourceId;
  }

  public MediaFileSource getSource() {
    return source;
  }

  public void setSource(MediaFileSource source) {
    this.source = source;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public enum MediaFileSource {
    CAMERA_RAW_VIDEO("0"),
    CAMERA_HUMAN_CLIP("1"),
    CAMERA_HUMAN_PANORAMA("2"),
    CAMERA_HUMAN_SNAPSHOT("3"),
    CAMERA_HUMAN_FACE("4"),
    CAMERA_HUMAN_UPLOAD("5"),
    CAMERA_VEHICLE_CLIP("6"),
    CAMERA_VEHICLE_PANORAMA("7"),
    CAMERA_VEHICLE_SNAPSHOT("8");

    private final String code;

    MediaFileSource(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }
}

@Converter(autoApply = true)
class MediaFileSourceConvertor implements AttributeConverter<MediaFile.MediaFileSource, String> {
  @Override
  public String convertToDatabaseColumn(MediaFile.MediaFileSource value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public MediaFile.MediaFileSource convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(MediaFile.MediaFileSource.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}