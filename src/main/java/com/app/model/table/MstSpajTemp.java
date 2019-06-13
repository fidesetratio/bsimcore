package com.app.model.table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

public class MstSpajTemp implements Serializable {
	
	/**
	 * 
	 * @author : Andy
	 * @since : Mar 6, 2014 (8:14:06 AM)
	 */
	private static final long serialVersionUID = 7850798413429399584L;
	private String no_temp;
	private String reg_spaj;
	private Integer flag_company;
	private String keyword_company;
	private String company_name;
	private String company_siup;
	private String company_npwp;
	private String pp_name;	
	private Date pp_bod;
	private Integer pp_sex;
	private String pp_mom_name;
	private Date tt_bod;
	private String tt_name;
	private Integer tt_sex;
	private String tt_mom_name;
	private String home_address;
	private String home_postal_code;
	private String home_city;
	private Integer lscb_id;
	private String lku_id;
	private Integer lus_id;
	private String msag_id;
	private String lca_id;
	private String lwk_id;
	private String lsrg_id;
	private Date tgl_upload;
	private String id_company;
	private Integer campaign_id;
	private Integer lsied_id_pp;
	private String mspe_no_identity_pp;
	private Integer mcl_jenis;
	private Integer flag_jn_tabungan;
	private Date mar_expired;
	private String mar_holder;
	private String mar_acc_no;
	private Integer mar_jenis;
	private Integer mar_active;
	private Integer lbn_id;
	private String tt_home_address;
	private String tt_home_city;
	private String tt_home_postal_code;
	private Integer lsied_tt;
	private String mspe_no_identity_tt;
	private String tt_alamat_kantor;
	private String tt_kota_kantor;
	private String tt_kd_pos_kantor;
	private String area_code_rumah_pp;
	private String area_code_kantor_pp;
	private String area_code_rumah_tt;
	private String area_code_kantor_tt;
	private String pp_birth_place;
	private String tt_birth_place;
	private String marital_status_pp;
	private String marital_status_tt;
	private Integer lsre_id_pp;
	private Integer lsre_id_tt;
	private String pp_alamat_kantor;
	private String pp_kd_pos_kantor;
	private String pp_kota_kantor;
	private String pp_no_hp_rumah;
	private String pp_telpon_rumah;
	private String pp_telpon_kantor;
	private String pp_hp_kantor;
	private String pp_fax_rumah;
	private String pp_fax_kantor;	
	private String email_pp;
	private Integer mste_age_pp;
	private Integer mste_age_tt;
	private Integer mspo_provider;
	private Date mste_tgl_recur;
	private Integer mste_flag_cc;
	private String mspo_nasabah_dcif;
	private String mspo_no_blanko;
	private String mste_no_vacc;
	private String no_proposal;
	private String no_spaj_gadget;
	private String no_imei;
	private String area_code_rumah_pp2;
	private String area_code_kantor_pp2;
	private String area_code_rumah_tt2;
	private String area_code_kantor_tt2;
	private String tt_telpon_rumah;
	private String tt_telpon_rumah2;
	private String tt_telpon_kantor;
	private String tt_telpon_kantor2;
	private String tt_no_hp_rumah;
	private String tt_no_hp_rumah2;
	private String pp_no_hp_rumah2;
	private String email_tt;
	private String mcl_id_pp;
	private String mcl_id_tt;
	private String mcl_id_payor;
	private Integer mspo_korespondensi;
	private Integer mspo_jenis_terbit;
	private Integer lsre_id_payor;
	private String jenis_spaj;
	private String mspo_ao;
	private Date mspo_spaj_date;
	private String mspo_id_sponsor;
	private String mspo_id_place;
	private String sertifikat;
	private Integer flag_app;
	private String version_app;
	private Integer lstp_id;
	private String mspo_id_member;

	public MstSpajTemp(){};
	
