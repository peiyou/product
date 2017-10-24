package task;
import com.btcc.Application;
import com.btcc.poi.CreateExcelImpl;
import com.btcc.poi.RowExcel;
import com.btcc.web.base.service.BaseService;
import com.btcc.web.system.service.SystemService;
import com.btcc.web.task.entity.ScheduleJob;
import com.btcc.web.task.service.TaskService;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by peiyou on 2016/11/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestTask {

    @Autowired
    TaskService taskService;
    @Autowired
    SystemService systemService;
    @Autowired
    BaseService baseService;

    @Test
    public void test(){
        ScheduleJob job = new ScheduleJob();
        job.setCronExpression("* */1 * * * ?");
        job.setBeanClass("com.btcc.web.task.TaskApplication");
        job.setJobGroup("test");
        job.setJobName("com.btcc.web.task.TaskApplication.taskTestAdd");
        job.setMethodName("taskTestAdd");
        taskService.insertSchedule(job);
    }


    @Test
    public void test2(){
        ScheduleJob job = taskService.selectScheduleByJobId(3L);
        taskService.deleteScheduleByJobId(job.getJobId());
        
    }


    @Test
    public void test3(){
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("a");
        Map<String,Object> map = new HashMap<>();
        map.put("pageNum",0);
        map.put("pageSize",0);
        JSONObject obj = baseService.setParams(map,5,systemService);
        String[] tableHeads = ((String)obj.get("tableHead")).split(",");
        List<String> tableHList = new ArrayList<>();
        Collections.addAll(tableHList,tableHeads);
        CreateExcelImpl<Object> createExcel
            = new CreateExcelImpl<Object>();
        createExcel.createHeader(sheet,tableHList,0);
        List<Map<String,Object>> list = null;
        PageInfo<Map<String,Object>> map1 =  baseService.queryData(map);
        list = map1.getList();
        List<String> bodys = new ArrayList<>();
        String[] tBody = ((String)obj.get("tableBody")).split(",");
        Collections.addAll(bodys,tBody);
        createExcel.createBody(1,sheet,list,bodys,mapRowExcel -> {
                    Row row = mapRowExcel.getRow();
                    Map<String,Object> mapdata = mapRowExcel.getData();
                    List<String> keys = mapRowExcel.getKeys();
                    for(int i = 0;i<keys.size();i++){
                        Cell cell = row.createCell(i);

                        if(mapdata.containsKey(keys.get(i))) {
                            Object a1 = mapdata.get(keys.get(i));
                            if(a1 != null)
                                cell.setCellValue(a1.toString());
                            else
                                cell.setCellValue("");
                        }else{
                            cell.setCellValue("");
                        }
                    }
                }
        );
        try {
            File file = new File("/Users/peiyou/Desktop/project/aaa.xls");
            workbook.write(new FileOutputStream(file));
        }catch (Exception e){

        }

        
    }

}
