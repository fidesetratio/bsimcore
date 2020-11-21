package com.app.model.gadget.sub;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.app.utils.CommonUtil;


public class Rider implements Serializable{
	/**
	 *@author Deddy
	 *@since Mar 5, 2014
	 *@description TODO
	 */
	private static final long serialVersionUID = -7764086375648455013L;
	
	private Integer masa_rider;
	private String tglmulai_rider;
	private String tglakhir_rider;
	private String akhirbayar_rider;
	private Date tglmulai_rider_date;
	private Date tglakhir_rider_date;
	private Date akhirbayar_rider_date;
	private Double up_rider;
	private Double premi_rider;
	private Integer klas_rider;
	private Double rate_rider;
	private Integer tertanggung_rider;
	private Integer nama_produk_rider;//FIXME
	private Integer persentase_rider;
	private Integer unit_rider;
	private Integer kode_produk_rider;
	private Integer kode_subproduk_rider;
	
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
	public Integer getMasa_rider() {
		return masa_rider;
	}
	public void setMasa_rider(Integer masa_rider) {
		this.masa_rider = masa_rider;
	}
	public String getTglmulai_rider() {
		return tglmulai_rider;
	}
	public void setTglmulai_rider(String tglmulai_rider) {
		this.tglmulai_rider = tglmulai_rider;
	}
	public Date getTglmulai_rider_date() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		this.tglmulai_rider_date = CommonUtil.isEmpty(tglmulai_rider)?null:formatter.parse(tglmulai_rider);
//		this.tglmulai_rider_date = formatter.parse(tglmulai_rider) ;
		return tglmulai_rider_date;
	}
	public void setTglmulai_rider_date(Date tglmulai_rider_date) {
		this.tglmulai_rider_date = tglmulai_rider_date;
	}
	public Date getTglakhir_rider_date() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		this.tglakhir_rider_date = CommonUtil.isEmpty(tglakhir_rider)?null:formatter.parse(tglakhir_rider);
		return tglakhir_rider_date;
	}
	public void setTglakhir_rider_date(Date tglakhir_rider_date) {
		this.tglakhir_rider_date = tglakhir_rider_date;
	}
	public Date getAkhirbayar_rider_date() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		this.akhirbayar_rider_date = CommonUtil.isEmpty(akhirbayar_rider)?null:formatter.parse(akhirbayar_rider);
		return akhirbayar_rider_date;
	}
	public void setAkhirbayar_rider_date(Date akhirbayar_rider_date) {
		this.akhirbayar_rider_date = akhirbayar_rider_date;
	}
	public Double getUp_rider() {
		return up_rider;
	}
	public void setUp_rider(Double up_rider) {
		this.up_rider = up_rider;
	}
	public Double getPremi_rider() {
		return premi_rider;
	}
	public void setPremi_rider(Double premi_rider) {
		this.premi_rider = premi_rider;
	}
	public Integer getKlas_rider() {
		return klas_rider;
	}
	public void setKlas_rider(Integer klas_rider) {
		this.klas_rider = klas_rider;
	}
	public Double getRate_rider() {
		return rate_rider;
	}
	public void setRate_rider(Double rate_rider) {
		this.rate_rider = rate_rider;
	}
	public Integer getTertanggung_rider() {
		return tertanggung_rider;
	}
	public void setTertanggung_rider(Integer tertanggung_rider) {
		this.tertanggung_rider = tertanggung_rider;
	}
	public Integer getNama_produk_rider() {
		return nama_produk_rider;
	}
	public void setNama_produk_rider(Integer nama_produk_rider) {
		this.nama_produk_rider = nama_produk_rider;
	}
	public Integer getPersentase_rider() {
		return persentase_rider;
	}
	public void setPersentase_rider(Integer persentase_rider) {
		this.persentase_rider = persentase_rider;
	}
	public Integer getUnit_rider() {
		return unit_rider;
	}
	public void setUnit_rider(Integer unit_rider) {
		this.unit_rider = unit_rider;
	}
	public String getTglakhir_rider() {
		return tglakhir_rider;
	}
	public void setTglakhir_rider(String tglakhir_rider) {
		this.tglakhir_rider = tglakhir_rider;
	}
	public String getAkhirbayar_rider() {
		return akhirbayar_rider;
	}
	public void setAkhirbayar_rider(String akhirbayar_rider) {
		this.akhirbayar_rider = akhirbayar_rider;
	}
	
}
