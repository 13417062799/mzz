package com.distlab.hqsms.broadcast.content;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;

@Service
public class BroadcastFTPService {
  private static final Logger logger = LoggerFactory.getLogger(BroadcastFTPService.class);

  /**
   * 登录ftp服务器，执行文件上传操作
   * @param file  文件
   * @param ftpInfo ftp信息（包含ip、端口、文件ID、用户名、密码）
   * @return  操作结果
   */
  public boolean uploadFile2FTP(MultipartFile file, JSONObject ftpInfo) {
    boolean flag = false;
    //创建FTPClient
    FTPClient ftpClient = new FTPClient();
    ftpClient.setConnectTimeout(1000 * 60); //60s
    //登录
    FTPParams ftpParams = parseFTPInfo(ftpInfo);
    String ip = ftpParams.getIp();
    int port = ftpParams.getPort();
    String username = ftpParams.getUsername();
    String password = ftpParams.getPassword();
    try {
      ftpClient.connect(ip, port);
      ftpClient.login(username, password);
    } catch (Exception e) {
      logger.error("Failed to login to FTP server." + e.getMessage());
    }
    int replyCode = ftpClient.getReplyCode();
    if (!FTPReply.isPositiveCompletion(replyCode)) {
      //登录失败，断开连接
      try {
        ftpClient.disconnect();
      } catch (Exception e) {
        logger.error("Failed to login to FTP server, and failed to disconnect." + e.getMessage());
      }
    } else {
      try {
        //登录成功，上传文件
        String fileId = ftpParams.getFileId();
        InputStream fileIS = file.getInputStream();
        String contentName = fileId + ".mp3";
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);//二进制文件类型
        ftpClient.setControlEncoding("utf-8");//中文支持
        ftpClient.enterLocalPassiveMode();//被动模式
        ftpClient.changeWorkingDirectory("/");//设定工作路径为首页
        ftpClient.storeFile(contentName, fileIS);//保存文件
        fileIS.close();//关闭输入流
        flag = true;
      } catch (Exception e) {
        logger.error("Failed to upload content, " + e.getMessage());
      }
    }

    //断开连接
    try {
      if (ftpClient.isConnected()) {
        ftpClient.logout();
        ftpClient.disconnect();
      }
    } catch (Exception e) {
      logger.warn("Upload content successful, but failed to disconnect FTPClient.");
    }
    return flag;
  }


  /**
   * 解析ftp信息（待测试）
   * @param ftpInfo ftp信息（包含ip、端口、文件ID、用户名、密码）
   * @return  封装好的ftp参数
   */
  public FTPParams parseFTPInfo(JSONObject ftpInfo) {
    String ftpUrl = ftpInfo.getString("FtpUrl");  //获取ftpUrl
    //解析ftpUrl 获取ip和port
    String ip = null;
    int port = -1;
    try {
      URL url = new URL(ftpUrl);
      ip = url.getHost();
      port = url.getPort();
    } catch (Exception e) {
      logger.error("Failed to parse ftpUrl. " + e.getMessage());
      return null;
    }
    String fileId = ftpInfo.getString("FileId");  //获取fileId
    String username = ftpInfo.getString("FtpUsr");  //获取登录用户名
    String password = ftpInfo.getString("FtpPwd");  //获取登录密码

    //缓存数据
    FTPParams ftpParams = new FTPParams();
    ftpParams.setIp(ip);
    ftpParams.setPort(port);
    ftpParams.setFileId(fileId);
    ftpParams.setUsername(username);
    ftpParams.setPassword(password);
    return ftpParams;
  }


  /**
   * ftp参数封装
   */
  public static class FTPParams {
    private String ip;
    private int port;
    private String fileId;
    private String username;
    private String password;

    public String getIp() {
      return ip;
    }

    public void setIp(String ip) {
      this.ip = ip;
    }

    public int getPort() {
      return port;
    }

    public void setPort(int port) {
      this.port = port;
    }

    public String getFileId() {
      return fileId;
    }

    public void setFileId(String fileId) {
      this.fileId = fileId;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }
}
