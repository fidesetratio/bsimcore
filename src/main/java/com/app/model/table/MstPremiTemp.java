package com.app.model.table;

import java.io.Serializable;
import java.math.BigDecimal;

public class MstPremiTemp implements Serializable {

	/**
	 *@author Deddy
	 *@since Aug 19, 2014
	 *@description TODO 
	 */
	private static final long serialVersionUID = 8597985927603158020L;
	private String no_temp;
	private Integer lt_id;
	private BigDecimal premi;
	
	public String getNo_temp() {
		return no_temp;
	}
	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}
	public Integer getLt_id() {
		return lt_id;
	}
	public void setLt_id(Integer lt_id) {
		this.lt_id = lt_id;
	}
	public BigDecimal getPremi() {
		return premi;
	}
	public void setPremi(BigDecimal premi) {
		this.premi = premi;
	} 
}
