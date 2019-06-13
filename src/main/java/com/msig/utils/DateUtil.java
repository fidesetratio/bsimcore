package com.msig.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	 public static Date selectAddDate(Date date, String add, boolean trunc, int nilai) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        
	        if("dd".equals(add)){
	            cal.add(Calendar.DATE, nilai);
	        }else if("mm".equals(add)){
	            cal.add(Calendar.MONTH, nilai);
	        }else if("yy".equals(add)){
	            cal.add(Calendar.YEAR, nilai);
	        }
	        
	        if(trunc) {
	            setTimeToMinimum(cal);
	        }
	        return cal.getTime();
	    }
	 
	   private static void setTimeToMinimum(Calendar calendar) {
	        calendar.set(Calendar.HOUR_OF_DAY, 0);
	        calendar.set(Calendar.MINUTE, 0);
	        calendar.set(Calendar.SECOND, 0);
	        calendar.set(Calendar.MILLISECOND, 0);
	    }
	   public static Date selectSysdateTrunc() {
	        Calendar calendar = Calendar.getInstance();
	        setTimeToMinimum(calendar);
	        return calendar.getTime();
	    }
	    
}
