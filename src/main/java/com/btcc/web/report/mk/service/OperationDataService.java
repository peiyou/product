package com.btcc.web.report.mk.service;

import com.btcc.web.report.mk.mapper.OperationDataMapper;
import com.btcc.web.report.mk.mapper.UserPhpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/9/6.
 */
@Service
public class OperationDataService {

    @Autowired
    OperationDataMapper operationDataMapper;

    @Autowired
    UserPhpMapper userPhpMapper;

    public Map<String,Object> queryDepositBtcInfo(Map<String,Object> param){
        return operationDataMapper.queryDepositBtcInfo(param);
    }

    public Map<String,Object> queryWithdrawBtcInfo(Map<String,Object> param){
        return operationDataMapper.queryWithdrawBtcInfo(param);
    }

    public Map<String,Object> queryDepositCnyInfo(Map<String,Object> param){
        return operationDataMapper.queryDepositCnyInfo(param);
    }

    public Map<String,Object> queryWithdrawCnyInfo(Map<String,Object> param){
        return operationDataMapper.queryWithdrawCnyInfo(param);
    }

    public Map<String,Object> queryTradeInfo(Map<String,Object> param){
        return operationDataMapper.queryTradeInfo(param);
    }

    public List<Map<String,Object>> queryTradeFeeInfo(Map<String,Object> param){
        return operationDataMapper.queryTradeFeeInfo(param);
    }

    public Integer queryRegTimeDepositOfUserNum(Map<String,Object> param){
        return operationDataMapper.queryRegTimeDepositOfUserNum(param);
    }

    public Map<String,Object> queryRegUserRange(Map<String,Object> param){
        return userPhpMapper.queryRegUserRange(param);
    }

    public Integer queryRegUserNumber(Map<String,Object> param){
        return userPhpMapper.queryRegUserNumber(param);
    }
}
