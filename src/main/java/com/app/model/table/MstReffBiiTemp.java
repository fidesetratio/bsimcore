package com.app.model.table;

import java.io.Serializable;
import java.util.Date;

public class MstReffBiiTemp implements Serializable {

	/**
	 * Create By Lufi
	 */
	private static final long serialVersionUID = 1L;
	private String no_temp;
	private Integer level_id;
	private String lcb_no;
	private String lrb_id;
	private Integer no_urut;
	private Date tgl_input;
	private String reff_id;
	private String jn_nasabah;
	private String lcb_penutup;
	private String lcb_freeze;
	private String lus_id;
	private String lcb_reff;
	private String lrbj_id1;
	private String lrbj_id1_2;
	private String error_message;
	
	
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public String getLrbj_id1() {
		return lrbj_id1;
	}
	public void setLrbj_id1(String lrbj_id1) {
		this.lrbj_id1 = lrbj_id1;
	}
	public String getLrbj_id1_2() {
		return lrbj_id1_2;
	}
	public void setLrbj_id1_2(String lrbj_id1_2) {
		this.lrbj_id1_2 = lrbj_id1_2;
	}
	public String getNo_temp() {
		return no_temp;
	}
	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}
	public Integer getLevel_id() {
		return level_id;
	}
	public void setLevel_id(Integer level_id) {
		this.level_id = level_id;
	}
	public String getLcb_no() {
		return lcb_no;
	}
	public void setLcb_no(String lcb_no) {
		this.lcb_no = lcb_no;
	}
	public String getLrb_id() {
		return lrb_id;
	}
	public void setLrb_id(String lrb_id) {
		this.lrb_id = lrb_id;
	}
	public Integer getNo_urut() {
		return no_urut;
	}
	public void setNo_urut(Integer no_urut) {
		this.no_urut = no_urut;
	}
	public Date getTgl_input() {
		return tgl_input;
	}
	public void setTgl_input(Date tgl_input) {
		this.tgl_input = tgl_input;
	}
	public String getReff_id() {
		return reff_id;
	}
	public void setReff_id(String reff_id) {
		this.reff_id = reff_id;
	}
	public String getJn_nasabah() {
		return jn_nasabah;
	}
	public void setJn_nasabah(String jn_nasabah) {
		this.jn_nasabah = jn_nasabah;
	}
	public String getLcb_penutup() {
		return lcb_penutup;
	}
	public void setLcb_penutup(String lcb_penutup) {
		this.lcb_penutup = lcb_penutup;
	}
	public String getLcb_freeze() {
		return lcb_freeze;
	}
	public void setLcb_freeze(String lcb_freeze) {
		this.lcb_freeze = lcb_freeze;
	}
	public String getLus_id() {
		return lus_id;
	}
	public void setLus_id(String lus_id) {
		this.lus_id = lus_id;
	}
	public String getLcb_reff() {
		return lcb_reff;
	}
	public void setLcb_reff(String lcb_reff) {
		this.lcb_reff = lcb_reff;
	}
}
