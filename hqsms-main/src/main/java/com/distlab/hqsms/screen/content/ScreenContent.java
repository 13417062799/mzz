package com.distlab.hqsms.screen.content;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

@ApiModel(value = "屏幕设备播放内容数据")
@Entity
@Table(name = "eg_screen_content")
public class ScreenContent {

  @Id
  @ApiModelProperty(value = "ID")
  private BigInteger id;
  @ApiModelProperty(value = "计划ID")
  private BigInteger screenId;
  @ApiModelProperty(value = "播放状态，0-未播放，1-正在播放")
  private boolean isPlayed;
  @ApiModelProperty(value = "分辨率")
  private String resolution;
  @ApiModelProperty(value = "文件格式")
  private String type;
  @ApiModelProperty(value = "文件大小")
  private BigInteger size;
  @ApiModelProperty(value = "文件名")
  private String name;
  @ApiModelProperty(value = "索引")
  private String contentIndex;
  @ApiModelProperty(value = "创建时间")
  private Date createdAt;
  @ApiModelProperty(value = "更新时间")
  private Date updatedAt;
  @ApiModelProperty(value = "删除时间")
  private Date deletedAt;

  public ScreenContent() {
  }

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public BigInteger getScreenId() {
    return screenId;
  }

  public void setScreenId(BigInteger screenId) {
    this.screenId = screenId;
  }

  public boolean isPlayed() {
    return isPlayed;
  }

  public void setPlayed(boolean played) {
    isPlayed = played;
  }

  public String getResolution() {
    return resolution;
  }

  public void setResolution(String resolution) {
    this.resolution = resolution;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BigInteger getSize() {
    return size;
  }

  public void setSize(BigInteger size) {
    this.size = size;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContentIndex() {
    return contentIndex;
  }

  public void setContentIndex(String contentIndex) {
    this.contentIndex = contentIndex;
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

  public Date getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(Date deletedAt) {
    this.deletedAt = deletedAt;
  }
}
