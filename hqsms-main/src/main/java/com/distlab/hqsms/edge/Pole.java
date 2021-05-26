package com.distlab.hqsms.edge;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

@ApiModel("灯杆信息")
@Entity
@Table(name = "eg_pole")
public class Pole {
  @ApiModelProperty("ID")
  @Id
  private BigInteger id;
  @ApiModelProperty("边缘服务器ID")
  private BigInteger serverId;
  @ApiModelProperty("名称")
  private String name;
  @ApiModelProperty("编号")
  private String code;
  @ApiModelProperty("经度")
  private Float longitude;
  @ApiModelProperty("纬度")
  private Float latitude;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public BigInteger getServerId() {
    return serverId;
  }

  public void setServerId(BigInteger serverId) {
    this.serverId = serverId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
