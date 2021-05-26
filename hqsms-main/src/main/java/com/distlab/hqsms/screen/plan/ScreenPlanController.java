package com.distlab.hqsms.screen.plan;

import com.alibaba.fastjson.JSONObject;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

@RestController
@RequestMapping(path = "/screen-plans")
@Api(tags = "屏幕")
public class ScreenPlanController {
  @Autowired
  ScreenPlanService screenPlanService;
  @Autowired
  ScreenPlanRepository screenPlanRepository;

  private static final Logger logger = LoggerFactory.getLogger(ScreenPlanController.class);

  //增
  @ResponseBody
  @RequestMapping(path = "")
  @ApiOperation(value = "新增计划接口", notes = "根据单个或多个设备ID新增计划", httpMethod = "POST")
  private WebResponse<ScreenPlan> addPlan(@RequestBody PlanJSON4Add json) {
    //获取用户输入参数
    BigInteger screenId = json.getScreenId();
    String cronExpr = json.getCronExpr();
    String endDate = json.getEndDate();
    BigInteger contentId = json.getContentId();
    String planName = json.getPlanName();

    ScreenPlan plan = screenPlanService.add(screenId, cronExpr, endDate, planName, contentId);
    if (plan == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success(plan);
  }


  //删
  @ResponseBody
  @DeleteMapping(path = "/{planId}")
  @ApiOperation(value = "删除计划接口", notes = "根据计划ID删除计划", httpMethod = "DELETE")
  private WebResponse<Map<String, BigInteger>> delete(@PathVariable BigInteger planId) {
    ScreenPlan plan = screenPlanService.delete(planId);
    if (plan == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", plan.getId());
    return WebResponse.success(map);
  }


  //改
  @ResponseBody
  @RequestMapping(path = "/update")
  @ApiOperation(value = "更新计划接口", notes = "可更新计划的时间、是否启用", httpMethod = "POST")
  private WebResponse<ScreenPlan> update(@RequestBody PlanJSON4Update json) {
    BigInteger planId = json.getPlanId();
    String cronExpr = json.getCronExpr();
    Boolean enable = json.getEnable();

    ScreenPlan plan = screenPlanService.update(planId, cronExpr, enable);
    if (plan == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success(plan);
  }


  //查
  @GetMapping(path = "")
  @ApiOperation(value = "获取屏幕计划列表", notes = "获取所有屏幕计划信息", httpMethod = "GET")
  private WebResponse<Page<ScreenPlan>> getPlans(Pageable pageable) {
    try {
      return WebResponse.success(screenPlanRepository.findAll(pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @GetMapping(path = "/{planId}")
  @ApiOperation(value = "获取屏幕计划信息", notes = "根据计划ID获取指定计划信息", httpMethod = "GET")
  private WebResponse<Optional<ScreenPlan>> getPlan(@PathVariable BigInteger planId) {
    try {
      return WebResponse.success(screenPlanRepository.findById(planId));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }


  //JSON 数据封装
  @ApiModel(value = "屏幕计划参数", description = "JSON格式封装发送")
  public static class PlanJSON4Add {

    @ApiModelProperty(value = "目标终端ID", required = true, example = "1")
    private BigInteger screenId;
    @ApiModelProperty(value = "计划名称", required = true, example = "test")
    private String planName;
    @ApiModelProperty(value = "计划频率", required = true, example = "*/5 * * * * ?")
    private String cronExpr;
    @ApiModelProperty(value = "结束日期", required = false, example = "2020-12-12")
    private String endDate;
    @ApiModelProperty(value = "节目ID", required = true, example = "626001")
    private BigInteger contentId;

    public String getPlanName() {
      return planName;
    }

    public void setPlanName(String planName) {
      this.planName = planName;
    }

    public String getCronExpr() {
      return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
      this.cronExpr = cronExpr;
    }

    public String getEndDate() {
      return endDate;
    }

    public void setEndDate(String endDate) {
      this.endDate = endDate;
    }

    public BigInteger getScreenId() {
      return screenId;
    }

    public void setScreenId(BigInteger screenId) {
      this.screenId = screenId;
    }

    public BigInteger getContentId() {
      return contentId;
    }

    public void setContentId(BigInteger contentId) {
      this.contentId = contentId;
    }
  }

  @ApiModel(value = "更新计划参数", description = "JSON格式封装发送")
  public static class PlanJSON4Update {
    @ApiModelProperty(value = "计划ID", required = true, example = "1")
    private BigInteger planId;
    @ApiModelProperty(value = "cron表达式", required = false, example = "*/5 * * * * ？")
    private String cronExpr;
    @ApiModelProperty(value = "是否启用", required = false, example = "true")
    private Boolean enable;

    public BigInteger getPlanId() {
      return planId;
    }

    public void setPlanId(BigInteger planId) {
      this.planId = planId;
    }

    public String getCronExpr() {
      return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
      this.cronExpr = cronExpr;
    }

    public Boolean getEnable() {
      return enable;
    }

    public void setEnable(Boolean enable) {
      this.enable = enable;
    }
  }
}
