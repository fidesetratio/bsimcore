package com.app.model.table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class MstProposalProductTopUp implements Serializable{

	/**
	 *@author Deddy
	 *@since Jul 3, 2014
	 *@description TODO 
	 */
	private static final long serialVersionUID = -6340288276339885651L;
	private String no_proposal;
	private Integer thn_ke;
	private BigDecimal topup;
	private BigDecimal tarik;
	
	public MstProposalProductTopUp(HashMap map){
		if(map.get("NO_PROPOSAL")!=null)	no_proposal = (String) map.get("NO_PROPOSAL");
		if(map.get("THN_KE")!=null)			thn_ke = new BigDecimal(map.get("THN_KE").toString()).intValue();
		if(map.get("TOPUP")!=null)			topup = new BigDecimal(map.get("TOPUP").toString());
        if(map.get("TARIK")!=null)          tarik = new BigDecimal(map.get("TARIK").toString());
	}
	
	public String getNo_proposal() {
		return no_proposal;
	}
	public void setNo_proposal(String no_proposal) {
		this.no_proposal = no_proposal;
	}
	public Integer getThn_ke() {
		return thn_ke;
	}
	public void setThn_ke(Integer thn_ke) {
		this.thn_ke = thn_ke;
	}
	public BigDecimal getTopup() {
		return topup;
	}
	public void setTopup(BigDecimal topup) {
		this.topup = topup;
	}
    public BigDecimal getTarik() {
        return tarik;
    }
    public void setTarik(BigDecimal tarik) {
        this.tarik = tarik;
    }
	
}
