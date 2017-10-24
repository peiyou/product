package com.btcc.poi;

import com.btcc.util.ExcelUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CreateExcelImpl<T> implements ICreateExcel<T> {

    @Override
    public void createHeader(Sheet sheet, List<String> header,int index) {
        createHeader(sheet,header,index,null);
    }


    @Override
    public void createHeader(Sheet sheet, List<String> header, int index, CellStyle cellStyle) {
        Row row = sheet.createRow(index);
        for(int i=0;i<header.size();i++) {
            Cell cell = row.createCell(i);
            if(header.get(i) == null ||
                    "".equals(header.get(i))) {
                cell.setCellValue("");
            }else{
                cell.setCellValue(header.get(i));
            }
            if(cellStyle != null){
                cell.setCellStyle(cellStyle);
            }
        }
    }

    /**
     * 处理数组对象的
     * @param beginIndex
     * @param sheet
     * @param data
     */
    @Override
    public int createBody(int beginIndex, Sheet sheet, List<T> data) {
        if(data == null || data.isEmpty()) return -1;
        if(data.get(0) instanceof Map){
            throw new RuntimeException("请使用对应的map版本的方法！");
        }else if(data.get(0) instanceof Array){
            // FIXME: 2016/12/19
            throw new RuntimeException("暂未实现数组加载的方法！");
        }
        return -1;
    }

    /**
     * 处理自定义对象的
     * @param beginIndex
     * @param sheet
     * @param data
     * @param consumer
     */
    @Override
    public int  createBody(int beginIndex, Sheet sheet, List<T> data, Consumer<RowExcel<T>> consumer) {
        if(data == null || data.isEmpty()) return -1;
        int row = beginIndex % ExcelUtils.rowNum;
        if(row == 0) row = 1;
        for(int i=beginIndex;i<data.size();i++) {
            consumer.accept(new RowExcel<T>(sheet.createRow(row), data.get(i)));
            if(row % ExcelUtils.rowNum == 0)
                break;
            row ++;
        }
        return beginIndex+=row;
    }

    /**
     * 处理map类型的
     * @param beginIndex
     * @param sheet
     * @param data
     * @param keys
     * @param consumer
     */
    @Override
    public int createBody(int beginIndex, Sheet sheet, List<Map<String, T>> data,
                           List<String> keys, Consumer<RowExcel<Map<String, T>>> consumer) {
        if(data == null || data.isEmpty()) return -1;
        int row = beginIndex % ExcelUtils.rowNum;
        if(row == 0) row = 1;
        for(int i=beginIndex;i<data.size();i++){
            consumer.accept(new RowExcel<Map<String, T>>(sheet.createRow(row),data.get(i),keys));

            if(row % ExcelUtils.rowNum == 0)
                break;
            row ++;
        }
        return beginIndex+=row;
    }
}


