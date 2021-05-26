package com.distlab.hqsms.edge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.math.BigInteger;
import java.util.Date;

@ApiModel("设备数据")
@MappedSuperclass
public class DeviceLog {
  @ApiModelProperty("主键")
  @Id
  private BigInteger id;
  @ApiModelProperty("设备ID")
  private BigInteger deviceId;
  @ApiModelProperty("经度")
  private Float longitude;
  @ApiModelProperty("纬度")
  private Float latitude;
  @ApiModelProperty("创建时间")
  private Date createdAt;

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

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Float getLongitude() {
    return longitude;
  }

  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }

  public Float getLatitude() {
    return latitude;
  }

  public void setLatitude(Float latitude) {
    this.latitude = latitude;
  }
}
