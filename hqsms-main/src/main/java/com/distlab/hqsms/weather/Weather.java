package com.distlab.hqsms.weather;

import com.distlab.hqsms.edge.Device;
import io.swagger.annotations.ApiModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@ApiModel("气象传感器设备信息")
@Entity
@Table(name = "eg_weather")
public class Weather extends Device {
  private Integer port;

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }
}
