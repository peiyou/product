package com.btcc.web.task.mapper;

import com.btcc.datasource.ReportDataSource;
import com.btcc.web.task.entity.ScheduleJob;

import java.util.List;

/**
 * Created by peiyou on 2016/11/3.
 */
@ReportDataSource
public interface TaskMapper {

    ScheduleJob selectScheduleByJobId(Long jobId);

    /**
     * 只查询要执行的
     * @return
     */
    List<ScheduleJob> selectScheduleByRun();

    /**
     * 查询所有的任务
     * @return
     */
    List<ScheduleJob> selectScheduleAll();

    void insertSchedule(ScheduleJob scheduleJob);

    void updateSchedule(ScheduleJob scheduleJob);

    void deleteScheduleByJobId(Long jobId);
}
