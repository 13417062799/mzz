package com.distlab.hqsms.broadcast.content;

import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.net.RestTemplateService;
import org.apache.commons.lang3.StringUtils;
import com.distlab.hqsms.strategy.Rule;
import com.distlab.hqsms.edge.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = "广播")
@Validated
@RestController
@RequestMapping(path = "/broadcast-contents")
public class BroadcastContentController {
  private final Logger logger = LoggerFactory.getLogger(BroadcastContentController.class);
  private static final String NOT_NULL = "must not be null!";
  private final String  CONTROL_COMMAND = "^stop$|^play$|^pause$";

  @Autowired
  BroadcastContentService broadcastContentService;
  @Autowired
  BroadcastContentRepository broadcastContentRepository;
  @Autowired
  DeviceService deviceService;
  @Autowired
  RestTemplateService restTemplateService;

  @Value("${hqsms.edge.broadcast.enable}")
  private Boolean edgeBroadcastEnable;

  //CREATE

  @ApiIgnore
  @PostMapping(path = "/edge", headers = "content-type=multipart/form-data")
  public WebResponse<BroadcastContent> uploadEdgeContent(
    @NotNull(message = NOT_NULL) @RequestParam(name = "file", required = false) MultipartFile file,
    @NotNull(message = NOT_NULL) @RequestParam(name = "broadcastId", required = false) BigInteger broadcastId
  ) {
    if (file.isEmpty()) {
      logger.error("file is empty");
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    if (!edgeBroadcastEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.BROADCAST));
    }
    BroadcastContent bcc = broadcastContentService.uploadContent(file, broadcastId);
    if (bcc == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success(bcc);
  }

