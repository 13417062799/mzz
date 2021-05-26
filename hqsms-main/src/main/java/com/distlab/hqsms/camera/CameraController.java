package com.distlab.hqsms.camera;

import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.net.RestTemplateService;
import com.distlab.hqsms.edge.DeviceAndLogFilter;
import com.distlab.hqsms.strategy.Rule;
import com.distlab.hqsms.edge.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = {"摄像头"})
@RestController
@RequestMapping(path = "/cameras")
public class CameraController {
  private final Logger logger = LoggerFactory.getLogger(CameraController.class);
  @Autowired
  private CameraHVService cameraHVService;
  @Autowired
  private DeviceService deviceService;
  @Autowired
  private RestTemplateService restTemplateService;
  @Autowired
  private CameraRepository cameraRepository;
  @Value("${hqsms.edge.camera.enable}")
  private Boolean edgeCameraEnable;

  @ApiOperation(value = "获取摄像头列表")
  @GetMapping(value = {""})
  public WebResponse<Page<Camera>> getAllCameras(@Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable) {
    return deviceService.search(cameraRepository, filter, pageable);
  }

  @ApiOperation(value = "创建摄像头")
  @PostMapping(value = {""})
  public WebResponse<Map<String, BigInteger>> addCamera(@RequestBody Camera camera) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.CAMERA);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.CAMERA));
    }
    camera.setId(id);
    camera.setCreatedAt(new Date());
    camera.setUpdatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", id);
    try {
      cameraRepository.save(camera);
      return WebResponse.success(map);
    } catch (Exception ex) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.CAMERA, id));
    }
  }

  @ApiOperation(value = "创建摄像头", hidden = true)
  @PostMapping(value = {"/edge"})
  public WebResponse<Map<String, BigInteger>> addEdgeCamera(@RequestBody Camera camera) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.CAMERA);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.CAMERA));
    }
    camera.setId(id);
    camera.setCreatedAt(new Date());
    camera.setUpdatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", id);
    try {
      cameraRepository.save(camera);
      return WebResponse.success(map);
    } catch (Exception ex) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.CAMERA, id));
    }
  }

  @ApiOperation(value = "获取摄像头信息")
  @GetMapping(value = {"/{id}"})
  public WebResponse<Optional<Camera>> getCamera(@PathVariable BigInteger id) {
    try {
      return WebResponse.success(cameraRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "更新摄像头信息", httpMethod = "POST")
  @PostMapping(value = {"/{id}"})
  public WebResponse<Camera> setCamera(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable BigInteger id,
    @RequestBody Camera camera
  ) {
    try {
      String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.CAMERA);
      if (StringUtils.isEmpty(address)) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
      }

      WebResponse<Camera> remoteRet = restTemplateService.exchange(
        String.format("http://%s/api/cameras/edge/%d", address, id),
        HttpMethod.POST,
        new HttpEntity<>(camera),
        new ParameterizedTypeReference<WebResponse<Camera>>() {
        }
      );
      if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
        return remoteRet;
      }
      Camera rCamera = remoteRet.getData();
      Camera ret = cameraRepository.save(rCamera);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.CAMERA, id));
    }
  }

  @ApiOperation(value = "更新摄像头信息", httpMethod = "POST", hidden = true)
  @PostMapping(value = {"/edge/{id}"})
  public WebResponse<Camera> setEdgeCamera(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable BigInteger id,
    @RequestBody Camera camera
  ) {
    try {
      Optional<Camera> tCamera = cameraRepository.findById(id);
      if (!tCamera.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.CAMERA, id));
      }
      Camera cameraTarget = deviceService.updateFields(camera, tCamera.get());
      cameraTarget.setUpdatedAt(new Date());
      Camera ret = cameraRepository.save(cameraTarget);
      return WebResponse.success(ret);
    } catch (IllegalAccessException e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.CAMERA, id));
    }
  }

  @ApiOperation(value = "删除摄像头信息")
  @DeleteMapping(value = {"/{id}"})
  public WebResponse<Map<String, BigInteger>> deleteCamera(@PathVariable BigInteger id) {
    try {
      Optional<Camera> ret = cameraRepository.findById(id);
      if (!ret.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.CAMERA, id));
      }
      String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.CAMERA);
      if (StringUtils.isEmpty(address)) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
      }
      WebResponse<Map<String, BigInteger>> remoteRet = restTemplateService.exchange(
        String.format("http://%s/api/cameras/edge/%d", address, id),
        HttpMethod.DELETE,
        new HttpEntity<>(id),
        new ParameterizedTypeReference<WebResponse<Map<String, BigInteger>>>() {
        }
      );
      if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
        return remoteRet;
      }
      cameraRepository.delete(ret.get());
      Map<String, BigInteger> map = new HashMap<>();
      map.put("Id", id);
      return WebResponse.success(map);
    } catch (Exception ex) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.CAMERA, id));
    }
  }

  @ApiOperation(value = "删除摄像头信息", hidden = true)
  @DeleteMapping(value = {"/edge/{id}"})
  public WebResponse<Map<String, BigInteger>> deleteEdgeCamera(@PathVariable BigInteger id) {
    try {
      Optional<Camera> ret = cameraRepository.findById(id);
      if (!ret.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.CAMERA, id));
      }
      cameraRepository.delete(ret.get());
      Map<String, BigInteger> map = new HashMap<>();
      map.put("Id", id);
      return WebResponse.success(map);
    } catch (Exception ex) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.CAMERA, id));
    }
  }

  @ApiOperation(value = "控制摄像头视野", notes = "详细命令参考海康威视SDK的 NET_DVR_PTZControlWithSpeed_Other()")
  @PostMapping(path = "/{id}/control")
  public @ResponseBody
  WebResponse<Camera> controlPTZ(
    @PathVariable(name = "id") BigInteger id,
    @RequestBody() CameraControlPTZ ptz
  ) {
    try {
      String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.CAMERA);
      if (StringUtils.isEmpty(address)) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
      }
      WebResponse<Camera> remoteRet = restTemplateService.exchange(
        String.format("http://%s/api/cameras/edge/%d/control/", address, id),
        HttpMethod.POST,
        new HttpEntity<>(ptz),
        new ParameterizedTypeReference<WebResponse<Camera>>() {
        }
      );
      if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
        return remoteRet;
      }
      Camera rCamera = remoteRet.getData();
      return WebResponse.success(rCamera);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "控制摄像头视野", notes = "/{id}/control的边缘执行接口", hidden = true)
  @PostMapping(path = "/edge/{id}/control")
  public @ResponseBody
  WebResponse<Camera> controlEdgePTZ(
    @PathVariable(name = "id") BigInteger id,
    @RequestBody() CameraControlPTZ ptz
  ) {
    try {
      if (!edgeCameraEnable) {
        return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.CAMERA));
      }
      Optional<Camera> tCamera = cameraRepository.findById(id);
      if (!tCamera.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.deviceUnable(MessageEnum.CAMERA));
      }
      Camera cameraTarget = tCamera.get();
      int command = ptz.getCommand();
      int duration = ptz.getDuration();
      int speed = ptz.getSpeed();
      int ret = cameraHVService.controlPTZ(id, command, duration, speed);
      if (ret != 0) {
        return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
      }
      return WebResponse.success(cameraTarget);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "获取设备预览地址", notes = "预览地址")
  @PostMapping(path = "/{id}/live")
  public @ResponseBody
  WebResponse<String> live(
    @PathVariable(name = "id") BigInteger id
  ) {
    try {
      String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.CAMERA);
      if (StringUtils.isEmpty(address)) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
      }
      WebResponse<String> remoteRet = restTemplateService.exchange(
        String.format("http://%s/api/cameras/edge/%d/live", address, id),
        HttpMethod.POST,
        new HttpEntity<>(id),
        new ParameterizedTypeReference<WebResponse<String>>() {
        }
      );
      if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
        return remoteRet;
      }
      return WebResponse.success(remoteRet.getData());
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "获取设备预览地址", notes = "/{id}/live的边缘执行接口", hidden = true)
  @PostMapping(path = "/edge/{id}/live")
  public @ResponseBody
  WebResponse<String> edgeLive(
    @PathVariable(name = "id") BigInteger id
  ) {
    try {
      if (!edgeCameraEnable) {
        return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.CAMERA));
      }
      String address = cameraHVService.getGB28181Address(id);
      if (StringUtils.isEmpty(address)) {
        return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
      }
      return WebResponse.success(address);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "控制摄像头视野", notes = "详细命令参考海康威视SDK的 NET_DVR_PTZPreset_Other()")
  @PostMapping(path = "/{id}/preset")
  public @ResponseBody
  WebResponse<Camera> controlPreset(
    @PathVariable(name = "id") BigInteger id,
    @RequestBody() CameraPresetPTZ ptz
  ) {
    try {
      String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.CAMERA);
      if (StringUtils.isEmpty(address)) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
      }
      WebResponse<Camera> remoteRet = restTemplateService.exchange(
        String.format("http://%s/api/cameras/edge/%d/preset/", address, id),
        HttpMethod.POST,
        new HttpEntity<>(ptz),
        new ParameterizedTypeReference<WebResponse<Camera>>() {
        }
      );
      if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
        return remoteRet;
      }
      Camera rCamera = remoteRet.getData();
      return WebResponse.success(rCamera);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "控制摄像头视野", notes = "/{id}/preset的边缘执行接口", hidden = true)
  @PostMapping(path = "/edge/{id}/preset")
  public @ResponseBody
  WebResponse<Camera> controlEdgePreset(
    @PathVariable(name = "id") BigInteger id,
    @RequestBody() CameraPresetPTZ ptz
  ) {
    try {
      if (!edgeCameraEnable) {
        return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.CAMERA));
      }
      Optional<Camera> tCamera = cameraRepository.findById(id);
      if (!tCamera.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.deviceUnable(MessageEnum.CAMERA));
      }
      Camera cameraTarget = tCamera.get();
      int command = ptz.getCommand();
      int index = ptz.getIndex();
      int ret = cameraHVService.presetPTZ(id, command, index);
      if (ret != 0) {
        return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
      }
      return WebResponse.success(cameraTarget);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }
}


@ApiModel("摄像头控制PTZ参数")
class CameraControlPTZ {
  @ApiModelProperty(
    value = "命令，具体参考海康威视SDK的 NET_DVR_PTZControlWithSpeed_Other()",
    required = true,
    allowableValues = "11, 12, 13, 14, 15, 16, 21, 22, 23, 24"
  )
  private int command;
  @ApiModelProperty(
    value = "持续时间，单位毫秒",
    required = true
  )
  private int duration;
  @ApiModelProperty(
    value = "速度，分七档",
    required = true,
    allowableValues = "1, 2, 3, 4, 5, 6, 7"
  )
  private int speed;

  public int getCommand() {
    return command;
  }

  public void setCommand(int command) {
    this.command = command;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }
}

@ApiModel("摄像头预置PTZ参数")
class CameraPresetPTZ {
  @ApiModelProperty(
    value = "命令，具体参考海康威视SDK的 NET_DVR_PTZPreset_Other()",
    required = true,
    allowableValues = "8, 9, 39"
  )
  private int command;
  @ApiModelProperty(
    value = "预置位序号",
    required = true
  )
  private int index;

  public int getCommand() {
    return command;
  }

  public void setCommand(int command) {
    this.command = command;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}