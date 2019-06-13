package com.app.model.table;

import java.io.Serializable;
import java.util.Date;

public class MstAccountRecurTemp implements Serializable {
	/**
	 *@author Deddy
	 *@since Mar 7, 2014
	 *@description TODO
	 */
	private static final long serialVersionUID = -4308146891286731049L;
	
	private String no_temp;
	private Integer mar_number;
	private Integer lbn_id;
	private Integer mar_jenis;
	private String mar_acc_no;
	private String mar_holder;
	private Date mar_expired;
	private Integer mar_active; 
	private Date mar_tgl;
	private Integer lus_id;
	private Integer flag_jn_tabungan; 
	private Integer flag_autodebet_nb;
	private Integer flag_verifikasi;
	private Integer flag_tt_sk_debet;
	private Integer user_tt_sk_debet;
	private Date tgl_tt_sk_debet;
	private Integer flag_set_auto;
	private Date tgl_debet;
	
	public String getNo_temp() {
		return no_temp;
	}
	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}
	public Integer getMar_number() {
		return mar_number;
	}
	public void setMar_number(Integer mar_number) {
		this.mar_number = mar_number;
	}
	public Integer getLbn_id() {
		return lbn_id;
	}
	public void setLbn_id(Integer lbn_id) {
		this.lbn_id = lbn_id;
	}
	public Integer getMar_jenis() {
		return mar_jenis;
	}
	public void setMar_jenis(Integer mar_jenis) {
		this.mar_jenis = mar_jenis;
	}
	public String getMar_acc_no() {
		return mar_acc_no;
	}
	public void setMar_acc_no(String mar_acc_no) {
		this.mar_acc_no = mar_acc_no;
	}
	public String getMar_holder() {
		return mar_holder;
	}
	public void setMar_holder(String mar_holder) {
		this.mar_holder = mar_holder;
	}
	public Date getMar_expired() {
		return mar_expired;
	}
	public void setMar_expired(Date mar_expired) {
		this.mar_expired = mar_expired;
	}
	public Integer getMar_active() {
		return mar_active;
	}
	public void setMar_active(Integer mar_active) {
		this.mar_active = mar_active;
	}
	public Date getMar_tgl() {
		return mar_tgl;
	}
	public void setMar_tgl(Date mar_tgl) {
		this.mar_tgl = mar_tgl;
	}
	public Integer getLus_id() {
		return lus_id;
	}
	public void setLus_id(Integer lus_id) {
		this.lus_id = lus_id;
	}
	public Integer getFlag_jn_tabungan() {
		return flag_jn_tabungan;
	}
	public void setFlag_jn_tabungan(Integer flag_jn_tabungan) {
		this.flag_jn_tabungan = flag_jn_tabungan;
	}
	public Integer getFlag_autodebet_nb() {
		return flag_autodebet_nb;
	}
	public void setFlag_autodebet_nb(Integer flag_autodebet_nb) {
		this.flag_autodebet_nb = flag_autodebet_nb;
	}
	public Integer getFlag_verifikasi() {
		return flag_verifikasi;
	}
	public void setFlag_verifikasi(Integer flag_verifikasi) {
		this.flag_verifikasi = flag_verifikasi;
	}
	public Integer getFlag_tt_sk_debet() {
		return flag_tt_sk_debet;
	}
	public void setFlag_tt_sk_debet(Integer flag_tt_sk_debet) {
		this.flag_tt_sk_debet = flag_tt_sk_debet;
	}
	public Integer getUser_tt_sk_debet() {
		return user_tt_sk_debet;
	}
	public void setUser_tt_sk_debet(Integer user_tt_sk_debet) {
		this.user_tt_sk_debet = user_tt_sk_debet;
	}
	public Date getTgl_tt_sk_debet() {
		return tgl_tt_sk_debet;
	}
	public void setTgl_tt_sk_debet(Date tgl_tt_sk_debet) {
		this.tgl_tt_sk_debet = tgl_tt_sk_debet;
	}
	public Integer getFlag_set_auto() {
		return flag_set_auto;
	}
	public void setFlag_set_auto(Integer flag_set_auto) {
		this.flag_set_auto = flag_set_auto;
	}
	public Date getTgl_debet() {
		return tgl_debet;
	}
	public void setTgl_debet(Date tgl_debet) {
		this.tgl_debet = tgl_debet;
	}
	
}
