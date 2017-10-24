package com.btcc.web.trade.mapper;

import com.btcc.datasource.MkDataSource;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/7/17.
 */
@MkDataSource
public interface ApiTradeMapper {

    List<Map<String,Object>> queryApiTradeBtcByDay(Map<String,Object> map);

    List<Map<String,Object>> queryApiTradeLtcByDay(Map<String,Object> map);
}
