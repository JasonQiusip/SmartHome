package com.smarthome.api.model;

public class HttpResponse {
	
	int code;
	String content;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean isSuccess(){
		return code == 200;
	}
	public boolean isServerError(){
		return code == 500;
	}
	public boolean isReqParaError(){
		return code == 400;
	}
	public boolean isAuthorizeFailed(){
		return code == 401;
	}
	public boolean isNotFound(){
		return code == 404;
	}
	
}
