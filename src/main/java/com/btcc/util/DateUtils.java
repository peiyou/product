package com.btcc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by peiyou on 10/26/16.
 */
public class DateUtils {
    /**定义常量**/
    public static final String DATE_JFP_STR="yyyyMM";
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_KEY_STR = "yyMMddHHmmss";

    /**
     * 使用预设格式提取字符串日期
     * @param strDate 日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate,DATE_FULL_STR);
    }

    /**
     * 使用用户格式提取字符串日期
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateFormat(Date date,String pattern){
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
    /**
     * 两个时间比较
     * @param date1
     * @return
     */
    public static int compareDateWithNow(Date date1){
        Date date2 = new Date();
        int rnum =date1.compareTo(date2);
        return rnum;
    }

    /**
     * 两个时间比较(时间戳比较)
     * @param date1
     * @return
     */
    public static int compareDateWithNow(long date1){
        long date2 = dateToUnixTimestamp();
        if(date1>date2){
            return 1;
        }else if(date1<date2){
            return -1;
        }else{
            return 0;
        }
    }


    /**
     * 获取系统当前时间
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
        return df.format(new Date());
    }

    /**
     * 获取系统当前时间
     * @return
     */
    public static String getNowTime(String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        return df.format(new Date());
    }

    /**
     * 获取系统当前计费期
     * @return
     */
    public static String getJFPTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);
        return df.format(new Date());
    }

    /**
     * 将指定的日期转换成Unix时间戳
     * @param date date 需要转换的日期 yyyy-MM-dd HH:mm:ss
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 将指定的日期转换成Unix时间戳
     * @param dateFormat date 需要转换的日期 yyyy-MM-dd
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date, String dateFormat) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 将当前日期转换成Unix时间戳
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp() {
        long timestamp = new Date().getTime();
        return timestamp;
    }


    /**
     * 将Unix时间戳转换成日期
     * @param  timestamp 时间戳
     * @return String 日期字符串
     */
    public static String unixTimestampToDate(long timestamp) {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sd.format(new Date(timestamp));
    }

    /**
     * 日期加减天
     * @param date  操作的时间
     * @param num 天数（正数则加，负数则减）
     * @return
     */
    public static Date addDate(Date date,int num){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,num);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        return date;
    }

    /**
     * 日期加减天
     * @param date
     * @param num
     * @param pattern
     * @return
     */
    public static String addDate(Date date,int num,String pattern){
        return dateFormat(addDate(date,num),pattern);
    }

    /**
     * 日期加减天
     * @param date
     * @param num
     * @param pattern
     * @return
     */
    public static String addDate(String date,int num,String pattern){
        Date date1 = parse(date,pattern);
        return addDate(date1,num,pattern);

    }

    public static Date addMonth(Date date,int num){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.MONTH,num);//把日期往后增加一月.整数往后推,负数往前移动
        date = calendar.getTime();
        return date;
    }

    public static String addMonth(String date,int num,String pattern){
        return dateFormat(addMonth(parse(date,pattern),num),pattern);
    }

    /**
     * date1 - date2 得到的天数差
     * @param date1
     * @param date2
     * @return
     */
    public static int diffDate(String date1,String date2){
        Date date11 = parse(date1,DateUtils.DATE_SMALL_STR);
        Date date22 = parse(date2,DateUtils.DATE_SMALL_STR);
        return diffDate(date11,date22);
    }

    public static int diffDate(Date date1,Date date2){
        long t = date1.getTime() - date2.getTime();
        int diff = (int)(t / 1000 / 60 / 60 / 24);
        return diff;
    }

    public static String getLateWeekOfMonday(String date,String pattern){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(parse(date,pattern));
        calendar.add(Calendar.WEEK_OF_YEAR,-1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return DateUtils.dateFormat(calendar.getTime(),pattern);
    }


    public static void main(String[] args) {
        String date = addMonth("2017-01-01",1,DateUtils.DATE_SMALL_STR);
        System.out.println(date);
        String startDate = getLateWeekOfMonday("2017-08-14",DateUtils.DATE_SMALL_STR);
        String endDate = addDate(startDate,7,DATE_SMALL_STR);
        System.out.println(startDate+"    " + endDate);
    }

}
