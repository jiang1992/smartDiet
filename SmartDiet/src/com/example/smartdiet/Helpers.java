package com.example.smartdiet;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A class with static helper methods
 * @author Hieu Tran
 *
 */
public class Helpers {
	
	public static double round(double unrounded, int precision)
	{
	    BigDecimal bd = new BigDecimal(unrounded);
	    BigDecimal rounded = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
	    return rounded.doubleValue();
	}
	
	public static boolean isValidDate(String dateString) {
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	        df.parse(dateString);
	        return true;
	    } catch (ParseException e) {
	        return false;
	    }
	}
	
	public static String getTodaysDate(){
		String date = "";
		Calendar today = Calendar.getInstance();
		String year = String.format("%04d", today.get(Calendar.YEAR));
		String month = String.format("%02d", today.get(Calendar.MONTH) + 1);
		String day = String.format("%02d", today.get(Calendar.DATE));
		date = year + "-" + month + "-" + day;
		return date;
	}
}
