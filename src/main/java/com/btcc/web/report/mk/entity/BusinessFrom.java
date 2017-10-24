package com.btcc.web.report.mk.entity;

import com.btcc.util.BaseBean;

/**
 * Created by peiyou on 10/27/16.
 */
public class BusinessFrom extends BaseBean{
    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
