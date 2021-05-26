package com.distlab.hqsms.broadcast.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class BroadcastService {
  private static final Logger logger = LoggerFactory.getLogger(BroadcastService.class);
  private final MediaType mediaType = MediaType.parse("application/json; charset = utf-8");

  OkHttpClient okHttpClient = new OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .retryOnConnectionFailure(true)
    .build();

  @Autowired
  BroadcastRepository broadcastRepository;


  //UPDATE

  /**
   * 查找中继服务器IP
   * @param broadcastId 设备ID
   * @return  中继服务器IP
   */
  public String findRelayIP(BigInteger broadcastId) {
    Broadcast bc = findBroadcast(broadcastId);
    if (bc == null) {
      return null;
    }
    return bc.getRelayIP();
  }

  /**
   * 登录中继服务器
   * @return  cookie
   */
  public String login(BigInteger broadcastId) {
    String cookie = null;
    Broadcast bc = findBroadcast(broadcastId);
    if (bc == null) {
      return null;
    }
    String relayIP = bc.getRelayIP();
    if (relayIP == null) {
      return null;
    }
    String url = String.format("http://%s/login", relayIP);
    //构建请求体
    String username = bc.getUsername();
    String password = bc.getPassword();
    if (username == null || password == null) {
      return null;
    }
    JSONObject obj = new JSONObject();
    obj.put("User", username);
    obj.put("Passwd", password);
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //构建post请求
    Request request = new Request.Builder()
      .url(url)
      .post(requestBody)
      .build();
    //发起网络请求
    try {
      Response execute = okHttpClient.newCall(request).execute();
      if (execute.isSuccessful()){
        Headers headers = execute.headers();
        cookie = headers.get("Set-Cookie");
      } else {
        logger.error("Failed to login, please check if the user and password are correct.");
      }
      execute.close();
    } catch (IOException e) {
      logger.error("Failed send a post request, " + e.getMessage());
    }

    return cookie;
  }

  /**
   * 校验cookie，如过期则更新
   * @param cookie  需校验cookie
   * @return  可用的cookie
   */
  private String refreshCookie(String cookie, BigInteger broadcastId) {
    String relayIP = findRelayIP(broadcastId);
    if (relayIP == null) {
      return null;
    }
    String url = String.format("http://%s/getTermIds", relayIP);
    JSONObject obj = new JSONObject();
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    Request request = new Request.Builder()
      .url(url)
      .addHeader("Cookie", cookie)
      .post(requestBody)
      .build();

    try {
      Response execute = okHttpClient.newCall(request).execute();
      if (execute.isSuccessful() && execute.body() != null) {
        String bodyString = execute.body().string();
        JSONObject reBody = JSON.parseObject(bodyString);
        String remark = reBody.getString("Remark");
        if (remark.contains("Invalid JSESSIONID")) {
          //cookie失效，更新cookie
          cookie = login(broadcastId);
        }
      } else {
        logger.error("Failed to execute.");
      }
      execute.close();
    } catch (IOException e) {
      logger.error("Failed send post request, " + e.getMessage());
    }

    return cookie;
  }

  /**
   * 获取可用cookie
   * @param broadcastId 广播设备ID
   * @return  可用的cookie (null-失败）
   */
  public String findCookie(BigInteger broadcastId) {
    Optional<Broadcast> tempBC = broadcastRepository.findById(broadcastId);
    if (!tempBC.isPresent()) {
      logger.error("Broadcast: " + broadcastId + " does not exist.");
      return null;
    }
    Broadcast bc = tempBC.get();
    String cookie = bc.getCookie();
    if (cookie == null) {
      //cookie不存在，获取并保存
      cookie = login(broadcastId);
      bc.setCookie(cookie);
      broadcastRepository.save(bc);
    }
    //刷新cookie，确认是否可用
    cookie = refreshCookie(cookie, broadcastId);
    return cookie;
  }

  /**
   * 发起post请求
   * @param broadcastId 广播设备ID
   * @param volume  音量参数
   * @return  操作结果
   */
  public boolean senPostRequest(BigInteger broadcastId, int volume, String cookie) {
    boolean flag = false;
    String relayIP = findRelayIP(broadcastId);
    if (relayIP == null) {
      return false;
    }
    BigInteger[] broadcastIds = {broadcastId};
    String url = String.format("http://%s/TermVolSet", relayIP);
    //构建请求体
    JSONObject obj = new JSONObject();
    obj.put("TermIds", broadcastIds);
    obj.put("Volume", volume);
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //构建请求
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
        if (remark.equals("OK")) {
          flag = true;
        }
      } else {
        logger.error("Failed to set volume, please check if arguments are correct.");
      }
      execute.close();
    } catch (Exception e) {
      logger.error("Failed send a post request, " + e.getMessage());

    }
    return flag;
  }

  /**
   * 查找设备（根据ID）
   * @param broadcastId 设备ID
   * @return  设备对象
   */
  public Broadcast findBroadcast(BigInteger broadcastId) {
    Optional<Broadcast> tempBC = broadcastRepository.findById(broadcastId);
    return tempBC.orElse(null);
  }

  /**
   * 音量控制器
   * @param broadcastId 设备ID
   * @param volume  音量
   * @return  JSON对象（反馈给前端的响应体）
   */
  public Broadcast volumeExecutor(BigInteger broadcastId, Integer volume) {
    Broadcast bc = findBroadcast(broadcastId);
    if (bc == null) {
      logger.error("broadcast not found: " + broadcastId);
      return null;
    }
    //获取cookie
    String cookie = findCookie(broadcastId);
    if (cookie == null) {
      logger.error("cookie not found");
      return null;
    }
    //执行操作
    boolean flag = senPostRequest(broadcastId, volume, cookie);
    if (flag) {
      //执行成功，更新数据
      bc.setVolume(volume);
      try {
        return broadcastRepository.save(bc);
      } catch (Exception e) {
        logger.warn("save data failed: " +  e.getMessage());
        return null;
      }
    } else {
      logger.error("failed to set volume on the relay server");
      return null;
    }
  }
}
