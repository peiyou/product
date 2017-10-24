package com.btcc.build.sql;

import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;

import java.util.Map;

/**
 * Created by peiyou on 2016/11/18.
 */
public class IFSqlNode implements SqlNode{

    public final static String beginOperator = "#if";
    public final static String endOperator = "!#if";
    public final static String endCondition = "]";
    public final static String beginCondition = "[";
    private int beginIndex;
    private int endIndex;
    private String text;


    public IFSqlNode(String text,int beginIndex,int endIndex){
        this.text = text;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void apply(SqlContext sqlContext) {
        sqlContext.sqlBuilder.append(analyse(sqlContext));
    }

    private boolean check(String expression,SqlContext context){
        try {
            Boolean obj = (Boolean)Ognl.getValue(expression, context.paramentMap);
            return obj;
        }catch (OgnlException e){
            new RuntimeException(e);
        }catch (Exception e){
            new RuntimeException("if expression error ,please modify expression.");
        }
        return false;
    }

    private String analyse(SqlContext context){
        String expression = text.substring(text.indexOf(beginCondition) + 1,text.indexOf(endCondition));
        if(check(expression,context)){
            return text.substring(text.indexOf(endCondition) + 1,text.indexOf(endOperator));
        }
        return "";

    }

    public int getEndIndex(){
        return endIndex;
    }
}
