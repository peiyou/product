package com.btcc.web.base.schedule;

import com.btcc.util.DateUtils;
import com.btcc.util.MultipleFlowJsonUtil;
import com.btcc.web.system.entity.WorkFlowSql;
import com.btcc.web.system.service.SystemService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by peiyou on 17/1/17.
 */
@Component
public class BaseSchedule {

    private static final String baseScheduleWorkFlowSqlId = "baseScheduleWorkFlowSqlId";
    @Autowired
    SystemService systemService;

    private Logger logger = LoggerFactory.getLogger(BaseSchedule.class);

    /**
     * 有参有返回
     * @param objstr
     * @return
     */
    public String baseScheduleForDateParams(String objstr){
        JSONObject obj = JSONObject.fromObject(objstr);
        try{
            int workFlowId = obj.getInt(baseScheduleWorkFlowSqlId);
            if(obj.containsKey("startDate") && obj.containsKey("endDate")) {
               // int workFlowId = obj.getInt(baseScheduleWorkFlowSqlId);
                logger.info("baseScheduleWorkFlowSqlId is " + workFlowId + " task start...");
                WorkFlowSql workFlowSql = systemService.queryWorkFlowSqlById((long) workFlowId);
                MultipleFlowJsonUtil.flowJsonSql(workFlowSql.getData(), obj);
                logger.info("baseScheduleWorkFlowSqlId is " + workFlowId + " task succeed！");
                String startDate = obj.getString("startDate");
                String endDate = obj.getString("endDate");
                int diff = DateUtils.diffDate(endDate,startDate);
                obj.put("startDate",endDate);
                obj.put("endDate",DateUtils.addDate(endDate,diff,DateUtils.DATE_SMALL_STR));
                return obj.toString();
            }else{
                logger.error("baseScheduleWorkFlowSqlId for the task of "+workFlowId + " is not in conformity with the rules.");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return objstr;
    }

    /**
     * 有参无返回
     */
    public void baseScheduleForParams(String objstr){
        try {
            JSONObject obj = JSONObject.fromObject(objstr);
            int workFlowId = obj.getInt(baseScheduleWorkFlowSqlId);
            logger.info("baseScheduleWorkFlowSqlId is " + workFlowId + " task start...");
            WorkFlowSql workFlowSql = systemService.queryWorkFlowSqlById((long) workFlowId);
            MultipleFlowJsonUtil.flowJsonSql(workFlowSql.getData(), obj);
            logger.info("baseScheduleWorkFlowSqlId is " + workFlowId + " task succeed！");
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            JSONObject obj = JSONObject.fromObject(objstr);
            logger.info("baseScheduleWorkFlowSqlId is " + obj.getInt(baseScheduleWorkFlowSqlId) + " task failed！");
        }
    }
}
