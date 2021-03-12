package com.letiencao.response;

import com.letiencao.model.AccountModel;

public class SignUpResponse extends BaseResponse {
	private AccountModel accountModel;

	public AccountModel getAccountModel() {
		return accountModel;
	}

	public void setAccountModel(AccountModel accountModel) {
		this.accountModel = accountModel;
	}
	
}
