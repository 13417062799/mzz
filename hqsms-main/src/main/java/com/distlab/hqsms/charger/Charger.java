package com.distlab.hqsms.charger;

import com.distlab.hqsms.edge.Device;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "eg_charger")
@ApiModel("充电桩设备信息")
public class Charger extends Device {

  @ApiModelProperty(value = "API url")
  private String url;
  @ApiModelProperty(value = "站场编号")
  private Integer stationId;
  @ApiModelProperty(value = "充电桩编号（聚能自定）")
  private String jChargerId;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getStationId() {
    return stationId;
  }

  public void setStationId(Integer stationId) {
    this.stationId = stationId;
  }

  public String getjChargerId() {
    return jChargerId;
  }

  public void setjChargerId(String jChargerId) {
    this.jChargerId = jChargerId;
  }
}
