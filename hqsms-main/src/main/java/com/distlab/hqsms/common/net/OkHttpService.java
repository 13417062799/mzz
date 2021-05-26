package com.distlab.hqsms.common.net;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class OkHttpService {

  private static final Logger log = LoggerFactory.getLogger(OkHttpService.class);
  private final String NOT_NULL = "must not be null";

  /**
   * 构建OkHttpClient
   * @return okHttpClient
   */
  public OkHttpClient okHttpClientBuilder() {
    return new OkHttpClient.Builder()
      .connectTimeout(30, TimeUnit.SECONDS) //连接超时
      .readTimeout(30, TimeUnit.SECONDS)  //读取超时
      .writeTimeout(30, TimeUnit.SECONDS) //写入超时
      .callTimeout(30, TimeUnit.SECONDS)  //呼叫超时
      .retryOnConnectionFailure(false) //失败重连1
      .build();
  }


  //EXECUTE 同步执行

  /**
   * 网络请求同步执行
   * @param request 请求（非空）
   * @return  boolean
   */
  public boolean doExecute(Request request) {
    if (request == null) {
      log.info("request " +NOT_NULL);
      return false;
    }
    OkHttpClient okHttpClient = okHttpClientBuilder();
    try {
      Response execute = okHttpClient.newCall(request).execute();

      if (!execute.isSuccessful()) {
        log.error("execute error: failed to execute");
        try {
          execute.close();
        } catch (Exception e) {
          log.warn("failed to close okHttpClient. " + e.getMessage());
        }
        return false;
      }

      try {
        execute.close();
      } catch (Exception e) {
        log.warn("failed to close okHttpClient. " + e.getMessage());
      }
      return true;

    } catch (IOException e) {
      log.error("network error: " + e.getMessage());
      return false;
    }
  }

  /**
   * 网络请求同步执行并获取响应体
   * @param request 请求（非空）
   * @return  操作结果及响应体
   */
  public String doExecuteAndGetBody(Request request) {
    if (request == null) {
      log.info("request " +NOT_NULL);
      return null;
    }
    OkHttpClient okHttpClient = okHttpClientBuilder();
    try {
      Response execute = okHttpClient.newCall(request).execute();

      if (!execute.isSuccessful()) {
        log.error("failed to execute");
        try {
          execute.close();
        } catch (Exception e) {
          log.warn("failed to close okHttpClient. " + e.getMessage());
        }
        return null;
      }
      if (execute.body() == null) {
        return null;
      }

      String bodyInString = execute.body().string();
      try {
        execute.close();
      } catch (Exception e) {
        log.warn("failed to close okHttpClient. " + e.getMessage());
      }
      return bodyInString;

    } catch (IOException e) {
      log.error("network error: " + e.getMessage());
      return null;
    }
  }

  /**
   * 网络请求同步执行并获取响应头
   * @param request 请求（非空）
   * @return  操作结果及响应头
   */
  public String doExecuteAndGetHeaders(Request request) {
    if (request == null) {
      log.info("request " +NOT_NULL);
      return null;
    }
    OkHttpClient okHttpClient = okHttpClientBuilder();
    try {
      Response execute = okHttpClient.newCall(request).execute();

      if (execute.isSuccessful()) {
        log.info("failed to execute");
        try {
          execute.close();
        } catch (Exception e) {
          log.warn("failed to close okHttpClient. " + e.getMessage());
        }
        return null;
      }

      String headersInString = execute.headers().toString();
      try {
        execute.close();
      } catch (Exception e) {
        log.warn("failed to close okHttpClient. " + e.getMessage());
      }
      return headersInString;

    } catch (IOException e) {
      log.info("network error: " + e.getMessage());
      return null;
    }
  }

  //POST

  /**
   * 发起post网络请求
   * @param url url
   * @param requestBody 请求体
   * @return  boolean
   */
  public boolean postRequest(String url, RequestBody requestBody) {

    Request request = new Request.Builder()
      .url(url)
      .post(requestBody)
      .build();

    return doExecute(request);
  }

  /**
   * 发起post网络请求
   * @param url url
   * @param requestBody 请求体
   * @param cookie  cookie
   * @return  boolean
   */
  public boolean postRequest(String url, RequestBody requestBody, String cookie) {

    Request request = new Request.Builder()
      .url(url)
      .addHeader("Cookie", cookie)
      .post(requestBody)
      .build();

    return doExecute(request);
  }

  /**
   * 发起post网络请求
   * @param url url
   * @param requestBody 请求体
   * @return  操作结果及响应体
   */
  public String postRequestAndGetBody(String url, RequestBody requestBody) {

    Request request = new Request.Builder()
      .url(url)
      .post(requestBody)
      .build();

    return doExecuteAndGetBody(request);
  }

  /**
   * 发起post网络请求
   * @param url url
   * @param requestBody 请求体
   * @param cookie  cookie
   * @return  操作结果及响应体
   */
  public String postRequestAndGetBody(String url, RequestBody requestBody, String cookie) {

    Request request = new Request.Builder()
      .url(url)
      .addHeader("Cookie", cookie)
      .post(requestBody)
      .build();

    return doExecuteAndGetBody(request);
  }

  /**
   * 发起post网络请求
   * @param url url
   * @param requestBody 请求体
   * @return  操作结果及响应头
   */
  public String postRequestAndGetHeaders(String url, RequestBody requestBody) {

    Request request = new Request.Builder()
      .url(url)
      .post(requestBody)
      .build();

    return doExecuteAndGetHeaders(request);
  }

  //PUT

  /**
   * 发起put网络请求
   * @param url url
   * @param requestBody 请求体
   * @return  操作结果
   */
  public boolean putRequest(String url, RequestBody requestBody) {
    Request request = new Request.Builder()
      .url(url)
      .put(requestBody)
      .build();

    return doExecute(request);
  }

  /**
   * 发起put网络请求
   * @param url url
   * @return    操作结果
   */
  public boolean putRequest(String url) {
    Request request = new Request.Builder()
      .url(url)
      .method("PUT", RequestBody.create(null, new byte[0]))
      .build();

    return doExecute(request);
  }

  //DELETE

  /**
   * 发起delete网络请求
   * @param url url
   * @return  操作结果
   */
  public boolean deleteRequest(String url) {
    Request request = new Request.Builder()
      .url(url)
      .delete()
      .build();

    return doExecute(request);
  }

  public String queryRequest(HttpUrl.Builder builder) {
    Request.Builder requestBuilder = new Request.Builder();
    requestBuilder.url(builder.build());
    Request request = requestBuilder.build();

    return doExecuteAndGetBody(request);
  }
}
