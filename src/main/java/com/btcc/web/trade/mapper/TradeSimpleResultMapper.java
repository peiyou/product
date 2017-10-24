package com.btcc.web.trade.mapper;

import com.btcc.Parameter;
import com.btcc.datasource.ReportDataSource;
import com.btcc.util.BaseMapper;
import com.btcc.web.trade.entity.TradeSimple;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by peiyou on 17/1/19.
 */
@ReportDataSource
public interface TradeSimpleResultMapper extends BaseMapper {

    @Insert("insert into trade_simple(date,trade_id,user_id) value(#{date},#{tradeId},#{userId})")
    public void insertTradeSimple(TradeSimple tradeSimple);

    @Delete("delete from trade_simple where date >= unix_timestamp(#{startDate}) and date < unix_timestamp(#{endDate})")
    public void deleteTradeSimple(Parameter parameter);
}
