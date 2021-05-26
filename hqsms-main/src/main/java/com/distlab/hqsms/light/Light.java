package com.distlab.hqsms.light;

import com.distlab.hqsms.edge.Device;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

@ApiModel("灯控设备信息")
@Entity
@Table(name = "eg_light")
public class Light extends Device {

  @ApiModelProperty("端口")
  private Integer port;


  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }
}
