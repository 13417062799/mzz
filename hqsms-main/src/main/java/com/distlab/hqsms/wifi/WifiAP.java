package com.distlab.hqsms.wifi;

import com.distlab.hqsms.edge.Device;
import io.swagger.annotations.ApiModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "eg_wifi_ap")
@ApiModel("无线网络AP设备信息")
public class WifiAP extends Device {

}
