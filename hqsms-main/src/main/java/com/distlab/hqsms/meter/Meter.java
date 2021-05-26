package com.distlab.hqsms.meter;

import com.distlab.hqsms.edge.Device;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

@ApiModel("电能表设备信息")
@Entity
@Table(name = "eg_meter")
public class Meter extends Device {

  @ApiModelProperty("服务器端口")
  private Integer port;
  @ApiModelProperty("从机地址")
  private Integer slaveId;

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public Integer getSlaveId() {
    return slaveId;
  }

  public void setSlaveId(Integer slaveId) {
    this.slaveId = slaveId;
  }
}
