package com.app.model.table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

public class MstProposalProductUlink implements Serializable{

	/**
	 *@author Deddy
	 *@since Jul 3, 2014
	 *@description TODO 
	 */
	private static final long serialVersionUID = -4968412415304889685L;
	private String no_proposal;
	private String lji_id;
	private Integer mdu_persen;
	
	public MstProposalProductUlink(HashMap map){
		if(map.get("NO_PROPOSAL")!=null)	no_proposal = (String) map.get("NO_PROPOSAL");
		if(map.get("LJI_ID")!=null)			lji_id = (String) map.get("LJI_ID");
		if(map.get("MDU_PERSEN")!=null)			mdu_persen = ((BigDecimal) map.get("MDU_PERSEN")).intValue();
	}
	
	public String getNo_proposal() {
		return no_proposal;
	}
	public void setNo_proposal(String no_proposal) {
		this.no_proposal = no_proposal;
	}
	public String getLji_id() {
		return lji_id;
	}
	public void setLji_id(String lji_id) {
		this.lji_id = lji_id;
	}
	public Integer getMdu_persen() {
		return mdu_persen;
	}
	public void setMdu_persen(Integer mdu_persen) {
		this.mdu_persen = mdu_persen;
	}
	
}
