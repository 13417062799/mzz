package com.distlab.hqsms.strategy;

import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.Optional;

@Api(tags = {"策略"})
@Controller
@RequestMapping(path = "/rule-events")
public class RuleEventController {
  private final Logger logger = LoggerFactory.getLogger(RuleEventController.class);
  @Autowired
  private RuleEventRepository ruleEventRepository;

  @ApiOperation(value = "获取策略数据列表")
  @GetMapping(path = "")
  public @ResponseBody WebResponse<Page<RuleEvent>> getRuleLogs(Pageable pageable) {
    try {
      return WebResponse.success(ruleEventRepository.findAll(pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "获取策略数据")
  @GetMapping(path = "/{id}")
  public @ResponseBody WebResponse<Optional<RuleEvent>> getRuleLog(@PathVariable BigInteger id) {
    try {
      return WebResponse.success(ruleEventRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

}
