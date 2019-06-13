package com.app.core.response;

public class EntityStatus {
	
	
	private int status;
	private String spaj;
	private Object msesage;
	
	public EntityStatus(){
		
	}
	public EntityStatus(int status, String spaj, Object msesage) {
		super();
		this.status = status;
		this.spaj = spaj;
		this.msesage = msesage;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSpaj() {
		return spaj;
	}
	public void setSpaj(String spaj) {
		this.spaj = spaj;
	}
	public Object getMsesage() {
		return msesage;
	}
	public void setMsesage(Object msesage) {
		this.msesage = msesage;
	}

}
