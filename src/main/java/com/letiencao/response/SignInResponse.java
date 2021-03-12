package com.letiencao.response;

import com.letiencao.model.AccountModel;

public class SignInResponse extends BaseResponse{
	private String token;
	private AccountModel accountModel;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public AccountModel getAccountModel() {
		return accountModel;
	}
	public void setAccountModel(AccountModel accountModel) {
		this.accountModel = accountModel;
	}
	
}
