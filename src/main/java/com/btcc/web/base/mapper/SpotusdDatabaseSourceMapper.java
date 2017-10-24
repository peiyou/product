package com.btcc.web.base.mapper;

import com.btcc.build.sql.BuildSql;
import com.btcc.datasource.SpotusdDataSource;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/8/4.
 */
@SpotusdDataSource
public interface SpotusdDatabaseSourceMapper {

    @SelectProvider(type= BuildSql.class,method = "find")
    List<Map<String,Object>> queryData(Map<String,Object> param);
}
