package com.distlab.hqsms.cloud;

import com.distlab.hqsms.camera.FaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Api(tags = {"媒体文件"})
@RestController
@RequestMapping(path = "/media-files")
public class MediaFileController {
  private final Logger logger = LoggerFactory.getLogger(MediaFileController.class);
  @Autowired
  private MediaFileService mediaFileService;
  @Autowired
  private MediaFileRepository mediaFileRepository;

  @ApiOperation(value = "上报媒体文件[EDGE->CLOUD]", hidden = true)
  @PostMapping(value = {"/report"})
  public ResponseEntity<String> report(
    @RequestParam("file") MultipartFile file,
    @RequestParam("source") MediaFile.MediaFileSource source,
    @RequestParam("sourceId") BigInteger sourceId
  ) {
    if (mediaFileService.writeRemoteFile(file, source) != 0) {
      return ResponseEntity.ok("failed: operation failed");
    }
    String remoteFilename = MediaFileService.getRemoteFileName(file);
    MediaFile mediaFile = new MediaFile();
    mediaFile.setSource(source);
    mediaFile.setSourceId(sourceId);
    mediaFile.setName(remoteFilename);
    mediaFile.setCreatedAt(new Date());
    mediaFileRepository.save(mediaFile);

    return ResponseEntity.ok("success");
  }

  @ApiOperation(value = "上报录像数据，录像文件默认只保存在边缘服务器，因此与上报媒体文件处理不同[EDGE->CLOUD]", hidden = true)
  @PostMapping(value = {"/record"})
  public ResponseEntity<String> recordData(
    @RequestParam("name") String name,
    @RequestParam("source") MediaFile.MediaFileSource source,
    @RequestParam("sourceId") BigInteger sourceId
  ) {
    MediaFile mediaFile = new MediaFile();
    mediaFile.setSource(source);
    mediaFile.setSourceId(sourceId);
    mediaFile.setName(name);
    mediaFile.setCreatedAt(new Date());
    mediaFileRepository.save(mediaFile);

    return ResponseEntity.ok("success");
  }

  @ApiOperation(value = "上传媒体文件")
  @PostMapping(value = {"/upload"})
  public ResponseEntity<String> report(
    @RequestParam("file") MultipartFile file,
    @RequestParam("source") MediaFile.MediaFileSource source
  ) {
    if (mediaFileService.writeRemoteFile(file, source) != 0) {
      return ResponseEntity.ok("failed: operation failed");
    }

    return ResponseEntity.ok("success");
  }

  @ApiOperation(value = "获取所有人脸特征[CLOUD->CLOUD]", hidden = true)
  @GetMapping(value = {"/human-face-codes"})
  public List<FaceService.FaceCode> humanFaces() {
    List<MediaFile> mediaFiles = mediaFileRepository.findBySource(MediaFile.MediaFileSource.CAMERA_HUMAN_FACE);
    List<FaceService.FaceCode> codes = new ArrayList<>();
    try {
      for (MediaFile mediaFile : mediaFiles) {
        Path path = Paths.get(MediaFileService.fileRoot, MediaFile.MediaFileSource.CAMERA_HUMAN_FACE.getCode(), mediaFile.getName());
        String line = Files.readAllLines(path).get(0);
        ObjectMapper mapper = new ObjectMapper();
        List<Float> lineCode = mapper.readValue(line, List.class);
        FaceService.FaceCode code = new FaceService.FaceCode();
        code.setCode(lineCode);
        code.setSourceId(mediaFile.getSourceId());
        codes.add(code);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return codes;
    }

    return codes;
  }
}
