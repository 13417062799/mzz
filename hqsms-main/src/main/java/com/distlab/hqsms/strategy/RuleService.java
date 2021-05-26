package com.distlab.hqsms.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;


@Service
@EnableScheduling
public class RuleService {
  public static final String LOCALHOST = "127.0.0.1";
  private static final Logger logger = LoggerFactory.getLogger(RuleService.class);
  @Value("${hqsms.edge.server.enable}")
  boolean isEdgeServerEnable;

}
