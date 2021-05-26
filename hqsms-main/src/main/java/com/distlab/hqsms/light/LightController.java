package com.distlab.hqsms.light;

import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.net.RestTemplateService;
import com.distlab.hqsms.edge.Device;
import com.distlab.hqsms.edge.DeviceAndLogFilter;
import com.distlab.hqsms.edge.DeviceService;
import com.distlab.hqsms.strategy.Rule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = "灯控")
@Validated
@RestController
@RequestMapping(path = "/lights")
public class LightController {

  private static final Logger logger = LoggerFactory.getLogger(LightController.class);

  @Autowired
  LightRepository lightRepository;
  @Autowired
  LightLogRepository lightLogRepository;
  @Autowired
  LightService lightService;
  @Autowired
  DeviceService deviceService;
  @Autowired
  RestTemplate restTemplate;
  @Autowired
  RestTemplateService restTemplateService;

  @Value("${hqsms.edge.Light.enable}")
  private boolean edgeLightEnable;


  // CREATE

  @PostMapping(path = "")
  @ApiOperation(value = "创建灯控设备信息")
  public WebResponse<Map<String, BigInteger>> createLight(@RequestBody Light light) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.LIGHT);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.LIGHT));
    }
    light.setId(id);
    light.setCreatedAt(new Date());
    light.setUpdatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", id);
    try {
      lightRepository.save(light);
      return WebResponse.success(map);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.LIGHT, id));
    }
  }


  // READ

  @GetMapping(path = "")
  @ApiOperation(value = "获取灯控设备信息列表")
  public WebResponse<Page<Light>> getLights(
    @Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable
  ) {
    return deviceService.search(lightRepository, filter, pageable);
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取灯控设备信息")
  @ApiImplicitParam(name = "id", value = "电灯控设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<Light>> getLight(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    try {
      return WebResponse.success(lightRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }


  // UPDATE

  @ApiIgnore
  @PostMapping(path = "/edge/{id}")
  public WebResponse<Light> updateEdgeLight(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody Light lightSource
  ) {
    Optional<Light> tLight = lightRepository.findById(id);
    if (!tLight.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.LIGHT, id));
    }
    try {
      Light lightTarget = deviceService.updateFields(lightSource, tLight.get());
      lightTarget.setUpdatedAt(new Date());
      Light ret = lightRepository.save(lightTarget);
      return WebResponse.success(ret);
    } catch (IllegalAccessException e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.LIGHT, id));
    }
  }

  @PostMapping(path = "/{id}")
  @ApiOperation(value = "更新灯控设备信息")
  @ApiImplicitParam(name = "id", value = "灯控设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Light> updateLight(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody Light light
  ) {
    String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.LIGHT);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.LIGHT, id));
    }

    WebResponse<Light> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/lights/edge/%d", address, id),
      HttpMethod.POST,
      new HttpEntity<>(light),
      new ParameterizedTypeReference<WebResponse<Light>>() {});

    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    Light rLight = remoteRet.getData();
    try {
      Light ret = lightRepository.save(rLight);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.LIGHT, rLight.getId()));
    }
  }

  @ApiIgnore
  @PostMapping(path = "/edge/{id}/control")
  public WebResponse<LightLog> lightEdgeControl(
    @Min(value = 0, message = "must be greater than -1") @Max(value = 4, message = "must be less than 5")
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @RequestParam(name = "cmd") int cmd,
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    if (!edgeLightEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.LIGHT));
    }
    LightLog log = lightService.controller(id, cmd);
    if (log == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success(log);
  }

  @PostMapping(path = "/{id}/control")
  @ApiOperation(value = "控制")
  public WebResponse<LightLog> lightControl(
    @Min(value = 0, message = "must be greater than -1") @Max(value = 4, message = "must be less than 5")
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @RequestParam(name = "cmd") int cmd,
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.LIGHT);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
    }

    MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
    requestBody.add("cmd", cmd);
    WebResponse<LightLog> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/lights/edge/%d/control", address, id),
      HttpMethod.POST,
      new HttpEntity<>(requestBody),
      new ParameterizedTypeReference<WebResponse<LightLog>>() {
      });
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    LightLog rLog = remoteRet.getData();
    try {
      LightLog ret = lightLogRepository.save(rLog);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.LIGHT_LOG, rLog.getId()));
    }
  }


  //DELETE

  @DeleteMapping(path = "/{id}")
  @ApiOperation(value = "删除灯控设备信息")
  @ApiImplicitParam(name = "id", value = "灯控设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Map<String, BigInteger>> deleteLight(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    Optional<Light> tLight = lightRepository.findById(id);
    if (!tLight.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.LIGHT, id));
    }
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", tLight.get().getId());
    try {
      lightRepository.delete(tLight.get());
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.LIGHT, id));
    }
  }
}
