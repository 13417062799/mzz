package com.distlab.hqsms.common;

import org.springframework.lang.Nullable;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
public class CustomTaskScheduler extends ThreadPoolTaskScheduler {

  private static final long serialVersionUID = 1L;
  private final Map<String, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

  @Nullable
  private volatile ErrorHandler errorHandler;

  /**
   * 取消定时器的执行
   *
   */
  public void cancelTask(String methodName) {
    ScheduledFuture<?> scheduledFuture = scheduledTasks.get(methodName);
    if (null != scheduledFuture) {
      scheduledFuture.cancel(true);
    }
  }

  @Override
  public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
    ScheduledFuture<?> future = super.schedule(task, trigger);

    scheduledTasks.put(task.toString(), future);
    return future;
  }

  private Runnable errorHandlingTask(Runnable task, boolean isRepeatingTask) {
    return TaskUtils.decorateTaskWithErrorHandler(task, this.errorHandler, isRepeatingTask);
  }
}
