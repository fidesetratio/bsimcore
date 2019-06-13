package com.app.model.table;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class MstQuestionAnswerTemp implements Serializable {
	
	private static final long serialVersionUID = -8763303097131786077L;
	
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
	
	public MstQuestionAnswerTemp(){}
	
	public MstQuestionAnswerTemp(HashMap map){
		if(map.get("NO_TEMP")!=null)			no_temp = (String) map.get("NO_TEMP");
		if(map.get("OPTION_TYPE")!=null)		option_type = (Integer)map.get("OPTION_TYPE");
		if(map.get("OPTION_GROUP")!=null)		option_group = (Integer)map.get("OPTION_GROUP");
		if(map.get("OPTION_ORDER")!=null)		option_order = (Integer)map.get("OPTION_ORDER");
		if(map.get("ANSWER_ORDER")!=null)		answer_order = (Integer)map.get("ANSWER_ORDER");
		if(map.get("QUESTION_ID")!=null)		question_id = (Integer)map.get("QUESTION_ID");
		if(map.get("ANSWER")!=null)				answer 		= (String)map.get("ANSWER");
		if(map.get("QUESTION_TYPE_ID")!=null)	question_type_id = (Integer)map.get("QUESTION_TYPE_ID");
		if(map.get("QUESTION_VALID_DATE")!=null)question_valid_date = (Date)map.get("QUESTION_VALID_DATE");
		if(map.get("LUS_ID")!=null)				lus_id 			= (Integer)map.get("LUS_ID");
		if(map.get("INPUT_DATE")!=null)			input_date 		= (Date)map.get("INPUT_DATE");
		if(map.get("KEY_TYPE")!=null)			key_type 		= (Integer)map.get("KEY_TYPE");
		
	}
	
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
