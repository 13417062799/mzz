package com.distlab.hqsms.broadcast.plan;

import com.alibaba.fastjson.JSONObject;
import com.distlab.hqsms.broadcast.content.BroadcastContentService;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.net.RestTemplateService;
import com.distlab.hqsms.strategy.Rule;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.edge.DeviceService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = "广播")
@RestController
@Validated
@RequestMapping("/broadcast-plans")
public class BroadcastPlanController {
  private static final Logger logger = LoggerFactory.getLogger(BroadcastPlanController.class);
  private final String NOT_NULL = "must not be null!";
  private final String INCORRECT = "may be incorrect";
  private final String CHECK_DATABASE = "please check database";
  private final String PLAN_NULL = "plan data not found";
  private final String CONTROL_COMMAND = "^start$|^stop$|^enable$|^frozen$";

  @Autowired
  BroadcastPlanService broadcastPlanService;
  @Autowired
  BroadcastPlanRepository broadcastPlanRepository;
  @Autowired
  BroadcastContentService broadcastContentService;
  @Autowired
  DeviceService deviceService;
  @Autowired
  RestTemplateService restTemplateService;

  @Value("${hqsms.edge.broadcast.enable}")
  private Boolean edgeBroadcastEnable;

  //CREATE

  @ApiIgnore
  @PostMapping("/edge")
  public WebResponse<BroadcastPlan> createEdgePlan(
    @Valid @RequestBody BroadcastPlanInfo bcpInfo
  ) {
    if (!edgeBroadcastEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.BROADCAST));
    }
    BroadcastPlan plan = broadcastPlanService.createPlan(bcpInfo);
    if (plan == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success(plan);
  }

  @PostMapping("")
  @ApiOperation(value = "创建计划接口")
  public WebResponse<BroadcastPlan> createPlan(
    @Valid @RequestBody BroadcastPlanInfo bcpInfo
  ) {
    BigInteger contentId = bcpInfo.getContentId();
    BigInteger broadcastId = broadcastContentService.findBroadcastId(contentId);
    if (broadcastId == null) {
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, "broadcast Id not found");
    }
    String address = deviceService.getServerAddress(broadcastId, Rule.RuleDeviceType.BROADCAST);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, broadcastId));
    }

    WebResponse<BroadcastPlan> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/broadcast-plans/edge", address),
      HttpMethod.POST,
      new HttpEntity<>(bcpInfo),
      new ParameterizedTypeReference<WebResponse<BroadcastPlan>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    BroadcastPlan rPlan = remoteRet.getData();
    try {
      BroadcastPlan ret = broadcastPlanRepository.save(rPlan);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST_PLAN, rPlan.getId()));
    }
  }


  //READ

  @GetMapping(path = "")
  @ApiOperation(value = "获取计划列表")
  public @ResponseBody WebResponse<Page<BroadcastPlan>> getPlans(Pageable pageable) {
    try {
      return WebResponse.success(broadcastPlanRepository.findAll(pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "根据计划ID获取计划信息")
  @ApiImplicitParam(name = "id", value = "计划ID", required = true, dataTypeClass = BigInteger.class, paramType = "path", example = "674001")
  public @ResponseBody WebResponse<Optional<BroadcastPlan>> getPlan(
    @NotNull(message = NOT_NULL) @PathVariable(name = "id") BigInteger planId
  ) {
    try {
      return WebResponse.success(broadcastPlanRepository.findById(planId));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @GetMapping(path = "/search/findByName")
  @ApiOperation(value = "根据计划名称获取计划信息列表")
  @ApiImplicitParam(name = "name", value = "计划名称", required = true, dataTypeClass = String.class, paramType = "query")
  public @ResponseBody WebResponse<Page<BroadcastPlan>> getPlansByNameLike(
    @NotBlank(message = NOT_NULL) @RequestParam(name = "name", required = false) String planName, Pageable pageable
  ) {
    try {
      return WebResponse.success(broadcastPlanRepository.findByPlanNameLike("%" + planName + "%", pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }


  //UPDATE

  @ApiIgnore
  @PostMapping("/edge/{id}")
  public WebResponse<BroadcastPlan> updateEdgePlan(
    @NotNull(message = NOT_NULL) @PathVariable(name = "id") BigInteger planId,
    @Valid @RequestBody BroadcastPlanInfo bcpInfo
  ) {
    if (!edgeBroadcastEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.BROADCAST));
    }
    BroadcastPlan plan = broadcastPlanService.updatePlan(bcpInfo, planId);
    if (plan == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success(plan);
  }

  @PostMapping("/{id}")
  @ApiOperation(value = "更新计划接口")
  @ApiImplicitParam(name = "id", value = "计划ID", required = true, dataTypeClass = BigInteger.class, paramType = "path", example = "674001")
  public WebResponse<BroadcastPlan> updatePlan(
    @NotNull(message = NOT_NULL) @PathVariable(name = "id") BigInteger planId,
    @Valid @RequestBody BroadcastPlanInfo bcpInfo
  ) {
    BigInteger contentId = bcpInfo.getContentId();
    BigInteger broadcastId = broadcastContentService.findBroadcastId(contentId);
    if (broadcastId == null) {
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, MessageUtil.notFound(MessageEnum.BROADCAST_ID, null));
    }
    String address = deviceService.getServerAddress(broadcastId, Rule.RuleDeviceType.BROADCAST);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, broadcastId));
    }

    WebResponse<BroadcastPlan> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/broadcast-plans/%d", address, planId),
      HttpMethod.POST,
      new HttpEntity<>(bcpInfo),
      new ParameterizedTypeReference<WebResponse<BroadcastPlan>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    BroadcastPlan rPlan = remoteRet.getData();
    try {
      BroadcastPlan ret = broadcastPlanRepository.save(rPlan);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST_PLAN, rPlan.getId()));
    }
  }

  @ApiIgnore
  @PostMapping("/{id}/status/edge")
  public WebResponse<BroadcastPlan> setEdgeStatus(
    @NotNull(message = NOT_NULL) @PathVariable(name = "id") BigInteger planId,
    @NotBlank(message = NOT_NULL) @Pattern(regexp = CONTROL_COMMAND, message = "must be 'start', 'stop', 'enable' or 'frozen'")
    @RequestParam(name = "command", required = false) String command
  ) {
    if (!edgeBroadcastEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.BROADCAST));
    }
    BroadcastPlan plan = broadcastPlanService.planExecutor(planId, command);
    if (plan == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success(plan);
  }

  @PostMapping("/{id}/status")
  @ApiOperation(value = "控制计划接口")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "计划ID", required = true, dataTypeClass = BigInteger.class, paramType = "path", example = "674001"),
    @ApiImplicitParam(name = "command", value = "控制命令（运行：stop/start；冻结：frozen/enable）", required = true, dataTypeClass = String.class, paramType = "query")
  })
  public WebResponse<BroadcastPlan> setStatus(
    @NotNull(message = NOT_NULL) @PathVariable(name = "id") BigInteger planId,
    @NotBlank(message = NOT_NULL) @Pattern(regexp = CONTROL_COMMAND, message = "must be 'start', 'stop', 'enable' or 'frozen'")
    @RequestParam(name = "command", required = false) String command
  ) {
    BigInteger contentId = broadcastPlanService.findContentId(planId);
    if (contentId == null) {
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, "broadcast content Id not found");
    }
    BigInteger broadcastId = broadcastContentService.findBroadcastId(contentId);
    if (broadcastId == null) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.BROADCAST_ID, null));
    }
    String address = deviceService.getServerAddress(broadcastId, Rule.RuleDeviceType.BROADCAST);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, broadcastId));
    }

    MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
    requestBody.add("command", command);
    WebResponse<BroadcastPlan> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/broadcast-plans/%d/status/edge", address, planId),
      HttpMethod.POST,
      new HttpEntity<>(requestBody),
      new ParameterizedTypeReference<WebResponse<BroadcastPlan>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    BroadcastPlan rPlan = remoteRet.getData();
    try {
      BroadcastPlan ret = broadcastPlanRepository.save(rPlan);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST_PLAN, rPlan.getId()));
    }
  }


  //DELETE

  @ApiIgnore
  @DeleteMapping("/edge/{id}")
  public WebResponse<Map<String, BigInteger>> deleteEdgePlan(
    @NotNull(message = NOT_NULL) @PathVariable(name = "id") BigInteger planId
  ) {
    if (!edgeBroadcastEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.BROADCAST));
    }
    BroadcastPlan plan = broadcastPlanService.planExecutor(planId, "delete");
    if (plan == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", plan.getId());
    return WebResponse.success(map);
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "删除计划接口")
  @ApiImplicitParam(name = "id", value = "计划ID", required = true, dataTypeClass = BigInteger.class, paramType = "path", example = "674001")
  public WebResponse<Map<String, BigInteger>> deletePlan(
    @NotNull(message = NOT_NULL) @PathVariable(name = "id") BigInteger planId
  ) {
    BigInteger contentId = broadcastPlanService.findContentId(planId);
    if (contentId == null) {
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, "broadcast content Id not found");
    }
    BigInteger broadcastId = broadcastContentService.findBroadcastId(contentId);
    if (broadcastId == null) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.BROADCAST_ID, null));
    }
    String address = deviceService.getServerAddress(broadcastId, Rule.RuleDeviceType.BROADCAST);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, broadcastId));
    }

    WebResponse<Map<String, BigInteger>> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/broadcast-plans/%d", address, planId),
      HttpMethod.DELETE,
      null,
      new ParameterizedTypeReference<WebResponse<Map<String, BigInteger>>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    Map<String, BigInteger> data = remoteRet.getData();
    BigInteger id = data.get("Id");
    Optional<BroadcastPlan> tPlan = broadcastPlanRepository.findById(id);
    if (!tPlan.isPresent()) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.BROADCAST_PLAN, id));
    }
    try {
      //删除数据
      broadcastPlanRepository.delete(tPlan.get());
      return remoteRet;
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST_PLAN, id));
    }
  }

}
