package com.distlab.hqsms.wifi;

import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.edge.DeviceAndLogFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

@Api(tags = "无线网络")
@Validated
@RestController
@RequestMapping(path = "/wifi-logs")
public class WifiLogController {

  @Autowired
  WifiLogRepository wifiLogRepository;
  @Autowired
  WifiService wifiService;


  @GetMapping(path = "")
  @ApiOperation(value = "获取无线用户数据列表")
  public WebResponse<Page<WifiLog>> getWifiLogs(
    @Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable
  ) {
    Specification<WifiLog> specification = wifiService.searchLog(filter);
    try {
      Page<WifiLog> pages = wifiLogRepository.findAll(specification, pageable);
      return WebResponse.success(pages);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取无线用户数据")
  @ApiImplicitParam(name = "id", value = "无线用户数据ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<WifiLog>> getWifiLog(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id")BigInteger id
  ) {
    try {
      return WebResponse.success(wifiLogRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @GetMapping(path = "/search/findByLogin")
  @ApiOperation(value = "根据上线时间获取无线用户数据列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "start", value = GlobalConstant.MSG_BY_TIME_START, dataTypeClass = String.class),
    @ApiImplicitParam(name = "end", value = GlobalConstant.MSG_BY_TIME_END, dataTypeClass = String.class)
  })
  public WebResponse<Page<WifiLog>> getWifiLogsByLogin(
//    @Pattern(regexp = GlobalConstant.RGP_DATETIME_FORMAT, message = GlobalConstant.MSG_DATETIME_FORMAT)
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @DateTimeFormat(pattern = GlobalConstant.FORMAT_DATETIME)
    @RequestParam(name = "start", required = false) Date start,
//    @Pattern(regexp = GlobalConstant.RGP_DATETIME_FORMAT, message = GlobalConstant.MSG_DATETIME_FORMAT)
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @DateTimeFormat(pattern = GlobalConstant.FORMAT_DATETIME)
    @RequestParam(name = "end", required = false) Date end,
    Pageable pageable
  ) {
    try {
      return WebResponse.success(wifiLogRepository.findByLoginBetween(start, end, pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @GetMapping(path = "/search/findByOffType")
  @ApiOperation(value = "根据离线类型获取无线用户数据列表")
  public WebResponse<Page<WifiLog>> getWifiLogsByOffType(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @Max(value = 1, message = "must be less than 2")
    @RequestParam(name = "offType") Integer offType,
    Pageable pageable
  ) {
    try {
      return WebResponse.success(wifiLogRepository.findByOffType(offType, pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }
}
