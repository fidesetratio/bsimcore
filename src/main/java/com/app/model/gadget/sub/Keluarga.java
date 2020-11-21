package com.app.model.gadget.sub;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.app.utils.CommonUtil;


public class Keluarga implements Serializable {
	/**
	 *@author Deddy
	 *@since Mar 4, 2014
	 */
	private static final long serialVersionUID = 4768841593128445299L;
	
	private String kat;
	private String nama;
	private String ttl;
	private Date ttl_date;
	
	public String getKat() {
		return kat;
	}
	public void setKat(String kat) {
		this.kat = kat;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getTtl() {
		return ttl;
	}
	public void setTtl(String ttl) {
		this.ttl = ttl;
	}
	public Date getTtl_date() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		this.ttl_date = CommonUtil.isEmpty(ttl)?null:formatter.parse(ttl);
		return ttl_date;
	}
	public void setTtl_date(Date ttl_date) {
		this.ttl_date = ttl_date;
	}
	
}
