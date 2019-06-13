package com.app.model.table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MstProposalProductRider implements Serializable {
	
	/**
	 * 
	 * @author : Andy
	 * @since : Mar 4, 2014 (4:45:33 PM)
	 */
	private static final long serialVersionUID = 8459413791101689404L;
	private String no_proposal;
	private Integer lsbs_id;
	private Integer lsdbs_number;
	private String lku_id;
	private BigDecimal premi;
	private BigDecimal up;
	private Integer persen_up;
	private Integer kelas;
	private Integer jml_unit;
	private Integer bulan_ke;
	
	public MstProposalProductRider(){};
	
	public MstProposalProductRider(HashMap map){
		if(map.get("NO_PROPOSAL")!=null)	no_proposal = (String) map.get("NO_PROPOSAL");
		if(map.get("LSBS_ID")!=null)		lsbs_id = new BigDecimal(map.get("LSBS_ID").toString()).intValue();
		if(map.get("LSDBS_NUMBER")!=null)	lsdbs_number = new BigDecimal(map.get("LSDBS_NUMBER").toString()).intValue();
		if(map.get("LKU_ID")!=null)			lku_id = (String) map.get("LKU_ID");
		if(map.get("PREMI")!=null)			premi = new BigDecimal(map.get("PREMI").toString());
		if(map.get("UP")!=null)				up = new BigDecimal(map.get("UP").toString());
		if(map.get("PERSEN_UP")!=null)		persen_up = new BigDecimal(map.get("PERSEN_UP").toString()).intValue();
		if(map.get("KELAS")!=null)			kelas = new BigDecimal(map.get("KELAS").toString()).intValue();
		if(map.get("JML_UNIT")!=null)		jml_unit = new BigDecimal(map.get("JML_UNIT").toString()).intValue();
		if(map.get("BULAN_KE")!=null)		bulan_ke = new BigDecimal(map.get("BULAN_KE").toString()).intValue();
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

	public String getLku_id() {
		return lku_id;
	}

	public void setLku_id(String lku_id) {
		this.lku_id = lku_id;
	}

	public BigDecimal getPremi() {
		return premi;
	}

	public void setPremi(BigDecimal premi) {
		this.premi = premi;
	}

	public BigDecimal getUp() {
		return up;
	}

	public void setUp(BigDecimal up) {
		this.up = up;
	}

	public Integer getPersen_up() {
		return persen_up;
	}

	public void setPersen_up(Integer persen_up) {
		this.persen_up = persen_up;
	}

	public Integer getKelas() {
		return kelas;
	}

	public void setKelas(Integer kelas) {
		this.kelas = kelas;
	}

	public Integer getJml_unit() {
		return jml_unit;
	}

	public void setJml_unit(Integer jml_unit) {
		this.jml_unit = jml_unit;
	}

	public Integer getBulan_ke() {
		return bulan_ke;
	}

	public void setBulan_ke(Integer bulan_ke) {
		this.bulan_ke = bulan_ke;
	}

}
