package com.app.model.table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MstProposalProductPacket implements Serializable {
	
	/**
	 * 
	 * @author : Andy
	 * @since : Mar 6, 2014 (11:35:05 AM)
	 */
	private static final long serialVersionUID = 5664541107131879158L;
	private String no_proposal;
	private Integer flag_packet;
	private Integer flag_no;
	
	public MstProposalProductPacket(){};
	
	public MstProposalProductPacket(HashMap map){
		if(map.get("NO_PROPOSAL")!=null)			no_proposal = (String) map.get("NO_PROPOSAL");
		if(map.get("FLAG_PACKET")!=null)			flag_packet = new BigDecimal(map.get("FLAG_PACKET").toString()).intValue();
		if(map.get("FLAG_NO")!=null)				flag_no = new BigDecimal(map.get("FLAG_NO").toString()).intValue();
	}

	public String getNo_proposal() {
		return no_proposal;
	}

	public void setNo_proposal(String no_proposal) {
		this.no_proposal = no_proposal;
	}

	public Integer getFlag_packet() {
		return flag_packet;
	}

	public void setFlag_packet(Integer flag_packet) {
		this.flag_packet = flag_packet;
	}

	public Integer getFlag_no() {
		return flag_no;
	}

	public void setFlag_no(Integer flag_no) {
		this.flag_no = flag_no;
	}
	
	

}
