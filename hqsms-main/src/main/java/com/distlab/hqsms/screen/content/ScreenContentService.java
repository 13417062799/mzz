package com.distlab.hqsms.screen.content;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.distlab.hqsms.cloud.MediaFileService;
import com.distlab.hqsms.common.net.OkHttpService;
import com.distlab.hqsms.edge.DeviceService;
import com.distlab.hqsms.screen.device.Screen;
import com.distlab.hqsms.screen.device.ScreenRepository;
import com.distlab.hqsms.screen.device.ScreenService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class ScreenContentService {
  private static final Logger logger = LoggerFactory.getLogger(ScreenContentService.class);

  @Autowired
  OkHttpService okHttpService;
  @Autowired
  ScreenService screenService;
  @Autowired
  ScreenRepository screenRepository;
  @Autowired
  ScreenContentRepository screenContentRepository;
  @Autowired
  MediaFileService mediaFileService;
  @Autowired
  DeviceService deviceService;

  //SERVICE 调用

  /**
   * 生成vsn文件
   * @param index       内容索引
   * @param vsnInfo     vsn文件信息
   * @return            vsn文件
   */
  public File vsnGenerator(String index, String vsnInfo) {
    String screenDir = System.getProperty("user.dir") + File.separator + "tmp" + File.separator + "screen";
    //保存svn
    Path filePath = Paths.get(screenDir, index + ".vsn");
    try {
      File vsnFile = filePath.toFile();
      try(BufferedWriter bw = new BufferedWriter(new FileWriter(vsnFile))) {
        bw.write(vsnInfo);
      }
      return vsnFile;
    } catch (IOException e) {
      logger.error("vsnGenerator error: " + e.getMessage());
      return null;
    }
  }

  /**
   * 上传内容
   * @param ip          屏幕设备ip
   * @param vsnFile     vsn文件
   * @param contentFile 内容文件
   * @param info        内容文件信息
   * @return            操作结果
   */
  public boolean contentUploader(String ip,File vsnFile, File contentFile, ContentInfo info) {
    String url = String.format("http://%s/api/program/%s.vsn", ip, info.getContentIndex());
    RequestBody vsn = RequestBody.create(MediaType.parse("application/form-data"), vsnFile);
    RequestBody content = RequestBody.create(MediaType.parse("application/form-data"), contentFile);
    RequestBody requestBody = new MultipartBody.Builder()
      .addFormDataPart("f1", String.format("%s.vsn", info.getContentIndex()), vsn)
      .addFormDataPart("f2", info.getOrgName(), content)
      .setType(MultipartBody.FORM)
      .build();
    return okHttpService.postRequest(url, requestBody);
  }

  /**
   * 删除内容
   * @param ip          屏幕设备ip
   * @param index       内容索引
   * @return            操作结果
   */
  public boolean contentDeleter(String ip, String index) {
    String url = String.format("http://%s/api/vsns/sources/lan/vsns/%s.vsn", ip, index);
    return okHttpService.deleteRequest(url);
  }

  /**
   * 切换内容
   * @param ip          屏幕设备ip
   * @param index       内容索引
   * @return            操作结果
   */
  public boolean contentSwitcher(String ip, String index) {
    String url = String.format("http://%s/api/vsns/sources/lan/vsns/%s.vsn/activated", ip, index);
    return okHttpService.putRequest(url);
  }

//  /**
//   * 重命名内容
//   * @param orgName 文件名
//   * @return        文件名
//   */
//  public String contentRename(String orgName) {
//    try {
//      String prefix = orgName.substring(0, orgName.lastIndexOf("."));
//      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//      String timeStamp = sdf.format(new Date());
//      return prefix + "_" + timeStamp;
//    } catch (Exception e) {
//      log.error("failed to create contentName: " + e.getMessage());
//      return null;
//    }
//  }

  /**
   * 生成vsn文件信息
   * @param info    内容信息
   * @param type    文件类型（picture, video)
   * @return        文件信息
   */
  public String vsnInfoGenerator(ContentInfo info, String type) {
    JSONObject item = ScreenContentUtils.item(info.getContentIndex(), info.getOrgName(), "", type);
    JSONArray items = new JSONArray();
    items.add(0, item);

    JSONObject region = ScreenContentUtils.region(items, "136", "204", "1");
    JSONArray regions = new JSONArray();
    regions.add(0, region);

    JSONObject page = ScreenContentUtils.page(regions, "5000", "1");
    JSONArray pages = new JSONArray();
    pages.add(0, page);

    return ScreenContentUtils.programs(pages, "136", "204").toJSONString();
  }

  /**
   * 解析内容信息
   * @param file  内容文件
   * @return      内容信息实体
   */
  public ContentInfo parseContent(MultipartFile file) {
    String orgName = file.getOriginalFilename();
    if (orgName == null) {
      logger.error("file name can not be empty");
      return null;
    }

    String title = orgName.substring(0, orgName.lastIndexOf("."));
    if (title.length() > 45) {
      title = title.substring(0, 45);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    String index = sdf.format(new Date());
    String contentType = file.getContentType();
    if (contentType == null) {
      logger.error("content type error");
      return null;
    }
    ContentInfo contentInfo = new ContentInfo();
    contentInfo.setOrgName(orgName);
    contentInfo.setName(title);
    contentInfo.setContentIndex(index);
    contentInfo.setContentType(contentType);
    return contentInfo;
  }

  /**
   * 解析内容类型
   * @param contentType 内容类型
   * @return            image/video
   */
  public String parseType(String contentType) {
    if (contentType == null) {
      logger.error("contentType not found");
      return null;
    }
    if (contentType.contains("image")) {
      return "image";
    }
    if (contentType.contains("video")) {
      return "video";
    }
    logger.error("contentType error: " + contentType);
    return null;
  }


  //CONTROLLER调用

  /**
   * 上传内容
   * @param screenId  设备ID
   * @param file      媒体文件
   * @return          内容ID
   */
  public ScreenContent uploadContent(BigInteger screenId, MultipartFile file) {
    Optional<Screen> screen = screenRepository.findById(screenId);
    if (!screen.isPresent()) {
      logger.error("screen not found");
      return null;
    }
    Screen sc = screen.get();
    String ip = sc.getIp();
    if (ip == null) {
      logger.error("ip not found");
      return null;
    }
    ContentInfo contentInfo = parseContent(file);
    if (contentInfo == null) {
      return null;
    }
    String type = parseType(contentInfo.getContentType());
    String vsnInfo = vsnInfoGenerator(contentInfo, type);
    if (vsnInfo == null) {
      return null;
    }
    File vsn = vsnGenerator(contentInfo.getContentIndex(), vsnInfo);
    if (vsn == null) {
      return null;
    }
    String screenDir = System.getProperty("user.dir") + File.separator + "tmp" + File.separator + "screen";
    File content = MediaFileService.fileSaver(screenDir, file);
    if (content == null) {
      return null;
    }

    boolean flag = contentUploader(ip, vsn, content, contentInfo);
    //删除临时文件
    boolean delete = vsn.delete();
    if (!delete) {
      delete = vsn.delete();
      if (!delete) {
        logger.warn("failed to delete temporary vsn");
      }
    }
    delete = content.delete();
    if (!delete) {
      delete = content.delete();
      if (!delete) {
        logger.warn("failed to delete temporary content");
      }
    }
    if (!flag) {
      return null;
    }
    //更新播放状态
    Iterable<ScreenContent> all = screenContentRepository.findAll();
    for (ScreenContent scc : all) {
      scc.setPlayed(false);
      screenContentRepository.save(scc);
    }
    // 获取分布式ID
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.SCREEN_CONTENT);
    if (id.equals(BigInteger.valueOf(-1))) {
      return null;
    }
    //保存数据
    ScreenContent scc = new ScreenContent();
    scc.setId(id);
    scc.setScreenId(screenId);
    scc.setPlayed(true);
    scc.setName(contentInfo.getName());
    scc.setContentIndex(contentInfo.getContentIndex());
    scc.setType(contentInfo.getContentType());
    scc.setSize(BigInteger.valueOf(file.getSize()));
    scc.setCreatedAt(new Date());
    try {
      return screenContentRepository.save(scc);
    } catch (Exception e) {
      logger.error("save data error: " + e.getMessage());
      return null;
    }
  }

  /**
   * 控制内容（删除、切换）
   * @param contentId 内容ID
   * @param function  控制指令
   * @return          操作结果
   */
  public ScreenContent controlContent(BigInteger contentId, String function) {
    Optional<ScreenContent> screenContent = screenContentRepository.findById(contentId);
    if (!screenContent.isPresent()) {
      logger.error("screen content not found");
      return null;
    }
    ScreenContent scc = screenContent.get();
    BigInteger screenId = scc.getScreenId();
    if (screenId == null) {
      logger.error("screenId not found");
      return null;
    }
    String index = scc.getContentIndex();
    if (index == null) {
      logger.error("index not found");
      return null;
    }
    Optional<Screen> screen = screenRepository.findById(screenId);
    if (!screen.isPresent()) {
      logger.error("screen not found");
      return null;
    }
    Screen sc = screen.get();
    String ip = sc.getIp();
    if (ip == null) {
      logger.error("ip not found");
      return null;
    }
    if ("delete".equals(function)) {
      boolean flag = contentDeleter(ip, index);
      if (!flag) {
        return null;
      }
      screenContentRepository.delete(scc);
      return scc;
    }
    else if ("switch".equals(function)) {
      boolean flag = contentSwitcher(ip, index);
      if (!flag) {
        return null;
      }
      scc.setPlayed(true);
      return screenContentRepository.save(scc);
    }
    else {
      logger.error("function error");
      return null;
    }
  }

  /**
   * 封装内容信息
   */
  public static class ContentInfo{
    private String orgName;
    private String name;
    private String contentIndex;
    private String contentType;

    public String getOrgName() {
      return orgName;
    }

    public void setOrgName(String orgName) {
      this.orgName = orgName;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getContentIndex() {
      return contentIndex;
    }

    public void setContentIndex(String contentIndex) {
      this.contentIndex = contentIndex;
    }

    public String getContentType() {
      return contentType;
    }

    public void setContentType(String contentType) {
      this.contentType = contentType;
    }
  }
}
