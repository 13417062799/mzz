package com.distlab.hqsms.screen.device;

import com.distlab.hqsms.edge.Device;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

@ApiModel(value = "屏幕设备信息")
@Entity
@Table(name = "eg_screen")
public class Screen extends Device {

  @ApiModelProperty(value = "音量")
  private int volume;
  @ApiModelProperty(value = "亮度")
  private int brightness;
  @ApiModelProperty(value = "分辨率")
  private String resolution;
  @ApiModelProperty(value = "色温")
  private int colorTemperature;

  public int getVolume() {
    return volume;
  }

  public void setVolume(int volume) {
    this.volume = volume;
  }

  public int getBrightness() {
    return brightness;
  }

  public void setBrightness(int brightness) {
    this.brightness = brightness;
  }

  public String getResolution() {
    return resolution;
  }

  public void setResolution(String resolution) {
    this.resolution = resolution;
  }

  public int getColorTemperature() {
    return colorTemperature;
  }

  public void setColorTemperature(int colorTemperature) {
    this.colorTemperature = colorTemperature;
  }
}
