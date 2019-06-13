package com.app.model.gadget.sub;

import java.io.Serializable;

public class JenisFund implements Serializable {
	/**
	 *@author Deddy
	 *@since Mar 5, 2014
	 *@description TODO
	 */
	private static final long serialVersionUID = 1855684453504830314L;
	
	private Double persen_alokasi;
	private Double jumlah_alokasi;
	private String jenis_dana;
	
	public Double getPersen_alokasi() {
		return persen_alokasi;
	}
	public void setPersen_alokasi(Double persen_alokasi) {
		this.persen_alokasi = persen_alokasi;
	}
	public Double getJumlah_alokasi() {
		return jumlah_alokasi;
	}
	public void setJumlah_alokasi(Double jumlah_alokasi) {
		this.jumlah_alokasi = jumlah_alokasi;
	}
	public String getJenis_dana() {
		return jenis_dana;
	}
	public void setJenis_dana(String jenis_dana) {
		this.jenis_dana = jenis_dana;
	}
	
	
}
