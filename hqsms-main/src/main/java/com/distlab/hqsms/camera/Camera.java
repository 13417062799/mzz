package com.distlab.hqsms.camera;

import com.distlab.hqsms.edge.Device;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;


@ApiModel("摄像头设备信息")
@Entity
@Table(name = "eg_camera")
public class Camera extends Device {

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
