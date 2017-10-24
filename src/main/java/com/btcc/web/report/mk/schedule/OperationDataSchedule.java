package com.btcc.web.report.mk.schedule;

import com.btcc.util.DateUtils;
import com.btcc.web.report.mk.service.OperationDataService;
import com.btcc.web.report.mk.service.ReportService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by peiyou on 2017/9/6.
 */
@Component
public class OperationDataSchedule {

    @Autowired
    OperationDataService operationDataService;

    @Autowired
    ReportService reportService;

    public String operationDataSchedule(String obj){
        JSONObject jsonObject = JSONObject.fromObject(obj);
        String startDate = jsonObject.getString("startDate");
        String endDate = jsonObject.getString("endDate");
        Map<String,Object> param = new HashMap();
        param.put("startDate",startDate);
        param.put("endDate",endDate);
        Integer regUserNum = operationDataService.queryRegUserNumber(param);
        Map<String,Object> regUserRange = operationDataService.queryRegUserRange(param);

        param.put("maxUserId",regUserRange.get("max_user_id"));
        param.put("minUserId",regUserRange.get("min_user_id"));
        Integer RegTimeDepositOfUserNum = operationDataService.queryRegTimeDepositOfUserNum(param);

        Map<String,Object> depositBtcInfo = operationDataService.queryDepositBtcInfo(param);

        Map<String,Object> withdrawBtcInfo = operationDataService.queryWithdrawBtcInfo(param);

        Map<String,Object> depositCnyInfo = operationDataService.queryDepositCnyInfo(param);

        Map<String,Object> withdrawCnyInfo = operationDataService.queryWithdrawCnyInfo(param);

        Map<String,Object> tradeInfo = operationDataService.queryTradeInfo(param);

        List<Map<String,Object>> tradeFeeInfo = operationDataService.queryTradeFeeInfo(param);

        Map<String,Object> result = new HashMap<>();
        result.put("dateline",depositBtcInfo != null && depositBtcInfo.containsKey("date") ? depositBtcInfo.get("date") : startDate);
        result.put("register_num",regUserNum);
        result.put("register_time_deposit_of_user_num",RegTimeDepositOfUserNum);
        result.put("deposit_btc_user_num",depositBtcInfo != null && depositBtcInfo.containsKey("deposit_user_num") ? depositBtcInfo.get("deposit_user_num") : 0);
        result.put("deposit_btc_num",depositBtcInfo != null && depositBtcInfo.containsKey("deposit_num") ? depositBtcInfo.get("deposit_num") : 0);
        result.put("deposit_btc_total",depositBtcInfo != null && depositBtcInfo.containsKey("deposit_total") ? depositBtcInfo.get("deposit_total") : 0);

        result.put("withdraw_btc_user_num",withdrawBtcInfo != null && withdrawBtcInfo.containsKey("withdraw_btc_user_num") ? withdrawBtcInfo.get("withdraw_btc_user_num") : 0);
        result.put("withdraw_btc_num",withdrawBtcInfo != null && withdrawBtcInfo.containsKey("withdraw_btc_num") ? withdrawBtcInfo.get("withdraw_btc_num") : 0);
        result.put("withdraw_btc_total",withdrawBtcInfo != null && withdrawBtcInfo.containsKey("withdraw_btc_total") ? withdrawBtcInfo.get("withdraw_btc_total") : 0);
        result.put("withdraw_btc_fee",withdrawBtcInfo != null && withdrawBtcInfo.containsKey("withdraw_btc_fee") ? withdrawBtcInfo.get("withdraw_btc_fee") : 0);

        result.put("deposit_cny_user_num",depositCnyInfo != null && depositCnyInfo.containsKey("deposit_cny_user_num") ? depositCnyInfo.get("deposit_cny_user_num") : 0);
        result.put("deposit_cny_num",depositCnyInfo != null && depositCnyInfo.containsKey("deposit_cny_num") ? depositCnyInfo.get("deposit_cny_num") : 0);
        result.put("deposit_cny_total",depositCnyInfo != null && depositCnyInfo.containsKey("deposit_cny_total") ? depositCnyInfo.get("deposit_cny_total") : 0);

        result.put("withdraw_cny_user_num",withdrawCnyInfo != null && withdrawCnyInfo.containsKey("withdraw_cny_user_num") ? withdrawCnyInfo.get("withdraw_cny_user_num") : 0);
        result.put("withdraw_cny_num",withdrawCnyInfo != null && withdrawCnyInfo.containsKey("withdraw_cny_num") ? withdrawCnyInfo.get("withdraw_cny_num") : 0);
        result.put("withdraw_cny_total",withdrawCnyInfo != null && withdrawCnyInfo.containsKey("withdraw_cny_total") ? withdrawCnyInfo.get("withdraw_cny_total") : 0);
        result.put("withdraw_cny_fee",withdrawCnyInfo != null && withdrawCnyInfo.containsKey("withdraw_cny_fee") ? withdrawCnyInfo.get("withdraw_cny_fee") : 0);

        result.put("trade_btc_user_num",tradeInfo != null && tradeInfo.containsKey("trade_btc_user_num") ? tradeInfo.get("trade_btc_user_num") : 0);
        result.put("trade_btc_total",tradeInfo != null && tradeInfo.containsKey("trade_btc_total") ? tradeInfo.get("trade_btc_total") : 0);
        result.put("trade_ltc_user_num",tradeInfo != null && tradeInfo.containsKey("trade_ltc_user_num") ? tradeInfo.get("trade_ltc_user_num") : 0);
        result.put("trade_ltc_total",tradeInfo != null && tradeInfo.containsKey("trade_ltc_total") ? tradeInfo.get("trade_ltc_total") : 0);


        if(tradeFeeInfo != null && tradeFeeInfo.size() > 0){
            for(Map map : tradeFeeInfo){
                if(map.containsKey("market") && map.get("market").toString().equalsIgnoreCase("cnybtc")){
                    result.put("trade_btc_of_cny_fee", map.get("cny"));
                    result.put("trade_btc_fee",map.get("btc"));
                }else if(map.containsKey("market") && map.get("market").toString().equalsIgnoreCase("cnyltc")){
                    result.put("trade_ltc_of_cny_fee", map.get("cny"));
                    result.put("trade_ltc_fee",map.get("ltc"));
                }
            }
        }
        if(!result.containsKey("trade_btc_of_cny_fee")) {
            result.put("trade_btc_of_cny_fee", 0);
            result.put("trade_ltc_of_cny_fee", 0);
        }

        if(!result.containsKey("trade_btc_fee")) {
            result.put("trade_btc_fee", 0);
            result.put("trade_ltc_fee", 0);
        }
        reportService.insertOperationData(result);

        startDate = endDate;
        endDate = DateUtils.addDate(startDate,1,DateUtils.DATE_SMALL_STR);
        jsonObject.put("startDate",startDate);
        jsonObject.put("endDate",endDate);

        return jsonObject.toString();
    }

}
