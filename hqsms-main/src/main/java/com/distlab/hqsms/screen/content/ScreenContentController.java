package com.distlab.hqsms.screen.content;

import com.distlab.hqsms.broadcast.content.BroadcastContentService;
import com.distlab.hqsms.cloud.MediaFileService;
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

import javax.validation.constraints.NotNull;
import java.io.File;
import java.math.BigInteger;
import java.util.*;

@Validated
@RestController
@RequestMapping(path = "/screen-contents")
@Api(tags = "屏幕")
public class ScreenContentController {

  private static final Logger logger = LoggerFactory.getLogger(ScreenContentController.class);

  @Autowired
  ScreenContentService screenContentService;
  @Autowired
  ScreenContentRepository screenContentRepository;
  @Autowired
  DeviceService deviceService;
  @Autowired
  BroadcastContentService broadcastContentService;
  @Autowired
  MediaFileService mediaFileService;
  @Autowired
  RestTemplateService restTemplateService;

  @Value("${hqsms.edge.screen.enable}")
  private Boolean edgeScreenEnable;


  //CREATE

  @ApiIgnore
  @PostMapping(path = "/edge")
  public WebResponse<ScreenContent> uploadEdge(
    @NotNull(message = "must not be empty") @RequestParam(name = "file", required = false) MultipartFile file,
    @NotNull(message = "must not be null") @RequestParam(name = "screenId", required = false) BigInteger screenId
  ) {
    if (file.isEmpty()) {
      logger.error("file is empty");
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    if (!edgeScreenEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.BROADCAST));
    }
    ScreenContent screenContent = screenContentService.uploadContent(screenId, file);
    if (screenContent == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success(screenContent);
  }

