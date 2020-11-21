package com.app.model.table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class MstPesertaTemp implements Serializable {
	
	/**
	 * 
	 * @author : Andy
	 * @since : Mar 5, 2014 (7:04:22 PM)
	 */
	private static final long serialVersionUID = -4636330685179230862L;
	private String no_temp;
	private String reg_spaj;
	private Integer no_urut;
	private Integer lsre_id;
	private String nama;
	private Date tgl_lahir;
	private Integer kelamin;
	private Integer umur;
	private BigDecimal premi;
	private Integer lsbs_id;
	private Integer lsdbs_number;
	private Integer discount;
	private Integer no_reg;
	private Integer flag_admedika;
	private Integer lspc_no;
	private Integer aktif;
	private Integer flag_val_send;
	private Date next_send;
	private Date tgl_aktif;
	private Integer jenis;
	private Integer flag_jenis_peserta;
	private Integer height;
	private Integer weight;
	private Integer lsne_id;
	
	public MstPesertaTemp(){};
	
	public MstPesertaTemp(HashMap map){
		if(map.get("NO_TEMP")!=null)				no_temp = (String) map.get("NO_TEMP");
		if(map.get("REG_SPAJ")!=null)				reg_spaj = (String) map.get("REG_SPAJ");
		if(map.get("NO_URUT")!=null)				no_urut = new BigDecimal(map.get("NO_URUT").toString()).intValue();
		if(map.get("LSRE_ID")!=null)				lsre_id = new BigDecimal(map.get("LSRE_ID").toString()).intValue();
		if(map.get("NAMA")!=null)					nama = (String) map.get("NAMA");
		if(map.get("TGL_LAHIR")!=null)				tgl_lahir = (Date) map.get("TGL_LAHIR");
		if(map.get("KELAMIN")!=null)				kelamin = new BigDecimal(map.get("KELAMIN").toString()).intValue();
		if(map.get("UMUR")!=null)					umur = new BigDecimal(map.get("UMUR").toString()).intValue();
		if(map.get("PREMI")!=null)					premi = new BigDecimal(map.get("PREMI").toString());
		if(map.get("LSBS_ID")!=null)				lsbs_id = new BigDecimal(map.get("LSBS_ID").toString()).intValue();
		if(map.get("LSDBS_NUMBER")!=null)			lsdbs_number = new BigDecimal(map.get("LSDBS_NUMBER").toString()).intValue();
		if(map.get("DISCOUNT")!=null)				discount = new BigDecimal(map.get("DISCOUNT").toString()).intValue();
		if(map.get("NO_REG")!=null)					no_reg = new BigDecimal(map.get("NO_REG").toString()).intValue();
		if(map.get("FLAG_ADMEDIKA")!=null)			flag_admedika = new BigDecimal(map.get("FLAG_ADMEDIKA").toString()).intValue();
		if(map.get("LSPC_NO")!=null)				lspc_no = new BigDecimal(map.get("LSPC_NO").toString()).intValue();
		if(map.get("AKTIF")!=null)					aktif = new BigDecimal(map.get("AKTIF").toString()).intValue();
		if(map.get("FLAG_VAL_SEND")!=null)			flag_val_send = new BigDecimal(map.get("FLAG_VAL_SEND").toString()).intValue();
		if(map.get("NEXT_SEND")!=null)				next_send = (Date) map.get("NEXT_SEND");
		if(map.get("TGL_AKTIF")!=null)				tgl_aktif = (Date) map.get("TGL_AKTIF");
		if(map.get("JENIS")!=null)					jenis = new BigDecimal(map.get("JENIS").toString()).intValue();
		if(map.get("FLAG_JENIS_PESERTA")!=null)		flag_jenis_peserta = new BigDecimal(map.get("FLAG_JENIS_PESERTA").toString()).intValue();
		if(map.get("HEIGHT")!=null)					height = new BigDecimal(map.get("HEIGHT").toString()).intValue();
		if(map.get("WEIGHT")!=null)					weight = new BigDecimal(map.get("WEIGHT").toString()).intValue();
		if(map.get("LSNE_ID")!=null)				lsne_id = new BigDecimal(map.get("LSNE_ID").toString()).intValue();
		
	}

	public String getNo_temp() {
		return no_temp;
	}

	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}

	public String getReg_spaj() {
		return reg_spaj;
	}

	public void setReg_spaj(String reg_spaj) {
		this.reg_spaj = reg_spaj;
	}

	public Integer getNo_urut() {
		return no_urut;
	}

	public void setNo_urut(Integer no_urut) {
		this.no_urut = no_urut;
	}

	public Integer getLsre_id() {
		return lsre_id;
	}

	public void setLsre_id(Integer lsre_id) {
		this.lsre_id = lsre_id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public Date getTgl_lahir() {
		return tgl_lahir;
	}

	public void setTgl_lahir(Date tgl_lahir) {
		this.tgl_lahir = tgl_lahir;
	}

	public Integer getKelamin() {
		return kelamin;
	}

	public void setKelamin(Integer kelamin) {
		this.kelamin = kelamin;
	}

	public Integer getUmur() {
		return umur;
	}

	public void setUmur(Integer umur) {
		this.umur = umur;
	}

	public BigDecimal getPremi() {
		return premi;
	}

	public void setPremi(BigDecimal premi) {
		this.premi = premi;
	}

	public Integer getLsbs_id() {
		return lsbs_id;
	}

	public void setLsbs_id(Integer lsbs_id) {
		this.lsbs_id = lsbs_id;
	}

	public Integer getLsdbs_number() {
		return lsdbs_number;
	}

	public void setLsdbs_number(Integer lsdbs_number) {
		this.lsdbs_number = lsdbs_number;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getNo_reg() {
		return no_reg;
	}

	public void setNo_reg(Integer no_reg) {
		this.no_reg = no_reg;
	}

	public Integer getFlag_admedika() {
		return flag_admedika;
	}

	public void setFlag_admedika(Integer flag_admedika) {
		this.flag_admedika = flag_admedika;
	}

	public Integer getLspc_no() {
		return lspc_no;
	}

	public void setLspc_no(Integer lspc_no) {
		this.lspc_no = lspc_no;
	}

	public Integer getAktif() {
		return aktif;
	}

	public void setAktif(Integer aktif) {
		this.aktif = aktif;
	}

	public Integer getFlag_val_send() {
		return flag_val_send;
	}

	public void setFlag_val_send(Integer flag_val_send) {
		this.flag_val_send = flag_val_send;
	}

	public Date getNext_send() {
		return next_send;
	}

	public void setNext_send(Date next_send) {
		this.next_send = next_send;
	}

	public Date getTgl_aktif() {
		return tgl_aktif;
	}

	public void setTgl_aktif(Date tgl_aktif) {
		this.tgl_aktif = tgl_aktif;
	}

	public Integer getJenis() {
		return jenis;
	}

	public void setJenis(Integer jenis) {
		this.jenis = jenis;
	}

	public Integer getFlag_jenis_peserta() {
		return flag_jenis_peserta;
	}

	public void setFlag_jenis_peserta(Integer flag_jenis_peserta) {
		this.flag_jenis_peserta = flag_jenis_peserta;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getLsne_id() {
		return lsne_id;
	}

	public void setLsne_id(Integer lsne_id) {
		this.lsne_id = lsne_id;
	}
	

}
