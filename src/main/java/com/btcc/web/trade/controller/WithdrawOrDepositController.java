package com.btcc.web.trade.controller;

import com.btcc.constant.ReportConstant;
import com.btcc.util.DateUtils;
import com.btcc.util.ExcelUtils;
import com.btcc.web.trade.service.WithdrawOrDepositService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by peiyou on 2017/9/29.
 */
@Controller
@RequestMapping("/withdrawOrDepositController")
public class WithdrawOrDepositController {

    private final String ETH = "ETH";
    private final String BTC = "BTC";
    private final String LTC = "LTC";
    private final String BCC = "BCC";

    private final String DEPOSIT="deposit";

    private final String WITHDRAW="withdraw";

    private final String WITHDRAW_SUCCESS="withdraw_success";

    @Autowired
    WithdrawOrDepositService withdrawOrDepositService;

    @RequestMapping("/{type}/{currency}/index")
    public ModelAndView index(@PathVariable(value = "currency")String currency,@PathVariable(value="type") String type,ModelAndView modelAndView){
        List<Map<String,Object>> list = null;
        Map<String,Object> map = new HashMap<>();
        map.put("dateline", DateUtils.dateFormat(new Date(),DateUtils.DATE_SMALL_STR));
        if(type.equalsIgnoreCase(DEPOSIT)) {
            if (currency.equalsIgnoreCase(ETH)) {
                list = withdrawOrDepositService.depositETH(map);
            }else if(currency.equalsIgnoreCase(BTC)) {
                list = withdrawOrDepositService.depositBTC(map);
            }else if(currency.equalsIgnoreCase(LTC)){
                list = withdrawOrDepositService.depositLTC(map);
            }
        }else if(type.equalsIgnoreCase(WITHDRAW)){
            if (currency.equalsIgnoreCase(ETH)) {
                list = withdrawOrDepositService.withdrawETH(map);
            }else if(currency.equalsIgnoreCase(BTC)) {
                list = withdrawOrDepositService.withdrawBTC(map);
            }else if(currency.equalsIgnoreCase(LTC)){
                list = withdrawOrDepositService.withdrawLTC(map);
            }else if(currency.equalsIgnoreCase(BCC)){
                list = withdrawOrDepositService.withdrawBCC(map);
            }
        }else if(type.equalsIgnoreCase(WITHDRAW_SUCCESS)){
            if (currency.equalsIgnoreCase(ETH)){
                list = withdrawOrDepositService.withdrawSuccessETH(map);
            }else{
                list = withdrawOrDepositService.withdrawSuccessBCC(map);
            }
        }
        modelAndView.addObject("type",type);
        modelAndView.addObject("currency",currency);
        modelAndView.addObject("list",list);
        modelAndView.setViewName("/trade/deposit-withdraw-data");
        return modelAndView;
    }

    @RequestMapping(value = "/{type}/{currency}/downloadExcel",method = RequestMethod.POST)
    public String downloadExcel(@PathVariable(value = "currency")String currency,@PathVariable(value = "type")String type,
                                @RequestParam Map<String,Object> paramsMap,
                                HttpServletResponse response,
                                HttpServletRequest request) throws Exception{
        List<Map<String,Object>> list = null;
        Map<String,Object> map = new HashMap<>();
        map.put("dateline", DateUtils.dateFormat(new Date(),DateUtils.DATE_SMALL_STR));
        if(type.equalsIgnoreCase(DEPOSIT)) {
            if (currency.equalsIgnoreCase(ETH)) {
                list = withdrawOrDepositService.depositETH(map);
            }else if(currency.equalsIgnoreCase(BTC)) {
                list = withdrawOrDepositService.depositBTC(map);
            }else if(currency.equalsIgnoreCase(LTC)){
                list = withdrawOrDepositService.depositLTC(map);
            }
        }else if(type.equalsIgnoreCase(WITHDRAW)){
            if (currency.equalsIgnoreCase(ETH)) {
                list = withdrawOrDepositService.withdrawETH(map);
            }else if(currency.equalsIgnoreCase(BTC)) {
                list = withdrawOrDepositService.withdrawBTC(map);
            }else if(currency.equalsIgnoreCase(LTC)){
                list = withdrawOrDepositService.withdrawLTC(map);
            }else if(currency.equalsIgnoreCase(BCC)){
                list = withdrawOrDepositService.withdrawBCC(map);
            }
        }else if(type.equalsIgnoreCase(WITHDRAW_SUCCESS)){
            if (currency.equalsIgnoreCase(ETH)){
                list = withdrawOrDepositService.withdrawSuccessETH(map);
            }else{
                list = withdrawOrDepositService.withdrawSuccessBCC(map);
            }
        }
        String fileName = type +"-" + currency + "-data-" + DateUtils.dateFormat(new Date(),DateUtils.DATE_SMALL_STR);
        String head = "id,user_id,name,phone,phone_country,value,account_type,amount,bj_time,email";
        String body = "id,user_id,name,phone,phone_country,value,account_type,amount,bj_time,user_email";
        boolean flag = ExcelUtils.createExcel(fileName,"",list,head,body);
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        if(flag){
            OutputStream outputStream = response.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            FileInputStream fileInputStream = new FileInputStream(ReportConstant.downloadBasePath+fileName+".xls");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bufferedInputStream.read(buff, 0, buff.length))) {
                bufferedOutputStream.write(buff, 0, bytesRead);
            }
            bufferedInputStream.close();
            bufferedOutputStream.close();
            ExcelUtils.deleteFile(ReportConstant.downloadBasePath+fileName+".xls");
            return null;
        }

        return null;
    }

}
