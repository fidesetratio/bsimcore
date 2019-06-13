package com.app.model.gadget.sub;

import java.io.Serializable;

public class JenisBiaya implements Serializable{
	/**
	 *@author Deddy
	 *@since Mar 5, 2014
	 *@description TODO
	 */
	private static final long serialVersionUID = -6426172996106885003L;
	
	private Integer id;
	private Double persen;
	private Double jumlah;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getPersen() {
		return persen;
	}
	public void setPersen(Double persen) {
		this.persen = persen;
	}
	public Double getJumlah() {
		return jumlah;
	}
	public void setJumlah(Double jumlah) {
		this.jumlah = jumlah;
	}
}
