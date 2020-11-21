package com.app.model.table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;



public class MstProposalProductPeserta implements Serializable {
	
	/**
	 * 
	 * @author : Andy
	 * @since : Mar 4, 2014 (4:46:44 PM)
	 */
	private static final long serialVersionUID = 6796677144262388321L;
	private String no_proposal;
	private Integer lsbs_id;
	private Integer lsdbs_number;
	private String nama_peserta;
	private Date tgl_lahir;
	private Integer usia;
	private Integer lsre_id;
	private Integer peserta_ke;
	private Integer sex;
	
	public MstProposalProductPeserta(){};
	
	public MstProposalProductPeserta(HashMap map){
		if(map.get("NO_PROPOSAL")!=null)	no_proposal = (String) map.get("NO_PROPOSAL");
		if(map.get("LSBS_ID")!=null)		lsbs_id = new BigDecimal(map.get("LSBS_ID").toString()).intValue();
		if(map.get("LSDBS_NUMBER")!=null)	lsdbs_number = new BigDecimal(map.get("LSDBS_NUMBER").toString()).intValue();
		if(map.get("NAMA_PESERTA")!=null)	nama_peserta = (String) map.get("NAMA_PESERTA");
		if(map.get("TGL_LAHIR")!=null)		tgl_lahir = (Date) map.get("TGL_LAHIR");
		if(map.get("USIA")!=null)			usia = new BigDecimal(map.get("USIA").toString()).intValue();
		if(map.get("LSRE_ID")!=null)		lsre_id = new BigDecimal(map.get("LSRE_ID").toString()).intValue();
        if(map.get("PESERTA_KE")!=null)     peserta_ke = new BigDecimal(map.get("PESERTA_KE").toString()).intValue();
        if(map.get("SEX")!=null)            sex = new BigDecimal(map.get("SEX").toString()).intValue();
	}
	
	public String getNo_proposal() {
		return no_proposal;
	}
	public void setNo_proposal(String no_proposal) {
		this.no_proposal = no_proposal;
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
	public String getNama_peserta() {
		return nama_peserta;
	}
	public void setNama_peserta(String nama_peserta) {
		this.nama_peserta = nama_peserta;
	}
	public Date getTgl_lahir() {
		return tgl_lahir;
	}
	public void setTgl_lahir(Date tgl_lahir) {
		this.tgl_lahir = tgl_lahir;
	}
	public Integer getUsia() {
		return usia;
	}
	public void setUsia(Integer usia) {
		this.usia = usia;
	}

	public Integer getLsre_id() {
		return lsre_id;
	}

	public void setLsre_id(Integer lsre_id) {
		this.lsre_id = lsre_id;
	}

    
    public Integer getPeserta_ke() {
        return peserta_ke;
    }

    
    public void setPeserta_ke(Integer peserta_ke) {
        this.peserta_ke = peserta_ke;
    }

    
    public Integer getSex() {
        return sex;
    }

    
    public void setSex(Integer sex) {
        this.sex = sex;
    }
	
	
}
