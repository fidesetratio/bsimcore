package com.app.model.table;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



public class IndomaretResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4603821713570009156L;

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
//	@XmlElement(name="MessageID")
	private String MessageID;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getMessageID() {
		return MessageID;
	}
	public void setMessageID(String messageID) {
		MessageID = messageID;
	}
	
	
}