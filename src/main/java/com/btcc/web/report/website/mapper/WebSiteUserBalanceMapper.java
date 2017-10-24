package com.btcc.web.report.website.mapper;

import com.btcc.datasource.ReportDataSource;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/8/17.
 */
@ReportDataSource
public interface WebSiteUserBalanceMapper {

    public void insertWebSiteUserBalance(List<Map<String,Object>> param);

    public List<Map<String,Object>> queryUserBalance(Map<String,Object> param);

    public void deleteWebSiteUserBalance();
}
