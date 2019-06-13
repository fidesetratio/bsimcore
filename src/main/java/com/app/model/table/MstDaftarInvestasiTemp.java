package com.app.model.table;

import java.io.Serializable;

public class MstDaftarInvestasiTemp implements Serializable{
	/**
	 *@author Deddy
	 *@since Mar 6, 2014
	 *@description TODO
	 */
	
	private static final long serialVersionUID = 5023933163122531374L;
	
	private String no_temp;
	private String lji_id;
	private Double mdu_persen;
	private Double mdu_jumlah;
	
	public String getNo_temp() {
		return no_temp;
	}
	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}
	public String getLji_id() {
		return lji_id;
	}
	public void setLji_id(String lji_id) {
		this.lji_id = lji_id;
	}
	public Double getMdu_persen() {
		return mdu_persen;
	}
	public void setMdu_persen(Double mdu_persen) {
		this.mdu_persen = mdu_persen;
	}
	public Double getMdu_jumlah() {
		return mdu_jumlah;
	}
	public void setMdu_jumlah(Double mdu_jumlah) {
		this.mdu_jumlah = mdu_jumlah;
	}
	
}
