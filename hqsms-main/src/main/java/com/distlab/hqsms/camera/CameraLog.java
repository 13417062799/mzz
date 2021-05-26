package com.distlab.hqsms.camera;

import com.distlab.hqsms.edge.DeviceLog;
import com.distlab.hqsms.strategy.RuleEvent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@ApiModel("摄像头设备录像")
@Entity
@Table(name = "eg_camera_log")
public class CameraLog extends DeviceLog {
  @Transient
  List<RuleEvent> ruleEvents;
  @ApiModelProperty("录像开始时间")
  private Date startedAt;
  @ApiModelProperty("录像结束时间")
  private Date endedAt;
  @ApiModelProperty("文件信息")
  @Transient
  private List<String> files;

  public List<String> getFiles() {
    return files;
  }

  public void setFiles(List<String> files) {
    this.files = files;
  }

  public List<RuleEvent> getRuleEvents() {
    return ruleEvents;
  }

  public void setRuleEvents(List<RuleEvent> ruleEvents) {
    this.ruleEvents = ruleEvents;
  }

  public Date getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(Date startedAt) {
    this.startedAt = startedAt;
  }

  public Date getEndedAt() {
    return endedAt;
  }

  public void setEndedAt(Date endedAt) {
    this.endedAt = endedAt;
  }
}
