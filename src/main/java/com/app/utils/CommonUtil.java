package com.app.utils;

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
