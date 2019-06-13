package com.app.model.table;

import java.io.Serializable;

public class MstKycTemp implements Serializable{
	/**
	 *@author Deddy
	 *@since Mar 6, 2014
	 *@description TODO
	 */
	private static final long serialVersionUID = -4308146891286731049L;
	
	private String no_temp;
	private Integer no_urut;
	private Integer kyc_id;
	private Integer kyc_pp;
	private String kyc_desc;
	
	public String getNo_temp() {
		return no_temp;
	}
	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}
	public Integer getNo_urut() {
		return no_urut;
	}
	public void setNo_urut(Integer no_urut) {
		this.no_urut = no_urut;
	}
	public Integer getKyc_id() {
		return kyc_id;
	}
	public void setKyc_id(Integer kyc_id) {
		this.kyc_id = kyc_id;
	}
	public Integer getKyc_pp() {
		return kyc_pp;
	}
	public void setKyc_pp(Integer kyc_pp) {
		this.kyc_pp = kyc_pp;
	}
	public String getKyc_desc() {
		return kyc_desc;
	}
	public void setKyc_desc(String kyc_desc) {
		this.kyc_desc = kyc_desc;
	}
	
}
