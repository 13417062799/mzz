package com.distlab.hqsms.broadcast.plan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.distlab.hqsms.broadcast.content.BroadcastContentService;
import com.distlab.hqsms.broadcast.device.Broadcast;
import com.distlab.hqsms.broadcast.device.BroadcastRepository;
import com.distlab.hqsms.broadcast.device.BroadcastService;
import com.distlab.hqsms.edge.DeviceService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BroadcastPlanService {
  @Autowired
  BroadcastService broadcastService;
  @Autowired
  BroadcastRepository broadcastRepository;
  @Autowired
  BroadcastPlanRepository broadcastPlanRepository;
  @Autowired
  BroadcastContentService broadcastContentService;
  @Autowired
  DeviceService deviceService;

  private static final Logger logger = LoggerFactory.getLogger(BroadcastContentService.class);
  private final MediaType mediaType = MediaType.parse("application/json; charset = utf-8");
  private final String INCORRECT = "may be incorrect";
  private final String CHECK_DATABASE = "please check database";

  OkHttpClient okHttpClient = new OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .retryOnConnectionFailure(true)
    .build();

  //CREATE

  /**
   * EveryDay对象属性
   * @param every 每几天执行一次
   * @return  JSON对象
   */
  public JSONObject everyDayJSON(int every) {
    JSONObject obj = new JSONObject();
    obj.put("Every", every);
    return obj;
  }

  /**
   * EveryWeek对象属性
   * @param every 每几周
   * @param daysInWeek  整型数组，一周中的有效天（1-7）
   * @return  JSON对象
   */
  public JSONObject everyWeekJSON(int every, int[] daysInWeek) {
    JSONObject obj = new JSONObject();
    obj.put("Every", every);
    obj.put("DaysInWeek", daysInWeek);
    return obj;
  }

  /**
   * EveryMonth对象属性
   * @param monthsInYear  整型数组，一年中的有效月（1-12）
   * @param dayInMonth  整型数组（maxsize=2）；数组size=1时，代表每月中的第几天（1-31）；数组size=2时，代表第几个星期几，第一数据取值（1-5），第二数据取值（1-7）
   * @return  JSON对象
   */
  public JSONObject everyMonthJSON(int[] monthsInYear, int[] dayInMonth) {
    JSONObject obj = new JSONObject();
    obj.put("MonthsInYear", monthsInYear);
    obj.put("DayInMonth", dayInMonth);
    return obj;
  }


  /**
   * 构建创建任务请求体（中继服务器）
   * @param bcpInfo 计划信息对象
   * @param planId  计划ID（-1-新建任务，正常ID-更新任务）
   * @param broadcastIds  设备ID数组
   * @param programIds  节目ID数组
   * @return  请求体
   */
  public RequestBody buildPlanRequestBody(BroadcastPlanInfo bcpInfo, BigInteger planId, int taskId, BigInteger[] broadcastIds, int[] programIds) {
    //提取参数
    String tempPlanName = bcpInfo.getPlanName();
    String planName = tempPlanName + "_" + planId;
    int type = bcpInfo.getType();
    int[] daysInWeek = bcpInfo.getDaysInWeek();
    int[] monthsInYear = bcpInfo.getMonthsInYear();
    int[] dayInMonth = bcpInfo.getDayInMonth();
    String startTime = bcpInfo.getStartTime();
    String endDate = bcpInfo.getEndDate();
    int repeatTime = bcpInfo.getRepeatTime();
    int playMode = bcpInfo.getPlayMode();
    Integer playVol = bcpInfo.getPlayVol();
    Integer status = bcpInfo.getStatus();
    Integer enable = bcpInfo.getEnable();
    //构建请求体
    JSONObject taskInfo = new JSONObject();
    taskInfo.put("ID", taskId); //-1-新建任务
    taskInfo.put("Name", planName);
    taskInfo.put("Type", type);
    switch (type) {
      case 1 :
        taskInfo.put("DayItem", everyDayJSON(bcpInfo.getEvery()));
        break;
      case 2 :
        taskInfo.put("WeekItem", everyWeekJSON(bcpInfo.getEvery(), daysInWeek));
        break;
      case 3 :
        taskInfo.put("MonthItem", everyMonthJSON(monthsInYear, dayInMonth));
      case 4 :
        break;
      default:
        return null;  //参数输入错误，返回null
    }
    taskInfo.put("StartTime", startTime);
    taskInfo.put("EndDate", endDate);
    taskInfo.put("RepeatTime", repeatTime);
    taskInfo.put("PlayMode", playMode);
    if (playVol != null && playVol > -1 && playVol < 57) {
      taskInfo.put("PlayVol", playVol);
    }
    taskInfo.put("Tids", broadcastIds);
    taskInfo.put("ProgIds", programIds);
    if (status != null && (status == 1 || status == 0)) {
      taskInfo.put("Status", status);
    }
    if (enable != null && (enable == 1 || enable == 0)) {
      taskInfo.put("Enable", enable);
    }
    JSONObject obj = new JSONObject();
    obj.put("Item", taskInfo);
    return RequestBody.create(mediaType, obj.toJSONString());
  }

  /**
   * 新增任务（中继服务器）
   * @param requestBody 请求体
   * @param relayIP 中继服务器IP
   * @param cookie  cookie
   * @return  任务ID（中继服务器生成，区别于计划ID）
   */
  public int createTask(RequestBody requestBody, String relayIP, String cookie) {
    int taskId = -1;
    if (requestBody == null) {
      return -1;
    }
    //构建POST请求
    String url = String.format("http://%s/TaskCreate", relayIP);
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
        taskId = reBody.getIntValue("TaskID");
      } else {
        logger.error("Failed to get plan info, please check if arguments are correct.");
      }
      execute.close(); //关闭call
    } catch (Exception e) {
      logger.error("Failed send post request, " + e.getMessage());
    }
    return taskId;
  }

  /**
   * 规范音量（若用户未设置，则提取设备默认音量）
   * @param playVol 计划音量
   * @param broadcastId 设备ID
   * @return  播放音量
   */
  public Integer normalizeVolume(Integer playVol, BigInteger broadcastId) {
    Integer volume;
    volume = playVol;
    if (volume == null) {
      Optional<Broadcast> tempBC = broadcastRepository.findById(broadcastId);
      if (tempBC.isPresent()) {
        Broadcast bc = tempBC.get();
        volume = bc.getVolume();
      }
    }
    return volume;
  }

  /**
   * 格式化开始日期字符串
   * @param dateTimeInString  开始日期字符串
   * @return  开始日期 (Date)
   */
  public Date formatStartTime(String dateTimeInString) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      return df.parse(dateTimeInString);
    } catch (Exception e) {
      logger.error("Failed to parse startTime, please check if the date is correct. " + e.getMessage());
      return null;
    }
  }

  /**
   * 格式化结束日期字符串
   * @param dateInString  结束日期字符串
   * @return  结束日期 (Date)
   */
  public Date formatEndDate(String dateInString) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return df.parse(dateInString);
    } catch (Exception e) {
      logger.error("Failed to parse startTime, please check if the date is correct. " + e.getMessage());
      return null;
    }
  }

  /**
   * 构建周期信息字符串
   * @param bcpInfo 计划信息对象
   * @return  周期信息
   */
  public String buildPeriodString(BroadcastPlanInfo bcpInfo) {
    String reString = null;
    int type = bcpInfo.getType();
    switch (type) {
      case 1:
        reString = "每日任务：每" + bcpInfo.getEvery() + "执行一次";
        break;
      case 2:
        reString = "每周任务：每" + bcpInfo.getEvery() + "周的周" + Arrays.toString(bcpInfo.getDaysInWeek()) + "执行";
        break;
      case 3:
        int[] dayInMonth = bcpInfo.getDayInMonth();
        if (dayInMonth.length == 1) {
          reString = "每月任务：" + Arrays.toString(bcpInfo.getMonthsInYear()) + "月的第" + dayInMonth[0] + "天执行";
        }
        if (dayInMonth.length == 2) {
          reString = "每月任务：" + Arrays.toString(bcpInfo.getMonthsInYear()) + "月的第" + dayInMonth[0] + "周周" + dayInMonth[1] + "执行";
        }
        break;
      case 4:
        reString = "一次性任务";
        break;
      default:
        break;
    }
    return reString;
  }

