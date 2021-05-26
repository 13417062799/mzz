package com.distlab.hqsms.charger;

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

@Api(tags = "充电桩")
@Validated
@RestController
@RequestMapping(path = "charger-orders")
public class ChargerOrderController {

  @Autowired
  ChargerOrderRepository chargerOrderRepository;
  @Autowired
  DeviceService deviceService;


  @GetMapping(path = "")
  @ApiOperation(value = "获取充电桩订单数据列表")
  public WebResponse<Page<ChargerOrder>> getChargerOrders(
    @Valid @RequestBody DeviceAndLogFilter filter, Pageable pageable
  ) {
    Specification<ChargerOrder> specification = (Specification<ChargerOrder>) (root, query, criteriaBuilder) -> null;
    specification = deviceService.searchLog(specification, filter);
    try {
      Page<ChargerOrder> pages = chargerOrderRepository.findAll(specification, pageable);
      return WebResponse.success(pages);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取充电桩订单数据")
  @ApiImplicitParam(name = "id", value = "充电桩订单数据ID", dataTypeClass = BigInteger.class)
  public WebResponse<Optional<ChargerOrder>> getChargerOrder(
    @NotNull(message = GlobalConstant.MSG_NOT_NULL) @PathVariable(name = "id")BigInteger id
  ) {
    try {
      return WebResponse.success(chargerOrderRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

}
