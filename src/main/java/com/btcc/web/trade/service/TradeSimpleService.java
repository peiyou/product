package com.btcc.web.trade.service;

import com.btcc.Parameter;
import com.btcc.web.trade.entity.TradeSimple;
import com.btcc.web.trade.mapper.TradeSimpleMapper;
import com.btcc.web.trade.mapper.TradeSimpleResultMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by peiyou on 17/1/19.
 */
@Service
public class TradeSimpleService {

    private Logger logger = LoggerFactory.getLogger(TradeSimpleService.class);

    @Autowired
    TradeSimpleMapper tradeSimpleMapper;
    @Autowired
    SqlSessionFactory reportSqlSessionFactory;

    @Autowired
    TradeSimpleResultMapper tradeSimpleResult;

    public List<TradeSimple> queryTradeSimpleByDate(String startDate,String endDate){
        Parameter parameter = new Parameter();
        parameter.put("startDate",startDate);
        parameter.put("endDate",endDate);
        return tradeSimpleMapper.queryTradeSimpleByDate(parameter);
    }

    public void insertBatchTradeSimple(List<TradeSimple> list){

        SqlSession sqlSession = reportSqlSessionFactory.openSession(ExecutorType.BATCH);
        TradeSimpleResultMapper tradeSimpleResultMapper = sqlSession.getMapper(TradeSimpleResultMapper.class);
        try {
            int i=1;
            for (TradeSimple tradeSimple : list) {
                tradeSimpleResultMapper.insertTradeSimple(tradeSimple);
                if (i % 5000 == 0) {
                    sqlSession.commit();
                }
                i++;
            }
            if(list.size() % 5000 != 0){
                sqlSession.commit();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            sqlSession.close();
        }


    }

    public void deleteTradeSimple(String startDate,String endDate){
        Parameter parameter = new Parameter();
        parameter.put("startDate",startDate);
        parameter.put("endDate",endDate);
        tradeSimpleResult.deleteTradeSimple(parameter);
    }
}
