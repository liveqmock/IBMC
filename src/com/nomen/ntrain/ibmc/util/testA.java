package com.nomen.ntrain.ibmc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class testA {
	public static void main(String[] args) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse("2019-01-09 09:10");
	    calendar.setTime(date);
	    System.out.println(calendar);
	}
}
