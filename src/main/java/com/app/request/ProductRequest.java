package com.app.request;

import java.util.HashMap;

public class ProductRequest {
	
	private int lsbs_id;
	private int lsdbs_number;
	private String kurs;
	private String paymode;
	
	private String function;
	
	private HashMap<String,Object> params;
	
	
	
	public ProductRequest(){
		this.function = "";
	}
	
	
	public int getLsbs_id() {
		return lsbs_id;
	}
	public void setLsbs_id(int lsbs_id) {
		this.lsbs_id = lsbs_id;
	}
	public int getLsdbs_number() {
		return lsdbs_number;
	}
	public void setLsdbs_number(int lsdbs_number) {
		this.lsdbs_number = lsdbs_number;
	}
	public String getKurs() {
		return kurs;
	}
	public void setKurs(String kurs) {
		this.kurs = kurs;
	}
	public String getPaymode() {
		return paymode;
	}
	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public HashMap<String, Object> getParams() {
		return params;
	}
	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}
	


}
