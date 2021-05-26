package com.distlab.hqsms.broadcast.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.distlab.hqsms.broadcast.device.Broadcast;
import com.distlab.hqsms.broadcast.device.BroadcastService;
import com.distlab.hqsms.broadcast.plan.BroadcastPlan;
import com.distlab.hqsms.broadcast.plan.BroadcastPlanRepository;
import com.distlab.hqsms.edge.DeviceService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class BroadcastContentService {
  @Autowired
  BroadcastService broadcastService;
  @Autowired
  BroadcastFTPService broadcastFTPService;
  @Autowired
  BroadcastContentRepository broadcastContentRepository;
  @Autowired
  BroadcastPlanRepository broadcastPlanRepository;
  @Autowired
  DeviceService deviceService;

  private static final Logger logger = LoggerFactory.getLogger(BroadcastContentService.class);
  private final MediaType mediaType = MediaType.parse("application/json; charset = utf-8");
  private final String CHECK_DATABASE = "please check database";
  private final String INCORRECT = "may be incorrect";

  OkHttpClient okHttpClient = new OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .retryOnConnectionFailure(true)
    .build();

  //CREATE

  /**
   * 发送上传请求
   * @param cookie  cookie
   * @param relayIP 中继服务器IP
   * @return  ftp信息（包含ip、端口、文件ID、用户名、密码）
   */
  private JSONObject sendUploadRequest(String cookie, String relayIP) {
    JSONObject reObj = null;
    //构建请求体
    JSONObject obj = new JSONObject();
    obj.put("Type", 1); //仅支持上传MP3文件
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //构建post请求
    String url = String.format("http://%s/FileUpload", relayIP);
    Request request = new Request.Builder()
      .url(url)
      .addHeader("Cookie", cookie)
      .post(requestBody)
      .build();
    //发起网络请求
    try {
      Response execute = okHttpClient.newCall(request).execute();
      if (execute.isSuccessful() && execute.body() != null) {
        String bodyString = execute.body().string();
        reObj = JSON.parseObject(bodyString);
      } else {
        logger.error("Failed to get FTP info, please check if arguments are correct.");
      }
      execute.close(); //关闭call
    } catch (Exception e) {
      logger.error("Failed send post request, " + e.getMessage());
    }
    return reObj;
  }

  /**
   * 从FTP服务器下载内容到中继服务器
   * @param fileId  ftp服务器唯一文件ID
   * @param fileName  文件原名
   * @param relayIP  中继服务器IP
   * @param cookie  cookie
   * @return  JSON对象（包含中继服务器生成的节目ID、节目时长）
   */
  private JSONObject downloadFile2Relay(String fileId, String fileName, String relayIP, String cookie) {
    JSONObject reObj = null;
    //构建请求体
    JSONObject obj = new JSONObject();
    obj.put("ParentId", 0); //保存在根目录
    obj.put("Type", 1); //仅支持MP3
    obj.put("Name", fileName);
    obj.put("FileId", fileId);
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //构建post请求
    String url = String.format("http://%s/MLCreateNode", relayIP);
    Request request = new Request.Builder()
      .url(url)
      .addHeader("Cookie", cookie)
      .post(requestBody)
      .build();
    //发起网络请求
    try {
      Response execute = okHttpClient.newCall(request).execute();
      if (execute.isSuccessful() && execute.body() != null) {
        String bodyString = execute.body().string();
        reObj = JSON.parseObject(bodyString);
        execute.body().close();
      } else {
        logger.error("Failed to download content to Relay server, please check if arguments are correct.");
      }
      execute.close(); //关闭call
    } catch (Exception e) {
      logger.error("Failed send post request, " + e.getMessage());
    }
    return reObj;
  }

  /**
   * 操作新增内容并保存内容数据
   * @param file  内容文件
   * @param broadcastId 广播设备ID
   * @return  JSON对象（反馈给前端的响应体）
   */
  public BroadcastContent uploadContent(MultipartFile file, BigInteger broadcastId) {
    Broadcast bc = broadcastService.findBroadcast(broadcastId);
    if (bc == null) {
      logger.error("broadcast info not found, broadcastId " + INCORRECT);
      return null;
    }
    String relayIP = bc.getRelayIP();
    if (relayIP == null) {
      logger.error("relayIP not found, " + CHECK_DATABASE);
      return null;
    }
    String cookie = broadcastService.findCookie(broadcastId);
    if (cookie == null){
      logger.error("cookie not found, please check relay server");
      return null;
    }

    /*
     * 以下内容需修改（操作成功直接返回响应）
     */

    //发送上传请求
    JSONObject ftpInfo = sendUploadRequest(cookie, relayIP);
    if (ftpInfo == null) {
      logger.info("failed to send upload request to relay server");
      return null;
    }
    //上传到FTP
    boolean flag = broadcastFTPService.uploadFile2FTP(file, ftpInfo); //需修改
    if (!flag) {
      logger.info("failed to upload file to ftp server");
      return null;
    }
    //从FTP下载到中继服务器
    BroadcastFTPService.FTPParams ftpParams = broadcastFTPService.parseFTPInfo(ftpInfo);
    String code = ftpParams.getFileId();
    String orgFilename = file.getOriginalFilename();
    long size = file.getSize();
    JSONObject obj = downloadFile2Relay(code, orgFilename, relayIP, cookie);  //需修改
    if (obj == null) {
      logger.info("failed to download file to relay server");
      return null;
    }
    //保存数据
    String ftpUrl = ftpInfo.getString("FtpUrl");
    Integer programId = obj.getInteger("ID");
    Integer length = obj.getInteger("Length");
    String contentType = file.getContentType();
    BroadcastContent bcc = new BroadcastContent();
    // 获取分布式ID
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.BROADCAST_CONTENT);
    if (id.equals(BigInteger.valueOf(-1))) {
      return null;
    }
    bcc.setId(id);
    bcc.setBroadcastId(broadcastId);
    if (programId != null) {
      bcc.setProgramId(programId);
    }
    if (length != null) {
      bcc.setLength(length);
    }
    bcc.setSize(size);
    bcc.setType(contentType);
    bcc.setContentName(orgFilename);
    bcc.setCode(code);
    bcc.setFtpUrl(ftpUrl);
    bcc.setCreatedAt(new Date());
    try {
      broadcastContentRepository.save(bcc);
      return bcc;
    } catch (Exception e) {
      logger.error("upload content successfully, but failed to save content's data. " + e.getMessage());
      return null;
    }
  }


  //READ

  /**
   * 根据计划ID获取内容信息
   * @param planId  计划ID
   * @return  内容对象
   */
  public Optional<BroadcastContent> getContentByPlanId(BigInteger planId) {
    Optional<BroadcastPlan> tempBCP = broadcastPlanRepository.findById(planId);
    if (tempBCP.isPresent()) {
      BroadcastPlan bcp = tempBCP.get();
      BigInteger contentId = bcp.getContentId();
      if (contentId != null) {
        return broadcastContentRepository.findById(contentId);
      }
    }
    return Optional.empty();
  }


  //UPDATE

  /**
   * 创建会话（用户控制文件播放）
   * @param broadcastId 设备ID
   * @param relayIP 中继服务器IP
   * @param cookie  cookie
   * @return  会话ID（-1-失败）
   */
  private int createSession(BigInteger broadcastId, String relayIP, String cookie) {
    Integer sessionId = -1;
    //构建请求体
    BigInteger[] broadcastIds = {broadcastId};
    JSONObject obj = new JSONObject();
    obj.put("Tids", broadcastIds); //终端ID数组
    obj.put("PlayPrior", 500); //0-1000，越大优先级越高
    obj.put("Name", "session"); //会话名称
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //构建POST请求
    String url = String.format("http://%s/FileSessionCreate", relayIP);
    Request request = new Request.Builder()
      .url(url)
      .addHeader("Cookie", cookie)
      .post(requestBody)
      .build();
    //发起网络请求
    try {
      Response execute = okHttpClient.newCall(request).execute();
      if (execute.isSuccessful() && execute.body() != null) {
        String bodyString = execute.body().string();
        JSONObject reBody = JSON.parseObject(bodyString);
        //提取会话ID
        sessionId = reBody.getInteger("Sid");
        if (sessionId == null) {
          sessionId = -1;
          logger.error("Failed to get sessionId, please check if the old sessionId has been deleted.");
        }
      } else {
        logger.error("Failed to get sessionId, please check if arguments are correct.");
      }
      execute.close(); //关闭call
    } catch (Exception e) {
      logger.error("Failed to send post request, " + e.getMessage());
    }
    return sessionId;
  }

  /**
   * 绑定会话和节目（实时播放）
   * @param sessionId 会话ID
   * @param programId 节目ID
   * @param relayIP 中继服务器IP
   * @param cookie  cookie
   * @return  操作结果
   */
  private boolean combineSessionAndProgram(int sessionId, int programId, String relayIP, String cookie) {
    boolean flag = false;
    //构建请求体
    JSONObject obj = new JSONObject();
    obj.put("Sid", sessionId);
    obj.put("ProgId", programId);
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //构建POST请求
    String url = String.format("http://%s/FileSessionSetProg", relayIP);
    Request request = new Request.Builder()
      .url(url)
      .addHeader("Cookie", cookie)
      .post(requestBody)
      .build();
    //发起网络请求
    try {
      Response execute = okHttpClient.newCall(request).execute();
      if (execute.isSuccessful() && execute.body() != null) {
        String bodyString = execute.body().string();
        JSONObject reBody = JSON.parseObject(bodyString);
        String remark = reBody.getString("Remark");
        if (remark.contains("OK")) {
          flag = true;
        }
      } else {
        logger.error("Failed to combine session and program, please check if arguments are correct.");
      }
      execute.close(); //关闭call
    } catch (Exception e) {
      logger.error("Failed to send post request, " + e.getMessage());
    }

    return flag;
  }

  /**
   * 从数据库查询会话ID
   * @param contentId 内容ID
   * @return  会话ID（-1-失败）
   */
  private int getSessionId(BigInteger contentId) {
    int sessionId = -1;
    //获取内容对象
    BroadcastContent bcc = findBroadcastContent(contentId);
    if (bcc != null) {
      sessionId = bcc.getSessionId();
    } else {
      logger.error("Content: " + contentId + " does not exist.");
    }
    return sessionId;
  }

  /**
   * 更新会话ID（会话空闲10分钟失效机制）
   * @param broadcastId 设备ID
   * @param relayIP 中继服务器IP
   * @param cookie  cookie
   * @return  会话ID (-1-失败）
   */
  private int refreshSessionId(BigInteger broadcastId, String relayIP, String cookie) {
    int sessionId = createSession(broadcastId, relayIP, cookie);
    if (sessionId == -1) {
      //双重检查
      sessionId = createSession(broadcastId, relayIP, cookie);
    }
    return sessionId;
  }

  /**
   * 更新会话ID数据
   * @param contentId 内容ID
   * @param sessionId 会话ID
   */
  private BroadcastContent updatedSessionIdData(BigInteger contentId, int sessionId) {
    //操作成功，更新内容数据
    BroadcastContent bcc = findBroadcastContent(contentId);
    if (bcc == null) {
      logger.info("content not found, failed to update sessionId's data");
      return null;
    } else {
      bcc.setSessionId(sessionId);
      bcc.setUpdatedAt(new Date());
      try {
        return broadcastContentRepository.save(bcc);
      } catch (Exception e) {
        logger.info("failed to update sessionId's data, " + e.getMessage());
        return null;
      }
    }
  }

  /**
   * 设置内容播放状态
   * @param sessionId 会话ID
   * @param status  状态（0-stop，1-play，2-pause）
   * @param relayIP 中继服务器IP
   * @param cookie  cookie
   * @return  操作结果
   */
  private boolean setStatus(int sessionId, int status, String relayIP, String cookie) {
    boolean flag = false;
    //构建请求体
    JSONObject obj = new JSONObject();
    obj.put("Sid", sessionId);
    obj.put("Status", status);
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //构建POST请求
    String url = String.format("http://%s/FileSessionSetStat", relayIP);
    Request request = new Request.Builder()
      .url(url)
      .addHeader("Cookie", cookie)
      .post(requestBody)
      .build();
    //发起网络请求
    try {
      Response execute = okHttpClient.newCall(request).execute();
      if (execute.isSuccessful() && execute.body() != null) {
        String bodyString = execute.body().string();
        JSONObject reBody = JSON.parseObject(bodyString);
        String remark = reBody.getString("Remark");
        if (remark.contains("OK")) {
          flag = true;
        } else {
          logger.error("Failed to set status, please check if the arguments are correct.");
        }
      }
      execute.close(); //关闭call
    } catch (Exception e) {
      logger.error("Failed to send post request, " + e.getMessage());
    }
    return  flag;
  }

  /**
   * 转换控制命令
   * @param command 控制命令
   * @return  status（数字）
   */
  private int convertCommand2Status(String command) {
    int status = -1;
    if (command.equals("stop")) {
      status = 0;
    }
    if (command.equals("play")) {
      status = 1;
    }
    if (command.equals("pause")) {
      status = 2;
    }
    return status;
  }

  /**
   * 内容播放控制
   * @param contentId 内容ID
   * @param command 控制命令
   * @return  JSON对象（反馈给前端的响应体）
   */
  public BroadcastContent setContentStatus(BigInteger contentId, String command) {
    //转换控制命令
    int status = convertCommand2Status(command);
    if (status == -1) {
      return null;
    }
    //获取设备ID
    BigInteger broadcastId = findBroadcastId(contentId);
    if (broadcastId.equals(BigInteger.valueOf(-1))) {
      logger.error("broadcastId not found, contentId may be incorrect");
      return null;
    }
    //获取中继服务器IP
    String relayIP = broadcastService.findRelayIP(broadcastId);
    if (relayIP == null) {
      logger.error("relayIP not found, please check database");
      return null;
    }
    //获取节目ID
    int programId = findProgramId(contentId);
    if (programId == -1) {
      logger.error("programId not found, please check database");
      return null;
    }
    //获取cookie
    String cookie = broadcastService.findCookie(broadcastId);
    if (cookie == null) {
      logger.error("cookie not found, please check relay server");
      return null;
    }
    int sessionId = getSessionId(contentId);
    boolean flag;
    // 会话存在
    if (sessionId != -1) {
      flag = setStatus(sessionId, status, relayIP, cookie);
      // 操作成功
      if (flag) {
        Optional<BroadcastContent> tBcc = broadcastContentRepository.findById(contentId);
        return tBcc.orElse(null);
      }
      // 操作失败，刷新会话
      sessionId = refreshSessionId(broadcastId, relayIP, cookie);
      if (sessionId == -1) {
        // 刷新失败
        logger.error("refresh session failed");
        return null;
      }
      // 刷新成功
      flag = combineSessionAndProgram(sessionId, programId, relayIP, cookie);
      if (!flag) {
        //绑定失败
        logger.error("new session has been created, but failed to combine session, please check network");
        return null;
      }
      //绑定成功，执行命令
      flag = setStatus(sessionId, status, relayIP, cookie);
      //更新数据
      BroadcastContent bcc = updatedSessionIdData(contentId, sessionId);
      if (flag) {
        return bcc;
      }
      logger.error("failed to set status, new session has been created, content is replaying now, please check network");
      return null;
    }


    //会话不存在,新建会话
    sessionId = refreshSessionId(broadcastId, relayIP, cookie);
    if (sessionId == -1) {
      //刷新失败
      logger.error("failed to create session, please check network");
      return null;
    }
    //刷新成功
    //绑定会话，实时播放
    flag = combineSessionAndProgram(sessionId, programId, relayIP, cookie);
    if (!flag) {
      //绑定失败
      logger.error("failed to combine session, please check network");
      return null;
    }
    //绑定成功，执行命令
    flag = setStatus(sessionId,status, relayIP, cookie);
    //更新数据
    BroadcastContent bcc = updatedSessionIdData(contentId, sessionId);
    if (flag) {
      //执行命令成功
      return bcc;
    }
    //执行命令失败
    logger.error("failed to set status, please check network");
    return null;
  }


  //DELETE

  /**
   * 从中继服务器（边缘服务器）删除内容
   * @param programId 中继服务器生成的节目ID
   * @param relayIP 中继服务器IP
   * @param cookie  cookie
   * @return  操作结果
   */
  private boolean deleteContentFromRelay(int programId, String relayIP, String cookie) {
    boolean flag = false;
    //构建请求体
    JSONObject obj = new JSONObject();
    obj.put("ID", programId);
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //构建请求
    String url = String.format("http://%s/MLDelProg", relayIP);
    Request request = new Request.Builder()
      .url(url)
      .addHeader("Cookie", cookie)
      .post(requestBody)
      .build();
    //发起网络请求
    try {
      Response execute = okHttpClient.newCall(request).execute();
      if (execute.isSuccessful() && execute.body() != null) {
        String bodyString = execute.body().string();
        JSONObject reBody = JSON.parseObject(bodyString);
        String remark = reBody.getString("Remark");
        if (remark.contains("OK")){
          flag = true;
        } else {
          logger.error("Failed to delete content from Relay server, please check if the user and password are correct.");
        }
      }
      execute.close(); //关闭call
    } catch (IOException e) {
      logger.error("Failed send post request, " + e.getMessage());
    }
    return flag;
  }

  /**
   * 从数据库查询节目ID（根据内容ID）
   * @param contentId 内容ID
   * @return  节目ID（-1-失败）
   */
  public int findProgramId(BigInteger contentId) {
    int programId = -1;
    BroadcastContent bcc = findBroadcastContent(contentId);
    if (bcc != null) {
      programId = bcc.getProgramId();
    } else {
      logger.error("Content: " + contentId + " does not exist.");
    }
    return programId;
  }

  /**
   * 从数据库查询设备ID（根据内容ID）
   * @param contentId 内容ID
   * @return  设备ID (null - fail)
   */
  public BigInteger findBroadcastId(BigInteger contentId) {
    Optional<BroadcastContent> tempBCC = broadcastContentRepository.findById(contentId);
    if (!tempBCC.isPresent()) {
      return null;
    }
    BroadcastContent bcc = tempBCC.get();
    return bcc.getBroadcastId();
  }

  /**
   * 从数据库查询内容信息
   * @param contentId 内容ID
   * @return  内容对象（null-失败）
   */
  public BroadcastContent findBroadcastContent(BigInteger contentId) {
    Optional<BroadcastContent> tempBCC = broadcastContentRepository.findById(contentId);
    return tempBCC.orElse(null);
  }

  /**
   * 操作删除内容并删除内容数据
   * @param contentId 内容ID
   * @return  JSON对象（反馈给前端的响应体）
   */
  public int deleteContent(BigInteger contentId) {
    // 获取设备ID（为了获取cookie）
    BigInteger broadcastId = findBroadcastId(contentId);
    if (broadcastId.equals(BigInteger.valueOf(-2))) {
      logger.error("broadcastId not found, contentId " + INCORRECT);
      return -1;
    }
    if (broadcastId.equals(BigInteger.valueOf(-1))){
      logger.error("broadcastId not found, " + CHECK_DATABASE);
      return -1;
    }
    //获取cookie
    String cookie = broadcastService.findCookie(broadcastId);
    if (cookie == null) {
      logger.error("cookie not found, please check relay server");
      return -1;
    }
    String relayIP = broadcastService.findRelayIP(broadcastId);
    if (relayIP == null) {
      logger.error("relayIp not found, " + CHECK_DATABASE);
      return -1;
    }
    //获取节目ID
    int programId = findProgramId(contentId);
    if (programId == -1) {
      logger.error("programId not found, " + CHECK_DATABASE);
      return -1;
    }

    //从中继服务器删除内容
    boolean flag = deleteContentFromRelay(programId, relayIP, cookie);
    if (flag) {
      BroadcastContent bcc = findBroadcastContent(contentId);
      if (bcc == null) {
        logger.error("data not found, " + CHECK_DATABASE);
        return -1;
      } else {
        broadcastContentRepository.delete(bcc);
        return 0;
      }
    } else {
      return -1;
    }
  }

  /**
   * 内容文件转存
   * @param file  文件
   * @return  文件
   */
  public File convertBCCFile(MultipartFile file) {
    if (file.isEmpty()) {
      return null;
    }
    String broadcastDir;
    try {
      broadcastDir = System.getProperty("user.dir") + File.separator + "tmp" + File.separator + "broadcast";
    } catch (Exception e) {
      return null;
    }
    String filename = file.getOriginalFilename();
    if (filename == null) {
      return null;
    }
    File bccFile = new File(broadcastDir, filename);
    try {
      byte[] bytes = file.getBytes();
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(bccFile));
      bos.write(bytes);
      bos.close();
    } catch (Exception e) {
      return null;
    }
    return bccFile;
  }

  /**
   * 根据计划名称获取内容ID
   * @param planName  计划名称
   * @param pageable  分页参数
   * @return  内容ID
   */
  public Collection<BigInteger> findCIdsByPlanName(String planName, Pageable pageable) {
    Page<BroadcastPlan> planNameLike = broadcastPlanRepository.findByPlanNameLike(planName, pageable);
    if (planNameLike == null) {
      return null;
    }
    Collection<BigInteger> contentIds = new ArrayList<>();
    for (BroadcastPlan bcp : planNameLike) {
      BigInteger contentId = bcp.getContentId();
      contentIds.add(contentId);
    }
    System.out.println(contentIds);
    return contentIds;
  }

}
