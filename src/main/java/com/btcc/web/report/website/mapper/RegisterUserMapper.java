package com.btcc.web.report.website.mapper;

import com.btcc.datasource.PhpbbDatasource;

import java.util.Map;

/**
 * Created by peiyou on 2017/8/16.
 */
@PhpbbDatasource
public interface RegisterUserMapper {

    /**
     * 获取注册用户数
     * startDate
     * endDate
     * @param param
     * @return
     */
    public Integer getRegisterCountUserId(Map<String,Object> param);
}
