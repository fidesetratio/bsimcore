package com.app.model.table;

import java.io.Serializable;
import java.sql.Date;

public class MstKuisionerTemp implements Serializable{

	private static final long serialVersionUID = -5455251782452044741L;
	private String no_temp;
	private Integer option_type;
	private Integer option_group;
	private Integer option_order;
	private Integer answer_order;
	private Integer question_id;
	private String answer;
	private Integer question_type_id;
	private Date question_valid_date;
	private Integer lus_id ;
	private Date input_date;
	private Integer key_type;
	
	public String getNo_temp() {
		return no_temp;
	}

	public Integer getOption_type() {
		return option_type;
	}
	public void setOption_type(Integer option_type) {
		this.option_type = option_type;
	}
	public Integer getOption_group() {
		return option_group;
	}
	public void setOption_group(Integer option_group) {
		this.option_group = option_group;
	}
	public Integer getOption_order() {
		return option_order;
	}
	public void setOption_order(Integer option_order) {
		this.option_order = option_order;
	}
	public Integer getAnswer_order() {
		return answer_order;
	}
	public void setAnswer_order(Integer answer_order) {
		this.answer_order = answer_order;
	}
	public Integer getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(Integer question_id) {
		this.question_id = question_id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getQuestion_type_id() {
		return question_type_id;
	}
	public void setQuestion_type_id(Integer question_type_id) {
		this.question_type_id = question_type_id;
	}
	public Date getQuestion_valid_date() {
		return question_valid_date;
	}
	public void setQuestion_valid_date(Date question_valid_date) {
		this.question_valid_date = question_valid_date;
	}
	public Integer getLus_id() {
		return lus_id;
	}
	public void setLus_id(Integer lus_id) {
		this.lus_id = lus_id;
	}
	public Date getInput_date() {
		return input_date;
	}
	public void setInput_date(Date input_date) {
		this.input_date = input_date;
	}
	public Integer getKey_type() {
		return key_type;
	}
	public void setKey_type(Integer key_type) {
		this.key_type = key_type;
	}
	public void setNo_temp(String no_temp) {
		this.no_temp = no_temp;
	}	
}
