package com.app.model.gadget.sub;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Pendanaan implements Serializable {
	/**
	 *@author Deddy
	 *@since Mar 4, 2014
	 */
	private static final long serialVersionUID = -5984592084508006144L;
	
	private String dana;
	private String jelaskan_dana;
	
	public String getDana() {
		return dana;
	}
	public void setDana(String dana) {
		this.dana = dana;
	}
	public String getJelaskan_dana() {
		return jelaskan_dana;
	}
	public void setJelaskan_dana(String jelaskan_dana) {
		this.jelaskan_dana = jelaskan_dana;
	}
	
}
