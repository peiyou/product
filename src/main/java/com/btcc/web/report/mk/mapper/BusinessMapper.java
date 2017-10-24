package com.btcc.web.report.mk.mapper;

import com.btcc.datasource.MkDataSource;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 10/27/16.
 */
@MkDataSource
public interface BusinessMapper {
    /**
     * 平台充值提现数据统计
     * @param params
     * @return
     */
    /*public List<Map<String,Object>> findPlatformTopUpWithdrawalData(Map<String,String> params);*/


    /**
     * 比特币充值统计
     * @param params
     * @return
     */
    public List<Map<String,Object>> payBtcDataTotal(Map<String,String> params);

    /**
     * 比特币提取统计
     * @param params
     * @return
     */
    public List<Map<String,Object>> takeBtcDataTotal(Map<String,String> params);

    /**
     * 人民币充值统计
     * @param params
     * @return
     */
    public List<Map<String,Object>> payCnyDataTotal(Map<String,String> params);

    /**
     * 人民币提取统计
     * @param params
     * @return
     */
    public List<Map<String,Object>> takeCnyDataTotal(Map<String,String> params);

    /**
     * 交易用户交易量
     * @param params
     * @return
     */
    List<Map<String,Object>> userBusinessData(Map<String,String> params);

    /**
     * 交易Api
     * @param params
     * @return
     */
    List<Map<String,Object>> amountApiBusinessData(Map<String,String> params);

    /**
     * 交易人数Api
     * @param params
     * @return
     */
    List<Map<String,Object>> userApiBusinessData(Map<String,String> params);

    /**
     * 当天新增借贷人民币用户数
     * 当天新增借贷人民币量
     * @param params
     * @return
     */
    List<Map<String,Object>> addBorrowedCnyDataInfo(Map<String,String> params);

    /**
     * 当天新增借贷BTC用户数
     * 当天新增借贷BTC量
     * @param params
     * @return
     */
    List<Map<String,Object>> addBorrowedBtcDataInfo(Map<String,String> params);

    /**
     * 借贷利息（已收取）
     * @param params
     * @return
     */
    List<Map<String,Object>> borrowedInterestDataInfo(Map<String,String> params);

    /**
     * 人民币退款
     * @param params
     * @return
     */
    /*List<Map<String,Object>> cnyRefundData(Map<String,String> params);*/

    /**
     * 比特币退款
     * @param params
     * @return
     */
    /*List<Map<String,Object>> btcRefundData(Map<String,String> params);*/
}
