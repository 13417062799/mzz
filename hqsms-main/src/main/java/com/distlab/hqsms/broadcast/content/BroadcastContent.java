package com.distlab.hqsms.broadcast.content;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

@Entity
@ApiModel("广播内容")
@Table(name = "eg_broadcast_content")
public class BroadcastContent {
  @Id
  @ApiModelProperty(value = "ID")
  private BigInteger id;
  @ApiModelProperty(value = "设备ID")
  private BigInteger broadcastId;
  @ApiModelProperty(value = "中继服务器产生的节目ID")
  private int programId;
  @ApiModelProperty(value = "会话ID")
  private int sessionId;
  @ApiModelProperty(value = "音频时长")
  private int length;
  @ApiModelProperty(value = "音频大小")
  private long size;
  @ApiModelProperty(value = "音频类型")
  private String type;
  @ApiModelProperty(value = "音频名称")
  private String contentName;
  @ApiModelProperty(value = "音频文件在FTP服务器的唯一识别码")
  private String code;
  @ApiModelProperty(value = "播放状态")
  private Boolean isPlayed;
  @ApiModelProperty(value = "FTP地址")
  private String ftpUrl;
  @ApiModelProperty(value = "创建时间")
  private Date createdAt;
  @ApiModelProperty(value = "更新时间")
  private Date updatedAt;
  @ApiModelProperty(value = "删除时间")
  private Date deletedAt;

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public BigInteger getBroadcastId() {
    return broadcastId;
  }

  public void setBroadcastId(BigInteger broadcastId) {
    this.broadcastId = broadcastId;
  }

  public int getProgramId() {
    return programId;
  }

  public void setProgramId(int programId) {
    this.programId = programId;
  }

  public int getSessionId() {
    return sessionId;
  }

  public void setSessionId(int sessionId) {
    this.sessionId = sessionId;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getContentName() {
    return contentName;
  }

  public void setContentName(String contentName) {
    this.contentName = contentName;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Boolean getPlayed() {
    return isPlayed;
  }

  public void setPlayed(Boolean played) {
    isPlayed = played;
  }

  public String getFtpUrl() {
    return ftpUrl;
  }

  public void setFtpUrl(String ftpUrl) {
    this.ftpUrl = ftpUrl;
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