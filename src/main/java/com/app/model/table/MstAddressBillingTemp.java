package com.app.model.table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;


public class MstAddressBillingTemp implements Serializable {
	
	/**
	 * 
	 * @author : Andy
	 * @since : Mar 5, 2014 (5:34:47 PM)
	 */
	private static final long serialVersionUID = -4308146891286731049L;
	
	private String no_temp;
	private String reg_spaj;
	private Integer lti_id;
	private Integer lska_id;
	private Integer lskc_id;
	private Integer lskl_id;
	private String msap_contact;
	private String msap_address;
	private String msap_zip_code;
	private String msap_area_code1;
	private String msap_phone1;
	private String msap_area_code2;
	private String msap_phone2;
	private String msap_area_code3;
	private String msap_phone3;
	private String msap_area_code_fax1;
	private String msap_fax1;
	private String msap_area_code_fax2;
	private String msap_fax2;
	private String lca_id;
	private String lwk_id;
	private String lsrg_id;
	private Integer flag_cc;
	private String e_mail;
	private String no_hp;
	private String no_hp2;
	private String kota;
	private Integer lsne_id;
	
	public MstAddressBillingTemp(){};
	
	public MstAddressBillingTemp(HashMap map){
		if(map.get("NO_TEMP")!=null)				no_temp = (String) map.get("NO_TEMP");
		if(map.get("REG_SPAJ")!=null)				reg_spaj = (String) map.get("REG_SPAJ");
		if(map.get("LTI_ID")!=null)					lti_id = new BigDecimal(map.get("LTI_ID").toString()).intValue();
		if(map.get("LSKA_ID")!=null)				lska_id = new BigDecimal(map.get("LSKA_ID").toString()).intValue();
		if(map.get("LSKC_ID")!=null)				lskc_id = new BigDecimal(map.get("LSKC_ID").toString()).intValue();
		if(map.get("LSKL_ID")!=null)				lskl_id = new BigDecimal(map.get("LSKL_ID").toString()).intValue();
		if(map.get("MSAP_CONTACT")!=null)			msap_contact = (String) map.get("MSAP_CONTACT");
		if(map.get("MSAP_ADDRESS")!=null)			msap_address = (String) map.get("MSAP_ADDRESS");
		if(map.get("MSAP_ZIP_CODE")!=null)			msap_zip_code = (String) map.get("MSAP_ZIP_CODE");
		if(map.get("MSAP_AREA_CODE1")!=null)		msap_area_code1 = (String) map.get("MSAP_AREA_CODE1");
		if(map.get("MSAP_PHONE1")!=null)			msap_phone1 = (String) map.get("MSAP_PHONE1");
		if(map.get("MSAP_AREA_CODE2")!=null)		msap_area_code2 = (String) map.get("MSAP_AREA_CODE2");
		if(map.get("MSAP_PHONE2")!=null)			msap_phone2 = (String) map.get("MSAP_PHONE2");
		if(map.get("MSAP_AREA_CODE3")!=null)		msap_area_code3 = (String) map.get("MSAP_AREA_CODE3");
		if(map.get("MSAP_PHONE3")!=null)			msap_phone3 = (String) map.get("MSAP_PHONE3");
		if(map.get("MSAP_AREA_CODE_FAX1")!=null)	msap_area_code_fax1 = (String) map.get("MSAP_AREA_CODE_FAX1");
		if(map.get("MSAP_FAX1")!=null)				msap_fax1 = (String) map.get("MSAP_FAX1");
		if(map.get("MSAP_AREA_CODE_FAX2")!=null)	msap_area_code_fax2 = (String) map.get("MSAP_AREA_CODE_FAX2");
		if(map.get("MSAP_FAX2")!=null)				msap_fax2 = (String) map.get("MSAP_FAX2");
		if(map.get("LCA_ID")!=null)					lca_id = (String) map.get("LCA_ID");
		if(map.get("LWK_ID")!=null)					lwk_id = (String) map.get("LWK_ID");
		if(map.get("LSRG_ID")!=null)				lsrg_id = (String) map.get("LSRG_ID");
		if(map.get("FLAG_CC")!=null)				flag_cc = new BigDecimal(map.get("FLAG_CC").toString()).intValue();
		if(map.get("E_MAIL")!=null)					e_mail = (String) map.get("E_MAIL");
		if(map.get("NO_HP")!=null)					no_hp = (String) map.get("NO_HP");
		if(map.get("NO_HP2")!=null)					no_hp2 = (String) map.get("NO_HP2");
		if(map.get("KOTA")!=null)					kota = (String) map.get("KOTA");
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

	public Integer getLti_id() {
		return lti_id;
	}

	public void setLti_id(Integer lti_id) {
		this.lti_id = lti_id;
	}

	public Integer getLska_id() {
		return lska_id;
	}

	public void setLska_id(Integer lska_id) {
		this.lska_id = lska_id;
	}

	public Integer getLskc_id() {
		return lskc_id;
	}

	public void setLskc_id(Integer lskc_id) {
		this.lskc_id = lskc_id;
	}

	public Integer getLskl_id() {
		return lskl_id;
	}

	public void setLskl_id(Integer lskl_id) {
		this.lskl_id = lskl_id;
	}

	public String getMsap_contact() {
		return msap_contact;
	}

	public void setMsap_contact(String msap_contact) {
		this.msap_contact = msap_contact;
	}

	public String getMsap_address() {
		return msap_address;
	}

	public void setMsap_address(String msap_address) {
		this.msap_address = msap_address;
	}

	public String getMsap_zip_code() {
		return msap_zip_code;
	}

	public void setMsap_zip_code(String msap_zip_code) {
		this.msap_zip_code = msap_zip_code;
	}

	public String getMsap_area_code1() {
		return msap_area_code1;
	}

	public void setMsap_area_code1(String msap_area_code1) {
		this.msap_area_code1 = msap_area_code1;
	}

	public String getMsap_phone1() {
		return msap_phone1;
	}

	public void setMsap_phone1(String msap_phone1) {
		this.msap_phone1 = msap_phone1;
	}

	public String getMsap_area_code2() {
		return msap_area_code2;
	}

	public void setMsap_area_code2(String msap_area_code2) {
		this.msap_area_code2 = msap_area_code2;
	}

	public String getMsap_phone2() {
		return msap_phone2;
	}

	public void setMsap_phone2(String msap_phone2) {
		this.msap_phone2 = msap_phone2;
	}

	public String getMsap_area_code3() {
		return msap_area_code3;
	}

	public void setMsap_area_code3(String msap_area_code3) {
		this.msap_area_code3 = msap_area_code3;
	}

	public String getMsap_phone3() {
		return msap_phone3;
	}

	public void setMsap_phone3(String msap_phone3) {
		this.msap_phone3 = msap_phone3;
	}

	public String getMsap_area_code_fax1() {
		return msap_area_code_fax1;
	}

	public void setMsap_area_code_fax1(String msap_area_code_fax1) {
		this.msap_area_code_fax1 = msap_area_code_fax1;
	}

	public String getMsap_fax1() {
		return msap_fax1;
	}

	public void setMsap_fax1(String msap_fax1) {
		this.msap_fax1 = msap_fax1;
	}

	public String getMsap_area_code_fax2() {
		return msap_area_code_fax2;
	}

	public void setMsap_area_code_fax2(String msap_area_code_fax2) {
		this.msap_area_code_fax2 = msap_area_code_fax2;
	}

	public String getMsap_fax2() {
		return msap_fax2;
	}

	public void setMsap_fax2(String msap_fax2) {
		this.msap_fax2 = msap_fax2;
	}

	public String getLca_id() {
		return lca_id;
	}

	public void setLca_id(String lca_id) {
		this.lca_id = lca_id;
	}

	public String getLwk_id() {
		return lwk_id;
	}

	public void setLwk_id(String lwk_id) {
		this.lwk_id = lwk_id;
	}

	public String getLsrg_id() {
		return lsrg_id;
	}

	public void setLsrg_id(String lsrg_id) {
		this.lsrg_id = lsrg_id;
	}

	public Integer getFlag_cc() {
		return flag_cc;
	}

	public void setFlag_cc(Integer flag_cc) {
		this.flag_cc = flag_cc;
	}

	public String getE_mail() {
		return e_mail;
	}

	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}

	public String getNo_hp() {
		return no_hp;
	}

	public void setNo_hp(String no_hp) {
		this.no_hp = no_hp;
	}

	public String getNo_hp2() {
		return no_hp2;
	}

	public void setNo_hp2(String no_hp2) {
		this.no_hp2 = no_hp2;
	}

	public String getKota() {
		return kota;
	}

	public void setKota(String kota) {
		this.kota = kota;
	}

	public Integer getLsne_id() {
		return lsne_id;
	}

	public void setLsne_id(Integer lsne_id) {
		this.lsne_id = lsne_id;
	}
	
}
