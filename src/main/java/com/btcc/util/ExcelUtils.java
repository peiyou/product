package com.btcc.util;

import com.btcc.constant.ReportConstant;
import com.btcc.poi.CreateExcelImpl;
import com.btcc.poi.RowExcel;
import com.btcc.web.base.service.BaseService;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ExcelUtils {

    public static final Integer rowNum = 65535; //边界(65535)

    private static HSSFDataFormat dataFormat = null;

    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    public static boolean createExcel(final String fileName,BaseService baseService,
                                      JSONObject jsonObject, Map<String,Object> param,String basePath){
        try {
            Workbook workbook = new HSSFWorkbook();
            if(dataFormat == null) {
                dataFormat = (HSSFDataFormat) workbook.createDataFormat();
            }
            String[] tableHeads = ((String) jsonObject.get("tableHead")).split(",");
            List<String> tableHList = new ArrayList<>();
            Collections.addAll(tableHList, tableHeads);
            CreateExcelImpl<Object> createExcel = new CreateExcelImpl<Object>();
            List<Map<String, Object>> list = null;
            PageInfo<Map<String, Object>> map1 = baseService.queryData(param);
            list = map1.getList();
            List<String> bodys = new ArrayList<>();
            String[] tBody = ((String) jsonObject.get("tableBody")).split(",");
            Collections.addAll(bodys, tBody);
            if(list.size() >= ExcelUtils.rowNum) {
                int count = list.size() /ExcelUtils.rowNum ;
                int index = 0;
                for(int i=0;i<count;i++) {
                    Sheet sheet = workbook.createSheet(fileName + "" + i);
                    createExcel.createHeader(sheet, tableHList, 0);
                    createExcel.createBody(0, sheet, list, bodys, ExcelUtils.getExcelMapForConsumer());
                }
            }else{
                Sheet sheet = workbook.createSheet(fileName);
                createExcel.createHeader(sheet, tableHList, 0);
                createExcel.createBody(0, sheet, list, bodys, ExcelUtils.getExcelMapForConsumer());
            }
            existsFile(basePath+ReportConstant.downloadBasePath);
            File file = new File(basePath+ReportConstant.downloadBasePath + fileName + ".xls");
            OutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
        }catch (IOException e){
            logger.error(e.getMessage());
            return false;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     *
     * @param fileName
     * @param basePath
     * @param list
     * @param tableHeadstr
     * @param tBodyStr
     * @param <T>
     * @return
     */
    public  static <T> boolean createExcel(final String fileName,String basePath,List<Map<String,T>> list,String tableHeadstr,String tBodyStr){
        try {
            Workbook workbook = new HSSFWorkbook();
            if(dataFormat == null) {
                dataFormat = (HSSFDataFormat) workbook.createDataFormat();
            }

            String[] tableHeads = tableHeadstr.split(",");
            List<String> tableHList = new ArrayList<>();
            Collections.addAll(tableHList, tableHeads);
            CreateExcelImpl<T> createExcel = new CreateExcelImpl<T>();
            List<String> bodys = new ArrayList<>();
            String[] tBody = tBodyStr.split(",");
            Collections.addAll(bodys, tBody);
            if(list.size() >= ExcelUtils.rowNum) {
                int count = list.size() / ExcelUtils.rowNum  + (list.size() % ExcelUtils.rowNum == 0 ? 0 : 1);
                int index = 0;
                int beginIndex = 0;
                for(int i=0;i<count;i++) {
                    Sheet sheet = workbook.createSheet(fileName + "" + i);
                    createExcel.createHeader(sheet, tableHList, 0);
                    beginIndex = createExcel.createBody(beginIndex, sheet, list, bodys, ExcelUtils.getExcelMapForConsumer());
                }
            }else{
                Sheet sheet = workbook.createSheet(fileName);
                createExcel.createHeader(sheet, tableHList, 0);
                createExcel.createBody(0, sheet, list, bodys, ExcelUtils.getExcelMapForConsumer());
            }
            existsFile(basePath+ReportConstant.downloadBasePath);
            File file = new File(basePath+ReportConstant.downloadBasePath + fileName + ".xls");
            OutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
        }catch (IOException e){
            logger.error(e.getMessage());
            return false;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    private  static <T> Consumer<RowExcel<Map<String, T>>> getExcelMapForConsumer(){
        Consumer<RowExcel<Map<String, T>>> consumer = mapRowExcel -> {
            Row row = mapRowExcel.getRow();
            Map<String,T> mapdata = mapRowExcel.getData();
            List<String> keys = mapRowExcel.getKeys();
            for(int i = 0;i<keys.size();i++){
                Cell cell = row.createCell(i);

                if(mapdata.containsKey(keys.get(i))) {
                    if(keys.get(i)!=null && keys.get(i).endsWith("_int")) {
                        HSSFCellStyle cellStyle = (HSSFCellStyle)cell.getCellStyle();
                        cellStyle.setDataFormat(dataFormat.getFormat("#,###.########"));
                        cell.setCellStyle(cellStyle);

                        Object a1 = mapdata.get(keys.get(i));
                        if (a1 != null)
                            cell.setCellValue(a1.toString());
                        else
                            cell.setCellValue("");
                    }else{
                        Object a1 = mapdata.get(keys.get(i));
                        if (a1 != null)
                            cell.setCellValue(a1.toString());
                        else
                            cell.setCellValue("");
                    }
                }else{
                    cell.setCellValue("");
                }
            }
        };
        return consumer;
    }


    public static boolean existsFile(final String path){
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    public static void deleteFile(final String path){
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

}
