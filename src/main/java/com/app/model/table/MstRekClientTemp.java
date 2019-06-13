package com.app.model.table;

import java.io.Serializable;
import java.util.Date;

public class MstRekClientTemp implements Serializable{
	/**
	 *@author Deddy
	 *@since Mar 7, 2014
	 *@description TODO
	 */
	private static final long serialVersionUID = -4308146891286731049L;
	
	private String no_temp;
	private Integer lsbp_id;
	private String mrc_cabang;
	private String mrc_nama;
	private String mrc_no_ac;
	private Integer mrc_jenis;
	private Integer mrc_jn_nasabah;
	private String mrc_kurs;
	private String mrc_kota; 
	private Integer lus_id;
	private Date tgl_change;
	private Integer mrc_kuasa;
	private Date tgl_surat;
	
	public String getNo_temp() {
		return no_temp;
	}
	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}
	public Integer getLsbp_id() {
		return lsbp_id;
	}
	public void setLsbp_id(Integer lsbp_id) {
		this.lsbp_id = lsbp_id;
	}
	public String getMrc_cabang() {
		return mrc_cabang;
	}
	public void setMrc_cabang(String mrc_cabang) {
		this.mrc_cabang = mrc_cabang;
	}
	public String getMrc_nama() {
		return mrc_nama;
	}
	public void setMrc_nama(String mrc_nama) {
		this.mrc_nama = mrc_nama;
	}
	public String getMrc_no_ac() {
		return mrc_no_ac;
	}
	public void setMrc_no_ac(String mrc_no_ac) {
		this.mrc_no_ac = mrc_no_ac;
	}
	public Integer getMrc_jenis() {
		return mrc_jenis;
	}
	public void setMrc_jenis(Integer mrc_jenis) {
		this.mrc_jenis = mrc_jenis;
	}
	public Integer getMrc_jn_nasabah() {
		return mrc_jn_nasabah;
	}
	public void setMrc_jn_nasabah(Integer mrc_jn_nasabah) {
		this.mrc_jn_nasabah = mrc_jn_nasabah;
	}
	public String getMrc_kurs() {
		return mrc_kurs;
	}
	public void setMrc_kurs(String mrc_kurs) {
		this.mrc_kurs = mrc_kurs;
	}
	public String getMrc_kota() {
		return mrc_kota;
	}
	public void setMrc_kota(String mrc_kota) {
		this.mrc_kota = mrc_kota;
	}
	public Integer getLus_id() {
		return lus_id;
	}
	public void setLus_id(Integer lus_id) {
		this.lus_id = lus_id;
	}
	public Integer getMrc_kuasa() {
		return mrc_kuasa;
	}
	public void setMrc_kuasa(Integer mrc_kuasa) {
		this.mrc_kuasa = mrc_kuasa;
	}
	public Date getTgl_change() {
		return tgl_change;
	}
	public void setTgl_change(Date tgl_change) {
		this.tgl_change = tgl_change;
	}
	public Date getTgl_surat() {
		return tgl_surat;
	}
	public void setTgl_surat(Date tgl_surat) {
		this.tgl_surat = tgl_surat;
	}
	
}
