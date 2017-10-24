package com.btcc.web.report.mk.service;

import com.btcc.util.DateUtils;
import com.btcc.web.report.mk.entity.BusinessFrom;
import com.btcc.web.report.mk.mapper.BusinessMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 10/27/16.
 */
@Service
public class BusinessService {

    private Logger logger = LoggerFactory.getLogger(BusinessService.class);

    @Autowired
    BusinessMapper transactionMapper;

    /**
     * 交易数据统计
     * @param form
     * @return
     */
    /*public PageInfo<Map<String,Object>> findPlatformTopUpWithdrawalData(BusinessFrom form){
        PageInfo<Map<String,Object>> page = null;
        PageHelper.startPage(form.getPageNum(),form.getPageSize());
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        logger.info("平台充值提现人民币与比特币数据查询....");
        Long current = System.currentTimeMillis();
        List<Map<String,Object>> list = transactionMapper.findPlatformTopUpWithdrawalData(param);
        Long lastTime = System.currentTimeMillis();
        logger.info("平台充值提现人民币与比特币数据查询耗时：" + (lastTime - current) +" 毫秒");
        page = new PageInfo<Map<String, Object>>(list);
        return page;
    }*/

    /**
     * 比特币充值数据统计
     * @param form
     * @return
     */
    public PageInfo<Map<String,Object>> payBtcDataTotal(BusinessFrom form){
        PageInfo<Map<String,Object>> page = null;
        PageHelper.startPage(form.getPageNum(),form.getPageSize());
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        Long current = System.currentTimeMillis();
        List<Map<String, Object>> list = null;
        try {
            list = transactionMapper.payBtcDataTotal(param);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("充值比特币数据统计查询出现异常...");
        }
        Long lastTime = System.currentTimeMillis();
        logger.info("queryTime:充值比特币数据统计查询耗时：" + (lastTime - current) +" 毫秒");
        page = new PageInfo<Map<String, Object>>(list);
        return page;
    }

    /**
     * 比特币提现数据统计
     * @param form
     * @return
     */
    public PageInfo<Map<String,Object>> takeBtcDataTotal(BusinessFrom form){
        PageInfo<Map<String,Object>> page = null;
        PageHelper.startPage(form.getPageNum(),form.getPageSize());
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        Long current = System.currentTimeMillis();
        List<Map<String, Object>> list = null;
        try {
            list = transactionMapper.takeBtcDataTotal(param);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("提现比特币数据统计查询出现异常...");
        }
        Long lastTime = System.currentTimeMillis();
        logger.info("queryTime:提现比特币数据统计查询耗时：" + (lastTime - current) +" 毫秒");
        page = new PageInfo<Map<String, Object>>(list);
        return page;
    }

    /**
     * 人民币充值数据统计
     * @param form
     * @return
     */
    public PageInfo<Map<String,Object>> payCnyDataTotal(BusinessFrom form){
        PageInfo<Map<String,Object>> page = null;
        PageHelper.startPage(form.getPageNum(),form.getPageSize());
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        Long current = System.currentTimeMillis();
        List<Map<String,Object>> list = null;
        try {
              list  =transactionMapper.payCnyDataTotal(param);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("充值人民币数据统计出现异常...");
        }
        Long lastTime = System.currentTimeMillis();
        logger.info("queryTime:充值人民币数据统计查询耗时：" + (lastTime - current) +" 毫秒");
        page = new PageInfo<Map<String, Object>>(list);
        return page;
    }

    /**
     * 人民币提现数据统计
     * @param form
     * @return
     */
    public PageInfo<Map<String,Object>> takeCnyDataTotal(BusinessFrom form){
        PageInfo<Map<String,Object>> page = null;
        PageHelper.startPage(form.getPageNum(),form.getPageSize());
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        Long current = System.currentTimeMillis();
        List<Map<String,Object>> list =null;
        try {
            list = transactionMapper.takeCnyDataTotal(param);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("提现人民币数据统计查询出现异常...");
        }
        Long lastTime = System.currentTimeMillis();
        logger.info("queryTime:提现人民币数据统计查询耗时：" + (lastTime - current) +" 毫秒");
        page = new PageInfo<Map<String, Object>>(list);
        return page;
    }

