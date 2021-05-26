package com.distlab.hqsms.screen.device;

import com.distlab.hqsms.common.exception.ScreenSetterInfoGroupsSequenceProvider;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@ApiModel(value = "屏幕设备控制参数")
@GroupSequenceProvider(ScreenSetterInfoGroupsSequenceProvider.class)
public class ScreenSetterInfo {
  private final String COMMAND = "^wakeup$|^sleep$|^reboot$";

  public interface P {}
  public interface V {}
  public interface B {}
  public interface C {}
  public interface R {}
  public interface D {}


  @NotBlank(message = "must not be null", groups = {P.class, D.class})
  @Null(message = "only supports single parameter", groups = {V.class, B.class, C.class, R.class})
  @Pattern(regexp = COMMAND, message = "must be 'wakeup', 'sleep' or 'reboot'")
  @ApiModelProperty(value = "电源控制命令")
  private String command;

  @NotNull(message = "must not be null", groups = {V.class, D.class})
  @Null(message = "only supports single parameter", groups = {P.class, B.class, C.class, R.class})
  @Range(min = 0, max = 15, message = "value range must be 0 to 15")
  @ApiModelProperty(value = "音量, 取值范围[0, 15]")
  private Integer volume;

  @NotNull(message = "must not be null", groups = {B.class, D.class})
  @Null(message = "only supports single parameter", groups = {V.class, P.class, C.class, R.class})
  @Range(min = 0, max = 255, message = "value range must be 0 to 255")
  @ApiModelProperty(value = "亮度, 取值范围[0, 255]")
  private Integer brightness;

  @NotNull(message = "must not be null", groups = {C.class, D.class})
  @Null(message = "only supports single parameter", groups = {V.class, B.class, P.class, R.class})
  @Range(min = 2000, max = 10000, message = "value range must ne 2000 to 10000")
  @ApiModelProperty(value = "色温, , 取值范围[2000, 10000]")
  private Integer colorTemp;

  @NotNull(message = "must not be null", groups = {R.class, D.class})
  @Null(message = "only supports single parameter", groups = {V.class, B.class, C.class, P.class})
  @Range(min = 16, max = 4096, message = "value range must be 16 to 4096")
  @ApiModelProperty(value = "分辨率-宽（必须16的倍数）, 取值范围[16, 4096]")
  private Integer width;

  @NotNull(message = "must not be null", groups = {R.class, D.class})
  @Null(message = "only supports single parameter", groups = {V.class, B.class, C.class, P.class})
  @Range(min = 64, max = 1536, message = "value range must be 64 to 1536")
  @ApiModelProperty(value = "分辨率-高（必须64的倍数）, 取值范围[64, 1536]")
  private Integer height;

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public Integer getVolume() {
    return volume;
  }

  public void setVolume(Integer volume) {
    this.volume = volume;
  }

  public Integer getBrightness() {
    return brightness;
  }

  public void setBrightness(Integer brightness) {
    this.brightness = brightness;
  }

  public Integer getColorTemp() {
    return colorTemp;
  }

  public void setColorTemp(Integer colorTemp) {
    this.colorTemp = colorTemp;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

}
