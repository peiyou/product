package com.btcc.web.report.website.mapper;

import com.btcc.datasource.MkDataSource;
import org.apache.ibatis.cursor.Cursor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/8/15.
 */
@MkDataSource
public interface TradeInfoMapper {

    /**
     * 单边BTC交易量
     * @param param
     * @return
     */
    public BigDecimal getTradeBtcTotal(Map<String,Object> param);

    /**
     * 交易BTC的人民币金额
     * @param param
     * @return
     */
    public BigDecimal getTradeBtcOfCnyTotal(Map<String,Object> param);

    /**
     * 交易BTC的用户数量
     * @param param
     * @return
     */
    public Integer getTradeBtcOfUserTotal(Map<String,Object> param);

    /**
     * 参与BTC交易的每天平均用户数
     * @param param
     * @return
     */
    public Integer getTradeBtcAvgDailyUser(Map<String,Object> param);

    /**
     * 获取时间内的最高成交BTC的价格
     * @param param
     * @return
     */
    public BigDecimal getMaxTradeBtcOfPrice(Map<String,Object> param);

    /**
     * 获取时间内的最低成交BTC的价格
     * @param param
     * @return
     */
    public BigDecimal getMinTradeBtcOfPrice(Map<String,Object> param);

    /**
     * 获取时间内的最后成交BTC的价格
     * @param param
     * @return
     */
    public BigDecimal getLastTradeBtcPrice(Map<String,Object> param);

    /**
     * 获取时间内的最后成交LTC的价格
     * @param param
     * @return
     */
    public BigDecimal getLastTradeLtcPrice(Map<String,Object> param);

    /**
     * 获取CNYBTC市场的挂单金额（转化为人民币）
     * 得到每个用户的挂单金额
     * @param param
     * @return
     */
    public List<Map<String,Object>> getCnyBtcMarketFreeCny(Map<String,Object> param);

    /**
     * 获取CNYLTC市场的挂单金额（转化为人民币）
     * 得到每个用户的挂单金额
     * @param param
     * @return
     */
    public List<Map<String,Object>> getCnyLtcMarketFreeCny(Map<String,Object> param);

    /**
     * 获取BTCLTC市场的挂单金额（转化为人民币）
     * 得到每个用户的挂单金额
     * @param param
     * @return
     */
    public List<Map<String,Object>> getBtcLtcMarketFreeCny(Map<String,Object> param);

    /**
     * 获取用户时间段内的余额
     * 得到每个用户的余额
     * @param param
     * @return
     */
    public Cursor<Map<String,Object>> getUserOfBalance(Map<String,Object> param);

}
