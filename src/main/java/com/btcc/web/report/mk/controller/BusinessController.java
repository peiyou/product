package com.btcc.web.report.mk.controller;

import com.btcc.util.DateUtils;
import com.btcc.web.report.mk.entity.BusinessFrom;
import com.btcc.web.report.mk.service.BusinessService;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 10/27/16.
 */
@Controller
@RequestMapping("/BusinessController")
public class BusinessController {

    @Autowired
    BusinessService businessService;



    /**
     * 比特币充值数据
     * @param model
     * @return
     */
    @RequestMapping("/payBtcDataTotalIndex")
    public ModelAndView payBtcDataTotalIndex(ModelAndView model){
        BusinessFrom form = new BusinessFrom();
        String date = DateUtils.addDate(new Date(),-1,DateUtils.DATE_SMALL_STR);
        form.setStartDate(date);
        form.setEndDate(date);
        model.addObject("form",form);
        model.setViewName("/business-mk/fund-btc-index");
        return model;
    }

    @RequestMapping(value = "/payBtcDataTotal" , method = RequestMethod.POST)
    @ResponseBody
    public String payBtcDataTotal(BusinessFrom from){
        JSONObject obj = new JSONObject();
        PageInfo<Map<String,Object>> list =businessService.payBtcDataTotal(from);
        obj.put("success",list);
        return obj.toString();
    }


    /**
     * 比特币提现数据
     * @param model
     * @return
     */
    @RequestMapping("/takeBtcDataTotalIndex")
    public ModelAndView takeBtcDataTotalIndex(ModelAndView model){
        BusinessFrom form = new BusinessFrom();
        String date = DateUtils.addDate(new Date(),-1,DateUtils.DATE_SMALL_STR);
        form.setStartDate(date);
        form.setEndDate(date);
        model.addObject("form",form);
        model.setViewName("/business-mk/withdraw-btc-index");
        return model;
    }

    /**
     * 比特币提现数据
     * @param from
     * @return
     */
    @RequestMapping(value = "/takeBtcDataTotal" , method = RequestMethod.POST)
    @ResponseBody
    public String takeBtcDataTotal(BusinessFrom from){
        JSONObject obj = new JSONObject();
        PageInfo<Map<String,Object>> list =businessService.takeBtcDataTotal(from);
        obj.put("success",list);
        return obj.toString();
    }


    /**
     * 人民币充值数据
     * @param model
     * @return
     */
    @RequestMapping("/payCnyDataTotalIndex")
    public ModelAndView payCnyDataTotalIndex(ModelAndView model){
        BusinessFrom form = new BusinessFrom();
        String date = DateUtils.addDate(new Date(),-1,DateUtils.DATE_SMALL_STR);
        form.setStartDate(date);
        form.setEndDate(date);
        model.addObject("form",form);
        model.setViewName("/business-mk/fund-cny-index");
        return model;
    }

    @RequestMapping(value = "/payCnyDataTotal" , method = RequestMethod.POST)
    @ResponseBody
    public String payCnyDataTotal(BusinessFrom from){
        JSONObject obj = new JSONObject();
        PageInfo<Map<String,Object>> list =businessService.payCnyDataTotal(from);
        obj.put("success",list);
        return obj.toString();
    }

    /**
     * 比特币提现数据
     * @param model
     * @return
     */
    @RequestMapping("/takeCnyDataTotalIndex")
    public ModelAndView takeCnyDataTotalIndex(ModelAndView model){
        BusinessFrom form = new BusinessFrom();
        String date = DateUtils.addDate(new Date(),-1,DateUtils.DATE_SMALL_STR);
        form.setStartDate(date);
        form.setEndDate(date);
        model.addObject("form",form);
        model.setViewName("/business-mk/withdraw-cny-index");
        return model;
    }

    /**
     * 比特币提现数据
     * @param from
     * @return
     */
    @RequestMapping(value = "/takeCnyDataTotal" , method = RequestMethod.POST)
    @ResponseBody
    public String takeCnyDataTotal(BusinessFrom from){
        JSONObject obj = new JSONObject();
        PageInfo<Map<String,Object>> list =businessService.takeCnyDataTotal(from);
        obj.put("success",list);
        return obj.toString();
    }

    /**
     *  交易用户数
     *  交易量
     * @param model
     * @return
     */
    @RequestMapping("/userBusinessDataIndex")
    public ModelAndView userBusinessDataIndex(ModelAndView model){
        BusinessFrom form = new BusinessFrom();
        String date = DateUtils.addDate(new Date(),-1,DateUtils.DATE_SMALL_STR);
        form.setStartDate(date);
        form.setEndDate(date);
        model.addObject("form",form);
        model.setViewName("/business-mk/trade-user-index");
        return model;
    }

    /**
     * 交易用户数
     * 交易量
     * @param from
     * @return
     */
    @RequestMapping(value = "/userBusinessData" , method = RequestMethod.POST)
    @ResponseBody
    public String userBusinessData(BusinessFrom from){
        JSONObject obj = new JSONObject();
        PageInfo<Map<String,Object>> list =businessService.userBusinessData(from);
        obj.put("success",list);
        return obj.toString();
    }
}
