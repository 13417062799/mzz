package com.distlab.hqsms.screen.plan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

@ApiModel(value = "屏幕设备播放计划数据")
@Entity
@Table(name = "eg_screen_plan")
public class ScreenPlan {

  @ApiModelProperty(value = "ID")
  @Id
  private BigInteger id;
  @ApiModelProperty(value = "屏幕ID")
  private BigInteger contentId;
  @ApiModelProperty(value = "计划名称")
  private String planName;
  @ApiModelProperty(value = "Cron表达式")
  private String cron;
  @ApiModelProperty(value = "工作名称")
  private String jobName;
  @ApiModelProperty(value = "工作相关类全名")
  private String jobClassName;
  @ApiModelProperty(value = "是否启用")
  private Boolean enable;
  @ApiModelProperty(value = "开始时间")
  private Date startTime;
  @ApiModelProperty(value = "结束日期")
  private Date endDate;
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

  public BigInteger getContentId() {
    return contentId;
  }

  public void setContentId(BigInteger contentId) {
    this.contentId = contentId;
  }

  public String getPlanName() {
    return planName;
  }

  public void setPlanName(String planName) {
    this.planName = planName;
  }

  public String getCron() {
    return cron;
  }

  public void setCron(String cron) {
    this.cron = cron;
  }

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public String getJobClassName() {
    return jobClassName;
  }

  public void setJobClassName(String jobClassName) {
    this.jobClassName = jobClassName;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
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
