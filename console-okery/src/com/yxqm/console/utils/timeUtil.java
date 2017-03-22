package com.yxqm.console.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class timeUtil {
	public static int time_cha(String start_time, String end_time) {
		int days = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(start_time);
			Date date2 = sdf.parse(end_time);

			Calendar can1 = Calendar.getInstance();
			can1.setTime(date1);
			Calendar can2 = Calendar.getInstance();
			can2.setTime(date2);

			int year1 = can1.get(1);
			int year2 = can2.get(1);
			Calendar can = null;

			if (can1.before(can2)) {
				days -= can1.get(6);
				days += can2.get(6);
				can = can1;
			} else {
				days -= can2.get(6);
				days += can1.get(6);
				can = can2;
			}
			for (int i = 0; i < Math.abs(year2 - year1); i++) {
				days += can.getActualMaximum(6);

				can.add(1, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}
}