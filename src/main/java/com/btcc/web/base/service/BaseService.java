package com.btcc.web.base.service;

import com.btcc.constant.ReportConstant;
import com.btcc.util.DateUtils;
import com.btcc.web.base.mapper.*;
import com.btcc.web.system.entity.BuildReport;
import com.btcc.web.system.service.SystemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2016/11/18.
 */
@Service
public class BaseService {

    private Logger logger = LoggerFactory.getLogger(BaseService.class);

    @Autowired
    MkDatabaseSourceMapper mkDatabaseSourceMapper;

    @Autowired
    ReportDatabaseSourceMapper reportDatabaseSourceMapper;

    @Autowired
    PhpbbDatabaseSourceMapper phpbbDatabaseSourceMapper;

    @Autowired
    ProgDatabaseSourceMapper progDatabaseSourceMapper;

    @Autowired
    SpotusdDatabaseSourceMapper spotusdDatabaseSourceMapper;

    @Autowired
    BttxDatabaseSourceMapper bttxDatabaseSourceMapper;


    public PageInfo<Map<String,Object>> queryData(Map<String,Object> param){
        long currentTime = System.currentTimeMillis();
        PageInfo<Map<String,Object>> pageInfo = null;
        if(!param.containsKey("pageNum") || param.get("pageNum")==null
                || param.get("pageNum").equals("")){
            param.put("pageNum",0);
            param.put("pageSize",0);
        }
        PageHelper.startPage(Integer.parseInt(param.get("pageNum").toString()),
                Integer.parseInt(param.get("pageSize").toString()));
        List<Map<String,Object>> list = null;
        if((ReportConstant.report).equals(param.get("datasource_run_sql"))){
            list = reportDatabaseSourceMapper.queryData(param);
        }else if((ReportConstant.btcchina_mk).equals(param.get("datasource_run_sql"))){
            list = mkDatabaseSourceMapper.queryData(param);
        }else if((ReportConstant.btcchina_phpbb).equals(param.get("datasource_run_sql"))){
            list = phpbbDatabaseSourceMapper.queryData(param);
        }else if(ReportConstant.prog.equals(param.get("datasource_run_sql"))){
            list = progDatabaseSourceMapper.queryData(param);
        }else if(ReportConstant.spotusd.equals(param.get("datasource_run_sql"))){
            list = spotusdDatabaseSourceMapper.queryData(param);
        }else if(ReportConstant.bttx.equals(param.get("datasource_run_sql"))){
            list = bttxDatabaseSourceMapper.queryData(param);
        }else {
            list = mkDatabaseSourceMapper.queryData(param);
        }

        pageInfo = new PageInfo<>(list);
        logger.info("queryTime: 当前报表id为--> "+param.get("run_sql_id_") +
                " 查询时间："+(System.currentTimeMillis() - currentTime)+"毫秒");
        return pageInfo;
    }

    public JSONObject setParams(Map<String,Object> paramsMap, int id,SystemService systemService){
        paramsMap.put("run_sql_id_",id);
        BuildReport buildReport = new BuildReport();
        buildReport.setId(id);
        PageInfo<BuildReport> pageInfo = systemService.queryBuildReport(buildReport);
        String param = pageInfo.getList().get(0).getParams();
        if(!paramsMap.containsKey("pageNum")){
            paramsMap.put("pageNum",1);
            paramsMap.put("pageSize",50);
        }
        JSONObject obj = JSONObject.fromObject(param);
        if(obj.containsKey("params")){
            JSONArray jsonArray = obj.getJSONArray("params");
            if(jsonArray == null) return obj;
            for(int i=0;i<jsonArray.size();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                if(("date").equals(object.getString("type"))
                        && ("yes").equals(object.getString("requisite"))){
                    if(!paramsMap.containsKey(object.getString("name"))){
                        paramsMap.put(object.getString("name"), DateUtils.addDate(new Date(),-1,
                                DateUtils.DATE_SMALL_STR));
                    }
                }else if(("list").equals(object.getString("type"))){
                    if(paramsMap.containsKey(object.getString("name"))){
                        String plist = paramsMap.get(object.getString("name")).toString();
                        if(!("").equals(plist) && !("").equals(plist.trim())) {
                            paramsMap.put(object.getString("name") + "_list", Arrays.asList(plist.split(",")));
                        }
                    }
                }
            }
        }
        paramsMap.put("run_sql",obj.getString("sql"));
        paramsMap.put("datasource_run_sql",obj.getString("datasource"));
        return obj;
    }
}
