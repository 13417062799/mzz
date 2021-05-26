package com.distlab.hqsms.broadcast.device;

import com.distlab.hqsms.edge.Device;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@ApiModel("广播设备")
@Table(name = "eg_broadcast")
public class Broadcast extends Device {

  @ApiModelProperty(value = "设备音量")
  private Integer volume;
  @ApiModelProperty(value = "用户名")
  private String username;
  @ApiModelProperty(value = "密码")
  private String password;
  @ApiModelProperty(value = "cookie")
  private String cookie;
  @ApiModelProperty(value = "中继服务器IP")
  private String relayIP;

  public Integer getVolume() {
    return volume;
  }

  public void setVolume(Integer volume) {
    this.volume = volume;
  }

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

  public String getCookie() {
    return cookie;
  }

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

  public String getRelayIP() {
    return relayIP;
  }

  public void setRelayIP(String relayIP) {
    this.relayIP = relayIP;
  }
}
