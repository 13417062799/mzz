package com.distlab.hqsms.screen.plan;

import com.alibaba.fastjson.JSONObject;
import com.distlab.hqsms.edge.DeviceService;
import com.distlab.hqsms.screen.content.ScreenContent;
import com.distlab.hqsms.screen.content.ScreenContentRepository;
import com.distlab.hqsms.screen.device.Screen;
import com.distlab.hqsms.screen.device.ScreenRepository;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;

@Service
public class ScreenPlanService {
  public volatile Scheduler scheduler;
  private static final Logger logger = LoggerFactory.getLogger(ScreenPlanService.class);

  @Autowired
  ScreenRepository screenRepository;
  @Autowired
  ScreenPlanRepository screenPlanRepository;
  @Autowired
  DeviceService deviceService;
  @Autowired
  ScreenContentRepository screenContentRepository;
  @Value("${hqsms.edge.screen.enable}")
  private Boolean edgeScreenEnable;

  //创建调度器工厂

  /**
   * 获得Scheduler 实例
   */
  public Scheduler getScheduler() {
    Scheduler s = scheduler;
    if (s == null) {
      synchronized (this) {
        s = scheduler;
        if (s == null) {
          //双重检查
          try {
            SchedulerFactory sf = new StdSchedulerFactory();
            s = scheduler = sf.getScheduler();
          } catch (Exception e) {
            logger.error("Get scheduler error : " + e.getMessage());
          }
        }
      }
    }
    return s;
  }


  //新代码

