package com.btcc.task;

import com.btcc.web.task.entity.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by peiyou on 2016/11/3.
 */
@Component
public class TaskOperator {
    Logger log = LoggerFactory.getLogger(TaskOperator.class);
    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 更改任务状态
     *
     * @throws SchedulerException
     */
    public void changeStatus(ScheduleJob scheduleJob, String cmd) throws SchedulerException {
        if (scheduleJob == null) {
            return;
        }
        if ("stop".equals(cmd)) {
            deleteJob(scheduleJob);
            scheduleJob.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
        } else if ("start".equals(cmd)) {
            scheduleJob.setJobStatus(ScheduleJob.STATUS_RUNNING);
            addJob(scheduleJob);
        }
    }

    /**
     * 更改任务 cron表达式
     *
     * @throws SchedulerException
     */
    public void updateCron(ScheduleJob scheduleJob, String cron) throws SchedulerException {
        if (scheduleJob == null) {
            return;
        }
        scheduleJob.setCronExpression(cron);
        if (ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
            updateJobCron(scheduleJob);
        }

    }


    public boolean isExistJob(ScheduleJob scheduleJob) throws SchedulerException{
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        return trigger == null ? false : true;
    }

    /**
     * 添加任务
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void addJob(ScheduleJob scheduleJob) throws SchedulerException {
        //非特殊添加任务时，要做检查，特殊时，跳过这一检查过程
        //特殊时主要指在页面上点击了运行按钮
        if(!scheduleJob.isTempRunStatus())
            if (scheduleJob == null || !ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
                return;
            }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        log.debug(scheduler + ".......................................................................................add");
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 不存在，创建一个
        if (null == trigger) {
            Class clazz = ScheduleJob.CONCURRENT_IS.equals(scheduleJob.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;

            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();

            jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

            trigger = TriggerBuilder.newTrigger().withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            jobDetail.getJobDataMap().put("scheduleJob",scheduleJob);

        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    public List<ScheduleJob> getAllJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJob> jobList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                ScheduleJob job = new ScheduleJob();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setTriggerState(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public List<ScheduleJob> getRunningJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJob job = new ScheduleJob();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setDescription("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(Integer.parseInt(triggerState.name()));
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.deleteJob(jobKey);

    }

    /**
     * 立即执行job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        scheduler.rescheduleJob(triggerKey, trigger);
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        jobDetail.getJobDataMap().put("scheduleJob",scheduleJob);
    }


    /**
     * 用于检测任务是否做过修改
     */
    public  void checkSchedule(List<ScheduleJob> jobList){

        try {
            List<ScheduleJob> jobAll = this.getAllJob();
            //比较任务是否存在，是否做过修改
            for(ScheduleJob dbJob : jobList){
                boolean flag = false;
                for(ScheduleJob taskJob : jobAll){
                    flag = check(dbJob,taskJob);
                    if(flag){
                        break;
                    }
                }
                if(!flag){
                    try {
                        this.addJob(dbJob);
                        log.info("jobGroup:"+dbJob.getJobGroup() + ",jobName:"+dbJob.getJobName()+",cron:"+dbJob.getCronExpression()+" add success....");
                    }catch (Exception e){
                        log.error("jobGroup:"+dbJob.getJobGroup() + ",jobName:"+dbJob.getJobName()+",cron:"+dbJob.getCronExpression()+" add failed....");
                    }
                }
            }

            for(ScheduleJob taskJob : jobAll){
                boolean flag = false;
                for(ScheduleJob dbJob : jobList){
                    if(dbJob.getJobName().equals(taskJob.getJobName())
                            && dbJob.getJobGroup().equals(taskJob.getJobGroup())
                            ){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    try {
                        this.deleteJob(taskJob);
                        log.info("jobGroup:"+taskJob.getJobGroup() + ",jobName:"+taskJob.getJobName()+",cron:"+taskJob.getCronExpression()+" remove success....");
                    }catch (Exception e){
                        log.error("jobGroup:"+taskJob.getJobGroup() + ",jobName:"+taskJob.getJobName()+",cron:"+taskJob.getCronExpression()+"remove failed....");
                    }
                }
            }
        }catch (SchedulerException e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean check(ScheduleJob dbJob,ScheduleJob taskJob){
        if(dbJob.getJobName().equals(taskJob.getJobName())
                && dbJob.getJobGroup().equals(taskJob.getJobGroup())
                ){
            if(!dbJob.getCronExpression().equals(taskJob.getCronExpression())){//更新了调度时间
                try {
                    this.updateJobCron(dbJob);
                    log.info("jobGroup:"+dbJob.getJobGroup() + ",jobName:"+dbJob.getJobName()+",cron:"+dbJob.getCronExpression()+" update success....");
                }catch (SchedulerException e){
                    log.error(e.getMessage());
                }
            }
            return true;
        }
        return false;
    }
}
