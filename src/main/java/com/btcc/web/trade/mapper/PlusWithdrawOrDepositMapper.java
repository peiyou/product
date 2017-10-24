package com.btcc.web.trade.mapper;

import com.btcc.datasource.BttxDataSource;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/9/29.
 */
@BttxDataSource
public interface PlusWithdrawOrDepositMapper {

    public List<Map<String,Object>> withdrawETH(Map<String,Object> param);

    public List<Map<String,Object>> withdrawBCC(Map<String,Object> param);

    public List<Map<String,Object>> depositETH(Map<String,Object> param);

    public List<Map<String,Object>> withdrawSuccessETH(Map<String,Object> param);

    public List<Map<String,Object>> withdrawSuccessBCC(Map<String,Object> param);
}
