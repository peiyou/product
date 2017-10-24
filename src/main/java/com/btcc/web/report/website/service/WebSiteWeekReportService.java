package com.btcc.web.report.website.service;

import com.btcc.web.report.website.mapper.WebSiteUserBalanceMapper;
import com.btcc.web.report.website.mapper.WebSiteWeekReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/8/17.
 */
@Service
public class WebSiteWeekReportService {

    @Autowired
    WebSiteWeekReportMapper webSiteWeekReportMapper;

    @Autowired
    WebSiteUserBalanceMapper webSiteUserBalanceMapper;

    public void insertWebSiteWeekReport(Map<String,Object> param){
        webSiteWeekReportMapper.insertWebSiteWeekReport(param);
    }

    public void updateWebSiteWeekReport(Map<String,Object> param){
        webSiteWeekReportMapper.updateWebSiteWeekReport(param);
    }

    public List<Map<String,Object>> queryWebSiteWeekReport(Map<String,Object> param){
        return webSiteWeekReportMapper.queryWebSiteWeekReport(param);
    }

    public void insertWebSiteUserBalance(List<Map<String,Object>> param){
        webSiteUserBalanceMapper.insertWebSiteUserBalance(param);
    }

    public List<Map<String,Object>> queryUserBalance(Map<String,Object> param){
        return webSiteUserBalanceMapper.queryUserBalance(param);
    }

    public void deleteWebSiteUserBalance(){
        webSiteUserBalanceMapper.deleteWebSiteUserBalance();
    }

    public void deleteWebSiteWeekReport(Map<String,Object> param){
        webSiteWeekReportMapper.deleteWebSiteWeekReport(param);
    }

}
