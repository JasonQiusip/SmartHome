package com.smarthome.api.model;

public class LoginResult {

	private String expireTimestamp;
	private String secret;
	private String account;
	public String getExpireTimestamp() {
		return expireTimestamp;
	}
	public void setExpireTimestamp(String expireTimestamp) {
		this.expireTimestamp = expireTimestamp;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	
	
}
