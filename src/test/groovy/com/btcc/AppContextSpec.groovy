package com.btcc

import com.btcc.poi.CreateExcelImpl
import com.btcc.poi.RowExcel
import com.btcc.web.base.service.BaseService
import com.btcc.web.system.service.SystemService
import com.github.pagehelper.PageInfo
import net.sf.json.JSONObject
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.function.Consumer



class AppContextSpec extends Specification {


    @Shared
    ConfigurableApplicationContext context
    @Shared
    SystemService systemService
    @Shared
    BaseService baseService

    void setupSpec(){
        Future future = Executors.newSingleThreadExecutor().submit(
                new Callable() {
                    @Override
                    ConfigurableApplicationContext call() throws Exception {
                        return (ConfigurableApplicationContext) SpringApplication.run(Application.class)
                    }
                }
        )
        context = future.get(60, TimeUnit.SECONDS)
        systemService = context.getBean(SystemService.class)
        baseService = context.getBean(BaseService.class)
    }


}
