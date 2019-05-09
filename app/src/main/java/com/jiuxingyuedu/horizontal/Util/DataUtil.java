package com.jiuxingyuedu.horizontal.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间管理类
 */
public class DataUtil {
    /**
     * 获取系统时间
     * @return
     */
    public static String DataTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        String substring = time.substring(0, time.indexOf(" "));
       return substring;

    }


    public  static String DateTime(){
        Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
        int year = calendar.get(Calendar.YEAR);
//月
        String Mon="";
        int month = calendar.get(Calendar.MONTH)+1;
        if(month<10){
            Mon="0"+month;
        }else{
            Mon=month+"";
        }
//日

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String Day="";
        if(day<10){
            Day="0"+day;
        }else{
            Day=day+"";
        }
 String time=year+"/"+Mon+"/"+Day;
 return  time;

    }


    public  static String DateTime2(){
        Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
        int year = calendar.get(Calendar.YEAR);
//月
        String Mon="";
        int month = calendar.get(Calendar.MONTH)+1;
        if(month<10){
            Mon="0"+month;
        }else{
            Mon=month+"";
        }
//日

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String Day="";
        if(day<10){
            Day="0"+day;
        }else{
            Day=day+"";
        }
        String time=year+"-"+Mon+"-"+Day;
        return  time;

    }
}
