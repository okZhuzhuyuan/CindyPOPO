package com.example.zzy_sports.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static SimpleDateFormat sdf_D = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdf_T = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getCurrDate(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return sdf_D.format(cal.getTime());
	}
}
