package com.btcc.web.task.service;

import com.btcc.task.TaskOperator;
import com.btcc.util.DateUtils;
import com.btcc.web.task.entity.ScheduleJob;
import com.btcc.web.task.mapper.TaskMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2016/11/3.
 */
@Service
public class TaskService {
    private Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    TaskOperator taskOperator;


    public ScheduleJob selectScheduleByJobId(Long jobId){
        return taskMapper.selectScheduleByJobId(jobId);
    }

    public List<ScheduleJob> selectScheduleByRun(){
        return taskMapper.selectScheduleByRun();
    }

    public PageInfo<ScheduleJob> selectScheduleAll(ScheduleJob job){
        PageInfo<ScheduleJob> page = null;
        PageHelper.startPage(job.getPageNum(),job.getPageSize());
        List<ScheduleJob> list = taskMapper.selectScheduleAll();
        page = new PageInfo<ScheduleJob>(list);
        return page;
    }

    public void insertSchedule(ScheduleJob scheduleJob){
        scheduleJob.setCreateDate(DateUtils.getNowTime(DateUtils.DATE_FULL_STR));
        scheduleJob.setUpdateDate(DateUtils.getNowTime(DateUtils.DATE_FULL_STR));
        try{
            if(ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getJobStatus())
                    || scheduleJob.getJobStatus() == ScheduleJob.STATUS_RUNNING)
                taskOperator.addJob(scheduleJob);
        }catch (SchedulerException e){

        }
        taskMapper.insertSchedule(scheduleJob);
    }

    public void updateSchedule(ScheduleJob scheduleJob){
        scheduleJob.setUpdateDate(DateUtils.getNowTime(DateUtils.DATE_FULL_STR));
        taskMapper.updateSchedule(scheduleJob);
        taskOperator.checkSchedule(this.selectScheduleByRun());
    }

    public void deleteScheduleByJobId(Long jobId){
        ScheduleJob job = taskMapper.selectScheduleByJobId(jobId);
        taskMapper.deleteScheduleByJobId(jobId);
        try{
            taskOperator.deleteJob(job);
        }catch (SchedulerException e){
            //可能任务不在列表中些处只是为了程序能正常运行下去。
        }
    }


    /**
     * 立即运行一个job
     * @param job
     * @return
     */
    public boolean nowRunScheduleByJob(ScheduleJob job){
        ScheduleJob dbJob = this.selectScheduleByJobId(job.getJobId());
        dbJob.setTempRunStatus(job.isTempRunStatus());
        try {
            boolean flag = false;
            try {
                flag = taskOperator.isExistJob(dbJob);
            }catch (SchedulerException e){
                logger.error(e.getMessage());
            }
            if(flag){
                taskOperator.deleteJob(dbJob);
            }
            taskOperator.addJob(dbJob);
            taskOperator.runAJobNow(dbJob);
        }catch (SchedulerException e ){
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
}
