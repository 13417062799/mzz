package com.distlab.hqsms.screen.plan;

import com.distlab.hqsms.screen.content.ScreenContentService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenJob implements Job {
  private static final Logger log = LoggerFactory.getLogger(ScreenJob.class);

  @Autowired
  ScreenContentService screenContentService;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    //输出当前时间
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateString = df.format(date);

    //获取参数
    String ip = context.getJobDetail().getJobDataMap().getString("ip");
    String contentName = context.getJobDetail().getJobDataMap().getString("contentName");

    //工作内容
    screenContentService.contentSwitcher(ip, contentName);
    log.info("Perform scheduled plan, contentName: " + contentName + ", execution time: " + dateString);
  }
}
