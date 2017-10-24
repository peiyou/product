package com.btcc.web.report.mk.mapper;

import com.btcc.datasource.MkDataSource;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/9/6.
 */
@MkDataSource
public interface OperationDataMapper {

    public Map<String,Object> queryDepositBtcInfo(Map<String,Object> param);

    public Map<String,Object> queryWithdrawBtcInfo(Map<String,Object> param);

    public Map<String,Object> queryDepositCnyInfo(Map<String,Object> param);

    public Map<String,Object> queryWithdrawCnyInfo(Map<String,Object> param);

    public Map<String,Object> queryTradeInfo(Map<String,Object> param);

    public List<Map<String,Object>> queryTradeFeeInfo(Map<String,Object> param);

    public Integer queryRegTimeDepositOfUserNum(Map<String,Object> param);

}
