package com.btcc.web.report.mk.mapper;

import com.btcc.datasource.PhpbbDatasource;

import java.util.Map;

/**
 * Created by peiyou on 2017/9/6.
 */
@PhpbbDatasource
public interface UserPhpMapper {

    public Map<String,Object> queryRegUserRange(Map<String,Object> param);

    public Integer queryRegUserNumber(Map<String,Object> param);

}
