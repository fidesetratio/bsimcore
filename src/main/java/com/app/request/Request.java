package com.app.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
	
	private AJSMSIG ajsmsig;
	
	private String data;

	public Request(){
		//	this.AJSMSIG = new AJSMSIG();
			
	}

	

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}



	


	@JsonProperty("AJSMSIG")
	public AJSMSIG getAjsmsig() {
		return ajsmsig;
	}



	public void setAjsmsig(AJSMSIG ajsmsig) {
		this.ajsmsig = ajsmsig;
	}
	

	



}
