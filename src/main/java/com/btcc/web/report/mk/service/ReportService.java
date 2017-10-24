package com.btcc.web.report.mk.service;


import com.btcc.Parameter;
import com.btcc.constant.ReportConstant;
import com.btcc.util.DateUtils;
import com.btcc.util.EntityPropertyUtils;
import com.btcc.util.MathUtils;
import com.btcc.web.report.mk.entity.BusinessFrom;
import com.btcc.web.report.mk.entity.BusinessReportDay;
import com.btcc.web.report.mk.mapper.ReportMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by peiyou on 2016/10/31.
 */
@Service
public class ReportService {

    Logger logger = LoggerFactory.getLogger(ReportService.class);
    @Autowired
    ReportMapper reportMapper;

    @Autowired
    BusinessService businessService;

    /**
     * 给businessReportDay表加入数据
     * 如果数据已经存在，则先删除数据
     * 如果数据没有，则直接插入数据
     */
    public void insertBusinessReportDay(BusinessReportDay reportDay){
        int count = reportMapper.queryBusinessReportDayByStatisDate(reportDay.getStatisDate());
        if(count > 0){
            reportMapper.deleteBusinessReportDayByStatisDate(reportDay.getStatisDate());
        }
        try {
            reportMapper.insertBusinessReportDay(reportDay);
        }catch (Exception e){
            logger.error("有异常数据：" + reportDay.toString() );
            e.printStackTrace();
        }
    }


    public void updateBusinessReportDay(BusinessReportDay reportDay){
        reportMapper.updateBusinessReportDay(reportDay);
    }

    /**
     * 得到businessReportDay对象
     * @param from
     * @return
     */
    public Map<String,BusinessReportDay> getBusinessReportDay(BusinessFrom from){
        Map<String,BusinessReportDay> mapDays = new LinkedHashMap<>();
        //比特币充值数据
        PageInfo<Map<String,Object>> pageInfo = businessService.payBtcDataTotal(from);
        paramBusinessReportDay(pageInfo,mapDays);
        //比特币提现数据
        pageInfo = businessService.takeBtcDataTotal(from);
        paramBusinessReportDay(pageInfo,mapDays);
        //人民币充值数据
        pageInfo = businessService.payCnyDataTotal(from);
        paramBusinessReportDay(pageInfo,mapDays);
        //人民币提现数据
        pageInfo = businessService.takeCnyDataTotal(from);
        paramBusinessReportDay(pageInfo,mapDays);
        //用户交易数据
        pageInfo = businessService.userBusinessData(from);
        paramBusinessReportDay(pageInfo,mapDays);
        //交易量api
        List<Map<String,Object>> list = new ArrayList<>();
        //关于交易数据的，因为查询数据速度太慢，先去除。
       // List<Map<String,Object>> list = businessService.amountApiBusinessData(from);
       // paramBusinessReportDay(list,mapDays);
        //交易人数api数据
        //list = businessService.userApiBusinessData(from);
       // paramBusinessReportDay(list,mapDays);
        //当天新增借贷人民币用户数 当天新增借贷人民币量
        list = businessService.addBorrowedCnyDataInfo(from);
        paramBusinessReportDay(list,mapDays);
        //当天新增借贷BTC用户数 当天新增借贷BTC量
        list = businessService.addBorrowedBtcDataInfo(from);
        paramBusinessReportDay(list,mapDays);
        //借贷利息（已收取）
        list = businessService.borrowedInterestDataInfo(from);
        paramBusinessReportDay(list,mapDays);
        return mapDays;
    }

    /**
     * 为businessReportDay设置属性值
     * @param pageInfo
     * @param mapDays
     */
    private void paramBusinessReportDay(PageInfo<Map<String,Object>> pageInfo,Map<String,BusinessReportDay> mapDays){
        if(pageInfo.getList()!= null && pageInfo.getList().size() > 0){
            paramBusinessReportDay(pageInfo.getList(),mapDays);
        }
    }

    /**
     * 为BusinessReportDay设置属性值
     * 并修改数据
     * @param list
     * @param mapDays
     */
    private void paramBusinessReportDay(List<Map<String,Object>> list,Map<String,BusinessReportDay> mapDays){
        if(list!= null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                if (map.containsKey("date") && map.get("date") != null) {
                    String date = MathUtils.getString(map.get("date"));
                    if (!mapDays.containsKey(date)) {
                        BusinessFrom from = new BusinessFrom();
                        from.setStartDate(date);
                        from.setEndDate(date);
                        PageInfo<BusinessReportDay> days = this.queryBusinessReportDay(from);
                        BusinessReportDay day = null;
                        if(days.getList()!=null && days.getList().size() > 0){
                            day = days.getList().get(0);
                        }else{
                            day = new BusinessReportDay();
                            day.setStatisDate(date);
                            this.insertBusinessReportDay(day);
                        }
                        // day.setStatisDate(date);
                        mapDays.put(date, day);
                    }
                    BusinessReportDay day = mapDays.get(date);
                    day = (BusinessReportDay) EntityPropertyUtils.getPropertyByMap(map, day);
                    this.updateBusinessReportDay(day);
                    mapDays.put(date, day);
                } else
                    continue;
            }
        }
    }


    /**
     * 得到当前最大的统计日期
     * @return
     */
    public String queryMaxStatisDateForBusinessReportDay(){
        return reportMapper.queryMaxStatisDateForBusinessReportDay();
    }

    /**
     * 查询交易数据
     * @param from
     * @return
     */
    public PageInfo<BusinessReportDay> queryBusinessReportDay(BusinessFrom from){
        PageInfo<BusinessReportDay> page = null;
        PageHelper.startPage(from.getPageNum(),from.getPageSize());
        Parameter parameter = new Parameter(from);
        List<BusinessReportDay> list = reportMapper.queryBusinessReportDay(parameter);
        page = new PageInfo<BusinessReportDay>(list);
        return page;
    }


    public void deleteUserSnapshot(){
        reportMapper.deleteUserSnapshot();
    }

    public void insertUserSnapshot(String userIds){
        reportMapper.insertUserSnapshot(userIds);
    }


    public void deleteUserApiTradeDataByDay(Map<String,Object> map){
        reportMapper.deleteUserApiTradeDataByDay(map);
    }

    public void insertUserApiTradeData(List<Map<String,Object>> list){
        if(list == null || list.size() == 0 ) return ;
        int size = list.size();
        int page = size / ReportConstant.rowNum;
        int remainder = size % ReportConstant.rowNum;
        page = remainder > 0 ? page + 1 : page;
        int begin = 0;
        int end = 0;
        if(size > ReportConstant.rowNum){
            for(int i=0;page > i; i++){
                end = (i==page-1) ? end + remainder : end + ReportConstant.rowNum;
                List<Map<String,Object>> temp = new ArrayList<>(list.subList(begin,end));
                reportMapper.insertUserApiTradeData(temp);
                begin = end;
            }
        }else{
            reportMapper.insertUserApiTradeData(list);
        }

    }

    public void insertOperationData(Map<String,Object> param){
        reportMapper.insertOperationData(param);
    }
}
