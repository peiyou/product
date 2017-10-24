package com.btcc.schedule;

import com.btcc.util.DateUtils;
import com.btcc.web.report.mk.service.ReportService;
import com.btcc.web.trade.service.ApiTradeService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by peiyou on 2017/7/14.
 */
@Component
public class ApiTradeSchedule {

    private Logger logger = LoggerFactory.getLogger(ApiTradeSchedule.class);

    private String historyDay = "2015-01-01";

    @Autowired
    ApiTradeService apiTradeService;

    @Autowired
    ReportService reportService;

    public void yesterdayApiTradeBtcByDay(){
        String end = DateUtils.dateFormat(new Date(),DateUtils.DATE_SMALL_STR);
        String begin = DateUtils.addDate(end,-1,DateUtils.DATE_SMALL_STR);
        Map<String,Object> map = new HashMap();
        map.put("beginDate",begin);
        map.put("endDate",end);
        map.put("type","btc");
        map.put("market","cnybtc");
        reportService.deleteUserApiTradeDataByDay(map);
        List<Map<String,Object>> list = apiTradeService.queryApiTradeBtcByDay(map);
        reportService.insertUserApiTradeData(list);
    }

    public String historyApiTradeBtcByDay(String obj){
        try{
            JSONObject jsonObject = JSONObject.fromObject(obj);
            String beginDate = jsonObject.getString("beginDate");
            String endDate = jsonObject.getString("endDate");
            String date = DateUtils.dateFormat(new Date(),DateUtils.DATE_SMALL_STR);
            if(endDate.compareTo(date) > 0){
                return obj;
            }
            Map<String,Object> map = new HashMap();
            map.put("beginDate",beginDate);
            map.put("endDate",endDate);
            map.put("type","btc");
            map.put("market","cnybtc");
            reportService.deleteUserApiTradeDataByDay(map);
            List<Map<String,Object>> list = apiTradeService.queryApiTradeBtcByDay(map);
            reportService.insertUserApiTradeData(list);
            beginDate = endDate;
            endDate = DateUtils.addMonth(beginDate,1,DateUtils.DATE_SMALL_STR);
            jsonObject.put("beginDate",beginDate);
            jsonObject.put("endDate",endDate);
            logger.info("ApiTradeSchedule of method historyApiTradeBtcByDay run succeed!");
            return jsonObject.toString();
        }catch (Exception e){
            logger.error("",e);
        }
        return obj;
    }

    public void yesterdayApiTradeLtcByDay(){
        String end = DateUtils.dateFormat(new Date(),DateUtils.DATE_SMALL_STR);
        String begin = DateUtils.addDate(end,-1,DateUtils.DATE_SMALL_STR);
        Map<String,Object> map = new HashMap();
        map.put("beginDate",begin);
        map.put("endDate",end);
        map.put("type","ltc");
        map.put("market","cnyltc");
        reportService.deleteUserApiTradeDataByDay(map);
        List<Map<String,Object>> list = apiTradeService.queryApiTradeLtcByDay(map);
        reportService.insertUserApiTradeData(list);
        logger.info("ApiTradeSchedule of method yesterdayApiTradeLtcByDay run succeed!");
    }

    public String historyApiTradeLtcByDay(String obj){
        try{
            String date = DateUtils.dateFormat(new Date(),DateUtils.DATE_SMALL_STR);
            JSONObject jsonObject = JSONObject.fromObject(obj);
            String beginDate = jsonObject.getString("beginDate");
            String endDate = jsonObject.getString("endDate");
            if(endDate.compareTo(date) > 0){
                return obj;
            }
            Map<String,Object> map = new HashMap();
            map.put("beginDate",beginDate);
            map.put("endDate",endDate);
            map.put("type","ltc");
            map.put("market","cnyltc");
            reportService.deleteUserApiTradeDataByDay(map);
            List<Map<String,Object>> list = apiTradeService.queryApiTradeLtcByDay(map);
            reportService.insertUserApiTradeData(list);
            beginDate = endDate;
            endDate = DateUtils.addMonth(beginDate,1,DateUtils.DATE_SMALL_STR);
            jsonObject.put("beginDate",beginDate);
            jsonObject.put("endDate",endDate);

            logger.info("ApiTradeSchedule of method historyApiTradeLtcByDay run succeed!");
            return jsonObject.toString();

        }catch (Exception e){
            logger.error("",e);
        }
        return obj;
    }

}
