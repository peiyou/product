package com.btcc.build.sql;

import java.util.Map;

/**
 * Created by peiyou on 2016/11/21.
 */
public class BuildSql {
    public String find(Map<String,Object> param){
        String sql = (String)param.get("run_sql");
        SqlContext sqlContext = new SqlContext(sql,param);
        String result = sqlContext.getSql();
        return result;
    }
}
