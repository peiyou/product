package com.btcc.poi;

import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class RowExcel<R> {
    private Row row;

    private R data;

    private List<String> keys;

    public RowExcel(Row row,R data){
        this.row = row;
        this.data = data;
    }

    public RowExcel(Row row,R data,List<String> keys){
        this.row = row;
        this.data = data;
        this.keys = keys;
    }

    public Row getRow() {
        return row;
    }

    public R getData() {
        return data;
    }

    public List<String> getKeys() {
        return keys;
    }
}
