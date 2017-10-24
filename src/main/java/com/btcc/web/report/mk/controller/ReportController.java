package com.btcc.web.report.mk.controller;

import com.btcc.util.DateUtils;
import com.btcc.web.report.mk.entity.BusinessFrom;
import com.btcc.web.report.mk.entity.BusinessReportDay;
import com.btcc.web.report.mk.service.ReportService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

/**
 * Created by peiyou on 2016/11/4.
 */
@Controller
@RequestMapping("/reportController")
public class ReportController {
    private Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    ReportService reportService;

    /**
     * 交易数据报表
     * @param model
     * @return
     */
    @RequestMapping("/businessReportIndex")
    public ModelAndView businessReportIndex(ModelAndView model, BusinessFrom from){
        if(from.getStartDate() == null || from.getEndDate() == null){
            String lastDate = DateUtils.addDate(new Date(),-1,DateUtils.DATE_SMALL_STR);
            from.setEndDate(lastDate);
            from.setStartDate(lastDate);
            from.setPageNum(1);
            from.setPageSize(50);
        }
        PageInfo<BusinessReportDay> page = reportService.queryBusinessReportDay(from);
        model.addObject("pageinfo",page);
        model.addObject("form",from);
        model.setViewName("/business-mk/business-day-index");
        return model;
    }
}
