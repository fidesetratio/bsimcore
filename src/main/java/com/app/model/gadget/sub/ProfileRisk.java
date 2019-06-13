package com.app.model.gadget.sub;

import java.io.Serializable;

public class ProfileRisk implements Serializable {

	/**
	 *@author Ryan
	 *@since Aug 10, 2017
	 *@description TODO
	 */
	private static final long serialVersionUID = -7212923529701545948L;
	private Integer answer_id;
	private Integer question_id;
	private String result;
	private String no_temp;
	

	public Integer getAnswer_id() {
		return answer_id;
	}
	public void setAnswer_id(Integer answer_id) {
		this.answer_id = answer_id;
	}
	public Integer getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(Integer question_id) {
		this.question_id = question_id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getNo_temp() {
		return no_temp;
	}
	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}
	
	
}
