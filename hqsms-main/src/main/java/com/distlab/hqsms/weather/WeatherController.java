package com.distlab.hqsms.weather;

import com.alibaba.fastjson.JSONObject;
import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.net.RestTemplateService;
import com.distlab.hqsms.edge.Device;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.exception.WebResponse;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = "气象传感器")
@RestController
@RequestMapping(path = "/weathers")
public class WeatherController {
  private final Logger logger = LoggerFactory.getLogger(WeatherController.class);
  @Autowired
  private WeatherRepository weatherRepository;
  @Autowired
  private DeviceService deviceService;
  @Autowired
  private RestTemplateService restTemplateService;


  // CREATE

  @PostMapping(path = "")
  @ApiOperation(value = "创建气象设备信息")
  public WebResponse<Map<String, BigInteger>> createWeather(@RequestBody Weather weather) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.WEATHER);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.WEATHER));
    }
    weather.setId(id);
    weather.setCreatedAt(new Date());
    weather.setUpdatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", weather.getId());
    try {
      weatherRepository.save(weather);
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.WEATHER, id));
    }
  }


  // READ

  @GetMapping(path = "")
  @ApiOperation(value = "获取气象设备信息列表")
  public WebResponse<Page<Weather>> getAllWeathers(
    @Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable
  ) {
    return deviceService.search(weatherRepository, filter, pageable);
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取气象设备信息")
  @ApiImplicitParam(name = "id", value = "气象设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<Weather>> getWeather(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    try {
      return WebResponse.success(weatherRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }


  // UPDATE

  @PostMapping(value = "/edge/{id}")
  public WebResponse<Weather> updateEdgeWeather(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable BigInteger id,
    @RequestBody Weather weatherSource
  ) {
    Optional<Weather> tWeather = weatherRepository.findById(id);
    if (!tWeather.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.WEATHER, id));
    }
    try {
      Weather weatherTarget = deviceService.updateFields(weatherSource, tWeather.get());
      weatherTarget.setUpdatedAt(new Date());
      Weather ret = weatherRepository.save(weatherTarget);
      return WebResponse.success(ret);
    } catch (IllegalAccessException e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.WEATHER, id));
    }
  }

  @PostMapping(value = "/{id}")
  @ApiOperation(value = "更新气象设备信息")
  @ApiImplicitParam(name = "id", value = "气象设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Weather> updateWeather(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable BigInteger id,
    @RequestBody Weather weather
  ) {
    String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.WEATHER);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.WEATHER, id));
    }

    WebResponse<Weather> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/weathers/edge/%d", address, id),
      HttpMethod.POST,
      new HttpEntity<>(weather),
      new ParameterizedTypeReference<WebResponse<Weather>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    Weather rWeather = remoteRet.getData();
    try {
      Weather ret = weatherRepository.save(rWeather);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.WEATHER, rWeather.getId()));
    }
  }


  // DELETE

  @DeleteMapping(value = "/{id}")
  @ApiOperation(value = "删除气象设备信息")
  @ApiImplicitParam(name = "id", value = "气象设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Map<String, BigInteger>> deleteWeather(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable BigInteger id
  ) {
    Optional<Weather> tWeather = weatherRepository.findById(id);
    if (!tWeather.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.WEATHER, id));
    }
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", tWeather.get().getId());
    try {
      weatherRepository.delete(tWeather.get());
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.WEATHER, id));
    }
  }
}
