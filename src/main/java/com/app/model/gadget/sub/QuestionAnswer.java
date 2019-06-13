package com.app.model.gadget.sub;

import java.io.Serializable;

public class QuestionAnswer implements Serializable {

	/**
	 *@author Lufi
	 *@since Des 13, 2014
	 *@description TODO
	 */
	private static final long serialVersionUID = 3757563109577600711L;
	
	private Integer option_type;
	private Integer option_group;
	private Integer option_order;
	private Integer answer_order;
	private Integer question_id;
	private String answer;
	private Integer question_type_id;
	private String question_valid_date;
	
	
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
	public String getQuestion_valid_date() {
		return question_valid_date;
	}
	public void setQuestion_valid_date(String question_valid_date) {
		this.question_valid_date = question_valid_date;
	}	
	
}