//  public JSONObject checkPlanInfo(BroadcastPlanInfo bcpInfo) {
//    JSONObject reObj = new JSONObject();
//    //提取内容ID
//    BigInteger contentId = bcpInfo.getContentId();
//    if (contentId == null) {
//      reObj.put("Message", "Failed, caused by: contentId can not be null.");
//      return reObj;
//    }
//    //提取计划名称
//    String tempPlanName = bcpInfo.getPlanName();
//    if (tempPlanName == null) {
//      reObj.put("Message", "Failed, caused by: planName can not be null.");
//      reObj.put("ContentId", contentId);
//      return reObj;
//    }
//    //提取计划类型
//    int type = bcpInfo.getType();
//    if (type != 1 && type != 2 && type != 3 && type != 4) {
//      reObj.put("Message", "Failed, caused by: type is incorrect, it must be 1 or 2 or 3 or 4.");
//      reObj.put("ContentId", contentId);
//      return reObj;
//    }
//    //提取开始时间
//    String startTimeInString = bcpInfo.getStartTime();
//    Date startTime = formatStartTime(startTimeInString);
//    if (startTime == null) {
//      reObj.put("Message", "Failed, caused by: startTime is incorrect.");
//      reObj.put("ContentId", contentId);
//      return reObj;
//    }
//    //提取重复次数
//    int repeatTime = bcpInfo.getRepeatTime();
//    if (repeatTime == 0) {
//      reObj.put("Message", "Failed, caused by: repeatTime must be >0.");
//      reObj.put("ContentId", contentId);
//      return reObj;
//    }
//    reObj.put("Remark", "OK");
//    return reObj;
//  }

  /**
   * 保存计划数据到数据库
   * @param bcpInfo 计划信息对象
   * @param planId  计划ID
   * @param taskId  任务ID（中继服务器生成，区别于计划ID）
   * @param broadcastId 设备ID（用于查找默认音量）
   * @return  操作结果
   */
  public BroadcastPlan savePlanData(BroadcastPlanInfo bcpInfo, BigInteger planId, int taskId, BigInteger broadcastId) {
    BroadcastPlan p = new BroadcastPlan();
    p.setId(planId);
    p.setContentId(bcpInfo.getContentId());
    p.setTaskId(taskId);
    p.setPlanName(bcpInfo.getPlanName() + "_" + planId);
    Integer planVol = normalizeVolume(bcpInfo.getPlayVol(), broadcastId);
    p.setPlanVol(planVol);
    p.setPlayMode(bcpInfo.getPlayMode());
    p.setRepeatTime(bcpInfo.getRepeatTime());
    Date startTime = formatStartTime(bcpInfo.getStartTime());
    p.setStartTime(startTime);
    if (bcpInfo.getEndDate() != null) {
      Date endDate = formatEndDate(bcpInfo.getEndDate());
      if (endDate != null) {
        p.setEndDate(endDate);
      }
    }
    p.setPeriod(buildPeriodString(bcpInfo));
    if (bcpInfo.getStatus() != null) {
      p.setPlayed(bcpInfo.getStatus() == 1);
    }
    if (bcpInfo.getEnable() != null) {
      p.setEnabled(bcpInfo.getEnable() == 1);
    }
    p.setCreatedAt(new Date());

    try {
      return broadcastPlanRepository.save(p);
    } catch (Exception e) {
      logger.error("Failed to save plan data, " + e.getMessage());
    }
    return null;
  }

  /**
   * 新增计划，保存数据
   * @param bcpInfo 计划信息对象
   * @return  JSON对象（反馈给前端的响应体）
   */
  public BroadcastPlan createPlan(BroadcastPlanInfo bcpInfo) {
    //获取设备ID
    BigInteger contentId = bcpInfo.getContentId();
    BigInteger broadcastId = broadcastContentService.findBroadcastId(contentId);
    if (broadcastId.equals(BigInteger.valueOf(-1))) {
      logger.error("broadcastId not found, contentId " + INCORRECT);
      return null;
    }
    //获取中继服务器IP
    String relayIP = broadcastService.findRelayIP(broadcastId);
    if (relayIP == null) {
      logger.error("relayIp not found, " + CHECK_DATABASE);
      return null;
    }
    //获取节目ID
    int programId = broadcastContentService.findProgramId(contentId);
    if (programId == -1) {
      logger.error("programId not found, " + CHECK_DATABASE);
      return null;
    }
    //获取cookie
    String cookie = broadcastService.findCookie(broadcastId);
    if (cookie == null) {
      logger.error("cookie not found, please check relay server");
      return null;
    }
    // 获取分布式ID
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.PLAN);
    if (id.equals(BigInteger.valueOf(-1))) {
      return null;
    }
    //格式化参数
    BigInteger[] broadcastIds = {broadcastId};
    int[] programIds = {programId};
    //构建请求体
    RequestBody requestBody = buildPlanRequestBody(bcpInfo, id, -1,  broadcastIds, programIds);
    //发起创建任务请求
    int taskId = createTask(requestBody, relayIP, cookie);
    if (taskId == -1) {
      logger.error("failed to create plan on the relay server, please check the relay server");
      return null;
    }
    return savePlanData(bcpInfo, id, taskId, broadcastId);
  }


  //UPDATE

  /**
   * 更新任务（中继服务器）
   * @param requestBody 请求体
   * @param relayIP 中继服务器IP
   * @param cookie  cookie
   * @return  操作结果
   */
  public boolean updateTask(RequestBody requestBody, String relayIP, String cookie) {
    boolean flag = false;
    //构建请求
    String url = String.format("http://%s/TaskUpdate", relayIP);
    Request request = new Request.Builder()
      .url(url)
      .addHeader("Cookie", cookie)
      .post(requestBody)
      .build();
    //发起网络请求
    try {
      Response execute = okHttpClient.newCall(request).execute();
      if (execute.isSuccessful() && execute.body() != null) {
        String bodyInString = execute.body().string();
        JSONObject reBody = JSON.parseObject(bodyInString);
        String remark = reBody.getString("Remark");
        if (remark.contains("OK")) {
          flag = true;
        }
      } else {
        logger.error("Failed to get plan info, please check if arguments are correct.");
      }
      execute.close(); //关闭call
    } catch (Exception e) {
      logger.error("Failed send post request, " + e.getMessage());
    }
    return flag;
  }

  /**
   * 确认计划是否存在
   * @param planId  计划ID
   * @return  计划对象
   */
  public BroadcastPlan findPlan(BigInteger planId) {
    Optional<BroadcastPlan> tempBCP = broadcastPlanRepository.findById(planId);
    return tempBCP.orElse(null);
  }

  /**
   * 更新计划数据
   * @param bcpInfo 计划信息对象
   * @param bcp 原计划对象
   * @return  操作结果
   */
  public BroadcastPlan updatePlanData(BroadcastPlanInfo bcpInfo, BroadcastPlan bcp) {
    bcp.setContentId(bcpInfo.getContentId());
    bcp.setPlanName(bcpInfo.getPlanName() + "_" + bcp.getId());
    if (bcpInfo.getPlayVol() != null) {
      bcp.setPlanVol(bcpInfo.getPlayVol());
    }
    bcp.setPlayMode(bcpInfo.getPlayMode());
    bcp.setRepeatTime(bcpInfo.getRepeatTime());
    Date startTime = formatStartTime(bcpInfo.getStartTime());
    bcp.setStartTime(startTime);
    if (bcpInfo.getEndDate() != null) {
      Date endDate = formatEndDate(bcpInfo.getEndDate());
      if (endDate != null) {
        bcp.setEndDate(endDate);
      }
    }
    bcp.setPeriod(buildPeriodString(bcpInfo));
    if (bcpInfo.getStatus() != null) {
      bcp.setPlayed(bcpInfo.getStatus() == 1);
    }
    if (bcpInfo.getEnable() != null) {
      bcp.setEnabled(bcpInfo.getEnable() == 1);
    }
    bcp.setUpdatedAt(new Date());

    try {
      return broadcastPlanRepository.save(bcp);
    } catch (Exception e) {
      logger.error("Failed to update plan data, " + e.getMessage());
    }
    return null;
  }

  /**
   * 更新计划
   * @param bcpInfo 计划信息对象
   * @param planId  计划ID
   * @return  JSON对象（反馈给前端的响应体）
   */
  public BroadcastPlan updatePlan(BroadcastPlanInfo bcpInfo, BigInteger planId) {
    BroadcastPlan bcp = findPlan(planId);
    if (bcp == null) {
      logger.error("plan not found, planId " + INCORRECT);
      return null;
    }
    int taskId = bcp.getTaskId();
    if (taskId == -1 || taskId == 0) {
      logger.error("taskId not found, " + CHECK_DATABASE);
      return null;
    }
    //获取内容ID
    BigInteger contentId = bcpInfo.getContentId();
    //获取设备ID
    BigInteger broadcastId = broadcastContentService.findBroadcastId(contentId);
    if (broadcastId.equals(BigInteger.valueOf(-1))) {
      logger.error("broadcastId not found, contentId " + INCORRECT);
      return null;
    }
    //获取节目ID
    int programId = broadcastContentService.findProgramId(contentId);
    if (programId == -1) {
      logger.error("programId not found, " + CHECK_DATABASE);
      return null;
    }
    //获取cookie
    String cookie = broadcastService.findCookie(broadcastId);
    if (cookie == null) {
      logger.error("cookie not found, please check relay server");
      return null;
    }
    //获取中继服务器IP
    String relayIP = broadcastService.findRelayIP(broadcastId);
    if (relayIP == null) {
      logger.error("relayIp not found, " + CHECK_DATABASE);
      return null;
    }
    //格式化参数
    BigInteger[] broadcastIds = {broadcastId};
    int[] programIds = {programId};
    //构建请求体
    RequestBody requestBody = buildPlanRequestBody(bcpInfo, planId, taskId, broadcastIds, programIds);
    //发起更新任务请求
    boolean flag = updateTask(requestBody, relayIP, cookie);
    if (flag) {
      //操作成功，更新数据
      return updatePlanData(bcpInfo, bcp);
    } else {
      //操作失败，反馈响应
      logger.error("failed to update plan on the relay server");
      return null;
    }
  }

  /**
   * 发起post连接
   * @param url  功能url
   * @param requestBody 请求体
   * @param cookie  cookie
   * @return  操作结果
   */
  public boolean sendPostRequest(String url, RequestBody requestBody, String cookie) {
    boolean flag = false;
    //构建请求体
    Request request = new Request.Builder()
      .url(url)
      .addHeader("Cookie", cookie)
      .post(requestBody)
      .build();
    //发起网络请求
    try {
      Response execute = okHttpClient.newCall(request).execute();
      if (execute.isSuccessful() && execute.body() != null) {
        String bodyInString = execute.body().string();
        JSONObject reBody = JSON.parseObject(bodyInString);
        String remark = reBody.getString("Remark");
        if (remark.contains("OK")) {
          flag = true;
        }
      } else {
        logger.error("Failed to set, please check if arguments are correct.");
      }
      execute.close();//关闭call
    } catch (Exception e) {
      logger.error("Failed to send post request, " + e.getMessage());
    }
    return flag;
  }

  /**
   * 计划控制器
   * @param taskId  任务ID
   * @param command 控制命令
   * @param relayIP 中继服务器IP
   * @param cookie  cookie
   * @param bcp 计划对象
   * @return  JSON对象（反馈给前端的响应体）
   */
  public BroadcastPlan playExecutor(int taskId, String command, String relayIP, String cookie, BroadcastPlan bcp) {
    //构建请求体
    JSONObject obj = new JSONObject();
    obj.put("TaskID", taskId);
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    String url = null;
    if (command.equals("start")) {
      url = String.format("http://%s/TaskManualStart", relayIP);
    }
    if (command.equals("stop")) {
      url = String.format("http://%s/TaskManualStop", relayIP);
    }
    //发起网络请求
    boolean flag = sendPostRequest(url, requestBody, cookie);
    if (flag) {
      //执行成功，更新数据
      bcp.setPlayed(command.equals("start"));
      try {
        return broadcastPlanRepository.save(bcp);
      } catch (Exception e) {
        logger.info("set status successfully, but failed to update data. " + e.getMessage());
        return null;
      }
    } else {
      logger.info("failed to set status on the relay server");
      return null;
    }
  }

  /**
   * 计划冻结控制器
   * @param taskId  任务ID
   * @param command 控制命令
   * @param relayIP 中继服务器IP
   * @param cookie  cookie
   * @param bcp 计划对象
   * @return  JSON对象（反馈给前端的响应体）
   */
  public BroadcastPlan frozenExecutor(int taskId, String command, String relayIP, String cookie, BroadcastPlan bcp) {
    int[] taskIds = {taskId};
    RequestBody requestBody = buildFroRequestBody(taskIds, command);
    String url = String.format("http://%s/TaskEnable", relayIP);
    //发起网络请求
    boolean flag = sendPostRequest(url, requestBody, cookie);
    if (flag) {
      //执行成功，更新数据
      bcp.setEnabled(command.equals("enable"));
      try {
        return broadcastPlanRepository.save(bcp);
      } catch (Exception e) {
        logger.info("set status successfully, but failed to update data. " + e.getMessage());
        return null;
      }
    } else {
      logger.info("failed to set status on the relay server");
      return null;
    }
  }

  /**
   * 构建冻结请求体
   * @param taskIds 任务ID数组
   * @param command 控制命令
   * @return  请求体
   */
  public RequestBody buildFroRequestBody(int[] taskIds, String command) {
    //构建请求体
    JSONObject obj = new JSONObject();
    if (command.equals("frozen")) {
      obj.put("Enable", 0);
    }
    if (command.equals("enable")) {
      obj.put("Enable", 1);
    }
    obj.put("TaskIDs", taskIds);

    return RequestBody.create(mediaType, obj.toJSONString());
  }

  /**
   * 计划控制器
   * @param planId  计划ID
   * @param command 控制命令
   * @return  JSON对象（反馈给前端的响应体）
   */
  public BroadcastPlan planExecutor(BigInteger planId, String command) {
    BroadcastPlan bcp = findPlan(planId);
    if (bcp == null) {
      logger.error("plan not found, " + CHECK_DATABASE);
      return null;
    }
    int taskId = bcp.getTaskId();
    if (taskId == -1 || taskId == 0) {
      logger.error("taskId not found, " + CHECK_DATABASE);
      return null;
    }
    BigInteger contentId = bcp.getContentId();
    if (contentId == null) {
      logger.error("contentId not found, " + CHECK_DATABASE);
      return null;
    }
    //获取设备ID
    BigInteger broadcastId = broadcastContentService.findBroadcastId(contentId);
    if (broadcastId == null) {
      logger.error("broadcastId not found, " + CHECK_DATABASE);
      return null;
    }
    //获取cookie
    String cookie = broadcastService.findCookie(broadcastId);
    if (cookie == null) {
      logger.error("cookie not found, please check the relay server");
      return null;
    }
    //获取中继服务器IP
    String relayIP = broadcastService.findRelayIP(broadcastId);
    if (relayIP == null) {
      logger.error("relayIp not found, " + CHECK_DATABASE);
      return null;
    }

    //执行操作
    switch (command) {
      case "start":
      case "stop":
        //播放计划操作
        return playExecutor(taskId, command, relayIP, cookie, bcp);
      case "frozen":
      case "enable":
        //冻结计划操作
        return frozenExecutor(taskId, command, relayIP, cookie, bcp);
      case "delete":
        //删除计划操作
        return deleteExecutor(taskId, relayIP, cookie, bcp);
      default:
        //命令错误
        logger.info("failed: " + command + " is incorrect.");
        return null;
    }
  }


  //DELETE

  /**
   * 计划删除控制器
   * @param taskId  任务ID
   * @param relayIP 中继服务器IP
   * @param cookie  cookie
   * @param bcp 计划对象
   * @return  JSON对象（反馈给前端的响应体）
   */
  public BroadcastPlan deleteExecutor(int taskId, String relayIP, String cookie, BroadcastPlan bcp) {
    //构建请求体
    JSONObject obj = new JSONObject();
    obj.put("TaskID", taskId);
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    String url = String.format("http://%s/TaskDelete", relayIP);
    //发起网络请求
    boolean flag = sendPostRequest(url, requestBody, cookie);
    if (flag) {
      //执行成功，删除数据
      try {
        broadcastPlanRepository.delete(bcp);
        return bcp;
      } catch (Exception e) {
        logger.info("delete plan successfully, but failed to delete data. " + e.getMessage());
        return null;
      }
    } else {
      logger.info("failed to delete plan on the relay server");
      return null;
    }
  }

