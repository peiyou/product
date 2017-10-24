package com.btcc.build.sql;

import java.util.Map;

/**
 * Created by peiyou on 2016/11/18.
 */
public class TextNode implements SqlNode {

    private int beginIndex;
    private int endIndex;
    private String text;


    public TextNode(String text,int beginIndex,int endIndex){
        this.text = text;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void apply(SqlContext sqlContext) {
        sqlContext.sqlBuilder.append(text);
    }

    public int getEndIndex(){
        return endIndex;
    }
}
