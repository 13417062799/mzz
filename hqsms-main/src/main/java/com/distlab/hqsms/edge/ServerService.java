package com.distlab.hqsms.edge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
@EnableScheduling
public class ServerService {
  public static final String LOCALHOST = "127.0.0.1";
  private static final Logger logger = LoggerFactory.getLogger(PoleService.class);
  @Autowired
  private ServerRepository serverRepository;
  @Value("${hqsms.edge.server.enable}")
  boolean isEdgeServerEnable;

  /**
   * 根据设备ID，获取灯杆所在边缘服务器信息
   * @param serverId  边缘服务器ID
   * @return  边缘服务器信息
   */
  public String getServerAddress(BigInteger serverId) {
    if (isEdgeServerEnable) {
      return LOCALHOST;
    }
    Optional<Server> server = serverRepository.findById(serverId);
    return server.map(value -> String.format("%s:%s", value.getIp(), value.getPort())).orElse(null);
  }

}
