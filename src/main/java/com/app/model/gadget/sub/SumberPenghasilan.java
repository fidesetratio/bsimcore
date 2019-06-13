package com.app.model.gadget.sub;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SumberPenghasilan implements Serializable {
	/**
	 *@author Deddy
	 *@since Mar 4, 2014
	 */
	private static final long serialVersionUID = 4839030750572649351L;
	
	private String jenis;
	private String jelaskan;
	
	public String getJenis() {
		return jenis;
	}
	public void setJenis(String jenis) {
		this.jenis = jenis;
	}
	public String getJelaskan() {
		return jelaskan;
	}
	public void setJelaskan(String jelaskan) {
		this.jelaskan = jelaskan;
	}
	
}