  @PostMapping(path = "", headers = "content-type=multipart/form-data")
  @ApiOperation(value = "上传内容接口")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "file", value = "内容文件", required = true, dataTypeClass = MultipartFile.class, paramType = "query"),
    @ApiImplicitParam(name = "broadcastId", value = "设备ID", required = true, dataTypeClass = BigInteger.class, paramType = "query")
  })
  public WebResponse<BroadcastContent> uploadContent(
    @NotNull(message = NOT_NULL) @RequestParam(name = "file", required = false) MultipartFile file,
    @NotNull(message = NOT_NULL) @RequestParam(name = "broadcastId", required = false) BigInteger broadcastId
  ) {
    //判参
    if (file.isEmpty()) {
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, MessageUtil.isEmpty(MessageEnum.FILE, null));
    }
    //获取IP
    String address = deviceService.getServerAddress(broadcastId, Rule.RuleDeviceType.BROADCAST);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, broadcastId));
    }
    File bccFile = broadcastContentService.convertBCCFile(file);
    if (bccFile == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", new FileSystemResource(bccFile));
    body.add("broadcastId", broadcastId);
    HttpEntity<MultiValueMap<String, Object>> requestBody = new HttpEntity<>(body, headers);
    WebResponse<BroadcastContent> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/broadcast-contents/edge", address),
      HttpMethod.POST,
      new HttpEntity<>(requestBody),
      new ParameterizedTypeReference<WebResponse<BroadcastContent>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    BroadcastContent rBcc = remoteRet.getData();
    try {
      //保存数据
      broadcastContentRepository.save(rBcc);
      return WebResponse.success(rBcc);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST_CONTENT, rBcc.getId()));
    }
  }


  //READ

  @GetMapping(path = "")
  @ApiOperation(value = "获取内容信息列表")
  public WebResponse<Page<BroadcastContent>> getContents(Pageable pageable) {
    try {
      return WebResponse.success(broadcastContentRepository.findAll(pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "根据内容ID获取内容信息")
  @ApiImplicitParam(name = "id", value = "内容ID", example = "686001", required = true, dataTypeClass = BigInteger.class, paramType = "path")
  public WebResponse<Optional<BroadcastContent>> getContent(
    @NotNull(message = NOT_NULL) @PathVariable(name = "id") BigInteger contentId
  ) {
    try {
      return WebResponse.success(broadcastContentRepository.findById(contentId));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
  }

  @GetMapping(path = "/search/findByName")
  @ApiOperation(value = "根据标题获取内容信息列表")
  @ApiImplicitParam(name = "name", value = "内容名称", example = "test001", required = true, dataTypeClass = String.class, paramType = "query")
  public WebResponse<Page<BroadcastContent>> getContentsByNameLike(
    @NotBlank(message = NOT_NULL) String contentName, Pageable pageable
  ) {
    try {
      return WebResponse.success(broadcastContentRepository.findByContentNameLike("%" + contentName + "%", pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
  }

  @GetMapping(path = "/search/findByPlanId")
  @ApiOperation(value = "根据计划ID获取内容信息")
  @ApiImplicitParam(name = "planId", value = "计划ID", example = "686001", required = true, dataTypeClass = BigInteger.class, paramType = "query")
  public WebResponse<Optional<BroadcastContent>> getContentByPlanId(
    @NotNull(message = NOT_NULL) BigInteger planId
  ) {
    try {
      return WebResponse.success(broadcastContentService.getContentByPlanId(planId));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
  }

  @GetMapping(path = "/search/findByPlanName")
  @ApiOperation(value = "根据计划名称获取内容信息")
  @ApiImplicitParam(name = "planName", value = "计划名称", example = "test", required = true, dataTypeClass = String.class, paramType = "query")
  public WebResponse<Page<BroadcastContent>> getContentByPlanName(
    @NotBlank(message = NOT_NULL) String planName, Pageable pageable
  ) {
    Collection<BigInteger> contentIds = broadcastContentService.findCIdsByPlanName("%" + planName + "%", pageable);
    try {
      return WebResponse.success(broadcastContentRepository.findByIdIn(contentIds, pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
  }


  //UPDATE

  @ApiIgnore
  @PostMapping(path = "/edge/{id}/status")
  public WebResponse<BroadcastContent> setEdgeStatus(
    @NotNull(message = NOT_NULL) @PathVariable(name = "id") BigInteger contentId,
    @NotBlank(message = NOT_NULL) @Pattern(regexp = CONTROL_COMMAND, message = "must be 'stop', 'play' or 'pause'")
    @RequestParam(name = "command", required = false) String command
  ) {
    if (!edgeBroadcastEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.BROADCAST));
    }
    BroadcastContent bcc = broadcastContentService.setContentStatus(contentId, command);
    if (bcc == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success(bcc);
  }

  @ResponseBody
  @PostMapping(path = "/{id}/status")
  @ApiOperation(value = "控制内容接口")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "内容ID", example = "686001",required = true, dataTypeClass = BigInteger.class, paramType = "path"),
    @ApiImplicitParam(name = "command", value = "控制命令", example = "stop/play/pause", required = true, dataTypeClass = String.class, paramType = "query")
  })
  public WebResponse<BroadcastContent> setStatus(
    @NotNull(message = NOT_NULL) @PathVariable(name = "id") BigInteger contentId,
    @NotBlank(message = NOT_NULL) @Pattern(regexp = CONTROL_COMMAND, message = "must be 'stop', 'play' or 'pause'")
    @RequestParam(name = "command", required = false) String command
  ) {
    BigInteger broadcastId = broadcastContentService.findBroadcastId(contentId);
    if (broadcastId == null) {
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, "broadcast Id not found");
    }
    String address = deviceService.getServerAddress(broadcastId, Rule.RuleDeviceType.BROADCAST);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, broadcastId));
    }

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("command", command);
    WebResponse<BroadcastContent> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/broadcast-contents/edge/%d/status", address, contentId),
      HttpMethod.POST,
      new HttpEntity<>(body),
      new ParameterizedTypeReference<WebResponse<BroadcastContent>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    BroadcastContent rBcc = remoteRet.getData();
    try {
      //更新数据
      BroadcastContent ret = broadcastContentRepository.save(rBcc);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST_CONTENT, rBcc.getId()));
    }
  }


  //DELETE

  @ApiIgnore
  @DeleteMapping(path = "/edge/{id}")
  public WebResponse<String> deleteEdgeContent(
    @NotNull(message = NOT_NULL) @PathVariable(name = "id") BigInteger contentId
  ) {
    if (!edgeBroadcastEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.BROADCAST));
    }
    int flag = broadcastContentService.deleteContent(contentId);
    if (flag == -1) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success();
  }

  @ResponseBody
  @DeleteMapping(path = "/{id}")
  @ApiOperation(value = "删除内容接口")
  @ApiImplicitParam(name = "id", value = "内容ID", example = "686001", required = true, dataTypeClass = BigInteger.class, paramType = "path")
  public WebResponse<Map<String, BigInteger>> deleteContent(
    @NotNull(message = NOT_NULL) @PathVariable(name = "id") BigInteger contentId
  ) {
    BigInteger broadcastId = broadcastContentService.findBroadcastId(contentId);
    if (broadcastId == null) {
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, "broadcast Id not found");
    }
    String address = deviceService.getServerAddress(broadcastId, Rule.RuleDeviceType.BROADCAST);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, broadcastId));
    }

    WebResponse<Map<String, BigInteger>> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/broadcast-contents/edge/%d", address, contentId),
      HttpMethod.DELETE,
      null,
      new ParameterizedTypeReference<WebResponse<Map<String, BigInteger>>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    BroadcastContent rBcc = broadcastContentService.findBroadcastContent(contentId);
    if (rBcc == null) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.BROADCAST_CONTENT, null));
    }
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", contentId);
    try {
      //保存数据
      broadcastContentRepository.delete(rBcc);
      return WebResponse.success(map);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST_CONTENT, contentId));
    }
  }

}
