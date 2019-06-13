package com.app.model.table;

import java.io.Serializable;
import java.util.Date;

public class MstBenefeciaryTemp implements Serializable{
	/**
	 *@author Deddy
	 *@since Mar 6, 2014
	 *@description TODO
	 */
	private static final long serialVersionUID = -4308146891286731049L;
	
	private String no_temp;
	private Integer mste_insured_no;
	private Integer msaw_number;
	private String msaw_first;
	private String msaw_middle;
	private String msaw_last;
	private Date msaw_birth;
	private Integer lsre_id;
	private Double msaw_persen;
	private Integer msaw_sex;
	private Integer lsne_id;
	
	public String getNo_temp() {
		return no_temp;
	}
	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}
	public Integer getMste_insured_no() {
		return mste_insured_no;
	}
	public void setMste_insured_no(Integer mste_insured_no) {
		this.mste_insured_no = mste_insured_no;
	}
	public Integer getMsaw_number() {
		return msaw_number;
	}
	public void setMsaw_number(Integer msaw_number) {
		this.msaw_number = msaw_number;
	}
	public String getMsaw_first() {
		return msaw_first;
	}
	public void setMsaw_first(String msaw_first) {
		this.msaw_first = msaw_first;
	}
	public String getMsaw_middle() {
		return msaw_middle;
	}
	public void setMsaw_middle(String msaw_middle) {
		this.msaw_middle = msaw_middle;
	}
	public String getMsaw_last() {
		return msaw_last;
	}
	public void setMsaw_last(String msaw_last) {
		this.msaw_last = msaw_last;
	}
	public Date getMsaw_birth() {
		return msaw_birth;
	}
	public void setMsaw_birth(Date msaw_birth) {
		this.msaw_birth = msaw_birth;
	}
	public Integer getLsre_id() {
		return lsre_id;
	}
	public void setLsre_id(Integer lsre_id) {
		this.lsre_id = lsre_id;
	}
	public Double getMsaw_persen() {
		return msaw_persen;
	}
	public void setMsaw_persen(Double msaw_persen) {
		this.msaw_persen = msaw_persen;
	}
	public Integer getMsaw_sex() {
		return msaw_sex;
	}
	public void setMsaw_sex(Integer msaw_sex) {
		this.msaw_sex = msaw_sex;
	}
	public Integer getLsne_id() {
		return lsne_id;
	}
	public void setLsne_id(Integer lsne_id) {
		this.lsne_id = lsne_id;
	}
	
}
