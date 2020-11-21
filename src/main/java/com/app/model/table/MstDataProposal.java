package com.app.model.table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;


public class MstDataProposal implements Serializable {
	
	/**
	 * 
	 * @author : Andy
	 * @since : Mar 3, 2014 (1:35:31 PM)
	 */
	private static final long serialVersionUID = 5023933163122531374L;
	
	private Integer id;
	private String no_proposal;
	private String nama_pp;
	private Date tgl_lahir_pp;
	private Integer umur_pp;
	private String sex_pp;//FIXME
	private String nama_tt;
	private Date tgl_lahir_tt;
	private Integer umur_tt;
	private String sex_tt;//FIXME
	private String nama_agen;
	private String msag_id;
	private Date tgl_input;
	private Integer flag_aktif;
	private Integer flag_test;
	private String nama_user;
	private String kode_agen;
	private String lead_id;
	private Integer lstb_id;
	private Integer id_dist;
	private Integer jenis_id;
	private Integer lus_id;
	private Integer lrb_id;
	private String lcb_no;
	private String old_no_proposal;
	private Integer flag_tt_calon_bayi;
	
	public MstDataProposal(){};
	
	public MstDataProposal(HashMap map){
		if(map.get("ID")!=null)				id = new BigDecimal(map.get("ID").toString()).intValue();
		if(map.get("NO_PROPOSAL")!=null)	no_proposal = (String) map.get("NO_PROPOSAL");
		if(map.get("NAMA_PP")!=null)		nama_pp = (String) map.get("NAMA_PP");
		if(map.get("TGL_LAHIR_PP")!=null)	tgl_lahir_pp = (Date) map.get("TGL_LAHIR_PP");
		if(map.get("UMUR_PP")!=null)		umur_pp = new BigDecimal(map.get("UMUR_PP").toString()).intValue();
		if(map.get("SEX_PP")!=null)			sex_pp = (String) map.get("SEX_PP");
		if(map.get("NAMA_TT")!=null)		nama_tt = (String) map.get("NAMA_TT");
		if(map.get("TGL_LAHIR_TT")!=null)	tgl_lahir_tt = (Date) map.get("TGL_LAHIR_TT");
		if(map.get("UMUR_TT")!=null)		umur_tt = new BigDecimal(map.get("UMUR_TT").toString()).intValue();
		if(map.get("SEX_TT")!=null)			sex_tt = (String) map.get("SEX_TT");
		if(map.get("NAMA_AGEN")!=null)		nama_agen = (String) map.get("NAMA_AGEN");
		if(map.get("MSAG_ID")!=null)		msag_id = (String) map.get("MSAG_ID");
		if(map.get("TGL_INPUT")!=null)		tgl_input = (Date) map.get("TGL_INPUT");
		if(map.get("FLAG_AKTIF")!=null)		flag_aktif = new BigDecimal(map.get("FLAG_AKTIF").toString()).intValue();
		if(map.get("FLAG_TEST")!=null)		flag_test = new BigDecimal(map.get("FLAG_TEST").toString()).intValue();
		if(map.get("NAMA_USER")!=null)		nama_user = (String) map.get("NAMA_USER");
		if(map.get("KODE_AGEN")!=null)		kode_agen = (String) map.get("KODE_AGEN");
		if(map.get("LEAD_ID")!=null)		lead_id = (String) map.get("LEAD_ID");
        if(map.get("LSTB_ID")!=null)        lstb_id = new BigDecimal(map.get("LSTB_ID").toString()).intValue();
        if(map.get("ID_DIST")!=null)        id_dist = new BigDecimal(map.get("ID_DIST").toString()).intValue();
        if(map.get("JENIS_ID")!=null)       jenis_id = new BigDecimal(map.get("JENIS_ID").toString()).intValue();
        if(map.get("LUS_ID")!=null)       lus_id = new BigDecimal(map.get("LUS_ID").toString()).intValue();
        if(map.get("LRB_ID")!=null)       lrb_id = new BigDecimal(map.get("LRB_ID").toString()).intValue();
        if(map.get("OLD_NO_PROPOSAL")!=null)        old_no_proposal = (String) map.get("OLD_NO_PROPOSAL");
        if(map.get("FLAG_TT_CALON_BAYI")!=null) flag_tt_calon_bayi = new BigDecimal(map.get("FLAG_TT_CALON_BAYI").toString()).intValue();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNo_proposal() {
		return no_proposal;
	}
	public void setNo_proposal(String no_proposal) {
		this.no_proposal = no_proposal;
	}
	public String getNama_pp() {
		return nama_pp;
	}
	public void setNama_pp(String nama_pp) {
		this.nama_pp = nama_pp;
	}
	public Date getTgl_lahir_pp() {
		return tgl_lahir_pp;
	}
	public void setTgl_lahir_pp(Date tgl_lahir_pp) {
		this.tgl_lahir_pp = tgl_lahir_pp;
	}
	public Integer getUmur_pp() {
		return umur_pp;
	}
	public void setUmur_pp(Integer umur_pp) {
		this.umur_pp = umur_pp;
	}
	public String getSex_pp() {
		return sex_pp;
	}
	public void setSex_pp(String sex_pp) {
		this.sex_pp = sex_pp;
	}
	public String getNama_tt() {
		return nama_tt;
	}
	public void setNama_tt(String nama_tt) {
		this.nama_tt = nama_tt;
	}
	public Date getTgl_lahir_tt() {
		return tgl_lahir_tt;
	}
	public void setTgl_lahir_tt(Date tgl_lahir_tt) {
		this.tgl_lahir_tt = tgl_lahir_tt;
	}
	public Integer getUmur_tt() {
		return umur_tt;
	}
	public void setUmur_tt(Integer umur_tt) {
		this.umur_tt = umur_tt;
	}
	public String getSex_tt() {
		return sex_tt;
	}
	public void setSex_tt(String sex_tt) {
		this.sex_tt = sex_tt;
	}
	public String getNama_agen() {
		return nama_agen;
	}
	public void setNama_agen(String nama_agen) {
		this.nama_agen = nama_agen;
	}
	public String getMsag_id() {
		return msag_id;
	}
	public void setMsag_id(String msag_id) {
		this.msag_id = msag_id;
	}
	public Date getTgl_input() {
		return tgl_input;
	}
	public void setTgl_input(Date tgl_input) {
		this.tgl_input = tgl_input;
	}
	public Integer getFlag_aktif() {
		return flag_aktif;
	}
	public void setFlag_aktif(Integer flag_aktif) {
		this.flag_aktif = flag_aktif;
	}
	public Integer getFlag_test() {
		return flag_test;
	}
	public void setFlag_test(Integer flag_test) {
		this.flag_test = flag_test;
	}
	public String getNama_user() {
		return nama_user;
	}
	public void setNama_user(String nama_user) {
		this.nama_user = nama_user;
	}
	public String getKode_agen() {
		return kode_agen;
	}
	public void setKode_agen(String kode_agen) {
		this.kode_agen = kode_agen;
	}
	
	public String getLead_id() {
		return lead_id;
	}

	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}

    
    public Integer getLstb_id() {
        return lstb_id;
    }

    
    public void setLstb_id(Integer lstb_id) {
        this.lstb_id = lstb_id;
    }

    
    public Integer getId_dist() {
        return id_dist;
    }

    
    public void setId_dist(Integer id_dist) {
        this.id_dist = id_dist;
    }

    
    public Integer getJenis_id() {
        return jenis_id;
    }

    
    public void setJenis_id(Integer jenis_id) {
        this.jenis_id = jenis_id;
    }

    
    
    public Integer getLus_id() {
        return lus_id;
    }

    
    public void setLus_id(Integer lus_id) {
        this.lus_id = lus_id;
    }

    
    public Integer getLrb_id() {
        return lrb_id;
    }

    
    public void setLrb_id(Integer lrb_id) {
        this.lrb_id = lrb_id;
    }

    
    public String getLcb_no() {
        return lcb_no;
    }

    
    public void setLcb_no(String lcb_no) {
        this.lcb_no = lcb_no;
    }

    
    public String getOld_no_proposal() {
        return old_no_proposal;
    }

    
    public void setOld_no_proposal(String old_no_proposal) {
        this.old_no_proposal = old_no_proposal;
    }

    public Integer getFlag_tt_calon_bayi() {
        return flag_tt_calon_bayi;
    }

    
    public void setFlag_tt_calon_bayi(Integer flag_tt_calon_bayi) {
        this.flag_tt_calon_bayi = flag_tt_calon_bayi;
    }
}