  /**
   * 新增任务服务方法
   */
  //新增计划方法
  public ScreenPlan add(BigInteger screenId, String cronExpr, String tempEndDate, String planName, BigInteger contentId) {
    //转日期格式
    Date endDate = null;
    if (tempEndDate != null) {
      try {
        endDate = ScreenPlanUtils.endDateFormat(tempEndDate);
      } catch (Exception e) {
        logger.error("Date format is incorrect, " + e.getMessage());
        return null;
      }
    }
    //获取plan对象
    ScreenPlan scp = genScreenPlan(screenId, cronExpr, endDate, planName);
    //调用调度器
    try {
      addPlan2Scheduler(scp, contentId);
      try {
        //调度器启动成功，保存数据
        ScreenContent scc = genContentWithPlanId(scp.getId(), contentId);
        screenContentRepository.save(scc);
        screenPlanRepository.save(scp);
        return scp;
      } catch (Exception e) {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  //生成ScreenPlan对象
  public ScreenPlan genScreenPlan(
    BigInteger screenId, String cronExpr, Date endDate, String planName
  ) {
    //新建ScreenPlan
    ScreenPlan scp = new ScreenPlan();
    // 获取分布式ID
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.PLAN);
    if (id.equals(BigInteger.valueOf(-1))) {
      return scp;
    }
    scp.setId(id);
    scp.setContentId(screenId);
    scp.setPlanName(planName);
    scp.setCron(cronExpr);
    scp.setJobName(ScreenPlanUtils.genJobName(scp.getId()));
    scp.setJobClassName(ScreenJob.class.getName());
    scp.setEnable(true);
    if (endDate != null) {
      scp.setEndDate(endDate);
    }
    scp.setCreatedAt(new Date());

    return scp;
  }

  //生成包含计划ID的ScreenContent对象
  public ScreenContent genContentWithPlanId(BigInteger planId, BigInteger contentId) throws Exception {
    ScreenContent scc = new ScreenContent();
    Optional<ScreenContent> tempSCC = screenContentRepository.findById(contentId);
    if (!tempSCC.isPresent()) {
      logger.error("Content does not exist, please check if the contentId is correct.");
    } else {
      // 获取分布式ID
      BigInteger id = deviceService.getLeafId(DeviceService.LeafType.SCREEN_CONTENT);
      if (id.equals(BigInteger.valueOf(-1))) {
        return scc;
      }
      ScreenContent oldSCC = tempSCC.get();
      scc.setId(id);
      scc.setScreenId(planId);
      scc.setPlayed(oldSCC.isPlayed());
//            scc.setResolution(oldSCC.getResolution());
      scc.setType(oldSCC.getType());
      scc.setSize(oldSCC.getSize());
      scc.setContentIndex(oldSCC.getContentIndex());
      scc.setCreatedAt(oldSCC.getCreatedAt());
    }

    return scc;
  }

  //获取内容名称
  public String getScreenContentName(BigInteger contentId) {
    String contentName = null;
    Optional<ScreenContent> tempSCC = screenContentRepository.findById(contentId);
    if (!tempSCC.isPresent()) {
      logger.warn("Content does not exist, please check if the contentId is correct.");
    } else {
      ScreenContent scc = tempSCC.get();
      contentName = scc.getContentIndex();
    }
    return contentName;
  }

  //获取设备ip
  public String getScreenIp(BigInteger screenId) {
    String ip = null;
    Optional<Screen> tempSC = screenRepository.findById(screenId);
    if (!tempSC.isPresent()) {
      logger.error("Device does not exist, please check if the screenId is correct.");
    } else {
      Screen sc = tempSC.get();
      ip = sc.getIp();
    }
    return ip;
  }

  //安排新计划
  @SuppressWarnings({"unchecked"})
  public void addPlan2Scheduler(ScreenPlan scp, BigInteger contentId) throws Exception {
    JobKey jobKey = ScreenPlanUtils.genCronJobKey(scp);//生成jobKey
    TriggerKey triggerKey = ScreenPlanUtils.genCronTriggerKey(scp);//生成triggerKey
    String cronExpr = scp.getCron();//获取cron表达式
    Class<?> jobClass = Class.forName(scp.getJobClassName().trim());
    Date endDate = scp.getEndDate();

    String ip = getScreenIp(scp.getContentId());
    String contentName = getScreenContentName(contentId);
    JobDataMap jobDataMap = new JobDataMap();
    if (ip != null && contentName != null) {
      jobDataMap.put("ip", ip);
      jobDataMap.put("contentName", contentName);
    } else {
      logger.error("Method addPlan2Scheduler arguments are invalid.");
      return;
    }

    //获取调度器实例
    Scheduler scheduler = this.getScheduler();

    //创建工作实例
    JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) jobClass)
      .withIdentity(jobKey)
      .setJobData(jobDataMap)
      .build();

    //创建触发器
    CronTrigger trigger;
    if (CronExpression.isValidExpression(cronExpr)) {
      if (endDate != null) {
        trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
          .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr)
            .withMisfireHandlingInstructionDoNothing())
          .endAt(endDate)
          .build();
      } else {
        trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
          .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr)
            .withMisfireHandlingInstructionDoNothing())
          .build();
      }
    } else {
      logger.error("CronExpression is invalid.");
      return;
    }

    //调度器
    scheduler.scheduleJob(jobDetail, trigger);
    scheduler.start();//启动
  }

  /**
   * 更新计划服务方法
   */
  //更新计划方法
  public ScreenPlan update(BigInteger planId, String cronExpr, Boolean enable) {
    try {
      return setAndRefresh(planId, cronExpr, enable);
    } catch (Exception e) {
      return null;
    }
  }

  //判断及更新调度器
  @Transactional
  @SuppressWarnings({"unchecked"})
  public ScreenPlan setAndRefresh(BigInteger planId, String cronExpr, Boolean enable) throws Exception {
    //旧计划
    ScreenPlan old = findOnePlan(planId);//根据ID查找数据库的旧计划
    TriggerKey triggerKey = ScreenPlanUtils.genCronTriggerKey(old);//获取旧计划的triggerKey
    JobKey jobKey = ScreenPlanUtils.genCronJobKey(old);//获取旧计划的jobKey

    Scheduler scheduler = this.getScheduler();//获取调度器

    //如果不同代表Cron表达式已被修改
    if (!old.getCron().equals(cronExpr)) {
      //更新trigger
      CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
        .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr)
          .withMisfireHandlingInstructionDoNothing()) //新的cron表达式
        .build();
      //更新调度器
      try {
        scheduler.rescheduleJob(triggerKey, newTrigger);
      } catch (Exception e) {
        logger.error("RescheduleJob error, " + e.getMessage());
        return null;
      }
    }

    //新旧计划状态不同
    if (!old.getEnable().equals(enable)) {
      if (!enable) {
        //新计划状态为0，即需更新旧计划状态
        try {
          scheduler.pauseJob(jobKey);
        } catch (Exception e) {
          logger.error("Failed to pause the schedule, " + e.getMessage());
          return null;
        }
      } else {
        //新计划状态为1，即启用
        Trigger curTrigger = scheduler.getTrigger(triggerKey);

        if (curTrigger == null) {
          //触发器为空，即调度器没有创建改计划
          Class<?> jobClass = Class.forName(old.getJobClassName().trim()); //获取旧计划的工作类
          //获取dataMap参数
          Optional<Screen> tempSC = screenRepository.findById(old.getContentId());
          if (!tempSC.isPresent()) {
            logger.error("Device of the plan does not exist.");
            return null;
          }
          Screen sc = tempSC.get();
          if (sc.getIp().isEmpty()) {
            logger.error("Can not get device ip.");
            return null;
          }

          Optional<ScreenContent> tempSCC = screenContentRepository.findById(planId);
          if (!tempSCC.isPresent()) {
            logger.error("Content of the plan does not exist.");
            return null;
          }
          ScreenContent scc = tempSCC.get();
          if (scc.getContentIndex().isEmpty()) {
            logger.error("can not get content's name.");
            return null;
          }
          //创建dataMap
          JobDataMap jobDataMap = new JobDataMap();
          jobDataMap.put("ip", sc.getIp());
          jobDataMap.put("contentName", scc.getContentIndex());

          //新建jobDetail
          JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) jobClass)
            .withIdentity(jobKey)
            .setJobData(jobDataMap)
            .build();
          //新建trigger
          CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr)
              .withMisfireHandlingInstructionDoNothing())
            .build();
          //安排到调度器
          try {
            scheduler.scheduleJob(jobDetail, newTrigger);
          } catch (Exception e) {
            logger.error("Failed to schedule the plan after the trigger was generated. " + e.getMessage());
            return null;
          }
        } else {
          //触发器非空，更新trigger
          CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr)//添加新计划的cron表达式
              .withMisfireHandlingInstructionDoNothing())
            .build();
          //调度器更新
          try {
            scheduler.rescheduleJob(triggerKey, newTrigger);
          } catch (Exception e) {
            logger.error("Failed to reschedule the trigger after the trigger was updated. " + e.getMessage());
            return null;
          }
        }
      }
      //更新数据库
      old.setCron(cronExpr);
      old.setEnable(enable);
      try {
        return screenPlanRepository.save(old);
      } catch (Exception e) {
        logger.warn("Failed to save data of update plan. " + e.getMessage());
        return null;
      }
    }
    return null;
  }

  //更新ScreenPlan对象
