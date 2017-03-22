package com.yxqm.console.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);
	public static final long ONE_SECOND = 1000;
	public static final long T05_SECOND = ONE_SECOND * 5;
	public static final long T10_SECOND = ONE_SECOND * 10;
	public static final long T15_SECOND = ONE_SECOND * 15;
	public static final long T20_SECOND = ONE_SECOND * 20;
	public static final long T25_SECOND = ONE_SECOND * 25;
	public static final long T30_SECOND = ONE_SECOND * 30;
	public static final long T35_SECOND = ONE_SECOND * 35;
	public static final long T40_SECOND = ONE_SECOND * 40;
	public static final long T45_SECOND = ONE_SECOND * 45;
	public static final long T50_SECOND = ONE_SECOND * 50;
	public static final long T55_SECOND = ONE_SECOND * 55;

	public static final long ONE_MINUTE = ONE_SECOND * 60;
	public static final long ONE_HOUR = ONE_MINUTE * 60;

	public static final long T12_HOUR = ONE_HOUR * 12;

	public static final long ONE_DAY = ONE_HOUR * 24;

	public static final String DATE_FORMAT_STANDARD="yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_1="yyyyMMdd";
	public static final String DATE_FORMAT_2="yyyy-MM-dd";
	public static final String DATE_FORMAT_3 = "yyyyMMddHHmmss";


	public static final int ONE_DataCycle =  7; //一个数据周期(单位 天 Date)

	//获取当前时间
	public static java.util.Date now()
	{
		return new java.util.Date();
	}

	public static String nowFormat()
	{
		return new SimpleDateFormat(DATE_FORMAT_STANDARD).format(now());
	}

	public static String format(java.util.Date d)
	{
		return new SimpleDateFormat(DATE_FORMAT_STANDARD).format(d);
	}


	public static String nowFormat(String fm)
	{
		return new SimpleDateFormat(fm).format(now());
	}

	public static String format(java.util.Date d,String fm)
	{
		return new SimpleDateFormat(fm).format(d);
	}


	public static Timestamp parseTimestamp(String s)
	{
		java.util.Date d;
		try {
			d = new SimpleDateFormat(DATE_FORMAT_STANDARD).parse(s);
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date parse(String s,String format)
	{
		java.util.Date d;
		try {
			return new SimpleDateFormat(format).parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static long convertDate(java.util.Date d,String fmt)
	{
		return Long.parseLong(new SimpleDateFormat(fmt).format(d));
	}

	/**
	 * 字符串形式的毫秒数转化为日期格式
	 *
	 * @param time
	 *            日期毫秒数字符串
	 * @param patten
	 *            要转换成的日期格式
	 * @return 以patten为格式的日期
	 */
	public static String timeToString(String time, String patten,long addTime) {

		return timeToString(Long.valueOf(time)+addTime, patten);

	}

	/**
	 * 字符串形式的毫秒数转化为日期格式
	 *
	 * @param time
	 *            日期毫秒数
	 * @param patten
	 *            要转换成的日期格式
	 * @return 以patten为格式的日期
	 */
	public static String timeToString(Long time, String patten) {
		Date date = null;
		if (time != null) {
			date = new Date(time);
		}
		return new SimpleDateFormat(patten).format(date);

	}


	//获得当前时间的小时数 0-23小时制
	public static int GetNowHour()
	{
		return Integer.parseInt(new SimpleDateFormat("HH").format(now()));
	}
	//获得当前时间的分钟数 0-59
	public static int GetNowMinute()
	{
		return Integer.parseInt(new SimpleDateFormat("mm").format(now()));
	}

	//获得当前时间的分钟数 0-59
	public static int GetNowSeconds()
	{
		return Integer.parseInt(new SimpleDateFormat("ss").format(now()));
	}

	/**
	 * 获取俩时间间距差(分钟)
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long getGapTimeMinute(Timestamp startTime,Timestamp endTime){
		return (endTime.getTime()-startTime.getTime())/60/1000;
	}

	public static String getDayOfWeekCn(String day) {
		String weekCn = "";
		int dayNum = Integer.valueOf(day);
		switch (dayNum) {
			case 1:
				weekCn = "周一";
				break;
			case 2:
				weekCn = "周二";
				break;
			case 3:
				weekCn = "周三";
				break;
			case 4:
				weekCn = "周四";
				break;
			case 5:
				weekCn = "周五";
				break;
			case 6:
				weekCn = "周六";
				break;
			case 7:
				weekCn = "周日";
				break;
		}
		return weekCn;
	}

	public static Date dayAdd(Date date,int day)
	{
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

}
