package com.letiencao.response.account;

import java.util.List;

import com.letiencao.model.AccountModel;

public class SearchAccountResponse {
	private List<AccountModel> list;

	public List<AccountModel> getList() {
		return list;
	}

	public void setList(List<AccountModel> list) {
		this.list = list;
	}
	
}
