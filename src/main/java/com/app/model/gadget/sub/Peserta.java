package com.app.model.gadget.sub;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.app.utils.CommonUtil;
	
public class Peserta implements Serializable {
	/**
	 *@author Deddy
	 *@since Mar 5, 2014
	 *@description TODO
	 */
	private static final long serialVersionUID = -3662175020570626858L;
	
	private Integer rider_askes;//FIXME
	private Integer produk_askes;
	private Integer usia_askes;
	private String ttl_askes;
	private Date ttl_askes_date;
	private Integer jekel_askes;
	private Integer hubungan_askes;
	private String peserta_askes;
	private Integer kode_produk_rider;
	private Integer kode_subproduk_rider;
	private Integer tinggi_askes;
	private Integer berat_askes;
	private Integer warganegara_askes;
	private String pekerjaan_askes;
	
	public Integer getKode_produk_rider() {
		return kode_produk_rider;
	}
	public void setKode_produk_rider(Integer kode_produk_rider) {
		this.kode_produk_rider = kode_produk_rider;
	}
	public Integer getKode_subproduk_rider() {
		return kode_subproduk_rider;
	}
	public void setKode_subproduk_rider(Integer kode_subproduk_rider) {
		this.kode_subproduk_rider = kode_subproduk_rider;
	}
	public Integer getRider_askes() {
		return rider_askes;
	}
	public void setRider_askes(Integer rider_askes) {
		this.rider_askes = rider_askes;
	}
	public Integer getProduk_askes() {
		return produk_askes;
	}
	public void setProduk_askes(Integer produk_askes) {
		this.produk_askes = produk_askes;
	}
	public Integer getUsia_askes() {
		return usia_askes;
	}
	public void setUsia_askes(Integer usia_askes) {
		this.usia_askes = usia_askes;
	}
	public String getTtl_askes() {
		return ttl_askes;
	}
	public void setTtl_askes(String ttl_askes) {
		this.ttl_askes = ttl_askes;
	}
	public Date getTtl_askes_date() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		this.ttl_askes_date = CommonUtil.isEmpty(ttl_askes)?null:formatter.parse(ttl_askes);
//		this.ttl_askes_date = formatter.parse(ttl_askes) ;
		return ttl_askes_date;
	}
	public void setTtl_askes_date(Date ttl_askes_date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		this.ttl_askes = CommonUtil.isEmpty(ttl_askes_date)?null:formatter.format(ttl_askes_date);
		this.ttl_askes_date = CommonUtil.isEmpty(ttl_askes_date)?null:ttl_askes_date;
	}
	public Integer getJekel_askes() {
		return jekel_askes;
	}
	public void setJekel_askes(Integer jekel_askes) {
		this.jekel_askes = jekel_askes;
	}
	public Integer getHubungan_askes() {
		return hubungan_askes;
	}
	public void setHubungan_askes(Integer hubungan_askes) {
		this.hubungan_askes = hubungan_askes;
	}
	public String getPeserta_askes() {
		return peserta_askes;
	}
	public void setPeserta_askes(String peserta_askes) {
		this.peserta_askes = peserta_askes;
	}
	public Integer getTinggi_askes() {
		return tinggi_askes;
	}
	public void setTinggi_askes(Integer tinggi_askes) {
		this.tinggi_askes = tinggi_askes;
	}
	public Integer getBerat_askes() {
		return berat_askes;
	}
	public void setBerat_askes(Integer berat_askes) {
		this.berat_askes = berat_askes;
	}
	public Integer getWarganegara_askes() {
		return warganegara_askes;
	}
	public void setWarganegara_askes(Integer warganegara_askes) {
		this.warganegara_askes = warganegara_askes;
	}
	public String getPekerjaan_askes() {
		return pekerjaan_askes;
	}
	public void setPekerjaan_askes(String pekerjaan_askes) {
		this.pekerjaan_askes = pekerjaan_askes;
	}
	
	
	
}
