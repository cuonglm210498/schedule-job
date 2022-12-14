package com.example.quartjob.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

/**
 * @author CuongLM18
 * @created 14/09/2022 - 4:18 PM
 * @project quart-job
 */
@Slf4j
@Data
@Service
public class TaskSchedulingService {

    private final TaskScheduler taskScheduler;

    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public void scheduleATask(String jobId, Runnable tasklet, String cronExpression) {
        log.info("Scheduling task with job id: " + jobId + " and cron expression: " + cronExpression);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(tasklet, new CronTrigger(cronExpression, TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        jobsMap.put(jobId, scheduledTask);
    }

    public void removeScheduledTask(String jobId) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(jobId, null);
        }
    }
}
