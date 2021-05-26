package com.distlab.hqsms.weather;

import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.edge.DeviceAndLogFilter;
import com.distlab.hqsms.edge.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Api(tags = "气象传感器")
@Validated
@RestController
@RequestMapping(path = "/weather-logs")
public class WeatherLogController {
  @Autowired
  private WeatherLogRepository weatherLogRepository;
  @Autowired
  private DeviceService deviceService;

  @GetMapping(path = "")
  @ApiOperation(value = "获取气象数据列表")
  public WebResponse<Page<WeatherLog>> getWeatherLogs(
    @Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable
  ) {
    Specification<WeatherLog> specification = (Specification<WeatherLog>) (root, query, criteriaBuilder) -> null;
    specification = deviceService.searchLog(specification, filter);
    try {
      Page<WeatherLog> pages = weatherLogRepository.findAll(specification, pageable);
      return WebResponse.success(pages);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取气象数据")
  @ApiImplicitParam(name = "id", value = "气象数据ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<WeatherLog>> getWeather(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    try {
      return WebResponse.success(weatherLogRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }


}
