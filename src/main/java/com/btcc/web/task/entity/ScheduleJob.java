package com.btcc.web.task.entity;

import com.btcc.util.BaseBean;

import java.util.Date;

/**
 * Created by peiyou on 2016/11/3.
 */
public class ScheduleJob extends BaseBean {
    public static final Integer STATUS_RUNNING = 1;
    public static final Integer STATUS_NOT_RUNNING = 0;
    public static final Integer CONCURRENT_IS = 1;
    public static final Integer CONCURRENT_NOT = 0;
    //任务需要参数
    public static final Integer IS_PARAM = 1;

    private Long jobId;

    private String createDate;

    private String updateDate;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务状态 是否启动任务
     */
    private Integer jobStatus;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 描述
     */
    private String description;
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    private String beanClass;
    /**
     * 任务是否有状态
     */
    private Integer isConcurrent;

    /**
     * 任务调用的方法名
     */
    private String methodName;

    /**
     * 取定时任务中的task时，可能用到的状态
     */
    private String triggerState;

    /**
     * 任务是否需要参数
     */
    private Integer isParam;

    /**
     * 任务的参数列表，只能传入一个json的参数，string类型的
     */
    private String jsonParam;

    /**
     * 任务的开始时间，与cron一起用，
     * 不填写时，表示马上开始
     */
    private Long startTime;

    /**
     * 任务的结束时间，与cron一起用，
     * 不填写时，表示一直不结束
     */
    private Long endTime;

    /**
     * 临时的状态，用于页面中的立即运行
     * 默认为非特殊情况
     */
    private boolean tempRunStatus = false;


    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public Integer getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(Integer isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public Integer getIsParam() {
        return isParam;
    }

    public void setIsParam(Integer isParam) {
        this.isParam = isParam;
    }

    public String getJsonParam() {
        return jsonParam;
    }

    public void setJsonParam(String jsonParam) {
        this.jsonParam = jsonParam;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public boolean isTempRunStatus() {
        return tempRunStatus;
    }

    public void setTempRunStatus(boolean tempRunStatus) {
        this.tempRunStatus = tempRunStatus;
    }

    public void resetTempRunStatus(){
        this.tempRunStatus = false;
    }
}
