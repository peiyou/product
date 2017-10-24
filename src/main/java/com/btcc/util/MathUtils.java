package com.btcc.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by peiyou on 2016/11/1.
 */
public class MathUtils {
    public static BigDecimal getBigDecimal(Object value ) {
        BigDecimal ret = null;
        if( value != null ) {
            if( value instanceof BigDecimal ) {
                ret = (BigDecimal) value;
            } else if( value instanceof String ) {
                ret = new BigDecimal( (String) value );
            } else if( value instanceof BigInteger) {
                ret = new BigDecimal( (BigInteger) value );
            } else if( value instanceof Number ) {
                ret = new BigDecimal( ((Number)value).doubleValue() );
            } else {
                throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");
            }
        }
        return ret;
    }

    public static Integer getInteger(Object value){
        Integer ret = null;
        if(value != null){
            if(value instanceof Number){
                ret = ((Number) value).intValue();
            }else if(value instanceof String){
                ret = Integer.valueOf((String)value);
            }else{
                throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a Integer.");
            }

        }
        return ret;
    }

    public static String getString(Object value){
        String ret = null;
        if(value != null){
            if(value instanceof String){
                ret = (String) value;
            }else if(value instanceof Number){
                ret = String.valueOf((Number)value);
            }else{
                throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a String.");
            }
        }
        return ret;
    }

    public static Double getDouble(Object value){
        Double ret = null;
        if(value != null){
            if(value instanceof String){
                ret = Double.valueOf((String)value);
            }else if(value instanceof BigDecimal){
                ret = ((BigDecimal) value).setScale(8,BigDecimal.ROUND_HALF_UP).doubleValue();
            }else if(value instanceof Number){//防止double数据过长，数据库存不下，而报错的问题,所以这个地方做了四舍五入
                ret =  new BigDecimal( ((Number)value).doubleValue() )
                        .setScale(8,BigDecimal.ROUND_HALF_UP)
                        .doubleValue();
            }else{
                throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a Double.");
            }

        }
        return ret;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
