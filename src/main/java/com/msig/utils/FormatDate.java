package com.msig.utils;

import java.util.Calendar;
import java.util.Date;

public class FormatDate {
	  public static Date add(Date tanggal, int kalendar, int angka) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(tanggal);
	        cal.add(kalendar, angka);
	        return cal.getTime();
	    }
}
