package com.msig.utils;


import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;



/**
 * Class berisi fungsi-fungsi umum, seperti generate menu, generate xml
 * 
 * @author Yusuf
 * @since 01/11/2005
 */
public class Common implements Serializable {

	private static final long serialVersionUID = 6370080722430452621L;
	private static List<Map> result;
	private static Map<String, String> map;

	/**
	 * Fungsi rekursif untuk mengambil exception paling atas (sumber dari exception), 
	 * agar tahu errornya apa, dan jelas error message nya
	 * 
	 * @author Yusuf
	 * @since 6 Feb 2012
	 * @param ex exception yang di throw
	 * @return exception parent nya
	 */
	public static Throwable getRootCause(Throwable ex){
		Throwable bapak = ex.getCause();
		if(bapak != null) return getRootCause(bapak);
		else return ex;
	}
	

	
	public static ArrayList serializableList(List dataList){
		if(dataList!=null){
			return new ArrayList(dataList);
		}else{
			return null;
		}
	}
	
	public static HashMap serializableMap(Map dataMap){
		if(dataMap!=null){
			return new HashMap(dataMap);
		}else{
			return null;
		}
	}
	
	
	
}
