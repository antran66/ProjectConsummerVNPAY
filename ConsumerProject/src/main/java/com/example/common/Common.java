package com.example.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.util.Strings;

import com.example.model.Request;

public class Common {

	public static String getCurDate(String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = Calendar.getInstance().getTime();
		return formatter.format(date);
	}

	public static boolean checkDateFormat(String stringDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		try {
			formatter.parse(stringDate);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
}
