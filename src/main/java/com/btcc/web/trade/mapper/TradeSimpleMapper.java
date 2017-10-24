package com.btcc.web.trade.mapper;

import com.btcc.Parameter;
import com.btcc.datasource.MkDataSource;
import com.btcc.util.BaseMapper;
import com.btcc.web.trade.entity.TradeSimple;

import java.util.List;

/**
 * Created by peiyou on 17/1/19.
 */
@MkDataSource
public interface TradeSimpleMapper extends BaseMapper {

    public List<TradeSimple> queryTradeSimpleByDate(Parameter parameter);
}
