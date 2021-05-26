package com.distlab.hqsms.broadcast.plan;

import com.distlab.hqsms.common.exception.BroadcastPlanInfoGroupsSequenceProvider;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.constraints.*;
import java.math.BigInteger;

@ApiModel(value = "广播计划参数", description = "JSON格式封装发送")
@GroupSequenceProvider(BroadcastPlanInfoGroupsSequenceProvider.class)
public class BroadcastPlanInfo {

  public interface Type1 {}
  public interface Type2 {}
  public interface Type3 {}

  private static final String NOT_NULL = "must not be null!";
  private static final String DATETIME_FORMAT = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29) ([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
  private static final String DATE_FORMAT = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)";

  @NotNull(message = NOT_NULL)
  @ApiModelProperty(required = true, value = "内容ID")
  private BigInteger contentId;

  @NotBlank(message = NOT_NULL)
  @ApiModelProperty(required = true, value = "计划名称")
  private String planName;

  @NotBlank(message = NOT_NULL)
  @Pattern(regexp = DATETIME_FORMAT, message = "format must be 'yyyy-MM-dd HH:mm:ss' or check if date and time are correct")
  @ApiModelProperty(required = true, value = "开始时间", example = "2020-12-12 12:12:12")
  private String startTime;

  @NotNull(message = NOT_NULL)
  @Min(value = 1, message = "value must be greater than zero")
  @ApiModelProperty(required = true, value = "重复次数")
  private Integer repeatTime;

  @NotNull(message = NOT_NULL)
  @Range(min = 0, max = 1, message = "value must be 0 or 1")
  @ApiModelProperty(required = true, value = "播放模式（0-顺序/1-随机）")
  private Integer playMode;

  @NotNull(message = NOT_NULL)
  @Range(min = 1, max = 4, message = "value range must be 1 to 4")
  @ApiModelProperty(required = true, value = "计划类型（1-每天任务/2-每周任务/3-每月任务/4-一次性任务）")
  private Integer type;

  @NotNull(message = NOT_NULL, groups = {Type1.class, Type2.class})
  @Min(value = 1, message = "must be greater than zero")
  @ApiModelProperty(value = "每几天/周（type=1或2时必填；每几天执行：每隔every-1天执行一次；每几周执行）")
  private Integer every;

  @NotEmpty(message = NOT_NULL, groups = Type2.class)
  @Size(min = 1, max = 7, message = "array size must be 1 to 7")
  @ApiModelProperty(value = "周有效天（type=2时比填，[1, 3, 7]表示周一三日执行）")
  private int[] daysInWeek;

  @NotEmpty(message = NOT_NULL, groups = Type3.class)
  @Size(min = 1, max = 12, message = "array size must be 1 to 12")
  @ApiModelProperty(value = "年有效月（type=3时必填；范围：1-12，[1, 4, 12]表示1、4、12月执行）")
  private int[] monthsInYear;

  @NotEmpty(message = NOT_NULL, groups = Type3.class)
  @Size(min = 1, max = 2, message = "array size must be 1 or 2")
  @ApiModelProperty(value = "每月中有效天（type=3时必填；数组大小范围：1-2，当数组大小=1，表示每月的第几天，取值：(1-31)；当数组大小=2，表示每月的第几个星期几，[2, 3]表示第2周周三）")
  private int[] dayInMonth;

  @Pattern(regexp = DATE_FORMAT, message = "format must be 'yyyy-MM-dd' or check if date is correct")
  @ApiModelProperty(value = "结束日期", example = "2020-12-12")
  private String endDate;

  @Range(min = 0, max = 56, message = "value range must be 0 to 56.")
  @ApiModelProperty(value = "计划音量（范围：0-56）")
  private Integer playVol;

  @Range(min = 0, max = 1, message = "value must be 0 or 1")
  @ApiModelProperty(value = "播放状态（0-停止/1-播放）")
  private Integer status;

  @Range(min = 0, max = 1, message = "value must be 0 or 1")
  @ApiModelProperty(value = "冻结状态（0-冻结/1-启用）")
  private Integer enable;

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

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public Integer getRepeatTime() {
    return repeatTime;
  }

  public void setRepeatTime(Integer repeatTime) {
    this.repeatTime = repeatTime;
  }

  public Integer getPlayMode() {
    return playMode;
  }

  public void setPlayMode(Integer playMode) {
    this.playMode = playMode;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getEvery() {
    return every;
  }

  public void setEvery(Integer every) {
    this.every = every;
  }

  public int[] getDaysInWeek() {
    return daysInWeek;
  }

  public void setDaysInWeek(int[] daysInWeek) {
    this.daysInWeek = daysInWeek;
  }

  public int[] getMonthsInYear() {
    return monthsInYear;
  }

  public void setMonthsInYear(int[] monthsInYear) {
    this.monthsInYear = monthsInYear;
  }

  public int[] getDayInMonth() {
    return dayInMonth;
  }

  public void setDayInMonth(int[] dayInMonth) {
    this.dayInMonth = dayInMonth;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public Integer getPlayVol() {
    return playVol;
  }

  public void setPlayVol(Integer playVol) {
    this.playVol = playVol;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getEnable() {
    return enable;
  }

  public void setEnable(Integer enable) {
    this.enable = enable;
  }
}
