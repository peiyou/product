package com.btcc.schedule;

import com.btcc.constant.ReportConstant;
import com.btcc.util.DateUtils;
import com.btcc.web.report.website.service.PlusInfoService;
import com.btcc.web.report.website.service.TradeInfoService;
import com.btcc.web.report.website.service.WebSiteWeekReportService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


/**
 * Created by peiyou on 2017/8/15.
 */
@Component
public class WebSiteWeekReportSchedule {

    @Autowired
    PlusInfoService plusInfoService;

    @Autowired
    TradeInfoService tradeInfoService;

    @Autowired
    WebSiteWeekReportService webSiteWeekReportService;

    private Logger logger = LoggerFactory.getLogger(WebSiteWeekReportSchedule.class);

    public String webSiteWeekReport(String strObject){
        Map<String,Object> param = new HashMap<>();
        param.put("company", ReportConstant.COMPANY_USER_ID_2);

        JSONObject jsonObject = JSONObject.fromObject(strObject);

        param.put("startDate", jsonObject.getString("startDate"));
        param.put("endDate", jsonObject.getString("endDate"));
        String type1 = jsonObject.getString("type");
        /**
         * 所有的SQL中含有 <=947634 的条件是表示在这个用户ID之后注册的用户都不进行统计了，这个ID为2017-09-14 19:00:00注册的最后一个ID
         */
        // 获取plus平台各币种的存量
        List<Map<String,Object>> plusPlatformOfStock = plusInfoService.getPlusPlatformOfStock(param);
        // 获取plus平台各币种的价格
        List<Map<String,Object>> lastSundryCoinOfPrice = plusInfoService.getLastSundryCoinOfPrice(param);
        // 交易BTC的价格
        BigDecimal btcPrice = tradeInfoService.getLastTradeBtcPrice(param);
        // 交易LTC的价格
        BigDecimal ltcPrice = tradeInfoService.getLastTradeLtcPrice(param);
        // 交易BTC的总量（单边）
        BigDecimal btcTotal = tradeInfoService.getTradeBtcTotal(param);
        // 人民币比特币市场交易人民币金额
        BigDecimal cnyTotal = tradeInfoService.getTradeBtcOfCnyTotal(param);
        // 交易比特币的用户数
        Integer userTotal = tradeInfoService.getTradeBtcOfUserTotal(param);
        // 参与BTC交易的每天平均用户数
        Integer avgDailyUser = tradeInfoService.getTradeBtcAvgDailyUser(param);
        // 最大的交易BTC价格
        BigDecimal maxPrice = tradeInfoService.getMaxTradeBtcOfPrice(param);
        //最小交易价格
        BigDecimal minPrice = tradeInfoService.getMinTradeBtcOfPrice(param);
        // 周期内新注册用户数
        Integer addUserRegister = plusInfoService.getRegisterCountUserId(param);
        Map<String,Object> paramReport = new HashMap<>();
        paramReport.put("dateline",jsonObject.getString("endDate"));
        paramReport.put("tradeBtcTotal",btcTotal != null ? btcTotal.doubleValue() : 0.0);
        paramReport.put("tradeBtcMoneyTotal",cnyTotal != null ? cnyTotal.doubleValue() : 0.0);
        paramReport.put("tradeBtcAvgDailyUser",avgDailyUser != null ? avgDailyUser : 0);
        paramReport.put("tradeBtcTotalUser",userTotal != null ? userTotal : 0);
        paramReport.put("tradeMaxBtcPrice",maxPrice != null ? maxPrice.doubleValue() : 0.0);
        paramReport.put("tradeMinBtcPrice",minPrice != null ? minPrice.doubleValue() : 0.0);
        paramReport.put("weekAddUser",addUserRegister != null ? addUserRegister : 0);
        paramReport.put("type",type1);


        if(plusPlatformOfStock != null && plusPlatformOfStock.size() > 0) {
            plusPlatformOfStock.forEach(map -> {
                if (map.get("currency").toString().equals("ETH")) {
                    paramReport.put("ethStock", map.get("total_balances"));
                } else if (map.get("currency").toString().equals("BCC")) {
                    paramReport.put("bccStock", map.get("total_balances"));
                }
            });
        }else{
            paramReport.put("ethStock", 0.0);
            paramReport.put("bccStock", 0.0);
        }




        webSiteWeekReportService.deleteWebSiteWeekReport(paramReport);
        webSiteWeekReportService.insertWebSiteWeekReport(paramReport);

        if(lastSundryCoinOfPrice.size() > 0) {
            lastSundryCoinOfPrice.forEach(map -> {
                if(map.get("symbol").toString().equals("BCCCNY")){
                    param.put("bccPrice",map.get("price"));
                }else if(map.get("symbol").toString().equals("ETHCNY")){
                    param.put("ethPrice",map.get("price"));
                }else if(map.get("symbol").toString().equals("ICOCNY")){
                    param.put("icoPrice",0); // ico数据已经退还， 所以算资产时不再计算了 。
                }
            });
            param.put("bccPrice", param.containsKey("bccPrice") ? param.get("bccPrice") : 0.0);
            param.put("ethPrice", param.containsKey("ethPrice") ? param.get("ethPrice") : 0.0);
            param.put("icoPrice", 0); // ico数据已经退还， 所以算资产时不再计算了 。
        }
        //删除中间表数据
        webSiteWeekReportService.deleteWebSiteUserBalance();
        // 插入中间表数据
        plusInfoService.insertUserBalance(param);
        param.put("btcPrice",btcPrice != null ? btcPrice.doubleValue() : 0.0);
        param.put("ltcPrice",ltcPrice != null ? ltcPrice.doubleValue() : 0.0);

        // 插入中间表数据
        tradeInfoService.insertUserOfBalance(param);
        /*List<Map<String,Object>> cnyBtc = tradeInfoService.getCnyBtcMarketFreeCny(param);
        cnyBtc.forEach(map -> {
            map.put("type",1);
            map.put("dateline",jsonObject.getString("endDate"));
        });
        List<Map<String,Object>> cnyLtc = tradeInfoService.getCnyLtcMarketFreeCny(param);
        cnyLtc.forEach(map -> {
            map.put("type",2);
            map.put("dateline",jsonObject.getString("endDate"));
        });
        List<Map<String,Object>> btcLtc = tradeInfoService.getBtcLtcMarketFreeCny(param);
        btcLtc.forEach(map -> {
            map.put("type",3);
            map.put("dateline",jsonObject.getString("endDate"));
        });*/


        List<Map<String,Object>> userBalance = webSiteWeekReportService.queryUserBalance(param);
        if(userBalance != null && userBalance.size() > 0){
            userBalance.forEach(map -> {
                String type = map.get("type").toString();
                if(type.equals("0")){
                    paramReport.put("assetsDistributionUserNum0",map.get("userNum"));
                }else if(type.equals("1")){
                    paramReport.put("assetsDistributionUserNum1",map.get("userNum"));
                }else if(type.equals("2")){
                    paramReport.put("assetsDistributionUserNum2",map.get("userNum"));
                }else if(type.equals("3")){
                    paramReport.put("assetsDistributionUserNum3",map.get("userNum"));
                }else if(type.equals("4")){
                    paramReport.put("assetsDistributionUserNum4",map.get("userNum"));
                }else if(type.equals("5")){
                    paramReport.put("assetsDistributionUserNum5",map.get("userNum"));
                }else if(type.equals("6")){
                    paramReport.put("assetsDistributionUserNum6",map.get("userNum"));
                }
                logger.info("--------------  userBalance number type:{},userNum:{} ----------------------- ",type,map.get("userNum"));
            });
        }else{
            paramReport.put("assetsDistributionUserNum0", 0);
            paramReport.put("assetsDistributionUserNum1", 0);
            paramReport.put("assetsDistributionUserNum2", 0);
            paramReport.put("assetsDistributionUserNum3", 0);
            paramReport.put("assetsDistributionUserNum4", 0);
            paramReport.put("assetsDistributionUserNum5", 0);
            paramReport.put("assetsDistributionUserNum6", 0);
        }
        Integer webSiteUserTotal = Integer.valueOf(paramReport.get("assetsDistributionUserNum0").toString())
                + Integer.valueOf(paramReport.get("assetsDistributionUserNum1").toString())
                + Integer.valueOf(paramReport.get("assetsDistributionUserNum2").toString())
                + Integer.valueOf(paramReport.get("assetsDistributionUserNum3").toString())
                + Integer.valueOf(paramReport.get("assetsDistributionUserNum4").toString())
                + Integer.valueOf(paramReport.get("assetsDistributionUserNum5").toString())
                + Integer.valueOf(paramReport.get("assetsDistributionUserNum6").toString());

        paramReport.put("btcPrice",btcPrice);
        paramReport.put("ltcPrice",ltcPrice);
        paramReport.put("bccPrice",param.get("bccPrice"));
        paramReport.put("ethPrice",param.get("ethPrice"));
        paramReport.put("userTotal",webSiteUserTotal);
        webSiteWeekReportService.updateWebSiteWeekReport(paramReport);
        String startDate = jsonObject.getString("endDate");
        String endDate = DateUtils.addDate(startDate,jsonObject.getInt("interval"),DateUtils.DATE_SMALL_STR);
        jsonObject.put("startDate",startDate);
        jsonObject.put("endDate",endDate);
        return jsonObject.toString();
    }
}
