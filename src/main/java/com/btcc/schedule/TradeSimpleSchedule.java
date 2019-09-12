//package com.btcc.schedule;
//
//import com.btcc.util.DateUtils;
//import com.btcc.web.trade.entity.TradeSimple;
//import com.btcc.web.trade.service.TradeSimpleService;
//import net.sf.json.JSONObject;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * Created by peiyou on 17/1/19.
// * 交易数据处理，因为数据量过大，历史数据采用批量处理
// */
//@Component
//public class TradeSimpleSchedule {
//
//    private Logger logger = LoggerFactory.getLogger(TradeSimpleSchedule.class);
//
//    @Autowired
//    TradeSimpleService tradeSimpleService;
//
//    public String historyTradeSimple(String strobj){
//
//        long current = System.currentTimeMillis();
//        JSONObject object = JSONObject.fromObject(strobj);
//        try{
//            String startDate = object.getString("startDate");
//            String endDate = object.getString("endDate");
//            String currentDate = DateUtils.getNowTime(DateUtils.DATE_SMALL_STR); //系统时间UTC
//            if(startDate.compareTo(currentDate) < 0){
//                logger.info("historyTradeSimple task begin....");
//                tradeSimpleService.deleteTradeSimple(startDate,endDate);
//                List<TradeSimple> list = tradeSimpleService.queryTradeSimpleByDate(startDate,
//                        endDate);
//                if(list != null && list.size() > 0){
//                    tradeSimpleService.insertBatchTradeSimple(list);
//                }
//                logger.info("historyTradeSimple task  end !");
//                if(currentDate.compareTo(endDate) <= 0) {
//                    object.put("startDate", currentDate);
//                    object.put("endDate", DateUtils.addDate(currentDate, 1, DateUtils.DATE_SMALL_STR));
//                }else {
//                    object.put("startDate", endDate);
//                    int diff = DateUtils.diffDate(endDate, startDate);
//                    object.put("endDate", DateUtils.addDate(endDate, diff, DateUtils.DATE_SMALL_STR));
//                }
//                logger.info("historyTradeSimple run time : " + (System.currentTimeMillis() - current) + " ms");
//            }
//            return object.toString();
//        }catch (Exception e){
//            e.printStackTrace();
//            logger.error("historyTradeSimple task error!");
//            logger.error(e.getMessage());
//        }
//        logger.info("historyTradeSimple run time : " +(System.currentTimeMillis() - current) +" ms" );
//        return strobj;
//    }
//}
