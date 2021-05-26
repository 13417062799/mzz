package com.distlab.hqsms.camera;

import com.distlab.hqsms.cloud.MediaFile;
import com.distlab.hqsms.cloud.MediaFileService;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.edge.DeviceAndLogFilter;
import com.distlab.hqsms.edge.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Api(tags = {"摄像头"})
@RestController
@RequestMapping(path = "/camera-humans")
public class CameraHumanController {
  private final Logger logger = LoggerFactory.getLogger(CameraHumanController.class);
  @Autowired
  private CameraHumanRepository cameraHumanRepository;
  @Autowired
  private MediaFileService mediaFileService;
  @Autowired
  private FaceService faceService;
  @Autowired
  private DeviceService deviceService;

  @ApiOperation(value = "获取人员数据列表")
  @GetMapping(value = {""})
  public WebResponse<Page<CameraHuman>> getAllCameraHumans(
    @Valid @RequestBody DeviceAndLogFilter filter,
    Pageable pageable,
    @RequestBody CameraHumanFeature body
  ) {
    Specification<CameraHuman> specification = (root, query, builder) -> {
      String faceCode = body.getFaceCode();
      List<BigInteger> ids = faceService.faceMatch(faceCode);
      return builder.and(root.get("id").in(ids));
    };
    specification = deviceService.searchLog(specification, filter);
    try {
      Page<CameraHuman> cameraHumans = cameraHumanRepository.findAll(specification, pageable);
      List<MediaFile.MediaFileSource> sources = new ArrayList<>();
      sources.add(MediaFile.MediaFileSource.CAMERA_HUMAN_CLIP);
      sources.add(MediaFile.MediaFileSource.CAMERA_HUMAN_PANORAMA);
      sources.add(MediaFile.MediaFileSource.CAMERA_HUMAN_SNAPSHOT);
      for (CameraHuman cameraHuman : cameraHumans) {
        cameraHuman.setFiles(mediaFileService.getRemoteFiles(cameraHuman.getId(), sources));
      }
      return WebResponse.success(cameraHumans);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "获取人员数据")
  @GetMapping(value = {"/{id}"})
  public WebResponse<Optional<CameraHuman>> getCameraHuman(
    @PathVariable BigInteger id
  ) {
    try {
      Optional<CameraHuman> cameraHuman = cameraHumanRepository.findById(id);
      if (!cameraHuman.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED);
      }
      List<MediaFile.MediaFileSource> sources = new ArrayList<>();
      sources.add(MediaFile.MediaFileSource.CAMERA_HUMAN_CLIP);
      sources.add(MediaFile.MediaFileSource.CAMERA_HUMAN_PANORAMA);
      sources.add(MediaFile.MediaFileSource.CAMERA_HUMAN_SNAPSHOT);
      cameraHuman.get().setFiles(mediaFileService.getRemoteFiles(cameraHuman.get().getId(), sources));
      return WebResponse.success(cameraHumanRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "识别上传的图片中所有人脸特征")
  @ResponseBody
  @PostMapping(path = "/feature")
  public WebResponse<Iterable<CameraHumanFace>> getCameraHumanFaces(
    @RequestParam("file") MultipartFile file
  ) {
    List<CameraHumanFace> ret = new ArrayList<>();
    if (mediaFileService.writeRemoteFile(file, MediaFile.MediaFileSource.CAMERA_HUMAN_UPLOAD) != 0) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, "保存文件失败");
    }
    String uploadName = MediaFileService.getRemoteName(file, MediaFile.MediaFileSource.CAMERA_HUMAN_UPLOAD);
    try {
      List<FaceService.FaceCode> codes = faceService.faceEncode(uploadName);
      List<FaceService.FaceLocation> locations = faceService.faceLocate(uploadName);

      for (int i = 0; i < codes.size(); i++) {
        FaceService.FaceCode code = codes.get(i);
        FaceService.FaceLocation location = locations.get(i);
        CameraHumanFace humanFace = new CameraHumanFace();
        humanFace.setCode(code.getCode().toString());
        humanFace.setTop(location.getLocation().get(0).toString());
        humanFace.setRight(location.getLocation().get(1).toString());
        humanFace.setBottom(location.getLocation().get(2).toString());
        humanFace.setLeft(location.getLocation().get(3).toString());
        ret.add(humanFace);
      }
      return WebResponse.success(ret);
    } catch (Exception ex) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, ex.getCause().getLocalizedMessage());
    }
  }
}

@ApiModel("人员特征查询参数")
class CameraHumanFeature {
  @ApiModelProperty(value = "人脸编码")
  private String faceCode;

  public String getFaceCode() {
    return faceCode;
  }

  public void setFaceCode(String faceCode) {
    this.faceCode = faceCode;
  }

}

@ApiModel("人脸特征相关参数")
class CameraHumanFace {
  @ApiModelProperty(value = "人脸编码")
  private String code;
  @ApiModelProperty(value = "人脸所在位置，左上角坐标横坐标X1，范围 0..1")
  private String top;
  @ApiModelProperty(value = "人脸所在位置，左上角坐标纵坐标Y1，范围 0..1")
  private String left;
  @ApiModelProperty(value = "人脸所在位置，右下角坐标横坐标X2，范围 0..1")
  private String right;
  @ApiModelProperty(value = "人脸所在位置，右下角坐标横坐标Y2，范围 0..1")
  private String bottom;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTop() {
    return top;
  }

  public void setTop(String top) {
    this.top = top;
  }

  public String getLeft() {
    return left;
  }

  public void setLeft(String left) {
    this.left = left;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public String getBottom() {
    return bottom;
  }

  public void setBottom(String bottom) {
    this.bottom = bottom;
  }
}
