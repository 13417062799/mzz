package com.distlab.hqsms.edge;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

@ApiModel("边缘服务器设备信息")
@Entity
@Table(name = "eg_server")
public class Server {

  @Id
  @ApiModelProperty("ID")
  private BigInteger id;
  @ApiModelProperty("组织ID")
  private BigInteger orgId;
  @ApiModelProperty("分组ID，用于设备资产管理")
  private BigInteger groupId;
  @ApiModelProperty("名称")
  private String name;
  @ApiModelProperty("编号")
  private String code;
  @ApiModelProperty("运行状态，0-不在线，1-在线")
  private Device.DeviceOn isOn;
  @ApiModelProperty("工作状态，0-冻结，1-正常")
  private Device.DeviceStatus status;
  @ApiModelProperty("IP地址")
  private String ip;
  @ApiModelProperty("MAC地址")
  private String mac;
  @ApiModelProperty("子网掩码")
  private String netmask;
  @ApiModelProperty("网关")
  private String gateway;
  @ApiModelProperty("生产批次")
  private String productBatch;
  @ApiModelProperty("采购批次")
  private String purchaseBatch;
  @ApiModelProperty("采购时间")
  private Date purchasedAt;
  @ApiModelProperty("供应商")
  private String supplier;
  @ApiModelProperty("型号")
  private String model;
  @ApiModelProperty("创建时间")
  private Date createdAt;
  @ApiModelProperty("更新时间")
  private Date updatedAt;
  @ApiModelProperty("删除时间")
  private Date deletedAt;
  @ApiModelProperty("端口")
  private int port;

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public BigInteger getOrgId() {
    return orgId;
  }

  public void setOrgId(BigInteger orgId) {
    this.orgId = orgId;
  }

  public BigInteger getGroupId() {
    return groupId;
  }

  public void setGroupId(BigInteger groupId) {
    this.groupId = groupId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Device.DeviceOn getIsOn() {
    return isOn;
  }

  public void setIsOn(Device.DeviceOn isOn) {
    this.isOn = isOn;
  }

  public Device.DeviceStatus getStatus() {
    return status;
  }

  public void setStatus(Device.DeviceStatus status) {
    this.status = status;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getNetmask() {
    return netmask;
  }

  public void setNetmask(String netmask) {
    this.netmask = netmask;
  }

  public String getGateway() {
    return gateway;
  }

  public void setGateway(String gateway) {
    this.gateway = gateway;
  }

  public String getProductBatch() {
    return productBatch;
  }

  public void setProductBatch(String productBatch) {
    this.productBatch = productBatch;
  }

  public String getPurchaseBatch() {
    return purchaseBatch;
  }

  public void setPurchaseBatch(String purchaseBatch) {
    this.purchaseBatch = purchaseBatch;
  }

  public Date getPurchasedAt() {
    return purchasedAt;
  }

  public void setPurchasedAt(Date purchasedAt) {
    this.purchasedAt = purchasedAt;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Date getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(Date deletedAt) {
    this.deletedAt = deletedAt;
  }
}
