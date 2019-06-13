package com.app.model.table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MstProposalProduct implements Serializable {
	
	
	/**
	 * 
	 * @author : Andy
	 * @since : Mar 4, 2014 (4:44:04 PM)
	 */
	private static final long serialVersionUID = 1522645148371402022L;
	
	private String no_proposal;
	private Integer lsbs_id;
	private Integer lsdbs_number;
	private String lku_id;
	private Integer flag_packet;
	private String grp_product_name;
	private Integer grp_product_sub_id;
	private BigDecimal premi;
	private BigDecimal premi_komb;
	private BigDecimal premi_pokok;
	private BigDecimal premi_topup;
	private BigDecimal up;
	private Integer cara_bayar;
	private Integer thn_cuti_premi;
	private Integer thn_masa_kontrak;
	private Integer thn_lama_bayar;
	private BigDecimal inv_fix;
	private BigDecimal inv_dynamic;
	private BigDecimal inv_aggressive;
	private String lji_fix;
	private String lji_dynamic;
	private String lji_aggresive;
	
	public MstProposalProduct(){};
	
	public MstProposalProduct(HashMap map){
		if(map.get("NO_PROPOSAL")!=null)			no_proposal = (String) map.get("NO_PROPOSAL");
		if(map.get("LSBS_ID")!=null)				lsbs_id = new BigDecimal(map.get("LSBS_ID").toString()).intValue();
		if(map.get("LSDBS_NUMBER")!=null)			lsdbs_number = new BigDecimal(map.get("LSDBS_NUMBER").toString()).intValue();
		if(map.get("LKU_ID")!=null)					lku_id = (String) map.get("LKU_ID");
		if(map.get("FLAG_PACKET")!=null)			flag_packet = new BigDecimal(map.get("FLAG_PACKET").toString()).intValue();
		if(map.get("GRP_PRODUCT_NAME")!=null)		grp_product_name = (String) map.get("GRP_PRODUCT_NAME");
		if(map.get("GRP_PRODUCT_SUB_ID")!=null)		grp_product_sub_id = new BigDecimal(map.get("GRP_PRODUCT_SUB_ID").toString()).intValue();
		if(map.get("PREMI")!=null)					premi = new BigDecimal(map.get("PREMI").toString());
		if(map.get("PREMI_KOMB")!=null)				premi_komb = new BigDecimal(map.get("PREMI_KOMB").toString());
		if(map.get("PREMI_POKOK")!=null)			premi_pokok = new BigDecimal(map.get("PREMI_POKOK").toString());
		if(map.get("PREMI_TOPUP")!=null)			premi_topup = new BigDecimal(map.get("PREMI_TOPUP").toString());
		if(map.get("UP")!=null)						up = new BigDecimal(map.get("UP").toString());
		if(map.get("CARA_BAYAR")!=null)				cara_bayar = new BigDecimal(map.get("CARA_BAYAR").toString()).intValue();
		if(map.get("THN_CUTI_PREMI")!=null)			thn_cuti_premi = new BigDecimal(map.get("THN_CUTI_PREMI").toString()).intValue();
		if(map.get("THN_MASA_KONTRAK")!=null)		thn_masa_kontrak = new BigDecimal(map.get("THN_MASA_KONTRAK").toString()).intValue();
		if(map.get("THN_LAMA_BAYAR")!=null)			thn_lama_bayar = new BigDecimal(map.get("THN_LAMA_BAYAR").toString()).intValue();
		if(map.get("INV_FIX")!=null)				inv_fix = new BigDecimal(map.get("INV_FIX").toString());
		if(map.get("INV_DYNAMIC")!=null)			inv_dynamic = new BigDecimal(map.get("INV_DYNAMIC").toString());
		if(map.get("INV_AGGRESSIVE")!=null)			inv_aggressive = new BigDecimal(map.get("INV_AGGRESSIVE").toString());
        if(map.get("LJI_FIX")!=null)                lji_fix = (String) map.get("LJI_FIX");
        if(map.get("LJI_DYNAMIC")!=null)            lji_dynamic = (String) map.get("LJI_DYNAMIC");
        if(map.get("LJI_AGGRESIVE")!=null)         lji_aggresive = (String) map.get("LJI_AGGRESIVE");
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

	public Integer getFlag_packet() {
		return flag_packet;
	}

	public void setFlag_packet(Integer flag_packet) {
		this.flag_packet = flag_packet;
	}

	public String getGrp_product_name() {
		return grp_product_name;
	}

	public void setGrp_product_name(String grp_product_name) {
		this.grp_product_name = grp_product_name;
	}

	public Integer getGrp_product_sub_id() {
		return grp_product_sub_id;
	}

	public void setGrp_product_sub_id(Integer grp_product_sub_id) {
		this.grp_product_sub_id = grp_product_sub_id;
	}

	public BigDecimal getPremi() {
		return premi;
	}

	public void setPremi(BigDecimal premi) {
		this.premi = premi;
	}

	public BigDecimal getPremi_komb() {
		return premi_komb;
	}

	public void setPremi_komb(BigDecimal premi_komb) {
		this.premi_komb = premi_komb;
	}

	public BigDecimal getPremi_pokok() {
		return premi_pokok;
	}

	public void setPremi_pokok(BigDecimal premi_pokok) {
		this.premi_pokok = premi_pokok;
	}

	public BigDecimal getPremi_topup() {
		return premi_topup;
	}

	public void setPremi_topup(BigDecimal premi_topup) {
		this.premi_topup = premi_topup;
	}

	public BigDecimal getUp() {
		return up;
	}

	public void setUp(BigDecimal up) {
		this.up = up;
	}

	public Integer getCara_bayar() {
		return cara_bayar;
	}

	public void setCara_bayar(Integer cara_bayar) {
		this.cara_bayar = cara_bayar;
	}

	public Integer getThn_cuti_premi() {
		return thn_cuti_premi;
	}

	public void setThn_cuti_premi(Integer thn_cuti_premi) {
		this.thn_cuti_premi = thn_cuti_premi;
	}

	public Integer getThn_masa_kontrak() {
		return thn_masa_kontrak;
	}

	public void setThn_masa_kontrak(Integer thn_masa_kontrak) {
		this.thn_masa_kontrak = thn_masa_kontrak;
	}

	public Integer getThn_lama_bayar() {
		return thn_lama_bayar;
	}

	public void setThn_lama_bayar(Integer thn_lama_bayar) {
		this.thn_lama_bayar = thn_lama_bayar;
	}

	public BigDecimal getInv_fix() {
		return inv_fix;
	}

	public void setInv_fix(BigDecimal inv_fix) {
		this.inv_fix = inv_fix;
	}

	public BigDecimal getInv_dynamic() {
		return inv_dynamic;
	}

	public void setInv_dynamic(BigDecimal inv_dynamic) {
		this.inv_dynamic = inv_dynamic;
	}

	public BigDecimal getInv_aggressive() {
		return inv_aggressive;
	}

	public void setInv_aggressive(BigDecimal inv_aggressive) {
		this.inv_aggressive = inv_aggressive;
	}

    
    public String getLji_fix() {
        return lji_fix;
    }

    
    public void setLji_fix(String lji_fix) {
        this.lji_fix = lji_fix;
    }

    
    public String getLji_dynamic() {
        return lji_dynamic;
    }

    
    public void setLji_dynamic(String lji_dynamic) {
        this.lji_dynamic = lji_dynamic;
    }

    
    public String getLji_aggresive() {
        return lji_aggresive;
    }

    
    public void setLji_aggresive(String lji_aggresive) {
        this.lji_aggresive = lji_aggresive;
    }
	
}
