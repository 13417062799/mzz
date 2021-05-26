package com.distlab.hqsms.edge;

import com.distlab.hqsms.common.CustomTaskScheduler;
import com.distlab.hqsms.common.GlobalConstant;
import com.distlab.hqsms.strategy.*;
import com.distlab.hqsms.weather.Weather;
import com.distlab.hqsms.weather.WeatherLog;
import com.distlab.hqsms.weather.WeatherLogRepository;
import com.distlab.hqsms.weather.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;


@Service
@EnableScheduling
public class PoleService {
  public static final String LOCALHOST = "127.0.0.1";
  private static final Logger logger = LoggerFactory.getLogger(PoleService.class);
  @Autowired
  private PoleRepository poleRepository;
  @Autowired
  private ServerRepository serverRepository;
  @Value("${hqsms.edge.server.enable}")
  boolean isEdgeServerEnable;

  /**
   * 根据设备ID，获取灯杆所在边缘服务器信息
   * @param poleId  灯杆ID
   * @return  边缘服务器信息
   */
  public String getServerAddress(BigInteger poleId) {
    if (isEdgeServerEnable) {
      return LOCALHOST;
    }
    Optional<Pole> pole = poleRepository.findById(poleId);
    if (!pole.isPresent()) {
      return null;
    }
    Optional<Server> server = serverRepository.findById(pole.get().getServerId());
    return server.map(value -> String.format("%s:%s", value.getIp(), value.getPort())).orElse(null);
  }
}
