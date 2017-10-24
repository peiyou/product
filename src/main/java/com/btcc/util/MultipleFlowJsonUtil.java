package com.btcc.util;

import com.btcc.constant.ReportConstant;
import com.btcc.web.base.mapper.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MultipleFlowJsonUtil {

    private static Logger logger = LoggerFactory.getLogger(MultipleFlowJsonUtil.class);

    public final static String name = "name";
    public final static String applicationParam="applicationParam";
    public final static String paramsName = "paramsName";
    public final static String sqlFlow = "sqlFlow";
    public final static String databaseName = "databaseName";
    public final static String type = "type";//insert / delete / update / select
    public final static String sql = "sql";
    public final static String resultType="resultType";// int / list
    public final static String condition = "condition";
    public final static String next = "next";
    /**
     * obj对应的是sqlFlow中的一个json对象
     *
     * @param obj
     * @return
     */
    public static Map<String,Object> getCurrentSqlBuild(String obj){
        Map<String,Object> map = new HashMap<>();
        JSONObject jsonObject = JSONObject.fromObject(obj);
        String database = jsonObject.getString(databaseName);
        String typeStr = jsonObject.getString(type);
        String sqlStr = jsonObject.getString(sql);
        String resultTypeStr = jsonObject.containsKey(resultType)?
                jsonObject.getString(resultType):null;
        String conditionStr = jsonObject.containsKey(condition)?
                jsonObject.getString(condition):null;
        String nextStr = jsonObject.containsKey(next)?
                jsonObject.getString(next):null;

        map.put(databaseName,database);
        map.put(type,typeStr);
        map.put(sql,sqlStr);
        if(resultTypeStr != null)
            map.put(resultType,resultTypeStr);
        if(conditionStr != null)
            map.put(condition,conditionStr);
        if(nextStr != null)
            map.put(next,nextStr);

        return map;
    }


    /**
     * 动态流程式的SQL运行
     * @param obj
     * @param paramsJson
     */
    public static void flowJsonSql(String obj,JSONObject paramsJson){
        JSONObject jsonObject = JSONObject.fromObject(obj);
        String[] names = null;
        Map<String,Object> paramsMap = new HashMap<>();//全局变量
        if(jsonObject.containsKey(applicationParam)){
            String params = jsonObject.getString(applicationParam);
            String paramName = JSONObject.fromObject(params).getString(paramsName);
            JSONArray jsonArray = JSONArray.fromObject(paramName);
            names = new String[jsonArray.size()];
            for(int i=0;i<jsonArray.size();i++){
                names[i] = jsonArray.getString(i);
            }
            paramsMap = getApplicationParams(names,paramsJson);
        }

        JSONArray sqlFlows = JSONArray.fromObject(jsonObject.getString(sqlFlow));
        for(int i=0;i<sqlFlows.size();i++){
            logger.info("----------------------------------------------------------");
            Map<String,Object> map = MultipleFlowJsonUtil.getCurrentSqlBuild(sqlFlows.getString(i));
            MultipleFlowJsonUtil.flow(map,paramsMap);
        }
    }

    private static Map<String,Object> getApplicationParams(String[] names,JSONObject paramsJson){
        Map<String,Object> map = new HashMap<>();
        for(String name:names){
            if(paramsJson.containsKey(name)){
                map.put(name,paramsJson.getString(name));
            }else{
                new RuntimeException("name is "+name+" of parameter is empty");
            }
        }
        return map;
    }

    public static void flow(Map<String,Object> map,Map<String,Object> params){

        String typeStr = map.get(type).toString();

        if(typeStr.equals("select")){
            executionSelect(map, params);
        }else if(typeStr.equals("insert")){
            //运行sql
            String sqlStr = map.get(sql).toString();
            String dbaStr = map.get(databaseName).toString();
            MultipleFlowJsonUtil.insertData(sqlStr,dbaStr,params);
            boolean conditionFlag = map.containsKey(condition);
            whichNext(map, params, conditionFlag);

        }else if(typeStr.equals("delete")){
            //运行sql
            String sqlStr = map.get(sql).toString();
            String dbaStr = map.get(databaseName).toString();
            MultipleFlowJsonUtil.deleteData(sqlStr,dbaStr,params);
            boolean conditionFlag = map.containsKey(condition);
            whichNext(map, params, conditionFlag);
        }else if(typeStr.equals("update")){
            //运行sql
            String sqlStr = map.get(sql).toString();
            String dbaStr = map.get(databaseName).toString();
            MultipleFlowJsonUtil.updateData(sqlStr,dbaStr,params);
            boolean conditionFlag = map.containsKey(condition);
            whichNext(map, params, conditionFlag);
        }
    }

    private static void whichNext(Map<String, Object> map, Map<String, Object> params, boolean conditionFlag) {
        if(conditionFlag){//有条件判断时
            String conditionStr = map.get(condition).toString();
            String nextStr = map.get(next).toString();
            JSONArray nextJSON = JSONArray.fromObject(nextStr);
            //条件判断，执行第一个next还是第二个next
            boolean whichFlag = MultipleFlowJsonUtil.execution(conditionStr,params);
            if(whichFlag){//如果是真，则执行第一个
                Map<String,Object> nextMap = MultipleFlowJsonUtil.getCurrentSqlBuild(nextJSON.getString(0));
                MultipleFlowJsonUtil.flow(nextMap,params);

            }else{//如果是假，则执行第二个
                Map<String,Object> nextMap = MultipleFlowJsonUtil.getCurrentSqlBuild(nextJSON.getString(1));
                MultipleFlowJsonUtil.flow(nextMap,params);
            }
        }else{//无条件判断，说明是直线运行，无分支
            if(map.containsKey(next)){//存在下一个
                String nextStr = map.get(next).toString();
                JSONArray nextJSON = JSONArray.fromObject(nextStr);
                for(int i=0;i<nextJSON.size();i++) {
                    Map<String, Object> nextMap = MultipleFlowJsonUtil.getCurrentSqlBuild(nextJSON.getString(i));
                    MultipleFlowJsonUtil.flow(nextMap, params);
                }
            }
        }
    }

    private static void executionSelect(Map<String, Object> map, Map<String, Object> params) {
        //运行sql
        String sqlStr = map.get(sql).toString();
        String dbaStr = map.get(databaseName).toString();
        List<Map<String,Object>> maps = MultipleFlowJsonUtil.getDataResult(sqlStr,dbaStr,params);
        boolean conditionFlag = map.containsKey(condition);
        if(maps == null) return;
        for(int i=0;i<maps.size();i++){
            Map<String,Object> tempMap = maps.get(i);
            Map<String,Object> tempParams = new  HashMap<>();

            if(params!= null){
                tempParams.putAll(params);
            }
            if(tempMap != null)
                tempParams.putAll(tempMap);

            whichNext(map, tempParams, conditionFlag);
        }
    }


    public static boolean execution(String condition,Map<String,Object> params){
        try {
            Boolean obj = (Boolean) Ognl.getValue(condition, params);
            return obj;
        }catch (OgnlException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public static List<Map<String,Object>> getDataResult(String sql,String dbaName,Map<String,Object> params){
        params.put("run_sql",sql);
        if((ReportConstant.report).equals(dbaName)){
            return SpringUtils.getBean(ReportDatabaseSourceMapper.class).queryData(params);
        }else if((ReportConstant.btcchina_mk).equals(dbaName)){
            return SpringUtils.getBean(MkDatabaseSourceMapper.class).queryData(params);
        }else if((ReportConstant.btcchina_phpbb).equals(dbaName)){
            return SpringUtils.getBean(PhpbbDatabaseSourceMapper.class).queryData(params);
        }else if(ReportConstant.prog.equals(dbaName)){
            return SpringUtils.getBean(ProgDatabaseSourceMapper.class).queryData(params);
        }else if(ReportConstant.spotusd.equals(dbaName)){
            return SpringUtils.getBean(SpotusdDatabaseSourceMapper.class).queryData(params);
        }else if(ReportConstant.bttx.equals(dbaName)){
            return SpringUtils.getBean(BttxDatabaseSourceMapper.class).queryData(params);
        }else{
            return SpringUtils.getBean(MkDatabaseSourceMapper.class).queryData(params);
        }
    }

    public static void insertData(String sql,String dbaName,Map<String,Object> params){
        params.put("run_sql",sql);
        if((ReportConstant.report).equals(dbaName)){
            SpringUtils.getBean(ReportDatabaseSourceMapper.class).insertData(params);
        }else if((ReportConstant.btcchina_mk).equals(dbaName)){
            logger.error("No permission to modify this database.");
            throw new RuntimeException("No permission to modify this database.");
        }
    }

    public static void updateData(String sql,String dbaName,Map<String,Object> params){
        params.put("run_sql",sql);
        if((ReportConstant.report).equals(dbaName)){
            SpringUtils.getBean(ReportDatabaseSourceMapper.class).updateData(params);
        }else if((ReportConstant.btcchina_mk).equals(dbaName)){
            logger.error("No permission to modify this database.");
            throw new RuntimeException("No permission to modify this database.");
        }
    }

    public static void deleteData(String sql,String dbaName,Map<String,Object> params){
        params.put("run_sql",sql);
        if((ReportConstant.report).equals(dbaName)){
            SpringUtils.getBean(ReportDatabaseSourceMapper.class).deleteData(params);
        }else if((ReportConstant.btcchina_mk).equals(dbaName)){
            logger.error("No permission to modify this database.");
            throw new RuntimeException("No permission to modify this database.");
        }
    }
}
