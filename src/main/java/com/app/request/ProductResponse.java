package com.app.request;

import java.util.HashMap;

public class ProductResponse {

	private String message;
	
	private HashMap result;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HashMap getResult() {
		return result;
	}

	public void setResult(HashMap result) {
		this.result = result;
	}
}
