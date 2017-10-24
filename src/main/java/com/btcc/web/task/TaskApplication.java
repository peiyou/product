package com.btcc.web.task;

import com.btcc.task.TaskOperator;
import com.btcc.web.task.entity.ScheduleJob;
import com.btcc.web.task.service.TaskService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by peiyou on 2016/11/3.
 */
@Component
public class TaskApplication {
    private Logger logger = LoggerFactory.getLogger(TaskApplication.class);

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    TaskOperator taskOperator;

    @Autowired
    TaskService taskService;

    @PostConstruct
    public void init() throws Exception {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        // 这里获取任务信息数据
        List<ScheduleJob> jobList = taskService.selectScheduleByRun();
        for (ScheduleJob job : jobList) {
            taskOperator.addJob(job);
        }
    }


    /**
     * 用于检测任务是否做过修改
     */
    public void checkSchedule(){
        List<ScheduleJob> jobList = taskService.selectScheduleByRun();
        taskOperator.checkSchedule(jobList);
    }




    public void taskTestAdd(){
        System.out.println("正在执行taskTestAdd方法.....");
        try {
            Thread.sleep(10000);
            System.out.println("taskTestAdd执行完成...");
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}


