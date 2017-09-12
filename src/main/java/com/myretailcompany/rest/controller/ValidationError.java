package com.myretailcompany.rest.controller;

public class ValidationError {
	public ValidationError() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ValidationError(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	
	
	private String code;
	private String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
