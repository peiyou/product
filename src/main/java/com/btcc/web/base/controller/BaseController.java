package com.btcc.web.base.controller;

import com.btcc.constant.ReportConstant;
import com.btcc.util.DateUtils;
import com.btcc.util.ExcelUtils;
import com.btcc.web.base.service.BaseService;
import com.btcc.web.system.entity.BuildReport;
import com.btcc.web.system.service.SystemService;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by peiyou on 2016/11/18.
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    BaseService baseService;

    @Autowired
    SystemService systemService;

    @RequestMapping(value="/{id}/index")
    public ModelAndView index(ModelAndView modelAndView, @PathVariable(value = "id")int id,
                              @RequestParam Map<String,Object> paramsMap){
        if(paramsMap == null) paramsMap = new HashMap<>();
        JSONObject obj = baseService.setParams(paramsMap,id,systemService);
        PageInfo<Map<String,Object>> pageInfo = baseService.queryData(paramsMap);
        List<Map<String,Object>> list = pageInfo.getList();
        modelAndView.setViewName("/base/index");
        paramsMap.put("menuName",(String)obj.get("menuName"));
        modelAndView.addObject("tableHeads",((String)obj.get("tableHead")).split(","));
        String[] tableBodys = ((String)obj.get("tableBody")).split(",");
        for(int i=0;i<tableBodys.length;i++){
            tableBodys[i] = tableBodys[i].trim();
        }
        modelAndView.addObject("tableBodys",tableBodys);
        modelAndView.addObject("from",paramsMap);
        modelAndView.addObject("pageinfo",pageInfo);
        modelAndView.addObject("id",id);
        modelAndView.addObject("isDownload",obj.containsKey("isDownload")?obj.getInt("isDownload"):0);
        modelAndView.addObject("params",obj.get("params"));
        return modelAndView;
    }



    @RequestMapping(value = "/{id}/downloadExcel",method = RequestMethod.POST)
    public String downloadExcel(@PathVariable(value = "id")int id,
                                @RequestParam Map<String,Object> paramsMap,
                                HttpServletResponse response,
                                HttpServletRequest request) throws Exception{
        if(paramsMap == null) paramsMap = new HashMap<>();
        //设置分页数为0，表示查询全部数据
        paramsMap.put("pageNum",0);
        paramsMap.put("pageSize",0);
        JSONObject obj = baseService.setParams(paramsMap,id,systemService);
        String menuName = obj.getString("menuName");
        String fileName = menuName + "_" + DateUtils.getNowTime(DateUtils.DATE_KEY_STR);
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        String basePath = "";//BaseController.class.getResource("/").getPath();
        logger.info("basePath-->" + basePath);
        boolean flag = ExcelUtils.createExcel(fileName,baseService,obj,paramsMap,basePath);
        if(flag){
            OutputStream outputStream = response.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            FileInputStream fileInputStream = new FileInputStream(basePath + ReportConstant.downloadBasePath+fileName+".xls");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bufferedInputStream.read(buff, 0, buff.length))) {
                bufferedOutputStream.write(buff, 0, bytesRead);
            }
            bufferedInputStream.close();
            bufferedOutputStream.close();
            ExcelUtils.deleteFile(basePath+ReportConstant.downloadBasePath+fileName+".xls");
            return null;
        }

        return null;
    }

}
