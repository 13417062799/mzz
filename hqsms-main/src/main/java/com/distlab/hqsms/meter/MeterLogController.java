package com.distlab.hqsms.meter;

import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.edge.DeviceAndLogFilter;
import com.distlab.hqsms.edge.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Optional;

@Api(tags = "电能表")
@Validated
@RestController
@RequestMapping(path = "/meter-logs")
public class MeterLogController {

  @Autowired
  MeterLogRepository meterLogRepository;
  @Autowired
  DeviceService deviceService;


  @GetMapping(path = "")
  @ApiOperation(value = "获取电能表数据列表")
  public WebResponse<Page<MeterLog>> getMeterLogs(
    @Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable
  ) {
    Specification<MeterLog> specification = (Specification<MeterLog>) (root, query, criteriaBuilder) -> null;
    specification = deviceService.searchLog(specification, filter);
    try {
      Page<MeterLog> pages = meterLogRepository.findAll(specification, pageable);
      return WebResponse.success(pages);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取电能表数据")
  @ApiImplicitParam(name = "id", value = "电能表数据ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<MeterLog>> getMeterLog(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id")BigInteger id
  ) {
    try {
      return WebResponse.success(meterLogRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }
}
