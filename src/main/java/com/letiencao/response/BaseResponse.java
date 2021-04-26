package com.letiencao.response;

public class BaseResponse {
//	private int code;
	private String code;
	private String message;
//	public int getCode() {
//		return code;
//	}
//	public void setCode(int code) {
//		this.code = code;
//	}
	
	public String getMessage() {
		return message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