	public MstSpajTemp(HashMap map){
		if(map.get("NO_TEMP")!=null)				no_temp = (String) map.get("NO_TEMP");
		if(map.get("REG_SPAJ")!=null)				reg_spaj = (String) map.get("REG_SPAJ");
		if(map.get("FLAG_COMPANY")!=null)			flag_company = new BigDecimal(map.get("FLAG_COMPANY").toString()).intValue();
		if(map.get("KEYWORD_COMPANY")!=null)		keyword_company = (String) map.get("KEYWORD_COMPANY");
		if(map.get("COMPANY_NAME")!=null)			company_name = (String) map.get("COMPANY_NAME");
		if(map.get("COMPANY_SIUP")!=null)			company_siup = (String) map.get("COMPANY_SIUP");
		if(map.get("COMPANY_NPWP")!=null)			company_npwp = (String) map.get("COMPANY_NPWP");
		if(map.get("PP_NAME")!=null)				pp_name = (String) map.get("PP_NAME");
		if(map.get("PP_BOD")!=null)					pp_bod = (Date) map.get("PP_BOD");
		if(map.get("PP_SEX")!=null)					pp_sex = new BigDecimal(map.get("PP_SEX").toString()).intValue();
		if(map.get("PP_MOM_NAME")!=null)			pp_mom_name = (String) map.get("PP_MOM_NAME");
		if(map.get("TT_BOD")!=null)					tt_bod = (Date) map.get("TT_BOD");
		if(map.get("TT_NAME")!=null)				tt_name = (String) map.get("TT_NAME");
		if(map.get("TT_SEX")!=null)					tt_sex = new BigDecimal(map.get("TT_SEX").toString()).intValue();
		if(map.get("TT_MOM_NAME")!=null)			tt_mom_name = (String) map.get("TT_MOM_NAME");
		if(map.get("HOME_ADDRESS")!=null)			home_address = (String) map.get("HOME_ADDRESS");
		if(map.get("HOME_POSTAL_CODE")!=null)		home_postal_code = (String) map.get("HOME_POSTAL_CODE");
		if(map.get("HOME_CITY")!=null)				home_city = (String) map.get("HOME_CITY");
		if(map.get("LSCB_ID")!=null)				lscb_id = (Integer) map.get("LSCB_ID");
		if(map.get("LKU_ID")!=null)					lku_id = (String) map.get("LKU_ID");
		if(map.get("LUS_ID")!=null)					lus_id = new BigDecimal(map.get("LUS_ID").toString()).intValue();
		if(map.get("MSAG_ID")!=null)				msag_id = (String) map.get("MSAG_ID");
		if(map.get("LCA_ID")!=null)					lca_id = (String) map.get("LCA_ID");
		if(map.get("LWK_ID")!=null)					lwk_id = (String) map.get("LWK_ID");
		if(map.get("LSRG_ID")!=null)				lsrg_id = (String) map.get("LSRG_ID");
		if(map.get("TGL_UPLOAD")!=null)				tgl_upload = (Date) map.get("TGL_UPLOAD");
		if(map.get("ID_COMPANY")!=null)				id_company = (String) map.get("ID_COMPANY");
		if(map.get("CAMPAIGN_ID")!=null)			campaign_id = new BigDecimal(map.get("CAMPAIGN_ID").toString()).intValue();
		if(map.get("LSIED_ID_PP")!=null)			lsied_id_pp = new BigDecimal(map.get("LSIED_ID_PP").toString()).intValue();
		if(map.get("MSPE_NO_IDENTITY_PP")!=null)	mspe_no_identity_pp = (String) map.get("MSPE_NO_IDENTITY_PP");
		if(map.get("MCL_JENIS")!=null)				mcl_jenis = new BigDecimal(map.get("MCL_JENIS").toString()).intValue();
		if(map.get("FLAG_JN_TABUNGAN")!=null)		flag_jn_tabungan = new BigDecimal(map.get("FLAG_JN_TABUNGAN").toString()).intValue();
		if(map.get("MAR_EXPIRED")!=null)			mar_expired = (Date) map.get("MAR_EXPIRED");
		if(map.get("MAR_HOLDER")!=null)				mar_holder = (String) map.get("MAR_HOLDER");
		if(map.get("MAR_ACC_NO")!=null)				mar_acc_no = (String) map.get("MAR_ACC_NO");
		if(map.get("MAR_JENIS")!=null)				mar_jenis = new BigDecimal(map.get("MAR_JENIS").toString()).intValue();
		if(map.get("MAR_ACTIVE")!=null)				mar_active = new BigDecimal(map.get("MAR_ACTIVE").toString()).intValue();
		if(map.get("LBN_ID")!=null)					lbn_id = new BigDecimal(map.get("LBN_ID").toString()).intValue();
		if(map.get("TT_HOME_ADDRESS")!=null)		tt_home_address = (String) map.get("TT_HOME_ADDRESS");
		if(map.get("TT_HOME_CITY")!=null)			tt_home_city = (String) map.get("TT_HOME_CITY");
		if(map.get("TT_HOME_POSTAL_CODE")!=null)	tt_home_postal_code = (String) map.get("TT_HOME_POSTAL_CODE");
		if(map.get("LSIED_TT")!=null)				lsied_tt = new BigDecimal(map.get("LSIED_TT").toString()).intValue();
		if(map.get("MSPE_NO_IDENTITY_TT")!=null)	mspe_no_identity_tt = (String) map.get("MSPE_NO_IDENTITY_TT");
		if(map.get("TT_ALAMAT_KANTOR")!=null)		tt_alamat_kantor = (String) map.get("TT_ALAMAT_KANTOR");
		if(map.get("TT_KOTA_KANTOR")!=null)			tt_kota_kantor = (String) map.get("TT_KOTA_KANTOR");
		if(map.get("TT_KD_POS_KANTOR")!=null)		tt_kd_pos_kantor = (String) map.get("TT_KD_POS_KANTOR");
		if(map.get("AREA_CODE_RUMAH_PP")!=null)		area_code_rumah_pp = (String) map.get("AREA_CODE_RUMAH_PP");
		if(map.get("AREA_CODE_KANTOR_PP")!=null)	area_code_kantor_pp = (String) map.get("AREA_CODE_KANTOR_PP");
		if(map.get("AREA_CODE_RUMAH_TT")!=null)		area_code_rumah_tt = (String) map.get("AREA_CODE_RUMAH_TT");
		if(map.get("AREA_CODE_KANTOR_TT")!=null)	area_code_kantor_tt = (String) map.get("AREA_CODE_KANTOR_TT");
		if(map.get("PP_BIRTH_PLACE")!=null)			pp_birth_place = (String) map.get("PP_BIRTH_PLACE");
		if(map.get("TT_BIRTH_PLACE")!=null)			tt_birth_place = (String) map.get("TT_BIRTH_PLACE");
		if(map.get("MARITAL_STATUS_PP")!=null)		marital_status_pp = (String) map.get("MARITAL_STATUS_PP");
		if(map.get("MARITAL_STATUS_TT")!=null)		marital_status_tt = (String) map.get("MARITAL_STATUS_TT");
		if(map.get("LSRE_ID_PP")!=null)				lsre_id_pp = new BigDecimal(map.get("LSRE_ID_PP").toString()).intValue();
		if(map.get("LSRE_ID_TT")!=null)				lsre_id_tt = new BigDecimal(map.get("LSRE_ID_TT").toString()).intValue();
		if(map.get("PP_ALAMAT_KANTOR")!=null)		pp_alamat_kantor = (String) map.get("PP_ALAMAT_KANTOR");
		if(map.get("PP_KD_POS_KANTOR")!=null)		area_code_kantor_pp = (String) map.get("AREA_CODE_KANTOR_PP");
		if(map.get("PP_KOTA_KANTOR")!=null)			pp_kota_kantor = (String) map.get("PP_KOTA_KANTOR");
		if(map.get("PP_NO_HP_RUMAH")!=null)			pp_no_hp_rumah = (String) map.get("PP_NO_HP_RUMAH");
		if(map.get("PP_TELPON_RUMAH")!=null)		pp_telpon_rumah = (String) map.get("PP_TELPON_RUMAH");
		if(map.get("PP_TELPON_KANTOR")!=null)		pp_telpon_kantor = (String) map.get("PP_TELPON_KANTOR");
		if(map.get("PP_HP_KANTOR")!=null)			pp_hp_kantor = (String) map.get("PP_HP_KANTOR");
		if(map.get("PP_FAX_RUMAH")!=null)			pp_fax_rumah = (String) map.get("PP_FAX_RUMAH");
		if(map.get("PP_FAX_KANTOR")!=null)			pp_fax_kantor = (String) map.get("PP_FAX_KANTOR");
		if(map.get("EMAIL_PP")!=null)				email_pp = (String) map.get("EMAIL_PP");
		if(map.get("MSTE_AGE_PP")!=null)			mste_age_pp = new BigDecimal(map.get("MSTE_AGE_PP").toString()).intValue();
		if(map.get("MSTE_AGE_TT")!=null)			mste_age_tt = new BigDecimal(map.get("MSTE_AGE_TT").toString()).intValue();
		if(map.get("MSPO_PROVIDER")!=null)			mspo_provider = new BigDecimal(map.get("MSPO_PROVIDER").toString()).intValue();
		if(map.get("MSTE_TGL_RECUR")!=null)			mste_tgl_recur = (Date) map.get("MSTE_TGL_RECUR");
		if(map.get("MSTE_FLAG_CC")!=null)			mste_flag_cc = new BigDecimal(map.get("MSTE_FLAG_CC").toString()).intValue();
		if(map.get("MSPO_NASABAH_DCIF")!=null)		mspo_nasabah_dcif = (String) map.get("MSPO_NASABAH_DCIF");
		if(map.get("MSPO_NO_BLANKO")!=null)			mspo_no_blanko = (String) map.get("MSPO_NO_BLANKO");
		if(map.get("MSTE_NO_VACC")!=null)			mste_no_vacc = (String) map.get("MSTE_NO_VACC");
		if(map.get("NO_PROPOSAL")!=null)			no_proposal = (String) map.get("NO_PROPOSAL");
		if(map.get("MCL_ID_PP")!=null)				mcl_id_pp = (String) map.get("MCL_ID_PP");
		if(map.get("MCL_ID_TT")!=null)				mcl_id_tt = (String) map.get("MCL_ID_TT");
		if(map.get("MCL_ID_PAYOR")!=null)			mcl_id_payor = (String) map.get("MCL_ID_PAYOR");
		if(map.get("MSPO_KORESPONDENSI")!=null)		mspo_korespondensi = new BigDecimal (map.get("MSPO_KORESPONDENSI").toString()).intValue();
		if(map.get("MSPO_JENIS_TERBIT")!=null)		mspo_jenis_terbit = new BigDecimal (map.get("MSPO_JENIS_TERBIT").toString()).intValue();
		if(map.get("LSRE_ID_PAYOR")!=null)			lsre_id_payor = new BigDecimal (map.get("LSRE_ID_PAYOR").toString()).intValue();
		if(map.get("JENIS_SPAJ")!=null)				jenis_spaj = (String) map.get("JENIS_SPAJ");
		if(map.get("MSPO_AO")!=null)				mspo_ao = (String) map.get("MSPO_AO");
		if(map.get("MSPO_SPAJ_DATE")!=null)			mspo_spaj_date = (Date) map.get("MSPO_SPAJ_DATE");
		if(map.get("MSPO_ID_SPONSOR")!=null)		mspo_id_sponsor = (String) map.get("MSPO_ID_SPONSOR");
		if(map.get("MSPO_ID_PLACE")!=null)			mspo_id_place = (String) map.get("MSPO_ID_PLACE");
		if(map.get("SERTIFIKAT")!=null)				sertifikat = (String) map.get("SERTIFIKAT");
		if(map.get("FLAG_APP")!=null)				flag_app = new BigDecimal (map.get("FLAG_APP").toString()).intValue();
		if(map.get("VERSION_APP")!=null)			version_app = (String) map.get("VERSION_APP");
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

	public Integer getFlag_company() {
		return flag_company;
	}

	public void setFlag_company(Integer flag_company) {
		this.flag_company = flag_company;
	}

	public String getKeyword_company() {
		return keyword_company;
	}

	public void setKeyword_company(String keyword_company) {
		this.keyword_company = keyword_company;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_siup() {
		return company_siup;
	}

	public void setCompany_siup(String company_siup) {
		this.company_siup = company_siup;
	}

	public String getCompany_npwp() {
		return company_npwp;
	}

	public void setCompany_npwp(String company_npwp) {
		this.company_npwp = company_npwp;
	}

	public String getPp_name() {
		return pp_name;
	}

	public void setPp_name(String pp_name) {
		this.pp_name = pp_name;
	}

	public Date getPp_bod() {
		return pp_bod;
	}

	public void setPp_bod(Date pp_bod) {
		this.pp_bod = pp_bod;
	}

	public Integer getPp_sex() {
		return pp_sex;
	}

	public void setPp_sex(Integer pp_sex) {
		this.pp_sex = pp_sex;
	}

	public String getPp_mom_name() {
		return pp_mom_name;
	}

	public void setPp_mom_name(String pp_mom_name) {
		this.pp_mom_name = pp_mom_name;
	}

	public Date getTt_bod() {
		return tt_bod;
	}

	public void setTt_bod(Date tt_bod) {
		this.tt_bod = tt_bod;
	}

	public String getTt_name() {
		return tt_name;
	}

	public void setTt_name(String tt_name) {
		this.tt_name = tt_name;
	}

	public Integer getTt_sex() {
		return tt_sex;
	}

	public void setTt_sex(Integer tt_sex) {
		this.tt_sex = tt_sex;
	}

	public String getTt_mom_name() {
		return tt_mom_name;
	}

	public void setTt_mom_name(String tt_mom_name) {
		this.tt_mom_name = tt_mom_name;
	}

	public String getHome_address() {
		return home_address;
	}

	public void setHome_address(String home_address) {
		this.home_address = home_address;
	}

	public String getHome_postal_code() {
		return home_postal_code;
	}

	public void setHome_postal_code(String home_postal_code) {
		this.home_postal_code = home_postal_code;
	}

	public String getHome_city() {
		return home_city;
	}

	public void setHome_city(String home_city) {
		this.home_city = home_city;
	}

	public Integer getLscb_id() {
		return lscb_id;
	}

	public void setLscb_id(Integer lscb_id) {
		this.lscb_id = lscb_id;
	}

	public String getLku_id() {
		return lku_id;
	}

	public void setLku_id(String lku_id) {
		this.lku_id = lku_id;
	}

	public Integer getLus_id() {
		return lus_id;
	}

	public void setLus_id(Integer lus_id) {
		this.lus_id = lus_id;
	}

	public String getMsag_id() {
		return msag_id;
	}

	public void setMsag_id(String msag_id) {
		this.msag_id = msag_id;
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

	public Date getTgl_upload() {
		return tgl_upload;
	}

	public void setTgl_upload(Date tgl_upload) {
		this.tgl_upload = tgl_upload;
	}

	public String getId_company() {
		return id_company;
	}

	public void setId_company(String id_company) {
		this.id_company = id_company;
	}

	public Integer getCampaign_id() {
		return campaign_id;
	}

	public void setCampaign_id(Integer campaign_id) {
		this.campaign_id = campaign_id;
	}

	public Integer getLsied_id_pp() {
		return lsied_id_pp;
	}

	public void setLsied_id_pp(Integer lsied_id_pp) {
		this.lsied_id_pp = lsied_id_pp;
	}

	public String getMspe_no_identity_pp() {
		return mspe_no_identity_pp;
	}

	public void setMspe_no_identity_pp(String mspe_no_identity_pp) {
		this.mspe_no_identity_pp = mspe_no_identity_pp;
	}

	public Integer getMcl_jenis() {
		return mcl_jenis;
	}

	public void setMcl_jenis(Integer mcl_jenis) {
		this.mcl_jenis = mcl_jenis;
	}

	public Integer getFlag_jn_tabungan() {
		return flag_jn_tabungan;
	}

	public void setFlag_jn_tabungan(Integer flag_jn_tabungan) {
		this.flag_jn_tabungan = flag_jn_tabungan;
	}

	public Date getMar_expired() {
		return mar_expired;
	}

	public void setMar_expired(Date mar_expired) {
		this.mar_expired = mar_expired;
	}

	public String getMar_holder() {
		return mar_holder;
	}

	public void setMar_holder(String mar_holder) {
		this.mar_holder = mar_holder;
	}

	public String getMar_acc_no() {
		return mar_acc_no;
	}

	public void setMar_acc_no(String mar_acc_no) {
		this.mar_acc_no = mar_acc_no;
	}

	public Integer getMar_jenis() {
		return mar_jenis;
	}

	public void setMar_jenis(Integer mar_jenis) {
		this.mar_jenis = mar_jenis;
	}

	public Integer getMar_active() {
		return mar_active;
	}

	public void setMar_active(Integer mar_active) {
		this.mar_active = mar_active;
	}

	public Integer getLbn_id() {
		return lbn_id;
	}

	public void setLbn_id(Integer lbn_id) {
		this.lbn_id = lbn_id;
	}

	public String getTt_home_address() {
		return tt_home_address;
	}

	public void setTt_home_address(String tt_home_address) {
		this.tt_home_address = tt_home_address;
	}

	public String getTt_home_city() {
		return tt_home_city;
	}

	public void setTt_home_city(String tt_home_city) {
		this.tt_home_city = tt_home_city;
	}

	public String getTt_home_postal_code() {
		return tt_home_postal_code;
	}

	public void setTt_home_postal_code(String tt_home_postal_code) {
		this.tt_home_postal_code = tt_home_postal_code;
	}

	public Integer getLsied_tt() {
		return lsied_tt;
	}

	public void setLsied_tt(Integer lsied_tt) {
		this.lsied_tt = lsied_tt;
	}

	public String getMspe_no_identity_tt() {
		return mspe_no_identity_tt;
	}

	public void setMspe_no_identity_tt(String mspe_no_identity_tt) {
		this.mspe_no_identity_tt = mspe_no_identity_tt;
	}

	public String getTt_alamat_kantor() {
		return tt_alamat_kantor;
	}

	public void setTt_alamat_kantor(String tt_alamat_kantor) {
		this.tt_alamat_kantor = tt_alamat_kantor;
	}

	public String getTt_kota_kantor() {
		return tt_kota_kantor;
	}

	public void setTt_kota_kantor(String tt_kota_kantor) {
		this.tt_kota_kantor = tt_kota_kantor;
	}

	public String getTt_kd_pos_kantor() {
		return tt_kd_pos_kantor;
	}

	public void setTt_kd_pos_kantor(String tt_kd_pos_kantor) {
		this.tt_kd_pos_kantor = tt_kd_pos_kantor;
	}

	public String getArea_code_rumah_pp() {
		return area_code_rumah_pp;
	}

	public void setArea_code_rumah_pp(String area_code_rumah_pp) {
		this.area_code_rumah_pp = area_code_rumah_pp;
	}

	public String getArea_code_kantor_pp() {
		return area_code_kantor_pp;
	}

	public void setArea_code_kantor_pp(String area_code_kantor_pp) {
		this.area_code_kantor_pp = area_code_kantor_pp;
	}

	public String getArea_code_rumah_tt() {
		return area_code_rumah_tt;
	}

	public void setArea_code_rumah_tt(String area_code_rumah_tt) {
		this.area_code_rumah_tt = area_code_rumah_tt;
	}

	public String getArea_code_kantor_tt() {
		return area_code_kantor_tt;
	}

	public void setArea_code_kantor_tt(String area_code_kantor_tt) {
		this.area_code_kantor_tt = area_code_kantor_tt;
	}

	public String getPp_birth_place() {
		return pp_birth_place;
	}

	public void setPp_birth_place(String pp_birth_place) {
		this.pp_birth_place = pp_birth_place;
	}

	public String getTt_birth_place() {
		return tt_birth_place;
	}

	public void setTt_birth_place(String tt_birth_place) {
		this.tt_birth_place = tt_birth_place;
	}

	public String getMarital_status_pp() {
		return marital_status_pp;
	}

	public void setMarital_status_pp(String marital_status_pp) {
		this.marital_status_pp = marital_status_pp;
	}

	public String getMarital_status_tt() {
		return marital_status_tt;
	}

	public void setMarital_status_tt(String marital_status_tt) {
		this.marital_status_tt = marital_status_tt;
	}

	public Integer getLsre_id_pp() {
		return lsre_id_pp;
	}

	public void setLsre_id_pp(Integer lsre_id_pp) {
		this.lsre_id_pp = lsre_id_pp;
	}

	public Integer getLsre_id_tt() {
		return lsre_id_tt;
	}

	public void setLsre_id_tt(Integer lsre_id_tt) {
		this.lsre_id_tt = lsre_id_tt;
	}

	public String getPp_alamat_kantor() {
		return pp_alamat_kantor;
	}

	public void setPp_alamat_kantor(String pp_alamat_kantor) {
		this.pp_alamat_kantor = pp_alamat_kantor;
	}

	public String getPp_kd_pos_kantor() {
		return pp_kd_pos_kantor;
	}

	public void setPp_kd_pos_kantor(String pp_kd_pos_kantor) {
		this.pp_kd_pos_kantor = pp_kd_pos_kantor;
	}

	public String getPp_kota_kantor() {
		return pp_kota_kantor;
	}

	public void setPp_kota_kantor(String pp_kota_kantor) {
		this.pp_kota_kantor = pp_kota_kantor;
	}

	public String getPp_no_hp_rumah() {
		return pp_no_hp_rumah;
	}

	public void setPp_no_hp_rumah(String pp_no_hp_rumah) {
		this.pp_no_hp_rumah = pp_no_hp_rumah;
	}

	public String getPp_telpon_rumah() {
		return pp_telpon_rumah;
	}

	public void setPp_telpon_rumah(String pp_telpon_rumah) {
		this.pp_telpon_rumah = pp_telpon_rumah;
	}

	public String getPp_telpon_kantor() {
		return pp_telpon_kantor;
	}

	public void setPp_telpon_kantor(String pp_telpon_kantor) {
		this.pp_telpon_kantor = pp_telpon_kantor;
	}

	public String getPp_hp_kantor() {
		return pp_hp_kantor;
	}

	public void setPp_hp_kantor(String pp_hp_kantor) {
		this.pp_hp_kantor = pp_hp_kantor;
	}

	public String getPp_fax_rumah() {
		return pp_fax_rumah;
	}

	public void setPp_fax_rumah(String pp_fax_rumah) {
		this.pp_fax_rumah = pp_fax_rumah;
	}

	public String getPp_fax_kantor() {
		return pp_fax_kantor;
	}

	public void setPp_fax_kantor(String pp_fax_kantor) {
		this.pp_fax_kantor = pp_fax_kantor;
	}

	public String getEmail_pp() {
		return email_pp;
	}

	public void setEmail_pp(String email_pp) {
		this.email_pp = email_pp;
	}

	public Integer getMste_age_pp() {
		return mste_age_pp;
	}

	public void setMste_age_pp(Integer mste_age_pp) {
		this.mste_age_pp = mste_age_pp;
	}

	public Integer getMste_age_tt() {
		return mste_age_tt;
	}

	public void setMste_age_tt(Integer mste_age_tt) {
		this.mste_age_tt = mste_age_tt;
	}

	public Integer getMspo_provider() {
		return mspo_provider;
	}

	public void setMspo_provider(Integer mspo_provider) {
		this.mspo_provider = mspo_provider;
	}

	public Date getMste_tgl_recur() {
		return mste_tgl_recur;
	}

	public void setMste_tgl_recur(Date mste_tgl_recur) {
		this.mste_tgl_recur = mste_tgl_recur;
	}

	public Integer getMste_flag_cc() {
		return mste_flag_cc;
	}

	public void setMste_flag_cc(Integer mste_flag_cc) {
		this.mste_flag_cc = mste_flag_cc;
	}

	public String getMspo_nasabah_dcif() {
		return mspo_nasabah_dcif;
	}

	public void setMspo_nasabah_dcif(String mspo_nasabah_dcif) {
		this.mspo_nasabah_dcif = mspo_nasabah_dcif;
	}

	public String getMspo_no_blanko() {
		return mspo_no_blanko;
	}

	public void setMspo_no_blanko(String mspo_no_blanko) {
		this.mspo_no_blanko = mspo_no_blanko;
	}
	
	public String getMste_no_vacc() {
		return mste_no_vacc;
	}

	public void setMste_no_vacc(String mste_no_vacc) {
		this.mste_no_vacc = mste_no_vacc;
	}

	public String getNo_proposal() {
		return no_proposal;
	}

	public void setNo_proposal(String no_proposal) {
		this.no_proposal = no_proposal;
	}
	
	public String getArea_code_rumah_pp2() {
		return area_code_rumah_pp2;
	}
	public void setArea_code_rumah_pp2(String area_code_rumah_pp2) {
		this.area_code_rumah_pp2 = area_code_rumah_pp2;
	}
	public String getArea_code_kantor_pp2() {
		return area_code_kantor_pp2;
	}
	public void setArea_code_kantor_pp2(String area_code_kantor_pp2) {
		this.area_code_kantor_pp2 = area_code_kantor_pp2;
	}
	public String getArea_code_rumah_tt2() {
		return area_code_rumah_tt2;
	}
	public void setArea_code_rumah_tt2(String area_code_rumah_tt2) {
		this.area_code_rumah_tt2 = area_code_rumah_tt2;
	}
	public String getArea_code_kantor_tt2() {
		return area_code_kantor_tt2;
	}
	public void setArea_code_kantor_tt2(String area_code_kantor_tt2) {
		this.area_code_kantor_tt2 = area_code_kantor_tt2;
	}
	public String getTt_telpon_rumah() {
		return tt_telpon_rumah;
	}
	public void setTt_telpon_rumah(String tt_telpon_rumah) {
		this.tt_telpon_rumah = tt_telpon_rumah;
	}
	public String getTt_telpon_rumah2() {
		return tt_telpon_rumah2;
	}
	public void setTt_telpon_rumah2(String tt_telpon_rumah2) {
		this.tt_telpon_rumah2 = tt_telpon_rumah2;
	}
	public String getTt_telpon_kantor() {
		return tt_telpon_kantor;
	}
	public void setTt_telpon_kantor(String tt_telpon_kantor) {
		this.tt_telpon_kantor = tt_telpon_kantor;
	}
	public String getTt_telpon_kantor2() {
		return tt_telpon_kantor2;
	}
	public void setTt_telpon_kantor2(String tt_telpon_kantor2) {
		this.tt_telpon_kantor2 = tt_telpon_kantor2;
	}
	public String getTt_no_hp_rumah() {
		return tt_no_hp_rumah;
	}
	public void setTt_no_hp_rumah(String tt_no_hp_rumah) {
		this.tt_no_hp_rumah = tt_no_hp_rumah;
	}
	public String getTt_no_hp_rumah2() {
		return tt_no_hp_rumah2;
	}
	public void setTt_no_hp_rumah2(String tt_no_hp_rumah2) {
		this.tt_no_hp_rumah2 = tt_no_hp_rumah2;
	}
	public String getPp_no_hp_rumah2() {
		return pp_no_hp_rumah2;
	}
	public void setPp_no_hp_rumah2(String pp_no_hp_rumah2) {
		this.pp_no_hp_rumah2 = pp_no_hp_rumah2;
	}
	public String getEmail_tt() {
		return email_tt;
	}
	public void setEmail_tt(String email_tt) {
		this.email_tt = email_tt;
	}
	public String getNo_spaj_gadget() {
		return no_spaj_gadget;
	}

	public void setNo_spaj_gadget(String no_spaj_gadget) {
		this.no_spaj_gadget = no_spaj_gadget;
	}

	public String getNo_imei() {
		return no_imei;
	}

	public void setNo_imei(String no_imei) {
		this.no_imei = no_imei;
	}

	public String getMcl_id_pp() {
		return mcl_id_pp;
	}

	public void setMcl_id_pp(String mcl_id_pp) {
		this.mcl_id_pp = mcl_id_pp;
	}

	public String getMcl_id_tt() {
		return mcl_id_tt;
	}

	public void setMcl_id_tt(String mcl_id_tt) {
		this.mcl_id_tt = mcl_id_tt;
	}

	public String getMcl_id_payor() {
		return mcl_id_payor;
	}

	public void setMcl_id_payor(String mcl_id_payor) {
		this.mcl_id_payor = mcl_id_payor;
	}

	public Integer getMspo_korespondensi() {
		return mspo_korespondensi;
	}

	public void setMspo_korespondensi(Integer mspo_korespondensi) {
		this.mspo_korespondensi = mspo_korespondensi;
	}

	public Integer getMspo_jenis_terbit() {
		return mspo_jenis_terbit;
	}

	public void setMspo_jenis_terbit(Integer mspo_jenis_terbit) {
		this.mspo_jenis_terbit = mspo_jenis_terbit;
	}

	public Integer getLsre_id_payor() {
		return lsre_id_payor;
	}

	public void setLsre_id_payor(Integer lsre_id_payor) {
		this.lsre_id_payor = lsre_id_payor;
	}

	public String getJenis_spaj() {
		return jenis_spaj;
	}

	public void setJenis_spaj(String jenis_spaj) {
		this.jenis_spaj = jenis_spaj;
	}

	public String getMspo_ao() {
		return mspo_ao;
	}

	public void setMspo_ao(String mspo_ao) {
		this.mspo_ao = mspo_ao;
	}

	public Date getMspo_spaj_date() {
		return mspo_spaj_date;
	}

	public void setMspo_spaj_date(Date mspo_spaj_date) {
		this.mspo_spaj_date = mspo_spaj_date;
	}	

	public String getMspo_id_sponsor() {
		return mspo_id_sponsor;
	}

	public void setMspo_id_sponsor(String mspo_id_sponsor) {
		this.mspo_id_sponsor = mspo_id_sponsor;
	}

	public String getMspo_id_place() {
		return mspo_id_place;
	}

	public void setMspo_id_place(String mspo_id_place) {
		this.mspo_id_place = mspo_id_place;
	}
	
	public String getSertifikat() {
		return sertifikat;
	}

	public void setSertifikat(String sertifikat) {
		this.sertifikat = sertifikat;
	}

	public Integer getFlag_app() {
		return flag_app;
	}

	public void setFlag_app(Integer flag_app) {
		this.flag_app = flag_app;
	}
	
	public String getVersion_app() {
		return version_app;
	}

	public void setVersion_app(String version_app) {
		this.version_app = version_app;
	}

	public Integer getLstp_id() {
		return lstp_id;
	}

	public void setLstp_id(Integer lstp_id) {
		this.lstp_id = lstp_id;
	}

	public String getMspo_id_member() {
		return mspo_id_member;
	}

	public void setMspo_id_member(String mspo_id_member) {
		this.mspo_id_member = mspo_id_member;
	}
}
