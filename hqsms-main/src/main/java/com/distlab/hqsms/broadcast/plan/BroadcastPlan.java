package com.distlab.hqsms.broadcast.plan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

@Entity
@ApiModel("广播计划")
@Table(name = "eg_broadcast_plan")
public class BroadcastPlan {
  @Id
  @ApiModelProperty(value = "ID")
  private BigInteger id;
  @ApiModelProperty(value = "内容ID")
  private BigInteger contentId;
  @ApiModelProperty(value = "中继服务器产生的任务ID")
  private int taskId;
  @ApiModelProperty(value = "计划名称")
  private String planName;
  @ApiModelProperty(value = "计划音量")
  private int planVol;
  @ApiModelProperty(value = "播放模式：0-顺序、1-随机")
  private int playMode;
  @ApiModelProperty(value = "重复次数")
  private int repeatTime;
  @ApiModelProperty(value = "开始时间")
  private Date startTime;
  @ApiModelProperty(value = "结束日期")
  private Date endDate;
  @ApiModelProperty(value = "周期")
  private String period;
  @ApiModelProperty(value = "播放状态")
  private Boolean isPlayed;
  @ApiModelProperty(value = "冻结状态")
  private Boolean isEnabled;
  @ApiModelProperty(value = "创建时间")
  private Date createdAt;
  @ApiModelProperty(value = "删除时间")
  private Date deletedAt;
  @ApiModelProperty(value = "更新时间")
  private Date updatedAt;

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public BigInteger getContentId() {
    return contentId;
  }

  public void setContentId(BigInteger contentId) {
    this.contentId = contentId;
  }

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }

  public String getPlanName() {
    return planName;
  }

  public void setPlanName(String planName) {
    this.planName = planName;
  }

  public int getPlanVol() {
    return planVol;
  }

  public void setPlanVol(int planVol) {
    this.planVol = planVol;
  }

  public int getPlayMode() {
    return playMode;
  }

  public void setPlayMode(int playMode) {
    this.playMode = playMode;
  }

  public int getRepeatTime() {
    return repeatTime;
  }

  public void setRepeatTime(int repeatTime) {
    this.repeatTime = repeatTime;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public Boolean getPlayed() {
    return isPlayed;
  }

  public void setPlayed(Boolean played) {
    isPlayed = played;
  }

  public Boolean getEnabled() {
    return isEnabled;
  }

  public void setEnabled(Boolean enabled) {
    isEnabled = enabled;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(Date deletedAt) {
    this.deletedAt = deletedAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
