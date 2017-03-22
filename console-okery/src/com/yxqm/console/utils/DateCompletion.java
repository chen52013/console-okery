package com.yxqm.console.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**  
 * Project Name:console-web  
 * File Name:DateTest.java  
 * Package Name:  
 * Date:2015年12月2日下午6:17:09  
 * Copyright (c) 2015, 雷湘剑 All Rights Reserved.  
 *  
 */

/**
 * ClassName:DateTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年12月2日 下午6:17:09 <br/>
 * 
 * @author 雷湘剑
 * @version
 * @since JDK 1.7
 * @see
 */
public class DateCompletion {
	static String dateFormat = "yyyy-MM-dd";
	static ThreadLocal<SimpleDateFormat> sdfLocal = new ThreadLocal<SimpleDateFormat>(){
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(dateFormat);
		}
	};

	public static void main(String[] args)throws ParseException {
		String date1 = "2015-12-01";
		String date2 = "2015-12-06";
		DateCompletion.process(date1, date2);
	}

	public static List<String> process(String date1, String date2) throws ParseException{
		date1 = addDay(date1,-1);
		date2 = addDay(date2,1);
		
		List<String> strLts = new ArrayList<String>();
		if (date1.equals(date2)) {
			strLts.add(sdfLocal.get().format(str2Date(date1)));
			return strLts;
		}

		String tmp;
		if (date1.compareTo(date2) > 0) { // 确保 date1的日期不晚于date2
			tmp = date1;
			date1 = date2;
			date2 = tmp;
		}

		tmp = sdfLocal.get().format(str2Date(date1).getTime() + 3600 * 24 * 1000);

		int num = 0;

		while (tmp.compareTo(date2) < 0) {
			strLts.add(tmp);
			num++;
			tmp = sdfLocal.get().format(str2Date(tmp).getTime() + 3600 * 24 * 1000);
		}
		return strLts;
	}

	public static Date str2Date(String str) throws ParseException {
		if (str == null)
			return null;

		return sdfLocal.get().parse(str);
	}
	
	private static String addDay(String date,int day) throws ParseException{
		Date dd = sdfLocal.get().parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dd);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return sdfLocal.get().format(calendar.getTime());
	}
}