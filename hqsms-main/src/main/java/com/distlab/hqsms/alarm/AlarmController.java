package com.distlab.hqsms.alarm;

import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.net.RestTemplateService;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.exception.WebResponse;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = "报警器")
@Validated
@RestController
@RequestMapping(path = "/alarms")
public class AlarmController {

  private final Logger logger = LoggerFactory.getLogger(AlarmController.class);

  @Autowired
  private AlarmRepository alarmRepository;
  @Autowired
  private DeviceService deviceService;
  @Autowired
  private RestTemplateService restTemplateService;


  // CREATE

  @PostMapping(path = "")
  @ApiOperation(value = "创建报警器设备信息")
  public WebResponse<Map<String, BigInteger>> createAlarm(@RequestBody Alarm alarm) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.ALARM);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.ALARM));
    }
    alarm.setId(id);
    alarm.setCreatedAt(new Date());
    alarm.setUpdatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", id);
    try {
      alarmRepository.save(alarm);
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.ALARM, id));
    }
  }


  // READ

  @GetMapping(path = "")
  @ApiOperation(value = "获取报警器设备信息列表")
  public WebResponse<Page<Alarm>> getAlarms(
    @Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable
  ) {
    return deviceService.search(alarmRepository, filter, pageable);
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取报警器设备信息")
  @ApiImplicitParam(name = "id", value = "报警器设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<Alarm>> getAlarm(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    try {
      return WebResponse.success(alarmRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
  }


  // UPDATE

  @ApiIgnore
  @PostMapping(value = "/edge/{id}")
  public WebResponse<Alarm> updateEdgeAlarm(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody Alarm alarmSource
  ) {
    Optional<Alarm> tAlarm = alarmRepository.findById(id);
    if (!tAlarm.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.ALARM, id));
    }
    try {
      Alarm alarmTarget = deviceService.updateFields(alarmSource, tAlarm.get());
      alarmTarget.setUpdatedAt(new Date());
      Alarm ret = alarmRepository.save(alarmTarget);
      return WebResponse.success(ret);
    }catch (IllegalAccessException e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.ALARM, id));
    }
  }

  @PostMapping(value = "/{id}")
  @ApiOperation(value = "更新报警器设备信息")
  @ApiImplicitParam(name = "id", value = "报警器设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Alarm> updateAlarm(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody Alarm alarm
  ) {
    String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.ALARM);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
    }

    WebResponse<Alarm> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/alarms/edge/%d", address, id),
      HttpMethod.POST,
      new HttpEntity<>(alarm),
      new ParameterizedTypeReference<WebResponse<Alarm>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    Alarm rAlarm = remoteRet.getData();
    try {
      Alarm ret = alarmRepository.save(rAlarm);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.ALARM, rAlarm.getId()));
    }
  }


  // DELETE

  @DeleteMapping(value = "/{id}")
  @ApiOperation(value = "删除报警器设备信息")
  @ApiImplicitParam(name = "id", value = "报警器设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Map<String, BigInteger>> deleteAlarm(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable BigInteger id
  ) {
    Optional<Alarm> tAlarm = alarmRepository.findById(id);
    if (!tAlarm.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.genIdFailed(MessageEnum.ALARM));
    }
    try {
      alarmRepository.delete(tAlarm.get());
      Map<String, BigInteger> map = new HashMap<>();
      map.put("Id", id);
      return WebResponse.success(map);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.ALARM, id));
    }
  }


}
