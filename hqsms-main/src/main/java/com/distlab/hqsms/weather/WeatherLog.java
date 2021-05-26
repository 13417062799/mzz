package com.distlab.hqsms.weather;

import com.distlab.hqsms.edge.DeviceLog;
import com.distlab.hqsms.strategy.RuleEvent;
import io.swagger.annotations.ApiModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@ApiModel("气象传感器设备数据")
@Entity
@Table(name = "eg_weather_log")
public class WeatherLog extends DeviceLog {
  @Transient
  List<RuleEvent> ruleEvents;
  private int dn;
  private int dm;
  private int dx;
  private float sn;
  private float sm;
  private float sx;
  private float ta;
  private float ua;
  private float pa;
  private Date createdAt;
  //FWS500 无法采集以下字段，需修改数据库默认值
  private float rc;
  private float sr;
  private int uv;

  public int getDn() {
    return dn;
  }

  public void setDn(int dn) {
    this.dn = dn;
  }

  public int getDm() {
    return dm;
  }

  public void setDm(int dm) {
    this.dm = dm;
  }

  public int getDx() {
    return dx;
  }

  public void setDx(int dx) {
    this.dx = dx;
  }

  public float getSn() {
    return sn;
  }

  public void setSn(float sn) {
    this.sn = sn;
  }

  public float getSm() {
    return sm;
  }

  public void setSm(float sm) {
    this.sm = sm;
  }

  public float getSx() {
    return sx;
  }

  public void setSx(float sx) {
    this.sx = sx;
  }

  public float getTa() {
    return ta;
  }

  public void setTa(float ta) {
    this.ta = ta;
  }

  public float getUa() {
    return ua;
  }

  public void setUa(float ua) {
    this.ua = ua;
  }

  public float getPa() {
    return pa;
  }

  public void setPa(float pa) {
    this.pa = pa;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public float getRc() {
    return rc;
  }

  public void setRc(float rc) {
    this.rc = rc;
  }

  public float getSr() {
    return sr;
  }

  public void setSr(float sr) {
    this.sr = sr;
  }

  public int getUv() {
    return uv;
  }

  public void setUv(int uv) {
    this.uv = uv;
  }

  public List<RuleEvent> getRuleEvents() {
    return ruleEvents;
  }

  public void setRuleEvents(List<RuleEvent> ruleEvents) {
    this.ruleEvents = ruleEvents;
  }
}
