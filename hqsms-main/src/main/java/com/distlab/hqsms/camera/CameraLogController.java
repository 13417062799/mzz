package com.distlab.hqsms.camera;

import com.distlab.hqsms.cloud.MediaFile;
import com.distlab.hqsms.cloud.MediaFileRepository;
import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.edge.*;
import com.distlab.hqsms.cloud.MediaFileService;
import com.distlab.hqsms.edge.DeviceService;
import com.distlab.hqsms.strategy.Rule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.File;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.*;


@Api(tags = {"摄像头"})
@RestController
@RequestMapping(path = "/camera-logs")
public class CameraLogController {
  private final Logger logger = LoggerFactory.getLogger(CameraLogController.class);
  @Autowired
  private DeviceService deviceService;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private CameraLogRepository cameraLogRepository;
  @Autowired
  private MediaFileService mediaFileService;
  @Autowired
  private MediaFileRepository mediaFileRepository;
  @Value("${hqsms.edge.camera.record.bypass}")
  Boolean edgeCameraRecordBypass;
  @Value("${hqsms.cloud.server.host}")
  String cloudServerHost;
  @Value("${hqsms.cloud.server.port}")
  String cloudServerPort;

  @ApiOperation(value = "获取录像列表")
  @GetMapping(value = {""})
  public WebResponse<Page<CameraLog>> getAllCameraLogs(
    @Valid @RequestBody DeviceAndLogFilter filter,
    Pageable pageable
  ) {
    Specification<CameraLog> specification = (root, query, builder) -> null;
    specification = deviceService.searchLog(specification, filter);
    try {
      Page<CameraLog> cameraLogs = cameraLogRepository.findAll(specification, pageable);
      List<MediaFile.MediaFileSource> sources = new ArrayList<>();
      sources.add(MediaFile.MediaFileSource.CAMERA_RAW_VIDEO);
      for (CameraLog cameraLog : cameraLogs) {
        cameraLog.setFiles(mediaFileService.getRemoteFiles(cameraLog.getId(), sources));
      }
      return WebResponse.success(cameraLogs);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "获取录像")
  @GetMapping(value = {"/{id}"})
  public WebResponse<Optional<CameraLog>> getCameraLog(
    @PathVariable BigInteger id
  ) {
    try {
      Optional<CameraLog> cameraLog = cameraLogRepository.findById(id);
      if (!cameraLog.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED);
      }
      List<MediaFile.MediaFileSource> sources = new ArrayList<>();
      sources.add(MediaFile.MediaFileSource.CAMERA_RAW_VIDEO);
      cameraLog.get().setFiles(mediaFileService.getRemoteFiles(cameraLog.get().getId(), sources));
      return WebResponse.success(cameraLog);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "获取录像文件回放路径")
  @PostMapping(value = {"/{id}/playback"})
  public WebResponse<String> playBack(
    @PathVariable BigInteger id
  ) {
    try {
      Optional<CameraLog> oLog = cameraLogRepository.findById(id);
      if (!oLog.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.CAMERA_LOG, id));
      }
      CameraLog log = oLog.get();
      String address = deviceService.getServerAddress(log.getDeviceId(), Rule.RuleDeviceType.CAMERA);
      if (StringUtils.isEmpty(address)) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
      }
      List<MediaFile.MediaFileSource> sources = new ArrayList<>();
      sources.add(MediaFile.MediaFileSource.CAMERA_RAW_VIDEO);
      List<MediaFile> mediaFiles = mediaFileRepository.findBySourceIdAndSourceIn(log.getId(), sources);
      if (mediaFiles.isEmpty()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.MEDIA_FILE, id));
      }
      MediaFile mediaFile = mediaFiles.get(0);
      // 如果请求是从局域网发出，旁路云服务器，直接返回边缘服务器录像地址
      if (edgeCameraRecordBypass) {
        DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(log.getDeviceId(), Rule.RuleDeviceType.CAMERA);
        if (hierarchy == null) {
          return WebResponse.fail(WebResponseEnum.SYS_REMOTE_OPERATE_FAILED);
        }
        String localName = MediaFileService.getLocalName(hierarchy, MediaFile.MediaFileSource.CAMERA_RAW_VIDEO, log.getStartedAt(), MediaFileService.VIDEO_MP4);
        if (StringUtils.isEmpty(localName)) {
          return WebResponse.fail(WebResponseEnum.SYS_REMOTE_OPERATE_FAILED);
        }
        return WebResponse.success(String.format("%s/api/tmp/%s", address, localName));
      }
      // 如果请求不是从局域网发出，录像文件先缓存到云服务器，再返回云服务器录像地址
      String url = String.format("http://%s/api/camera-logs/edge/%d/playback", address, id);
      byte[] bs = restTemplate.getForObject(url, byte[].class);
      if (bs == null || bs.length == 0) {
        return WebResponse.fail(WebResponseEnum.SYS_REMOTE_OPERATE_FAILED);
      }

      String remoteName = Paths.get(MediaFileService.fileRoot, MediaFile.MediaFileSource.CAMERA_RAW_VIDEO.getCode(), mediaFile.getName()).toString();
      try {
        FileUtils.writeByteArrayToFile(new File(remoteName), bs);
      } catch (Exception ex) {
        return WebResponse.fail(WebResponseEnum.SYS_REMOTE_OPERATE_FAILED);
      }

      return WebResponse.success(String.format("%s:%s/api/file/%s/%s", cloudServerHost, cloudServerPort, MediaFile.MediaFileSource.CAMERA_RAW_VIDEO.getCode(), mediaFile.getName()));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "获取录像文件回放路径",  hidden = true)
  @GetMapping(value = {"/edge/{id}/playback"})
  public byte[] edgePlayback(@PathVariable BigInteger id) {
    byte[] ret = new byte[0];
    Optional<CameraLog> oLog = cameraLogRepository.findById(id);
    if (!oLog.isPresent()) {
      return ret;
    }
    CameraLog log = oLog.get();
    DeviceHierarchy<Server, Pole, Device> hierarchy = deviceService.getHierarchy(log.getDeviceId(), Rule.RuleDeviceType.CAMERA);
    if (hierarchy == null) {
      return ret;
    }
    String localName = MediaFileService.getLocalName(hierarchy, MediaFile.MediaFileSource.CAMERA_RAW_VIDEO, log.getStartedAt(), MediaFileService.VIDEO_MP4);
    if (StringUtils.isEmpty(localName)) {
      return ret;
    }
    try {
      ret = FileUtils.readFileToByteArray(new File(localName));
    } catch (Exception ex) {
      return ret;
    }
    return ret;
  }

}
