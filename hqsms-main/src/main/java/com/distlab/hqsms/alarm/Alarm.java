package com.distlab.hqsms.alarm;


import com.distlab.hqsms.edge.Device;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

@ApiModel("报警器设备信息")
@Entity
@Table(name = "eg_alarm")
public class Alarm extends Device {

  @ApiModelProperty("用户名")
  private String username;
  @ApiModelProperty("密码")
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
