package com.btcc.web.trade.mapper;

import com.btcc.datasource.MkDataSource;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/9/29.
 */
@MkDataSource
public interface WithdrawOrDepositMapper {

    public List<Map<String,Object>> withdrawBTC(Map<String,Object> param);

    public List<Map<String,Object>> withdrawLTC(Map<String,Object> param);

    public List<Map<String,Object>> depositBTC(Map<String,Object> param);

    public List<Map<String,Object>> depositLTC(Map<String,Object> param);

    public List<Map<String,Object>> userInfo(List<Integer> userIds);
}
