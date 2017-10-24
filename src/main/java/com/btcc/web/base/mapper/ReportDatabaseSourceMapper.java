package com.btcc.web.base.mapper;

import com.btcc.build.sql.BuildSql;
import com.btcc.datasource.ReportDataSource;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2016/11/18.
 */
@ReportDataSource
public interface ReportDatabaseSourceMapper {

    @SelectProvider(type= BuildSql.class,method = "find")
    List<Map<String,Object>> queryData(Map<String,Object> param);

    @UpdateProvider(type = BuildSql.class,method = "find")
    void updateData(Map<String,Object> param);

    @InsertProvider(type=BuildSql.class,method = "find")
    void insertData(Map<String,Object> param);

    @DeleteProvider(type=BuildSql.class ,method = "find")
    void deleteData(Map<String,Object> param);
}
