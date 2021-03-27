package com.letiencao.response.account;

import com.letiencao.model.AccountModel;
import com.letiencao.response.BaseResponse;

public class SignUpResponse extends BaseResponse {
	private AccountModel accountModel;

	public AccountModel getAccountModel() {
		return accountModel;
	}

	public void setAccountModel(AccountModel accountModel) {
		this.accountModel = accountModel;
	}
	
}
