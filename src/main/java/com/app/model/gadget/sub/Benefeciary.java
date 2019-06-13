package com.app.model.gadget.sub;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.app.utils.CommonUtil;

public class Benefeciary implements Serializable{
	
	/**
	 *@author Deddy
	 *@since Mar 5, 2014
	 *@description TODO
	 */
	private static final long serialVersionUID = -7055004281232907287L;
	
	private Integer hub_dgcalon_tt;
	private String nama;
	private String ttl;
	private Date ttl_date;
	private Integer jekel;
	private Double manfaat;//FIXME pakai type data double
	private Integer warganegara;
	
	public Integer getHub_dgcalon_tt() {
		return hub_dgcalon_tt;
	}
	public void setHub_dgcalon_tt(Integer hub_dgcalon_tt) {
		this.hub_dgcalon_tt = hub_dgcalon_tt;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public Integer getJekel() {
		return jekel;
	}
	public void setJekel(Integer jekel) {
		this.jekel = jekel;
	}
	public Double getManfaat() {
		return manfaat;
	}
	public void setManfaat(Double manfaat) {
		this.manfaat = manfaat;
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
	public Integer getWarganegara() {
		return warganegara;
	}
	public void setWarganegara(Integer warganegara) {
		this.warganegara = warganegara;
	}
	
	
}
