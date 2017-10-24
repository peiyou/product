package com.btcc.web.report.mk.mapper;

import com.btcc.datasource.ReportDataSource;
import com.btcc.web.report.mk.entity.BusinessFrom;
import com.btcc.web.report.mk.entity.BusinessReportDay;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2016/10/31.
 */
@ReportDataSource
public interface ReportMapper {

    void insertBusinessReportDay(BusinessReportDay reportDay);

    void updateBusinessReportDay(BusinessReportDay reportDay);

    int queryBusinessReportDayByStatisDate(String statisDate);

    void deleteBusinessReportDayByStatisDate(String statisDate);

    /**
     * 查询当前表中最大的一个统计日期
     * @return
     */
    String queryMaxStatisDateForBusinessReportDay();

    /**
     * 查询报表数据
     * @param from
     * @return
     */
    List<BusinessReportDay> queryBusinessReportDay(Map<String,Object> from);

    /**
     * 删除快照数据，只会删除当天的
     */
    void deleteUserSnapshot();


    /**
     * 插入指定id的用户的快照
     * 快照数据由sql查询得来
     * @param userIds
     */
    void insertUserSnapshot(@Param(value="userIds") String userIds);

    void deleteUserApiTradeDataByDay(Map<String,Object> map);

    void insertUserApiTradeData(List<Map<String,Object>> list);

    void insertOperationData(Map<String,Object> param);
}
