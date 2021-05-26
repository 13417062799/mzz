package com.distlab.hqsms.broadcast.device;

import com.alibaba.fastjson.JSONObject;
import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.net.RestTemplateService;
import com.distlab.hqsms.edge.DeviceAndLogFilter;
import com.distlab.hqsms.strategy.Rule;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.edge.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Range;
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

@Api(tags = "广播")
@Validated
@RestController
@RequestMapping(path = "/broadcasts")
public class BroadcastController {

  private static final Logger logger = LoggerFactory.getLogger(BroadcastController.class);
  @Autowired
  BroadcastRepository broadcastRepository;
  @Autowired
  BroadcastService broadcastService;
  @Autowired
  DeviceService deviceService;
  @Autowired
  RestTemplateService restTemplateService;

  @Value("${hqsms.edge.broadcast.enable}")
  private Boolean edgeBroadcastEnable;


  // CREATE

  @PostMapping(path = "")
  @ApiOperation(value = "创建广播设备信息")
  public WebResponse<Map<String, BigInteger>> createBroadcast(@RequestBody Broadcast broadcast) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.BROADCAST);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.BROADCAST));
    }
    broadcast.setId(id);
    broadcast.setCreatedAt(new Date());
    broadcast.setUpdatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", broadcast.getId());
    try {
      broadcastRepository.save(broadcast);
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST, id));
    }
  }


  //READ

  @GetMapping(path = "")
  @ApiOperation(value = "获取广播设备信息列表")
  public WebResponse<Page<Broadcast>> getAllBroadcasts(
    @Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable
  ) {
    return deviceService.search(broadcastRepository, filter, pageable);
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取广播设备信息")
  @ApiImplicitParam(name = "id", value = "广播设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<Broadcast>> getBroadcast(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    try {
      return WebResponse.success(broadcastRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }


  //UPDATE

  @ApiIgnore
  @PostMapping(path = "/edge/{id}")
  public WebResponse<Broadcast> updateEdgeBroadcast(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody Broadcast broadcastSource
  ) {
    Optional<Broadcast> tBroadcast = broadcastRepository.findById(id);
    if (!tBroadcast.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.BROADCAST, id));
    }
    try {
      Broadcast broadcastTarget = deviceService.updateFields(broadcastSource, tBroadcast.get());
      broadcastTarget.setUpdatedAt(new Date());
      Broadcast ret = broadcastRepository.save(broadcastTarget);
      return WebResponse.success(ret);
    } catch (IllegalAccessException e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST, id));
    }
  }

  @PostMapping(path = "/{id}")
  @ApiOperation(value = "更新广播设备信息")
  @ApiImplicitParam(name = "id", value = "广播设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Broadcast> updateBroadcast(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody Broadcast broadcast
  ) {
    String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.BROADCAST);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.BROADCAST, id));
    }

    WebResponse<Broadcast> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/broadcasts/edge/%d", address, id),
      HttpMethod.POST,
      new HttpEntity<>(broadcast),
      new ParameterizedTypeReference<WebResponse<Broadcast>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    Broadcast rBroadcast = remoteRet.getData();
    try {
      Broadcast ret = broadcastRepository.save(rBroadcast);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST, rBroadcast.getId()));
    }
  }

  @ApiIgnore
  @PostMapping(path = "/edge/{id}/volume")
  public WebResponse<Broadcast> setEdgeVolume(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger broadcastId,
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @Range(min = 0, max = 56, message = "value range 0-56.")
    @RequestParam(name = "volume", required = false) Integer volume
  ) {
    if (!edgeBroadcastEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.BROADCAST));
    }
    Broadcast broadcast = broadcastService.volumeExecutor(broadcastId, volume);
    if (broadcast == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success(broadcast);
  }

  @PostMapping(path = "/{id}/volume")
  @ApiOperation(value = "控制广播设备音量")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "设备ID", dataTypeClass = BigInteger.class),
    @ApiImplicitParam(name = "volume", value = "音量(0-56)", dataTypeClass = Integer.class)
  })
  public WebResponse<Broadcast> setVolume(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @Range(min = 0, max = 56, message = "value range 0-56.")
    @RequestParam(name = "volume", required = false) Integer volume
  ) {
    String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.BROADCAST);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
    }

    MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
    requestBody.add("volume", volume);
    WebResponse<Broadcast> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/broadcasts/edge/%d/volume", address, id),
      HttpMethod.POST,
      new HttpEntity<>(requestBody),
      new ParameterizedTypeReference<WebResponse<Broadcast>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    Broadcast rBroadcast = remoteRet.getData();
    try {
      Broadcast ret = broadcastRepository.save(rBroadcast);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST, rBroadcast.getId()));
    }
  }


  //DELETE

  @DeleteMapping(path = "/{id}")
  @ApiOperation(value = "删除广播设备信息")
  @ApiImplicitParam(name = "id", value = "广播设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Map<String, BigInteger>> deleteBroadcast(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    Optional<Broadcast> tBroadcast = broadcastRepository.findById(id);
    if (!tBroadcast.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.BROADCAST, id));
    }
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", tBroadcast.get().getId());
    try {
      broadcastRepository.delete(tBroadcast.get());
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST, id));
    }
  }
}
