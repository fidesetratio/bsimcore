package com.app.model.table;

import java.io.Serializable;
import java.util.Date;

public class MstBiayaUlinkTemp implements Serializable{
	/**
	 *@author Deddy
	 *@since Mar 6, 2014
	 *@description TODO
	 */
	private static final long serialVersionUID = 5023933163122531374L;
	
	private String no_temp;
	private Integer mu_ke;
	private Integer ljb_id;
	private Double mbu_jumlah;
	private Double mbu_persen;
	private Date mbu_end_pay;
	
	public String getNo_temp() {
		return no_temp;
	}
	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}
	public Integer getMu_ke() {
		return mu_ke;
	}
	public void setMu_ke(Integer mu_ke) {
		this.mu_ke = mu_ke;
	}
	public Integer getLjb_id() {
		return ljb_id;
	}
	public void setLjb_id(Integer ljb_id) {
		this.ljb_id = ljb_id;
	}
	public Double getMbu_jumlah() {
		return mbu_jumlah;
	}
	public void setMbu_jumlah(Double mbu_jumlah) {
		this.mbu_jumlah = mbu_jumlah;
	}
	public Double getMbu_persen() {
		return mbu_persen;
	}
	public void setMbu_persen(Double mbu_persen) {
		this.mbu_persen = mbu_persen;
	}
	public Date getMbu_end_pay() {
		return mbu_end_pay;
	}
	public void setMbu_end_pay(Date mbu_end_pay) {
		this.mbu_end_pay = mbu_end_pay;
	}
	
}
