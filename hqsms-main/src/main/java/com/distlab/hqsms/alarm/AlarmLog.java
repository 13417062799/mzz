package com.distlab.hqsms.alarm;


import com.distlab.hqsms.edge.DeviceLog;
import com.distlab.hqsms.strategy.RuleEvent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigInteger;
import java.util.List;

@ApiModel("报警器设备数据")
@Entity
@Table(name = "eg_alarm_log")
public class AlarmLog extends DeviceLog {

  @Transient
  List<RuleEvent> ruleEvents;
  @ApiModelProperty("视频URL")
  private String videoUrl;
  @ApiModelProperty("图片URL")
  private String imageUrl;
  @ApiModelProperty("音频URL")
  private String audioUrl;

  public List<RuleEvent> getRuleEvents() {
    return ruleEvents;
  }

  public void setRuleEvents(List<RuleEvent> ruleEvents) {
    this.ruleEvents = ruleEvents;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getAudioUrl() {
    return audioUrl;
  }

  public void setAudioUrl(String audioUrl) {
    this.audioUrl = audioUrl;
  }

}
