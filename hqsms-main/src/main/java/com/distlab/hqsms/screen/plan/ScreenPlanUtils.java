package com.distlab.hqsms.screen.plan;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 任务管理模块的工具类
 */
public class ScreenPlanUtils {
  private static final Logger log = LoggerFactory.getLogger(ScreenPlanUtils.class);

  //基于cron调度的工作的默认组名
  private static final String CRON_JOB_GROUP_NAME = "cron_plan_group";

  //产生JobKey
  public static JobKey genCronJobKey(ScreenPlan job) {
    return new JobKey(job.getJobName().trim(), CRON_JOB_GROUP_NAME);
  }

  //产生TriggerKey
  public static TriggerKey genCronTriggerKey(ScreenPlan plan) {
    return new TriggerKey("trigger_" + plan.getJobName().trim(), CRON_JOB_GROUP_NAME);
  }

  //判断两个TriggerKey是否相等
  public static boolean isTriggerKeyEqual(TriggerKey k1, TriggerKey k2) {
    return k1.getName().equals(k2.getName()) &&
      ((k1.getGroup() == null && k2.getGroup() == null) || (k1.getGroup() != null && k1.getGroup().equals(k2.getGroup())));
  }

  //格式化日期
  public static Date endDateFormat(String date) throws Exception {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    return df.parse(date);
  }

  //产生JobName
  public static String genJobName(BigInteger id) {
    return "job_" + id;
  }
}
