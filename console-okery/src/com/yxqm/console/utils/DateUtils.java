package com.yxqm.console.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils{

	public static String formatDate(Date date ,String format){
		if(null==date){
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	/***
	 * 获取是第几周
	 */
	public static String getWeekStr(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	int w = calendar.get(Calendar.WEEK_OF_YEAR);
    	if(calendar.get(Calendar.DAY_OF_WEEK)==1&&w==1){//最后一个月的星期天
    		w=52;
    	}else if(calendar.get(Calendar.DAY_OF_WEEK)==1){//普通的星期天
    		w-=1;
    	}
    	int m = calendar.get(Calendar.MONTH)+1;
    	int year = calendar.get(Calendar.YEAR);
    	if(m==12&&w==1){//最后一个月的星期一
    		year+=1;
    	}
    	String y = year+"";
    	String ws = w+"";
		if(ws.length()==1){
			ws = "0"+ws;
		}
		return y+ws;
    }
	
	/***
	 * 获取相对日期周一开始时间
	 */
    public static String getWeekFirstDay(Date date){
    	
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
		if(cal.get(Calendar.DAY_OF_WEEK)==1){
			cal.add(Calendar.WEEK_OF_YEAR,-1);
		}
		cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		
		
    	DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    	return sdf.format(cal.getTime());
    }
    
    
    public static int getWeek(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int week = cal.get(Calendar.DAY_OF_WEEK);
    	if(week==1){
    		return 7;
    	}
    	return week-1;
    }
    
    /**
     * 获取两个时间相差的分钟数
     */
    public static int getBetweenDateMin(Date d1 , Date d2){
    	long seconds = d1.getTime() - d2.getTime();
    	
    	return (int) (seconds/60/1000);
    }
    
    /**
     * 取得当前日期所在周的最后一天
     *
     */
    public static String getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
        
        Date result = calendar.getTime();
        
        return new SimpleDateFormat("yyyy-MM-dd").format(result) + " 23:59:59";
    }
    
    /**
     * 比较两个时间相差多少天，不足24小时按照一天算
     * @throws ParseException 
     */
    public static int getBetweenDays(Date date1 ,Date date2) throws ParseException{
    	Date d1 = getStartDaysTime(date1);
    	Date d2 = getStartDaysTime(date2);
    	return daysBetween(d1, d2);
    }
    
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
    
    public static Date getStartDaysTime(Date date) throws ParseException{
    	String sDate = toStringOfDate(date, "yyyy-MM-dd") + " 00:00:00";
    	
    	return parseDate(sDate, "yyyy-MM-dd HH:mm:ss");
    }
    
    public static String toStringOfDate(Date date , String patten){
    	return new SimpleDateFormat(patten).format(date);
    }
    
}
