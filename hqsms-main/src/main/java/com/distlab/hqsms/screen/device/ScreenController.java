package com.distlab.hqsms.screen.device;

import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.net.AutoNaviService;
import com.distlab.hqsms.common.net.RestTemplateService;
import com.distlab.hqsms.strategy.Rule;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.edge.DeviceService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags = "屏幕")
@Validated
@RestController
@RequestMapping(path = "/screens")
public class ScreenController {

  private static final Logger logger = LoggerFactory.getLogger(ScreenController.class);
  @Autowired
  ScreenService screenService;
  @Autowired
  ScreenRepository screenRepository;
  @Autowired
  DeviceService deviceService;
  @Autowired
  RestTemplateService restTemplateService;
  @Autowired
  AutoNaviService autoNaviService;
  @Value("${hqsms.edge.screen.enable}")
  private Boolean edgeScreenEnable;


  //CREATE

  @PostMapping(path = "")
  @ApiOperation(value = "创建屏幕设备信息")
  public WebResponse<Map<String, BigInteger>> createScreen(@RequestBody Screen screen) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.SCREEN);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.SCREEN));
    }
    screen.setId(id);
    screen.setCreatedAt(new Date());
    screen.setUpdatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", screen.getId());
    try {
      screenRepository.save(screen);
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.SCREEN, id));
    }
  }


  //READ

  @GetMapping(path = "")
  @ApiOperation(value = "获取屏幕设备信息列表")
  public WebResponse<Page<Screen>> getAllScreens(Pageable pageable) {
    try {
      return WebResponse.success(screenRepository.findAll(pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取屏幕设备信息")
  @ApiImplicitParam(name = "id", value = "屏幕设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<Screen>> getScreen(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    try {
      return WebResponse.success(screenRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiIgnore
  @GetMapping(path = "/search/findByDistrict")
  @ApiOperation(value = "根据行政区获取屏幕设备信息列表")
  public Page<Screen> getScreensByDistrict(
    @NotBlank(message = GlobalConstant.MSG_NOT_BLANK) @RequestParam(name = "district", required = false) String district, Pageable pageable
  ) {
    ArrayList<BigInteger> poleIds = autoNaviService.findPoleIdsByDistrict(district);
    if (poleIds.isEmpty()) {
      return null;
    }
    return screenRepository.findByPoleIdIn(poleIds, pageable);
  }

  @GetMapping(path = "/search/findByField")
  @ApiOperation(value = "根据指定字段获取屏幕设备信息列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "type", value = GlobalConstant.MSG_BY_FIELD_TYPE, dataTypeClass = Integer.class),
    @ApiImplicitParam(name = "keyword", value = GlobalConstant.MSG_BY_FIELD_KEYWORD, dataTypeClass = String.class)
  })public WebResponse<Page<Screen>> getScreensByField(
    @Min(value = 1, message = "must be greater than 0") @Max(value = 5, message = "must be less than 6")
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @RequestParam(name = "type", required = false) Integer type,
    @NotBlank(message = GlobalConstant.MSG_NOT_BLANK) @RequestParam(name = "keyword", required = false) String keyword,
    Pageable pageable
  ) {
    return deviceService.search(screenRepository, type, keyword, pageable);
  }

  @GetMapping(path = "/search/findByTime")
  @ApiOperation(value = "根据创建时间获取屏幕设备信息列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "start", value = GlobalConstant.MSG_BY_TIME_START, dataTypeClass = String.class),
    @ApiImplicitParam(name = "end", value = GlobalConstant.MSG_BY_TIME_END, dataTypeClass = String.class)
  })public WebResponse<Page<Screen>> getScreensByTime(
    @Pattern(regexp = GlobalConstant.RGP_DATETIME_FORMAT, message = GlobalConstant.MSG_DATETIME_FORMAT)
    @NotBlank(message = GlobalConstant.MSG_NOT_BLANK) @RequestParam(name = "start", required = false) String start,
    @Pattern(regexp = GlobalConstant.RGP_DATETIME_FORMAT, message = GlobalConstant.MSG_DATETIME_FORMAT)
    @NotBlank(message = GlobalConstant.MSG_NOT_BLANK) @RequestParam(name = "end", required = false) String end,
    Pageable pageable
  ) {
    Date kStart, kEnd;
    try {
      kStart = new SimpleDateFormat(GlobalConstant.FORMAT_DATETIME).parse(start);
      kEnd = new SimpleDateFormat(GlobalConstant.FORMAT_DATETIME).parse(end);
    } catch (ParseException e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
    try {
      return WebResponse.success(screenRepository.findByCreatedAtBetween(kStart, kEnd, pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }



  //UPDATE

  @ApiIgnore
  @PostMapping(path = "/edge/{id}")
  public WebResponse<Screen> updateEdgeScreen(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody Screen screenSource
  ) {
    Optional<Screen> tScreen = screenRepository.findById(id);
    if (!tScreen.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SCREEN, id));
    }
    try {
      Screen screenTarget = deviceService.updateFields(screenSource, tScreen.get());
      screenTarget.setUpdatedAt(new Date());
      Screen ret = screenRepository.save(screenTarget);
      return WebResponse.success(ret);
    } catch (IllegalAccessException e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.SCREEN, id));
    }
  }

  @PostMapping(path = "/{id}")
  @ApiOperation(value = "更新屏幕设备信息")
  @ApiImplicitParam(name = "id", value = "屏幕设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Screen> updateScreen(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody Screen screen
  ) {
    String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.SCREEN);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SCREEN, id));
    }

    WebResponse<Screen> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/screens/edge/%d", address, id),
      HttpMethod.POST,
      new HttpEntity<>(screen),
      new ParameterizedTypeReference<WebResponse<Screen>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    Screen rScreen = remoteRet.getData();
    try {
      Screen ret = screenRepository.save(rScreen);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.SCREEN, rScreen.getId()));
    }
  }

  @ApiIgnore
  @PostMapping(path = "/edge/{id}/set")
  public WebResponse<Screen> setEdgeScreen(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody ScreenSetterInfo setterInfo
  ) {
    if (!edgeScreenEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.SCREEN));
    }
    Screen screen = screenService.setter(id, setterInfo);
    if (screen == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success(screen);
  }

  @PostMapping(path = "/{id}/set")
  @ApiOperation(value = "控制屏幕设备", notes = "可控制功能：音量、亮度、色温、分辨率、电源")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "屏幕设备ID", dataTypeClass = BigInteger.class),
    @ApiImplicitParam(name = "setterInfo", value = "控制参数（选其一，分辨率需height和width同时）")
  })
  public WebResponse<Screen> setScreen(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id,
    @RequestBody ScreenSetterInfo setterInfo
  ) {
    String address = deviceService.getServerAddress(id, Rule.RuleDeviceType.SCREEN);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
    }

    WebResponse<Screen> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/screens/edge/%d/set", address, id),
      HttpMethod.POST,
      new HttpEntity<>(setterInfo),
      new ParameterizedTypeReference<WebResponse<Screen>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    Screen rScreen = remoteRet.getData();
    try {
      Screen ret = screenRepository.save(rScreen);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST, rScreen.getId()));
    }
  }


  //DELETE

  @DeleteMapping(path = "/{id}")
  @ApiOperation(value = "删除屏幕设备信息")
  @ApiImplicitParam(name = "id", value = "屏幕设备ID", dataTypeClass = BigInteger.class)
  public WebResponse<Map<String, BigInteger>> deleteScreen(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    Optional<Screen> tScreen = screenRepository.findById(id);
    if (!tScreen.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SCREEN, id));
    }
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", tScreen.get().getId());
    try {
      screenRepository.delete(tScreen.get());
      return WebResponse.success(map);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.SCREEN, id));
    }
  }

}
