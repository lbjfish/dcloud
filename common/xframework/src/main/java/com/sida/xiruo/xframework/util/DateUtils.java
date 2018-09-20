/**
 * FileName：DateUtil
 * Author：  chenguanshou
 * Date：    2017/10/10 0010 下午 7:42
 */
package com.sida.xiruo.xframework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期公共类
 */
public class DateUtils {
    /**
     * 给指定的日期相加/减小 时/分/秒/周/日/月/年等
     * @param date 准备要相加的日期
     * @param num  相加的数量
     * @param type 相加类型 如月Calendar.MONTH  Calendar.YEAR等
     * @return
     */
    private static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
    public static Date addDate(Date date,int num, int type){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(type,num);
        return c.getTime();
    }
    public static String formatTostr(Date date){
         return sf.format(date);
    }

    public static Date formatToDate(String dateStr){
        try {
            return sf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public static Date formatToDateYYMMDD(String dateStr){
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public static Date formatToDateYYMMDDS(String dateStr){
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public static String formatToDateYYMMDDStr(Date date){
        if(BlankUtil.isEmpty(date)){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String formatToStartDateYYMMDDStr(Date date){
        if(BlankUtil.isEmpty(date)){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date)+" 00:00:00";
    }

    public static String formatToEndDateYYMMDDStr(Date date){
        if(BlankUtil.isEmpty(date)){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date)+" 23:59:59";
    }

    public static String formatToDateYYMMDDHHMMSSStr(Date date){
        if(BlankUtil.isEmpty(date)){
            return null;
        }
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
    }
    public static String formatToDateNYRStr(Date date){
        if(BlankUtil.isEmpty(date)){
            return null;
        }
        return new SimpleDateFormat("yyyy年MM月dd日").format(date);
    }
    public static String formatToDateYYMM(Date date){
        return new SimpleDateFormat("yyyy年MM月").format(date);
    }

    public static Date getCurentMonth(){
        Date firstday = null;
        Calendar cale = null;
        cale = Calendar.getInstance();
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        cale.set(Calendar.HOUR_OF_DAY,0);
        cale.set(Calendar.MINUTE,0);
        cale.set(Calendar.SECOND,0);
        cale.set(Calendar.MILLISECOND,0);
        firstday=cale.getTime();
        return firstday;
    }
    public static String formatToDateHHMMSSStr(Date date){
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }
    public static String getDateStr(Date date){
        if(BlankUtil.isEmpty(date)){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }
    public static String getDateStrHHmm(Date date){
        if(BlankUtil.isEmpty(date)){
            return null;
        }
        return new SimpleDateFormat("HH:mm").format(date);
    }

    public static String formatToStr(Date date,String format){
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatToYYYYMMDDHHMMSSStr(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    public  static void main(String [] args) {
        Date date = new Date();
    }
    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
    /**
     * 通过时间秒毫秒数判断两个时间的小时数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByHours(Date date1,Date date2)
    {
        int hours = (int) ((date2.getTime() - date1.getTime()) / (1000*3600));
        return hours;
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0) //闰年
                {
                    timeDistance += 366;
                }
                else //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }


    public static Date getTodayyyyMMdd() throws ParseException {
        Date date = new Date();
        Date today = new SimpleDateFormat("yyyy-MM-dd").parse( new SimpleDateFormat("yyyy-MM-dd").format(date));
        return today;
    }
    public static Date getDateyyyyMM(String dateStr) throws ParseException {
        Date today = new SimpleDateFormat("yyyy-MM").parse(dateStr);
        return today;
    }

    public static String getDateyyyyMMStr(Date date)  {
        String dateStr = new SimpleDateFormat("yyyy-MM").format(date);
        return dateStr;
    }


    public static Date getYestoday(Date today) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_YEAR,-1);
        return c.getTime();
    }
    public static Date getYYMMDDHHMMssDate(String date) throws ParseException{
        if(date!=null) {
            Date dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            return dates;
        }
        return null;
    }
    public static Date getSDate(Date date) throws ParseException{
        if(date!=null) {
            String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
            dateStr += " 00:00:00";
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        }
        return date;
    }
    public static Date getEDate(Date date) throws ParseException{
        if(date!=null) {
            String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
            dateStr += " 23:59:59";
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        }
        return date;
    }

    /**
     * 比较两个时间的月份差距（后者-前者）
     * @param firstDate
     * @param secondDate
     * @return
     */
    public static Integer compareMonth(Date firstDate, Date secondDate) {
        Integer num = null;
        if (firstDate!=null && secondDate!=null){
            Calendar first = Calendar.getInstance();
            first.setTime(firstDate);
            Calendar second = Calendar.getInstance();
            second.setTime(secondDate);

            num = (second.get(Calendar.YEAR) - first.get(Calendar.YEAR))*12 + (second.get(Calendar.MONTH) - first.get(Calendar.MONTH));
        }
        return num;
    }
}
