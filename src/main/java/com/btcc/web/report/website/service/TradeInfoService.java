package com.btcc.web.report.website.service;

import com.btcc.constant.ReportConstant;
import com.btcc.util.DateUtils;
import com.btcc.util.MathUtils;
import com.btcc.web.report.website.mapper.TradeInfoMapper;
import com.btcc.web.report.website.mapper.WebSiteUserBalanceMapper;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by peiyou on 2017/8/16.
 */
@Service
public class TradeInfoService {

    private Logger logger = LoggerFactory.getLogger(TradeInfoService.class);

    @Autowired
    private TradeInfoMapper tradeInfoMapper;


    @Resource(name="btcchinaMkSqlSessionFactory")
    private SqlSessionFactory btcchinaMkSqlSessionFactory;

    @Autowired
    private WebSiteUserBalanceMapper webSiteUserBalanceMapper;

    public BigDecimal getTradeBtcTotal(Map<String,Object> param){
        return tradeInfoMapper.getTradeBtcTotal(param);
    }

    public BigDecimal getTradeBtcOfCnyTotal(Map<String,Object> param){
        return tradeInfoMapper.getTradeBtcOfCnyTotal(param);
    }

    public Integer getTradeBtcOfUserTotal(Map<String,Object> param){
        return tradeInfoMapper.getTradeBtcOfUserTotal(param);
    }

    public Integer getTradeBtcAvgDailyUser(Map<String,Object> param){
        return tradeInfoMapper.getTradeBtcAvgDailyUser(param);
    }

    public BigDecimal getMaxTradeBtcOfPrice(Map<String,Object> param){
        return tradeInfoMapper.getMaxTradeBtcOfPrice(param);
    }

    public BigDecimal getMinTradeBtcOfPrice(Map<String,Object> param){
        return tradeInfoMapper.getMinTradeBtcOfPrice(param);
    }

    public BigDecimal getLastTradeBtcPrice(Map<String,Object> param){
        return tradeInfoMapper.getLastTradeBtcPrice(param);
    }

    public BigDecimal getLastTradeLtcPrice(Map<String,Object> param){
        return tradeInfoMapper.getLastTradeLtcPrice(param);
    }

    public List<Map<String,Object>> getCnyBtcMarketFreeCny(Map<String,Object> param){
        return tradeInfoMapper.getCnyBtcMarketFreeCny(param);
    }

    public List<Map<String,Object>> getCnyLtcMarketFreeCny(Map<String,Object> param){
        return tradeInfoMapper.getCnyLtcMarketFreeCny(param);
    }

    public List<Map<String,Object>> getBtcLtcMarketFreeCny(Map<String,Object> param){
        return tradeInfoMapper.getBtcLtcMarketFreeCny(param);
    }

    public void insertUserOfBalance(Map<String,Object> param){
        SqlSession sqlSession = btcchinaMkSqlSessionFactory.openSession();
        Cursor<Map<String,Object>> cursor = sqlSession.selectCursor("getUserOfBalance",param);
        Iterator<Map<String,Object>> iterator = cursor.iterator();
        List<Map<String,Object>> result = new ArrayList<>();
        while (iterator.hasNext()){
            Map<String,Object> map = iterator.next();
            if(!MathUtils.isNumeric(map.get("user_id").toString())) continue;
            map.put("type",5);
            map.put("dateline", DateUtils.dateFormat(new Date(),DateUtils.DATE_SMALL_STR));
            result.add(map);
            if(result.size() >= ReportConstant.rowNum){
                webSiteUserBalanceMapper.insertWebSiteUserBalance(result);
                result.clear();
            }
        }
        if (result.size() > 0) {
            webSiteUserBalanceMapper.insertWebSiteUserBalance(result);
            result.clear();
        }
        try {
            cursor.close();
        }catch (IOException e){
            logger.error("",e);
        }

    }



}