//  /**
//   * 检测任务类型参数
//   * @param bcpInfo 计划信息
//   * @return  JSON对象
//   */
//  public JSONObject checkTypeParams(BroadcastPlanInfo bcpInfo) {
//    JSONObject reObj = new JSONObject();
//    int type = bcpInfo.getType();
//
//    switch (type) {
//      case 1:
//        if (bcpInfo.getEvery() == 0) {
//          reObj.put("Message", "'every' is incorrect.");
//          return reObj;
//        }
//        break;
//      case 2:
//        if (bcpInfo.getEvery() == 0) {
//          reObj.put("Message", "'every' is incorrect.");
//          return reObj;
//        }
//        if (bcpInfo.getDaysInWeek() == null) {
//          reObj.put("Message", "'daysInWeek' is incorrect.");
//          return reObj;
//        }
//        break;
//      case 3:
//        if (bcpInfo.getMonthsInYear() == null) {
//          reObj.put("Message", "'monthsInYear' is incorrect.");
//          return reObj;
//        }
//        if (bcpInfo.getDayInMonth() == null) {
//          reObj.put("Message", "'dayInMonth' is incorrect.");
//          return reObj;
//        }
//        break;
//      default:
//        break;
//    }
//    reObj.put("Remark", "OK");
//    return reObj;
//  }

  /**
   * 根据计划ID获取内容ID
   * @param planId  计划ID
   * @return  内容ID
   */
  public BigInteger findContentId(BigInteger planId) {
    Optional<BroadcastPlan> tempBCP = broadcastPlanRepository.findById(planId);
    if (!tempBCP.isPresent()) {
      return null;
    }
    BroadcastPlan bcp = tempBCP.get();
    return bcp.getContentId();
  }
}
