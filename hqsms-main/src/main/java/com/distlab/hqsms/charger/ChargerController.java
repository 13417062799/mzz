package com.distlab.hqsms.charger;

import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.net.AutoNaviService;
import com.distlab.hqsms.common.net.RestTemplateService;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.edge.DeviceAndLogFilter;
import com.distlab.hqsms.edge.DeviceService;
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
import java.util.*;

@Api(tags = "充电桩")
@Validated
@RestController
@RequestMapping(path = "/chargers")
public class ChargerController {

  private static final Logger logger = LoggerFactory.getLogger(ChargerController.class);
  @Autowired
  ChargerRepository chargerRepository;
  @Autowired
  ChargerOrderRepository chargerOrderRepository;
  @Autowired
  AutoNaviService autoNaviService;
  @Autowired
  DeviceService deviceService;
  @Autowired
  RestTemplateService restTemplateService;


  // CREATE

  @PostMapping(path = "")
  @ApiOperation(value = "创建充电桩设备信息")
  public WebResponse<Map<String, BigInteger>> createCharger(@RequestBody Charger charger) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.CHARGER);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.CHARGER));
    }
    charger.setId(id);
    charger.setCreatedAt(new Date());
    charger.setUpdatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", id);
    try {
      chargerRepository.save(charger);
      return WebResponse.success(map);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.CHARGER, id));
    }
  }


  // READ

  @GetMapping(path = "")
  @ApiOperation(value = "获取充电桩设备信息列表")
  public WebResponse<Page<Charger>> getChargers(
    @Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable
  ) {
    return deviceService.search(chargerRepository, filter, pageable);
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取充电桩设备信息")
  @ApiImplicitParam(name = "id", value = "充电桩设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<Charger>> getCharger(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
    ) {
    try {
      return WebResponse.success(chargerRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiIgnore
  @GetMapping(path = "/search/findByDistrict")
  @ApiOperation(value = "根据行政区获取充电桩设备信息列表")
  public Page<Charger> getChargersByDistrict(
    @NotBlank(message = GlobalConstant.MSG_NOT_BLANK) @RequestParam(name = "district", required = false) String district, Pageable pageable
  ) {
    ArrayList<BigInteger> poleIds = autoNaviService.findPoleIdsByDistrict(district);
    if (poleIds.isEmpty()) {
      return null;
    }
    return chargerRepository.findByPoleIdIn(poleIds, pageable);
  }


  // UPDATE

  @ApiIgnore
  @PostMapping(path = "/edge/{id}")
  public WebResponse<Charger> updateEdgeCharger(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody Charger chargerSource
  ) {
    Optional<Charger> tCharger = chargerRepository.findById(id);
    if (!tCharger.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.CHARGER, id));
    }
    try {
      Charger chargerTarget = deviceService.updateFields(chargerSource, tCharger.get());
      chargerTarget.setUpdatedAt(new Date());
      Charger ret = chargerRepository.save(chargerTarget);
      return WebResponse.success(ret);
    } catch (IllegalAccessException e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.CHARGER, id));
    }
  }

  @PostMapping(path = "/{id}")
  public WebResponse<Charger> updateCharger(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody Charger charger
  ) {
    String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.CHARGER);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.CHARGER, id));
    }

    WebResponse<Charger> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/chargers/edge/%d", address, id),
      HttpMethod.POST,
      new HttpEntity<>(charger),
      new ParameterizedTypeReference<WebResponse<Charger>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    Charger rCharger = remoteRet.getData();
    try {
      Charger ret = chargerRepository.save(rCharger);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.CHARGER, rCharger.getId()));
    }
  }


  //DELETE

  @DeleteMapping(path = "/{id}")
  @ApiOperation(value = "删除充电桩设备信息")
  @ApiImplicitParam(name = "id", value = "充电桩设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Map<String, BigInteger>> deleteCharger(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    Optional<Charger> tCharger = chargerRepository.findById(id);
    if (!tCharger.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.CHARGER, id));
    }
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", tCharger.get().getId());
    try {
      chargerRepository.delete(tCharger.get());
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.CHARGER, id));
    }
  }
}
