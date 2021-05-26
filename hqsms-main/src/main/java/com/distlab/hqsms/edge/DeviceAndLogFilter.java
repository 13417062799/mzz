package com.distlab.hqsms.edge;

import com.distlab.hqsms.common.GlobalConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.text.SimpleDateFormat;

@ApiModel(value = "设备过滤条件")
@GroupSequenceProvider(DeviceFilterGroupsSequenceProvider.class)
public class DeviceAndLogFilter {
  private static final String DATETIME_FORMAT = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29) ([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
//  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public interface CreatedAt {}
  public interface Login {}
  public interface Time {}

  /***************** 通用部分 ******************/
  @NotNull(message = GlobalConstant.MSG_NOT_NULL, groups = {CreatedAt.class, Time.class})
  @Pattern(regexp = DATETIME_FORMAT, message = GlobalConstant.MSG_DATETIME_FORMAT)
  @ApiModelProperty(value = "根据创建时间查询, 起始时间点")
  private String start;
  @Pattern(regexp = DATETIME_FORMAT, message = GlobalConstant.MSG_DATETIME_FORMAT)
  @ApiModelProperty(value = "根据创建时间查询, 结束时间点")
  private String end;

  /***************** 设备信息部分 ******************/
  @ApiModelProperty(value = "根据设备在线状态查询, 仅设备信息可用")
  private Device.DeviceOn isOn;
  @ApiModelProperty(value = "根据供应商查询, 仅设备信息可用")
  private String supplier;
  @ApiModelProperty(value = "根据设备编号查询, 仅设备信息可用")
  private String code;
  @ApiModelProperty(value = "根据设备型号查询, 仅设备信息可用")
  private String model;
  @ApiModelProperty(value = "根据设备名称查询, 仅设备信息可用")
  private String name;

  /***************** 设备数据部分 ******************/
  @ApiModelProperty(value = "根据设备ID查询, 仅设备数据可用")
  private BigInteger deviceId;
  //WIFI-LOG
  @NotNull(message = GlobalConstant.MSG_NOT_NULL, groups = {Login.class, Time.class})
  @Pattern(regexp = DATETIME_FORMAT, message = GlobalConstant.MSG_DATETIME_FORMAT)
  @ApiModelProperty(value = "根据登录时间区间查询, 起始时间点, 仅Wifi-Log可用")
  private String inStart;
  @Pattern(regexp = DATETIME_FORMAT, message = GlobalConstant.MSG_DATETIME_FORMAT)
  @ApiModelProperty(value = "根据登录时间区间查询, 结束时间点, 仅Wifi-Log可用")
  private String inEnd;
  @ApiModelProperty(value = "根据离线类型查询, 1-在线 -1-离线, 仅Wifi-Log可用")
  private int offType;


  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public Device.DeviceOn getIsOn() {
    return isOn;
  }

  public void setIsOn(Device.DeviceOn isOn) {
    this.isOn = isOn;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigInteger getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(BigInteger deviceId) {
    this.deviceId = deviceId;
  }

  public String getInStart() {
    return inStart;
  }

  public void setInStart(String inStart) {
    this.inStart = inStart;
  }

  public String getInEnd() {
    return inEnd;
  }

  public void setInEnd(String inEnd) {
    this.inEnd = inEnd;
  }

  public int getOffType() {
    return offType;
  }

  public void setOffType(int offType) {
    this.offType = offType;
  }
}
