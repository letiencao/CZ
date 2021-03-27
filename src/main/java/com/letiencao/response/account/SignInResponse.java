package com.letiencao.response.account;

import com.letiencao.model.AccountModel;
import com.letiencao.response.BaseResponse;

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
