package com.app.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	  public static boolean validPhone(String no){
	         boolean isNumberFlag=false;
	         String [] splitNo=no.trim().split("");
	        
	        for (int i = 0; i < splitNo.length; i++) {
	            String c=splitNo[i];
	            if(i==1){
	                if(c.equals("+"))c="0";
	            }
	            
	            char[] x=c.toCharArray();
	             
	             for (int j = 0; j < x.length; j++) {
	                 char y =x[j];
	                 if ((y >= '0') && (y <= '9')){ // numeric
	                     isNumberFlag=true;
	                     continue;
	                 }else{
	                     return false;
	                 }
	            }
	            
	        }
	        
	        return isNumberFlag;
	    }
	  
		public static boolean isEmpty(Object cek) {
			if(cek==null) return true;
			else	if(cek instanceof String) {
				String tmp = (String) cek;
					if(tmp.trim().equals("")) return true;
					else return false;
			}else if(cek instanceof List) {
				List tmp = (List) cek;
				return tmp.isEmpty();
			}else if(cek instanceof Map){
				return ((Map) cek).isEmpty();
			}else if(cek instanceof Integer || cek instanceof Long|| cek instanceof Double|| cek instanceof Float|| cek instanceof BigDecimal || cek instanceof Date){
				return false;
			}
			return true;
		}
	  
	  public static boolean isEmailValid(String email) {
			boolean isValid = false;
			String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			CharSequence inputStr = email;

			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(inputStr);
			if (matcher.matches()) {
				isValid = true;
			}
			return isValid;
	     }
}
