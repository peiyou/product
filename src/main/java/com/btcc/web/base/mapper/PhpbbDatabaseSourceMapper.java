package com.btcc.web.base.mapper;

import com.btcc.build.sql.BuildSql;
import com.btcc.datasource.PhpbbDatasource;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@PhpbbDatasource
public interface PhpbbDatabaseSourceMapper {
    @SelectProvider(type= BuildSql.class,method = "find")
    List<Map<String,Object>> queryData(Map<String,Object> param);
}
