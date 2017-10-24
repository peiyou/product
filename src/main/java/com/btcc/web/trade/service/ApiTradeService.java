package com.btcc.web.trade.service;

import com.btcc.web.trade.mapper.ApiTradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/7/17.
 */
@Service
public class ApiTradeService {

    @Autowired
    ApiTradeMapper apiTradeMapper;

    public List<Map<String,Object>> queryApiTradeBtcByDay(Map<String,Object> map){
        return apiTradeMapper.queryApiTradeBtcByDay(map);
    }

    public List<Map<String,Object>> queryApiTradeLtcByDay(Map<String,Object> map){
        return apiTradeMapper.queryApiTradeLtcByDay(map);
    }
}