//    public ScreenPlan updateScreenPlan(BigInteger planId, String cronExpr, Boolean enable) {
//        ScreenPlan scp = new ScreenPlan();
//        Optional<ScreenPlan> tempSCP = screenPlanRepository.findById(planId);
//        if (!tempSCP.isPresent()) {
//            log.error("Plan does not exist, please check if the planId is correct.");
//            return null;
//        } else {
//            ScreenPlan oldSCP = tempSCP.get();
//            scp.set
//            if (cronExpr != null) {
//                scp.setCron(cronExpr);
//            }
//            if (enable != null) {
//                scp.setEnable(enable);
//            }
//        }
//        return scp;
//    }

  //获取ScreenPlan对象
  public ScreenPlan findOnePlan(BigInteger id) {
    ScreenPlan scp = null;
    try {
      Optional<ScreenPlan> tempSCP = screenPlanRepository.findById(id);
      if (tempSCP.isPresent()) {
        scp = tempSCP.get();
      } else {
        logger.warn("Can not find the screenPlan by ID, please check if the ID is correct.");
      }
    } catch (Exception e) {
      logger.error("Failed to find the screenPlan, maybe it's not unique, please check the database");
    }

    return scp;
  }

  //暂时未使用
//    public Iterable<ScreenPlan> findAll() {
//        return screenPlanRepository.findAll();
//    }

  /**
   * 删除计划服务方法
   */
  //删除计划方法
  public ScreenPlan delete(BigInteger planId) {
    ScreenPlan scp = findOnePlan(planId);

    try {
      unSchedule(scp);
      try {
        screenPlanRepository.delete(scp);
        return scp;
      } catch (Exception e) {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  //取消调度器
  public void unSchedule(ScreenPlan plan) throws Exception {
    TriggerKey triggerKey = ScreenPlanUtils.genCronTriggerKey(plan);
    JobKey jobKey = ScreenPlanUtils.genCronJobKey(plan);
    Scheduler scheduler = this.getScheduler();
    //删除
    scheduler.pauseTrigger(triggerKey);//暂停触发器
    scheduler.unscheduleJob(triggerKey);//取消工作
    scheduler.deleteJob(jobKey);//删除工作
  }

  /**
   * 初始化计划服务方法
   */
  //初始化
  @PostConstruct
  public void init() {
    if (edgeScreenEnable) {
      Scheduler scheduler = this.getScheduler();
      if (scheduler == null) {
        logger.error("Failed to initialize the plan component, scheduler is null");
        return;
      }

      //初始化计划列表
      try {
        initCronJobs(scheduler);
      } catch (Exception e) {
        logger.error("Failed to initialize plan, " + e.getMessage());
      }

      //启动调度器
      try {
        logger.info("The scheduler is starting...");
        scheduler.start();
      } catch (Exception e) {
        logger.error("Failed to start scheduler, " + e.getMessage());
      }
    }
  }

  //初始化所有工作
  private void initCronJobs(Scheduler scheduler) throws Exception {
    Iterable<ScreenPlan> planList = screenPlanRepository.findAll();
    for (ScreenPlan plan : planList) {
      //安排计划到调度器
      scheduleCronJob(plan, scheduler);
    }
  }

  //安排到调度器
  private void scheduleCronJob(ScreenPlan plan, Scheduler scheduler) {
    if (
      plan != null && //计划非空
        StringUtils.isNoneBlank(plan.getJobName()) && //工作名非空
        StringUtils.isNoneBlank(plan.getJobClassName()) && //工作类非空
        StringUtils.isNoneBlank(plan.getCron()) && //cron表达式非空
        scheduler != null //调度器非空
    ) {
      //新计划是否启用
      if (!plan.getEnable()) {
        return; //未被启用
      }

      try {
        JobKey jobKey = ScreenPlanUtils.genCronJobKey(plan);//根据jobName获取jobKey

        if (!scheduler.checkExists(jobKey)) {
          //工作不存在，添加工作到调度器
          logger.info("Add new cron job to scheduler, jobName = " + plan.getJobName());
          newJobAndNewCronTrigger(plan, scheduler, jobKey);
        } else {
          //工作存在，更新到调度器
          logger.info("Update cron job to scheduler, jobName = " + plan.getJobName());
          updateCronTriggerOfJob(plan, scheduler, jobKey);
        }
      } catch (Exception e) {
        //调度器错误
        logger.error("ScheduleCronJob is error, " + e.getMessage());
      }
    } else {
      //参数错误
      logger.error("Method scheduleCronJob arguments are invalid.");
    }
  }

  //新建job和trigger
  @SuppressWarnings({"unchecked"})
  private void newJobAndNewCronTrigger(ScreenPlan plan, Scheduler scheduler, JobKey jobKey) {
    TriggerKey triggerKey = ScreenPlanUtils.genCronTriggerKey(plan); //生成新的triggerKey
    String cronExpr = plan.getCron();//获取用户新建的cron表达式;

    if (!CronExpression.isValidExpression(cronExpr)) {
      return;//cron表达式无效
    }

    try {
      //获取工作类
      Class<?> jobClass = Class.forName(plan.getJobClassName().trim());
      //创建jobDetail
      JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) jobClass)
        .withIdentity(jobKey)
        .build();
      //创建触发器
      CronTrigger trigger = TriggerBuilder.newTrigger()
        .withIdentity(triggerKey)
        .forJob(jobKey)
        .withSchedule(
          CronScheduleBuilder.cronSchedule(cronExpr)
            .withMisfireHandlingInstructionDoNothing()) //不触发立即执行,等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
        .build();
      //安排任务到调度器
      Date after = trigger.getFireTimeAfter(new Date());
      if (after != null) {
        scheduler.scheduleJob(jobDetail, trigger);
      } else {
        plan.setEnable(false);
        try {
          screenPlanRepository.save(plan);
        } catch (Exception e) {
          logger.error("Failed to update disable for overdue cron, " + e.getMessage());
        }
      }
    } catch (Exception e) {
      logger.error("Failed to schedule the plan, " + e.getLocalizedMessage());
    }
  }

  //更新job的trigger
  private void updateCronTriggerOfJob(ScreenPlan plan, Scheduler scheduler, JobKey jobKey) {
    TriggerKey triggerKey = ScreenPlanUtils.genCronTriggerKey(plan);//生成触发器
    String cronExpr = plan.getCron().trim();//获取用户新建的cron表达式

    try {
      List<? extends Trigger> oldTriggers = scheduler.getTriggersOfJob(jobKey);//获取工作的旧触发器

      for (Trigger oldTrigger : oldTriggers) {
        TriggerKey curTriggerKey = oldTrigger.getKey();//获取旧的triggerKey

        //判断两个新旧triggerKey是否一样
        if (ScreenPlanUtils.isTriggerKeyEqual(triggerKey, curTriggerKey)) {
          //新旧triggerKey一样
          if (oldTrigger instanceof CronTrigger && //是子类
            cronExpr.equalsIgnoreCase(((CronTrigger) oldTrigger).getCronExpression())//新旧cron表达忽略大小写比较
          ) {
            //新旧cron一样、不执行操作
          } else {
            //新旧cron不同，更新
            if (CronExpression.isValidExpression(cronExpr)) {
              //cron表达式有效，创建新的trigger替代旧的
              CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr)//新的cron
                  .withMisfireHandlingInstructionDoNothing())
                .build();

              //安排到任务调度器
              Date after = newTrigger.getFireTimeAfter(new Date());
              if (after != null) {
                scheduler.rescheduleJob(curTriggerKey, newTrigger);
              } else {
                plan.setEnable(false);
                try {
                  screenPlanRepository.save(plan);
                } catch (Exception e) {
                  logger.error("Failed to update disable for overdue cron, " + e.getMessage());
                }
              }
            }
          }
        } else {
          //新旧triggerKey不一样，取消旧triggerKey
          scheduler.unscheduleJob(curTriggerKey);
        }
      }
    } catch (Exception e) {
      logger.error("Failed to update trigger, " + e.getMessage());
    }
  }
}
