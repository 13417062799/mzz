package com.distlab.hqsms.wifi;

import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.net.RestTemplateService;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.edge.DeviceAndLogFilter;
import com.distlab.hqsms.edge.DeviceService;
import com.distlab.hqsms.meter.*;
import com.distlab.hqsms.strategy.Rule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = "无线网络")
@Validated
@RestController
@RequestMapping(path = "/wifi-aps")
public class WifiAPController {
  private static final Logger logger = LoggerFactory.getLogger(MeterController.class);
  @Autowired
  WifiAPRepository wifiAPRepository;
  @Autowired
  DeviceService deviceService;
  @Autowired
  RestTemplateService restTemplateService;


  // CREATE

  @PostMapping(path = "")
  @ApiOperation(value = "创建AP设备信息")
  public WebResponse<Map<String, BigInteger>> createWifiAP(@RequestBody WifiAP wifiAP) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.WIFI);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.WIFI));
    }
    wifiAP.setId(id);
    wifiAP.setCreatedAt(new Date());
    wifiAP.setUpdatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", wifiAP.getId());
    try {
      wifiAPRepository.save(wifiAP);
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.WIFI, id));
    }
  }

  // READ
  @GetMapping(path = "")
  @ApiOperation(value = "获取AP设备信息列表")
  public WebResponse<Page<WifiAP>> getWifiAPs(
    @Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable
  ) {
    return deviceService.search(wifiAPRepository, filter, pageable);
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取AP设备信息")
  @ApiImplicitParam(name = "id", value = "AP设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<WifiAP>> getWifiAP(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    try {
      return WebResponse.success(wifiAPRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }


  // UPDATE

  @ApiIgnore
  @PostMapping(path = "/edge/{id}")
  public WebResponse<WifiAP> updateEdgeWifiAP(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody WifiAP wifiAPSource
  ) {
    Optional<WifiAP> tWifiAP = wifiAPRepository.findById(id);
    if (!tWifiAP.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.WIFI, id));
    }
    try {
      WifiAP wifiAPTarget = deviceService.updateFields(wifiAPSource, tWifiAP.get());
      wifiAPTarget.setUpdatedAt(new Date());
      WifiAP ret = wifiAPRepository.save(wifiAPTarget);
      return WebResponse.success(ret);
    } catch (IllegalAccessException e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.WIFI, id));
    }
  }

  @PostMapping(path = "/{id}")
  @ApiOperation(value = "更新AP设备信息")
  @ApiImplicitParam(name = "id", value = "AP设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<WifiAP> updateWifiAP(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody WifiAP wifiAP
  ) {
    String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.WIFI);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.WIFI, id));
    }

    WebResponse<WifiAP> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/wifi-aps/edge/%d", address, id),
      HttpMethod.POST,
      new HttpEntity<>(wifiAP),
      new ParameterizedTypeReference<WebResponse<WifiAP>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    WifiAP rWifiAP = remoteRet.getData();
    try {
      WifiAP ret = wifiAPRepository.save(rWifiAP);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.WIFI, rWifiAP.getId()));
    }
  }


  //DELETE

  @DeleteMapping(path = "/{id}")
  @ApiOperation(value = "删除AP设备信息")
  @ApiImplicitParam(name = "id", value = "AP设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Map<String, BigInteger>> deleteWifiAP(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    Optional<WifiAP> tWifiAP = wifiAPRepository.findById(id);
    if (!tWifiAP.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.WIFI, id));
    }
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", tWifiAP.get().getId());
    try {
      wifiAPRepository.delete(tWifiAP.get());
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.WIFI, id));
    }
  }
}
