package com.btcc.web.report.website.mapper;

import com.btcc.datasource.ReportDataSource;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/8/17.
 */
@ReportDataSource
public interface WebSiteWeekReportMapper {

    public void insertWebSiteWeekReport(Map<String,Object> param);

    public void updateWebSiteWeekReport(Map<String,Object> param);

    public List<Map<String,Object>> queryWebSiteWeekReport(Map<String,Object> param);

    public void deleteWebSiteWeekReport(Map<String,Object> param);

}
