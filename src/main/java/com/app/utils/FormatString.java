package com.app.utils;

public class FormatString {
	public static String rpad(String karakter, String kata, int panjang) {
		if(kata==null) return null;
		StringBuffer result = new StringBuffer();
		if (kata.length() < panjang) {
			for (int i = 0; i < panjang - kata.length(); i++) {
				result.append(karakter);
			}
			result.append(kata);
			return result.toString();
		} else {
			return kata;
		}
	}
	public static String nomorPolis(String kata) {
//		Regex reg9 = new Regex("\\d{9,9}");
//		Regex reg11 = new Regex("\\d{11,11}");
//		Regex reg14 = new Regex("\\d{14,14}");
		
		if(kata==null){
			return kata;
		}else if(kata.length()==9){
			return kata.substring(0,2)+"."+kata.substring(2);
		}else if(kata.length()==11){
			return kata.substring(0,2)+"."+kata.substring(2,6)+"."+kata.substring(6);
		}else if(kata.length()==14){
			return kata.substring(0,2)+"."+kata.substring(2,5)+"."+kata.substring(5,9)+"."+kata.substring(9);
		}else return kata;

	}
}
