package com.distlab.hqsms.wifi;

import com.distlab.hqsms.edge.DeviceLog;
import com.distlab.hqsms.strategy.RuleEvent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "eg_wifi_log")
@ApiModel(value = "无线网络数据信息")
public class WifiLog extends DeviceLog {

  @Transient
  List<RuleEvent> ruleEvents;
  @ApiModelProperty(value = "无线网络名称")
  private String ssid;
  @ApiModelProperty(value = "用户MAC地址")
  private String userMac;
  @ApiModelProperty(value = "用户IP地址")
  private String userIp;
  @ApiModelProperty(value = "用户名称")
  private String username;
  @ApiModelProperty(value = "客户端类型")
  private String clientType;
  @ApiModelProperty(value = "用户上行速率")
  private Integer up;
  @ApiModelProperty(value = "用户下行速率")
  private Integer down;
  @ApiModelProperty(value = "用户总上行流量")
  private Long totalUp;
  @ApiModelProperty(value = "用户总下行流量")
  private Long totalDown;
  @ApiModelProperty(value = "用户上线时间")
  private Date login;
  @ApiModelProperty(value = "用户下线时间")
  private Date logout;
  @ApiModelProperty(value = "用户下线原因")
  private Integer offType;

  public List<RuleEvent> getRuleEvents() {
    return ruleEvents;
  }

  public void setRuleEvents(List<RuleEvent> ruleEvents) {
    this.ruleEvents = ruleEvents;
  }

  public String getSsid() {
    return ssid;
  }

  public void setSsid(String ssid) {
    this.ssid = ssid;
  }

  public String getUserMac() {
    return userMac;
  }

  public void setUserMac(String userMac) {
    this.userMac = userMac;
  }

  public String getUserIp() {
    return userIp;
  }

  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  public Integer getUp() {
    return up;
  }

  public void setUp(Integer up) {
    this.up = up;
  }

  public Integer getDown() {
    return down;
  }

  public void setDown(Integer down) {
    this.down = down;
  }

  public Long getTotalUp() {
    return totalUp;
  }

  public void setTotalUp(Long totalUp) {
    this.totalUp = totalUp;
  }

  public Long getTotalDown() {
    return totalDown;
  }

  public void setTotalDown(Long totalDown) {
    this.totalDown = totalDown;
  }

  public Date getLogin() {
    return login;
  }

  public void setLogin(Date login) {
    this.login = login;
  }

  public Date getLogout() {
    return logout;
  }

  public void setLogout(Date logout) {
    this.logout = logout;
  }

  public Integer getOffType() {
    return offType;
  }

  public void setOffType(Integer offType) {
    this.offType = offType;
  }
}
