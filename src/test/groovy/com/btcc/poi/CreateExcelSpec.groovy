package com.btcc.poi

import com.btcc.AppContextSpec
import com.btcc.util.ExcelUtils
import com.github.pagehelper.PageInfo
import net.sf.json.JSONObject
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

class CreateExcelSpec extends AppContextSpec {



    def "测试超过65535行时，是否出错" (){
        when:""
            Workbook workbook = new HSSFWorkbook();
            List<String> tableHList = new ArrayList<>();
            tableHList.add("测试");
            CreateExcelImpl<Integer> createExcel = new CreateExcelImpl<Integer>();
            List<Integer> datalist = new ArrayList<>();
            for(int i=0;i<6553;i++){
                datalist.add(i)
            }
            def intRowExcel = {
                intRowExcel ->
                    Row row = intRowExcel.getRow()
                    Integer data = intRowExcel.getData()
                    row.createCell(0).setCellValue(data);
            }
            if(datalist.size() >= ExcelUtils.rowNum){
                def count = datalist.size() /ExcelUtils.rowNum ;
                def index = 0;
                for(int i=0;i<count;i++){
                    Sheet sheet = workbook.createSheet("a"+i);
                    createExcel.createHeader(sheet,tableHList,0);
                    index = createExcel.createBody(index,sheet,datalist,intRowExcel)
                }
            }else{
                Sheet sheet = workbook.createSheet("a");
                createExcel.createHeader(sheet,tableHList,0);
                createExcel.createBody(0,sheet,datalist,intRowExcel)
            }
            File file = new File("/Users/peiyou/Desktop/project/bbb.xls")
            workbook.write(new FileOutputStream(file));
        then:"thrown"
            file.exists()
    }


    def "测试动态sql生成的excel" (){
        given: "get excel"
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("a");
        Map<String,Object> map = new HashMap<>();
        map.put("pageNum",0);
        map.put("pageSize",0);
        JSONObject obj = baseService.setParams(map,5,systemService);
        String[] tableHeads = ((String)obj.get("tableHead")).split(",");
        List<String> tableHList = new ArrayList<>();
        Collections.addAll(tableHList,tableHeads);
        CreateExcelImpl<Object> createExcel = new CreateExcelImpl<Object>();
        createExcel.createHeader(sheet,tableHList,0);
        List<Map<String,Object>> list = null;
        when:"set"
        PageInfo<Map<String,Object>> map1 =  baseService.queryData(map);
        list = map1.getList();
        List<String> bodys = new ArrayList<>();
        String[] tBody = ((String)obj.get("tableBody")).split(",");
        Collections.addAll(bodys,tBody);
        def mapRowExcel = {
            mapRowExcel ->
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
        createExcel.createBody(1,sheet,list,bodys,mapRowExcel)

        File file = new File("/Users/peiyou/Desktop/project/aaa.xls")
        workbook.write(new FileOutputStream(file));

        then:"result"
        file.exists()

    }

}
