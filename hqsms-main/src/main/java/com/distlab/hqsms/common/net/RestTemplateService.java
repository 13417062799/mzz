package com.distlab.hqsms.common.net;

import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateService {

  @Autowired
  RestTemplate restTemplate;

  public <T> WebResponse<T> exchange(String remoteUrl, HttpMethod method, @Nullable HttpEntity<?> entity, ParameterizedTypeReference<WebResponse<T>> typeRef) {
    WebResponse<T> remoteRet;
    try {
      remoteRet = restTemplate.exchange(remoteUrl, method, entity, typeRef).getBody();
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_REMOTE_OPERATE_FAILED, e.getCause().getLocalizedMessage());
    }
    if (remoteRet == null) {
      return WebResponse.fail(WebResponseEnum.SYS_INNER_ERROR, MessageUtil.REMOTE_RESULTS_NOT_FOUND);
    }
    if(remoteRet.getData() == null) {
      return WebResponse.fail(WebResponseEnum.SYS_INNER_ERROR, "remote data not found");
    }
    return remoteRet;
  }
}
