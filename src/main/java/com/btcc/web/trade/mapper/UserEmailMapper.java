package com.btcc.web.trade.mapper;

import com.btcc.datasource.PhpbbDatasource;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/9/29.
 */
@PhpbbDatasource
public interface UserEmailMapper {


    public List<Map<String,Object>> queryUserEmail(List<Integer> userIds);
}