    /**
     * 交易用户数
     * 交易量
     * @param form
     * @return
     */
    public PageInfo<Map<String,Object>> userBusinessData(BusinessFrom form){
        PageInfo<Map<String,Object>> page = null;
        PageHelper.startPage(form.getPageNum(),form.getPageSize());
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        Long current = System.currentTimeMillis();
        List<Map<String,Object>> list = null;
        try {
            list = transactionMapper.userBusinessData(param);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("交易用户数，交易量数据统计查询出现异常...");
        }
        Long lastTime = System.currentTimeMillis();
        logger.info("queryTime:交易用户数，交易量数据统计查询耗时：" + (lastTime - current) +" 毫秒");
        page = new PageInfo<Map<String, Object>>(list);
        return page;
    }

    /**
     * 交易Api
     * @param form
     * @return
     */
    public List<Map<String,Object>> amountApiBusinessData(BusinessFrom form){
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        Long current = System.currentTimeMillis();
        List<Map<String,Object>> list = null;
        try {
            list = transactionMapper.amountApiBusinessData(param);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("API交易数据统计查询出现异常...");
        }
        Long lastTime = System.currentTimeMillis();
        logger.info("queryTime:API交易数据统计查询耗时：" + (lastTime - current) +" 毫秒");
        return list;
    }


    /**
     * 交易人数Api
     * @param form
     * @return
     */
    public List<Map<String,Object>> userApiBusinessData(BusinessFrom form){
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        Long current = System.currentTimeMillis();
        List<Map<String,Object>> list = null;
        try {
            list = transactionMapper.userApiBusinessData(param);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("API交易人数数据统计查询出现异常...");
        }
        Long lastTime = System.currentTimeMillis();
        logger.info("queryTime:API交易人数数据统计查询耗时：" + (lastTime - current) +" 毫秒");
        return list;
    }

    /**
     * 当天新增借贷人民币用户数
     * 当天新增借贷人民币量
     * @param form
     * @return
     */
    public List<Map<String,Object>> addBorrowedCnyDataInfo(BusinessFrom form){
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        Long current = System.currentTimeMillis();
        List<Map<String,Object>> list =  null;
        try {
            list = transactionMapper.addBorrowedCnyDataInfo(param);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("API交易人数数据统计查询出现异常...");
        }
        Long lastTime = System.currentTimeMillis();
        logger.info("queryTime:借贷人民币数据统计查询耗时：" + (lastTime - current) +" 毫秒");
        return list;
    }

    /**
     * 当天新增借贷BTC用户数
     * 当天新增借贷BTC量
     * @param form
     * @return
     */
    public List<Map<String,Object>> addBorrowedBtcDataInfo(BusinessFrom form){
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        Long current = System.currentTimeMillis();
        List<Map<String,Object>> list = null;
        try {
            list = transactionMapper.addBorrowedBtcDataInfo(param);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("借贷比特币数据统计查询出现异常...");
        }
        Long lastTime = System.currentTimeMillis();
        logger.info("queryTime:借贷比特币数据统计查询耗时：" + (lastTime - current) +" 毫秒");
        return list;
    }

    /**
     * 借贷利息（已收取）
     * @param form
     * @return
     */
    public List<Map<String,Object>> borrowedInterestDataInfo(BusinessFrom form){
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        Long current = System.currentTimeMillis();
        List<Map<String,Object>> list =  null;
        try {
            list = transactionMapper.borrowedInterestDataInfo(param);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("借贷利息（已收取）数据统计查询出现异常...");
        }
        Long lastTime = System.currentTimeMillis();
        logger.info("queryTime:借贷利息（已收取）数据统计查询耗时：" + (lastTime - current) +" 毫秒");
        return list;
    }

    /**
     * 人民币退款
     * @param form
     * @return
     */
    /*public List<Map<String,Object>> cnyRefundData(BusinessFrom form){
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        return transactionMapper.cnyRefundData(param);
    }*/

    /**
     * 人民币退款
     * @param form
     * @return
     */
    /*public List<Map<String,Object>> btcRefundData(BusinessFrom form){
        Map<String,String> param = new HashMap<>();
        param.put("startDate",form.getStartDate());
        param.put("endDate", DateUtils.addDate(form.getEndDate(),1,DateUtils.DATE_SMALL_STR));
        return transactionMapper.btcRefundData(param);
    }*/



}
