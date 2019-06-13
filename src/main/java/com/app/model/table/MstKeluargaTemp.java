package com.app.model.table;

import java.io.Serializable;
import java.util.Date;

public class MstKeluargaTemp implements Serializable{
	/**
	 *@author Deddy
	 *@since Mar 6, 2014
	 *@description TODO
	 */
	
	private static final long serialVersionUID = -4308146891286731049L;
	
	private String no_temp;
	private String nama;
	private Integer lsre_id;
	private Date tanggal_lahir;
	private Integer no;
	private Integer insured;
	private Double free_pa;
	private Integer usia;
	private String kerja;
	private String ljb_id;
	private String nama_perusahaan;
	private String bidang_usaha;
	private String npwp;
	private String penghasilan;
	
	public String getNo_temp() {
		return no_temp;
	}
	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public Integer getLsre_id() {
		return lsre_id;
	}
	public void setLsre_id(Integer lsre_id) {
		this.lsre_id = lsre_id;
	}
	public Date getTanggal_lahir() {
		return tanggal_lahir;
	}
	public void setTanggal_lahir(Date tanggal_lahir) {
		this.tanggal_lahir = tanggal_lahir;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public Integer getInsured() {
		return insured;
	}
	public void setInsured(Integer insured) {
		this.insured = insured;
	}
	public Double getFree_pa() {
		return free_pa;
	}
	public void setFree_pa(Double free_pa) {
		this.free_pa = free_pa;
	}
	public Integer getUsia() {
		return usia;
	}
	public void setUsia(Integer usia) {
		this.usia = usia;
	}
	public String getKerja() {
		return kerja;
	}
	public void setKerja(String kerja) {
		this.kerja = kerja;
	}
	public String getLjb_id() {
		return ljb_id;
	}
	public void setLjb_id(String ljb_id) {
		this.ljb_id = ljb_id;
	}
	public String getNama_perusahaan() {
		return nama_perusahaan;
	}
	public void setNama_perusahaan(String nama_perusahaan) {
		this.nama_perusahaan = nama_perusahaan;
	}
	public String getBidang_usaha() {
		return bidang_usaha;
	}
	public void setBidang_usaha(String bidang_usaha) {
		this.bidang_usaha = bidang_usaha;
	}
	public String getNpwp() {
		return npwp;
	}
	public void setNpwp(String npwp) {
		this.npwp = npwp;
	}
	public String getPenghasilan() {
		return penghasilan;
	}
	public void setPenghasilan(String penghasilan) {
		this.penghasilan = penghasilan;
	}
	
	
}
