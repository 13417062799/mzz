package com.distlab.hqsms.alarm;

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

@Api(tags = "报警器")
@Validated
@RestController
@RequestMapping(path = "/alarm-logs")
public class AlarmLogController {
  @Autowired
  private AlarmLogRepository alarmLogRepository;
  @Autowired
  private DeviceService deviceService;

  @GetMapping(path = "")
  @ApiOperation(value = "获取报警器数据列表")
  public WebResponse<Page<AlarmLog>> getAlarmLogs(
    @Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable
  ) {
    Specification<AlarmLog> specification = (Specification<AlarmLog>) (root, query, criteriaBuilder) -> null;
    specification = deviceService.searchLog(specification, filter);
    try {
      Page<AlarmLog> pages = alarmLogRepository.findAll(specification, pageable);
      return WebResponse.success(pages);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取报警器数据")
  @ApiImplicitParam(name = "id", value = "报警器数据ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<AlarmLog>> getAlarmLog(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id") BigInteger id
  ) {
    try {
      return WebResponse.success(alarmLogRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
  }

}