  @PostMapping(path = "")
  @ApiOperation(value = "投放内容")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "file", dataTypeClass = MultipartFile.class, value = "内容文件", required = true),
    @ApiImplicitParam(name = "screenId", dataTypeClass = BigInteger.class, value = "设备ID", required = true)
  })
  public WebResponse<ScreenContent> upload(
    @NotNull(message = "must not be empty") @RequestParam(name = "file", required = false) MultipartFile file,
    @NotNull(message = "must not be null") @RequestParam(name = "screenId", required = false) BigInteger screenId
  ) {
    if (file.isEmpty()) {
      logger.error("file is empty");
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, MessageUtil.isEmpty(MessageEnum.FILE, null));
    }
    String address = deviceService.getServerAddress(screenId, Rule.RuleDeviceType.SCREEN);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, screenId));
    }
    String screenDir = System.getProperty("user.dir") + File.separator + "tmp" + File.separator + "screen";
    File content = MediaFileService.fileSaver(screenDir, file);
    if (content == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, "convert file error");
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", new FileSystemResource(content));
    body.add("screenId", screenId);
    HttpEntity<MultiValueMap<String, Object>> requestBody = new HttpEntity<>(body, headers);
    WebResponse<ScreenContent> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/screen-contents/edge", address),
      HttpMethod.POST,
      new HttpEntity<>(requestBody),
      new ParameterizedTypeReference<WebResponse<ScreenContent>>() {
      }
    );
    boolean flag = content.delete();
    if (!flag) content.delete();
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    ScreenContent rScreenContent = remoteRet.getData();
    try {
      ScreenContent ret = screenContentRepository.save(rScreenContent);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.SCREEN_CONTENT, rScreenContent.getId()));
    }
  }


  //READ

  @GetMapping(path = "")
  @ApiOperation(value = "获取屏幕内容列表")
  public WebResponse<Page<ScreenContent>> getContents(
    Pageable pageable
  ) {
    try {
      return WebResponse.success(screenContentRepository.findAll(pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @GetMapping(path = "/{id}")
  @ApiOperation(value = "获取屏幕内容")
  public WebResponse<Optional<ScreenContent>> getContent(
    @NotNull(message = "must not be null") @PathVariable(name = "id") BigInteger id
  ) {
    try {
      return WebResponse.success(screenContentRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }


  //UPDATE

  @ApiIgnore
  @PostMapping(path = "/edge/{id}/switch")
  public WebResponse<ScreenContent> switchEdgeProgram(
    @NotNull(message = "must not be null") @PathVariable(name = "id") BigInteger contentId
  ) {
    if (!edgeScreenEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.SCREEN));
    }
    ScreenContent screenContent = screenContentService.controlContent(contentId, "switch");
    if (screenContent == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    return WebResponse.success(screenContent);
  }

  @PostMapping(path = "/{id}/switch")
  @ApiOperation(value = "切换内容")
  public WebResponse<ScreenContent> switchProgram(
    @NotNull(message = "must not be null") @PathVariable(name = "id") BigInteger contentId
  ) {
    Optional<ScreenContent> tempScc = screenContentRepository.findById(contentId);
    if (!tempScc.isPresent()) {
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, MessageUtil.notFound(MessageEnum.SCREEN_CONTENT, contentId));
    }
    ScreenContent scc = tempScc.get();
    BigInteger screenId = scc.getScreenId();
    if (screenId == null) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SCREEN_ID, null));
    }
    String address = deviceService.getServerAddress(screenId, Rule.RuleDeviceType.SCREEN);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, screenId));
    }

    WebResponse<ScreenContent> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/screen-contents/edge/%d/switch", address, contentId),
      HttpMethod.POST,
      null,
      new ParameterizedTypeReference<WebResponse<ScreenContent>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    ScreenContent rScreenContent = remoteRet.getData();
    try {
      ScreenContent ret = screenContentRepository.save(rScreenContent);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.SCREEN_CONTENT, rScreenContent.getId()));
    }
  }


  //DELETE

  @ApiIgnore
  @DeleteMapping(path = "/edge/{id}")
  public WebResponse<Map<String, BigInteger>> deleteEdgeProgram(
    @NotNull(message = "must not be null") @PathVariable(name = "id") BigInteger contentId
  ) {
    if (!edgeScreenEnable) {
      return WebResponse.fail(WebResponseEnum.SYS_PROPERTY_ERROR, MessageUtil.deviceUnable(MessageEnum.BROADCAST));
    }
    ScreenContent screenContent = screenContentService.controlContent(contentId, "delete");
    if (screenContent == null) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
    }
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", screenContent.getId());
    return WebResponse.success(map);
  }

  @DeleteMapping(path = "/{id}")
  @ApiOperation(value = "删除内容")
  public WebResponse<Map<String, BigInteger>> deleteProgram(
    @NotNull(message = "must not be null") @PathVariable(name = "id") BigInteger contentId
  ) {
    Optional<ScreenContent> tempScc = screenContentRepository.findById(contentId);
    if (!tempScc.isPresent()) {
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, MessageUtil.notFound(MessageEnum.SCREEN_CONTENT, contentId));
    }
    ScreenContent scc = tempScc.get();
    BigInteger screenId = scc.getScreenId();
    if (screenId == null) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SCREEN_ID, null));
    }
    String address = deviceService.getServerAddress(screenId, Rule.RuleDeviceType.SCREEN);
    if (StringUtils.isEmpty(address)) {
      return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, screenId));
    }

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("screenId", screenId);
    HttpEntity<MultiValueMap<String, Object>> requestBody = new HttpEntity<>(body);
    WebResponse<Map<String, BigInteger>> remoteRet = restTemplateService.exchange(
      String.format("http://%s/api/screen-contents/edge/%d", address, contentId),
      HttpMethod.DELETE,
      new HttpEntity<>(requestBody),
      new ParameterizedTypeReference<WebResponse<Map<String, BigInteger>>>() {
      }
    );
    if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
      return remoteRet;
    }
    Map<String, BigInteger> data = remoteRet.getData();
    BigInteger id = data.get("Id");
    Optional<ScreenContent> tSCC = screenContentRepository.findById(id);
    try {
      screenContentRepository.delete(tSCC.get());
      return remoteRet;
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.BROADCAST_CONTENT, scc.getId()));
    }
  }

}
