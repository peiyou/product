package com.btcc.web.report.mk.schedule;

import com.btcc.constant.ReportConstant;
import com.btcc.util.DateUtils;
import com.btcc.web.report.mk.entity.BusinessFrom;
import com.btcc.web.report.mk.entity.BusinessReportDay;
import com.btcc.web.report.mk.service.ReportService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by peiyou on 2016/10/31.
 */
@Component
public class MkScheduledTask {

    @Autowired
    ReportService reportService;
    private Logger logger = LoggerFactory.getLogger(MkScheduledTask.class);
    public void statisticsBusinessReportDay(){
        //获取表中最大的统计日期
        String lastMaxDate = reportService.queryMaxStatisDateForBusinessReportDay();
        //设置默认开始时间
        if(lastMaxDate == null) lastMaxDate = ReportConstant.DEFAULT_STATIS_DATE;
        //得到本次统计的最小时间，即查询的开始时间
        String startDate = DateUtils.addDate(lastMaxDate,1,DateUtils.DATE_SMALL_STR);
        //得到当前时间减一天（T-1）
        String lastDate = DateUtils.addDate(new Date(),-1,DateUtils.DATE_SMALL_STR);
        logger.info("statistics date ："+"startDate:"+startDate +"\t lastDate：" + lastDate);
        //开始时间小于T-1天时，才会运行这个
        if(startDate.compareTo(lastDate) <= 0){
            BusinessFrom from = new BusinessFrom();
            from.setStartDate(startDate);
            from.setEndDate(lastDate);
            Map<String,BusinessReportDay> mapDays = reportService.getBusinessReportDay(from);
            logger.info("data handle finished......");
        }
        logger.info("schedule task success....");
    }

    /**
     * 带参数的调度
     *
     * @param objstr 有两个参数 startDate  表示开始时间   lastDate 表示结束时间
     */
    public String statisticsBusinessReportDayByParam(String objstr){
        JSONObject obj = JSONObject.fromObject(objstr);
        //得到本次统计的最小时间，即查询的开始时间
        String startDate = obj.getString("startDate");
        //得到当前时间减一天（T-1）
        String lastDate = obj.getString("lastDate");
        logger.info("statistics date ："+"startDate:"+startDate +"\t lastDate：" + lastDate);
        //开始时间小于T-1天时，才会运行这个
        if(startDate.compareTo(lastDate) <= 0
                && startDate.compareTo(DateUtils.dateFormat(new Date(),DateUtils.DATE_SMALL_STR)) < 0){
            BusinessFrom from = new BusinessFrom();
            from.setStartDate(startDate);
            from.setEndDate(lastDate);
            Map<String,BusinessReportDay> mapDays = reportService.getBusinessReportDay(from);
            logger.info("data handle finished ......");
            int diff = DateUtils.diffDate(lastDate,startDate);
            obj.put("startDate",DateUtils.addDate(lastDate,1,DateUtils.DATE_SMALL_STR));
            obj.put("lastDate",DateUtils.addDate(lastDate,1+diff,DateUtils.DATE_SMALL_STR));
        }
        logger.info("schedule task success....");
        return obj.toString();
    }


    public void snapshotUser(String objstr){
        JSONObject obj = JSONObject.fromObject(objstr);
        String userIds = obj.getString("userIds");
        logger.info("user id : " + userIds);
        if(userIds == null){
            return;
        }
        try{
            reportService.deleteUserSnapshot();
            reportService.insertUserSnapshot(userIds);
            logger.info("userIds:"+userIds+" snapshot data handle finish，please see it!");
        }catch (Exception e){
            logger.error("snapshot data is handle error,please quickly handle it !");
            e.printStackTrace();
        }

    }
}
