package com.btcc.poi;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface ICreateExcel<T> {



    void createHeader(Sheet sheet,List<String> header,int index);

    void createHeader(Sheet sheet, List<String> header, int index, CellStyle cellStyle);

    int createBody(int beginIndex,Sheet sheet,List<T> data);

    int createBody(int beginIndex, Sheet sheet, List<T> data, Consumer<RowExcel<T>> consumer);

    int createBody(int beginIndex,Sheet sheet,List<Map<String,T>> data,List<String> keys,Consumer<RowExcel<Map<String,T>>> consumer);

    enum SheetType {
        XLSX,XLS
    }
}
