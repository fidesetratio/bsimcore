package com.app.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BankSinarmasSyariahResponse extends Response {

	public BankSinarmasSyariahResponse(String message, String result,
			Object data) {
		super(message, result, data);
		// TODO Auto-generated constructor stub
	}
	
	

}
