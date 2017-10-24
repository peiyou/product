package com.btcc.util;

import com.btcc.task.TaskOperator;
import com.btcc.web.task.entity.ScheduleJob;
import com.btcc.web.task.service.TaskService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by peiyou on 2016/11/3.
 */
public class TaskUtils {
    private static Logger logger = LoggerFactory.getLogger(TaskUtils.class);

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static void invokMethod(ScheduleJob scheduleJob) {
        //非临时任务运行时，需要做任务开始与结束的检查
        if(!scheduleJob.isTempRunStatus()) {
            //任务还没到开始时间时，跳过本次执行
            if (scheduleJob.getStartTime() != null
                    && scheduleJob.getStartTime() > DateUtils.dateToUnixTimestamp())
                return;

            //任务已到结束时间
            if (scheduleJob.getEndTime() != null
                    && scheduleJob.getEndTime() < DateUtils.dateToUnixTimestamp())
                return;
        }
        Object object = null;
        Class clazz = null;
        if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
            try {
                clazz = Class.forName(scheduleJob.getBeanClass());
                object = SpringUtils.getBean(clazz);
              //  object = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (object == null) {
            logger.error("task name = [" + scheduleJob.getJobName() + "]--------------- init is failed,please quickly handle it！！！");
            return;
        }
        clazz = object.getClass();
        Method method = null;
        try {
            if(scheduleJob.getIsParam()==ScheduleJob.IS_PARAM){
                //一个string类型的json格式的参数
                method = clazz.getDeclaredMethod(scheduleJob.getMethodName(),new Class[]{String.class});
            }else {
                method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
            }
        } catch (NoSuchMethodException e) {
            logger.error("task name = [" + scheduleJob.getJobName() + "]--------------- init is failed，method name set error,please modify it！！！");
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                if(scheduleJob.getIsParam()==ScheduleJob.IS_PARAM) {
                    TaskService taskService = SpringUtils.getBean(TaskService.class);
                    ScheduleJob dbJob =  taskService.selectScheduleByJobId(scheduleJob.getJobId());
                    Object param = method.invoke(object,dbJob.getJsonParam());
                    if(param != null){
                        scheduleJob.setJsonParam((String)param);
                        taskService.updateSchedule(scheduleJob);
                    }
                }else {
                    method.invoke(object);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if(scheduleJob.isTempRunStatus()){//如果是特殊情况运行的任务，检查是否要立即停止
            if(scheduleJob.getJobStatus().equals(ScheduleJob.STATUS_NOT_RUNNING)
                    || scheduleJob.getJobStatus() == ScheduleJob.STATUS_NOT_RUNNING){
                //重置特殊状态
                scheduleJob.resetTempRunStatus();
                TaskOperator operator = SpringUtils.getBean(TaskOperator.class);
                try {
                    operator.deleteJob(scheduleJob);
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }

        }
        System.out.println("task name = [" + scheduleJob.getJobName() + "]----------run succeed");
    }


    public static boolean checkCronExpression(String cron){
        return CronExpression.isValidExpression(cron);
    }
    /*public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.put("startDate","2016-01-01");
        obj.put("lastDate","2016-02-02");
        System.out.println(obj);
    }*/

}
