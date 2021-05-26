package com.distlab.hqsms.cloud;

import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.edge.*;
import com.distlab.hqsms.edge.Server;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MediaFileService {
  public static String VIDEO_MP4 = "mp4";
  public static String IMAGE_JPG = "jpg";
  public static String RAW_TEXT = "txt";
  public static String fileRoot;
  public static String tmpRoot;
  private static final Logger logger = LoggerFactory.getLogger(MediaFileService.class);

  static {
    try {
      // 长期保存的目录，可公共访问
      fileRoot = System.getProperty("user.dir") + File.separator + "file";
      File fileDir = new File(fileRoot);
      if (!fileDir.isDirectory() && !fileDir.mkdirs()) {
        logger.error("create file directory failed: " + fileDir);
      }
      // 随时清理的目录
      tmpRoot = System.getProperty("user.dir") + File.separator + "tmp";
      File tempDir = new File(tmpRoot);
      if (!tempDir.isDirectory() && !tempDir.mkdirs()) {
        logger.error("create temp directory failed: " + tempDir);
      }

      File tempScreenDir = new File(tmpRoot + File.separator + "screen");
      File tempBroadcastDir = new File(tmpRoot + File.separator + "broadcast");
      if (!tempScreenDir.isDirectory() && !tempScreenDir.mkdirs()) {
        logger.error("create screen directory failed: " + tempScreenDir);
      }
      if (!tempBroadcastDir.isDirectory() && !tempBroadcastDir.mkdirs()) {
        logger.error("create screen directory failed: " + tempBroadcastDir);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Autowired
  RestTemplate restTemplate;
  @Autowired
  MediaFileRepository mediaFileRepository;
  @Value("${hqsms.cloud.server.host}")
  String cloudServerHost;
  @Value("${hqsms.cloud.server.port}")
  String cloudServerPort;
  @Value("${hqsms.edge.server.enable}")
  boolean isEdgeServerEnable;
  @Value("${hqsms.cloud.server.enable}")
  boolean isCloudServerEnable;

  public List<String> getRemoteFiles(BigInteger sourceId, List<MediaFile.MediaFileSource> sources) {
    return mediaFileRepository.findBySourceIdAndSourceIn(sourceId, sources)
      .stream()
      .map(mediaFile -> String.format("/file/%s/%s", mediaFile.getSource().getCode(), mediaFile.getName()))
      .collect(Collectors.toList());
  }

  /**
   * 边缘服务器上传文件到云端服务器
   * 大部分情况下，上传文件与MQ消息一起执行，该方法在边缘服务器执行
   * @param localName 本地路径文件名
   * @param source 文件来源
   * @param sourceId 文件来源ID
   * @return 0，操作成功
   */
  public int reportRemoteFile(String localName, MediaFile.MediaFileSource source, BigInteger sourceId) {
    if (!isEdgeServerEnable) {
      return 0;
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    File file = new File(localName);
    if (!file.isFile() || !file.canRead()) {
      logger.error("File not exist: " + localName + " " + file.canRead());
      return -1;
    }
    body.add("file", new FileSystemResource(new File(localName)));
    body.add("source", source.name());
    body.add("sourceId", sourceId);
    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    String url = String.format("http://%s:%s/api/media-files/report", cloudServerHost, cloudServerPort);
    ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
    if (!Objects.equals(response.getBody(), "success")) {
      logger.error("Failed to report file: " + localName);
      return -1;
    }
    return 0;
  }

  /**
   * 边缘服务器上传录像文件到云端服务器
   * 只上传录像文件信息，不上传录像文件，与MQ消息一起执行，该方法在边缘服务器执行
   * @param localName 本地路径文件名
   * @param source 文件来源
   * @param sourceId 文件来源ID
   * @return 0，操作成功
   */
  public int recordRemoteFile(String localName, MediaFile.MediaFileSource source, BigInteger sourceId) {
    if (!isEdgeServerEnable) {
      return 0;
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    String remoteName;
    // 获取文件名
    try {
      File file = new File(localName);
      String extension = FilenameUtils.getExtension(file.getName());
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(FileUtils.readFileToByteArray(file));
      byte[] digest = md.digest();
      String checksum = DatatypeConverter.printHexBinary(digest).toUpperCase();
      remoteName = String.format("%s.%s", checksum, extension);
    } catch (Exception ex) {
      return -1;
    }

    body.add("name", remoteName);
    body.add("source", source.name());
    body.add("sourceId", sourceId);
    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    String url = String.format("http://%s:%s/api/media-files/record", cloudServerHost, cloudServerPort);
    ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
    if (!Objects.equals(response.getBody(), "success")) {
      logger.error("Failed to record file: " + remoteName);
      return -1;
    }
    return 0;
  }

  /**
   * 保存上传的文件到云端，该方法在云端服务器执行
   * @param file 上传的文件
   * @param source 上传的文件来源
   * @return 0，操作成功，-1，未知错误
   */
  public int writeRemoteFile(MultipartFile file, MediaFile.MediaFileSource source) {
    if (!isCloudServerEnable) {
      return 0;
    }
    if (file.isEmpty()) {
      logger.error("Failed to store empty file.");
      return -1;
    }
    String remoteName = getRemoteName(file, source);
    if (StringUtils.isEmpty(remoteName)) {
      logger.error("Failed to get file path.");
      return -1;
    }

    try {
      InputStream inputStream = file.getInputStream();
      Files.copy(inputStream, Paths.get(remoteName), StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception ex) {
      logger.error("Failed to read stored files: ", ex);
      return -1;
    }

    return 0;
  }

  /**
   * 获取本地路径名，用于定期删除临时文件。格式如：
   * tmp/{hierarchy.poleId}/{deviceType}/{hierarchy.deviceId}/{source}/{datetime:yyyy-MM-dd}
   *
   * @param hierarchy 设备架构信息
   * @param source 文件来源
   * @param datetime  文件生成时间
   * @return 本地路径名
   */
  public static String getLocalPath(
    DeviceHierarchy<Server, Pole, Device> hierarchy,
    MediaFile.MediaFileSource source,
    Date datetime
  ) {
    // 获取本地绝对路径
    Path path = Paths.get(
      tmpRoot,
      hierarchy.getPole().getId().toString(),
      hierarchy.getDeviceType().getCode(),
      hierarchy.getDevice().getId().toString(),
      source.getCode(),
      new SimpleDateFormat("yyyy_MM_dd").format(datetime)
    );
    File file = path.toFile();
    if (!file.isDirectory() && !file.mkdirs()) {
      logger.warn("create directory failed: " + file);
      return null;
    }

    return path.toString();
  }

  /**
   * 获取本地文件名。格式如：
   * {datetime:HH_mm_ss:SSS}.{extension}
   *
   * @param datetime 文件生成时间
   * @param extension 文件后缀名
   * @return 本地文件名
   */
  public static String getLocalFileName(
    Date datetime,
    String extension
  ) {
    return String.format("%s.%s", new SimpleDateFormat("HH_mm_ss_SSS").format(datetime), extension);
  }

  /**
   * 获取本地路径文件名，用于临时文件保存。格式如：
   * tmp/{hierarchy.poleId}/{deviceType}/{hierarchy.deviceId}/{source}/{datetime:yyyy_MM_dd}/{datetime:HH_mm_ss:SSS}.{extension}
   *
   * @param hierarchy 设备架构信息
   * @param source 文件来源
   * @param datetime  文件生成时间
   * @param extension 文件后缀名
   * @return 本地路径文件名
   */
  public static String getLocalName(
    DeviceHierarchy<Server, Pole, Device> hierarchy,
    MediaFile.MediaFileSource source,
    Date datetime,
    String extension
  ) {
    String path = getLocalPath(hierarchy, source, datetime);
    if (StringUtils.isEmpty(path)) {
      return null;
    }

    String name = getLocalFileName(datetime, extension);
    return Paths.get(path, name).toString();
  }

  /**
   * 获取云端媒体文件路径。格式如：
   * file/{source}
   *
   * @param source 文件来源
   * @return 本地路径名
   */
  public static String getRemotePath(
    MediaFile.MediaFileSource source
  ) {
    // 获取本地绝对路径
    Path path = Paths.get(
      fileRoot,
      source.getCode()
    );
    File file = path.toFile();
    if (!file.isDirectory() && !file.mkdirs()) {
      logger.warn("create directory failed: " + file);
      return null;
    }

    return path.toString();
  }

  /**
   * 根据时间戳生成全局文件名，用于分布式存储。格式如：
   * {checksum}.{extension}
   *
   * @param file 文件
   * @return 全局文件名
   */
  public static String getRemoteFileName(MultipartFile file) {
    try {
      String extension = FilenameUtils.getExtension(file.getOriginalFilename());
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(file.getBytes());
      byte[] digest = md.digest();
      String checksum = DatatypeConverter.printHexBinary(digest).toUpperCase();
      return String.format("%s.%s", checksum, extension);
    } catch (Exception ex) {
      return null;
    }
  }

  /**
   * 获取本地路径文件名，用于临时文件保存。格式如：
   * file/{source}/{file.hash}.{file.extension}
   *
   * @param file 文件
   * @param source 文件来源
   * @return 本地路径文件名
   */
  public static String getRemoteName(
    MultipartFile file,
    MediaFile.MediaFileSource source
  ) {
    String path = getRemotePath(source);
    if (StringUtils.isEmpty(path)) {
      return null;
    }

    String name = getRemoteFileName(file);
    if (StringUtils.isEmpty(name)) {
      return null;
    }
    return Paths.get(path, name).toString();
  }

  /**
   * 检查IP地址是否为本机地址
   *
   * @param addr IP地址
   * @return 检查结果
   */
  public static boolean isLocalhost(InetAddress addr) {
    if (addr.isAnyLocalAddress() || addr.isLoopbackAddress()) {
      return true;
    }

    try {
      return NetworkInterface.getByInetAddress(addr) != null;
    } catch (SocketException e) {
      return false;
    }
  }

  /**
   * 保存文件
   * @param dir   保存路径
   * @param file  文件流
   * @return      文件
   */
  public static File fileSaver(String dir, MultipartFile file) {
    if (file.isEmpty()) {
      logger.error("file can not be empty");
      return null;
    }
    if (dir == null) {
      logger.error("dir can not be null");
      return null;
    }
    String orgName = file.getOriginalFilename();
    Path path = Paths.get(dir, orgName);
    try {
      File tempFile = path.toFile();
      try(OutputStream os = new FileOutputStream(tempFile)) {
        os.write(file.getBytes());
      }
      return tempFile;
    } catch (Exception e) {
      logger.error("save file failed: " + e.getMessage());
      return null;
    }
  }
}
