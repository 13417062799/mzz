package com.distlab.hqsms.edge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.math.BigInteger;
import java.util.Date;
import java.util.stream.Stream;

@ApiModel("设备信息")
@MappedSuperclass
public class Device {

  public interface Create {}
  public interface Update {}

  @Id
  @ApiModelProperty("ID")
  private BigInteger id;
  @ApiModelProperty("灯杆ID")
  private BigInteger poleId;
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

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public BigInteger getPoleId() {
    return poleId;
  }

  public void setPoleId(BigInteger poleId) {
    this.poleId = poleId;
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

  public DeviceOn getIsOn() {
    return isOn;
  }

  public void setIsOn(DeviceOn isOn) {
    this.isOn = isOn;
  }

  public DeviceStatus getStatus() {
    return status;
  }

  public void setStatus(DeviceStatus status) {
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

  public enum DeviceOn {
    NO("0"),
    YES("1");

    private final String code;

    private DeviceOn(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum DeviceStatus {
    FREEZE("0"),
    NORMAL("1");

    private final String code;

    private DeviceStatus(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }
}

@Converter(autoApply = true)
class DeviceOnConvertor implements AttributeConverter<Device.DeviceOn, String> {
  @Override
  public String convertToDatabaseColumn(Device.DeviceOn value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public Device.DeviceOn convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(Device.DeviceOn.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}

@Converter(autoApply = true)
class DeviceStatusConvertor implements AttributeConverter<Device.DeviceStatus, String> {
  @Override
  public String convertToDatabaseColumn(Device.DeviceStatus value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public Device.DeviceStatus convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(Device.DeviceStatus.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}