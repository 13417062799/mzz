package com.distlab.hqsms.meter;

import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.net.RestTemplateService;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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

@Api(tags = "电能表")
@Validated
@RestController
@RequestMapping(path = "/meters")
public class MeterController {

  private static final Logger logger = LoggerFactory.getLogger(MeterController.class);
  @Autowired
  MeterRepository meterRepository;
  @Autowired
  DeviceService deviceService;
  @Autowired
  RestTemplateService restTemplateService;


  // CREATE

  @PostMapping(path = "")
  @ApiOperation(value = "创建电能表设备信息")
  public WebResponse<Map<String, BigInteger>> createMeter(@RequestBody Meter meter) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.METER);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.METER));
    }
    meter.setId(id);
    meter.setCreatedAt(new Date());
    meter.setUpdatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", meter.getId());
    try {
      meterRepository.save(meter);
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.METER, id));
    }
  }

  // READ
  @GetMapping(path = "")
  @ApiOperation(value = "获取电能表设备信息列表")
  public WebResponse<Page<Meter>> getMeters(
    @Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable
  ) {
    return deviceService.search(meterRepository, filter, pageable);
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取电能表设备信息")
  @ApiImplicitParam(name = "id", value = "电能表设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<Meter>> getMeter(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    try {
      return WebResponse.success(meterRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());

    }
  }


  // UPDATE

  @ApiIgnore
  @PostMapping(path = "/edge/{id}")
  public WebResponse<Meter> updateEdgeMeter(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody Meter meterSource
  ) {
    Optional<Meter> tMeter = meterRepository.findById(id);
    if (!tMeter.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.METER, id));
    }
    try {
      Meter meterTarget = deviceService.updateFields(meterSource, tMeter.get());
      meterTarget.setUpdatedAt(new Date());
      Meter ret = meterRepository.save(meterTarget);
      return WebResponse.success(ret);
    } catch (IllegalAccessException e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.METER, id));
    }
  }

  @PostMapping(path = "/{id}")
  @ApiOperation(value = "更新电能表设备信息")
  @ApiImplicitParam(name = "id", value = "电能表设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Meter> updateMeter(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody Meter meter
  ) {
    String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.METER);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.METER, id));
    }

    WebResponse<Meter> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/meters/edge/%d", address, id),
      HttpMethod.POST,
      new HttpEntity<>(meter),
      new ParameterizedTypeReference<WebResponse<Meter>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    Meter rMeter = remoteRet.getData();
    try {
      Meter ret = meterRepository.save(rMeter);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.METER, rMeter.getId()));
    }
  }


  //DELETE

  @DeleteMapping(path = "/{id}")
  @ApiOperation(value = "删除电能表设备信息")
  @ApiImplicitParam(name = "id", value = "电能表设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Map<String, BigInteger>> deleteMeter(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    Optional<Meter> tMeter = meterRepository.findById(id);
    if (!tMeter.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.METER, id));
    }
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", tMeter.get().getId());
    try {
      meterRepository.delete(tMeter.get());
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.METER, id));
    }
  }
}
