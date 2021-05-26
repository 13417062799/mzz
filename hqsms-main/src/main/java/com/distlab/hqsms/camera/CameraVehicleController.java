package com.distlab.hqsms.camera;

import com.distlab.hqsms.cloud.MediaFile;
import com.distlab.hqsms.cloud.MediaFileRepository;
import com.distlab.hqsms.cloud.MediaFileService;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.net.RestTemplateService;
import com.distlab.hqsms.edge.DeviceAndLogFilter;
import com.distlab.hqsms.edge.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(tags = {"摄像头"})
@RestController
@RequestMapping(path = "/camera-vehicles")
public class CameraVehicleController {
  private final Logger logger = LoggerFactory.getLogger(CameraVehicleController.class);
  @Autowired
  private CameraVehicleRepository cameraVehicleRepository;
  @Autowired
  private MediaFileService mediaFileService;
  @Autowired
  private DeviceService deviceService;

  @ApiOperation(value = "获取车辆数据列表")
  @GetMapping(value = {""})
  public WebResponse<Page<CameraVehicle>> getAllCameraVehicles(
    @Valid @RequestBody DeviceAndLogFilter filter,
    Pageable pageable
  ) {
    Specification<CameraVehicle> specification = (root, query, builder) -> builder.equal(root.get("plateChars"), query);
    specification = deviceService.searchLog(specification, filter);
    try {
      Page<CameraVehicle> cameraVehicles = cameraVehicleRepository.findAll(specification, pageable);
      List<MediaFile.MediaFileSource> sources = new ArrayList<>();
      sources.add(MediaFile.MediaFileSource.CAMERA_VEHICLE_CLIP);
      sources.add(MediaFile.MediaFileSource.CAMERA_VEHICLE_PANORAMA);
      sources.add(MediaFile.MediaFileSource.CAMERA_VEHICLE_SNAPSHOT);
      for (CameraVehicle cameraVehicle : cameraVehicles) {
        cameraVehicle.setFiles(mediaFileService.getRemoteFiles(cameraVehicle.getId(), sources));
      }
      return WebResponse.success(cameraVehicleRepository.findAll(pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "获取车辆数据")
  @GetMapping(value = {"/{id}"})
  public WebResponse<Optional<CameraVehicle>> getCameraVehicle(
    @PathVariable BigInteger id
  ) {
    try {
      Optional<CameraVehicle> cameraVehicle = cameraVehicleRepository.findById(id);
      if (!cameraVehicle.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED);
      }
      List<MediaFile.MediaFileSource> sources = new ArrayList<>();
      sources.add(MediaFile.MediaFileSource.CAMERA_VEHICLE_CLIP);
      sources.add(MediaFile.MediaFileSource.CAMERA_VEHICLE_PANORAMA);
      sources.add(MediaFile.MediaFileSource.CAMERA_VEHICLE_SNAPSHOT);
      cameraVehicle.get().setFiles(mediaFileService.getRemoteFiles(cameraVehicle.get().getId(), sources));
      return WebResponse.success(cameraVehicle);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }
}
