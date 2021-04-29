package com.letiencao.response.account;

import com.letiencao.response.BaseResponse;

public class SignInResponse extends BaseResponse{
	private DataSignInResponse data;

	public DataSignInResponse getData() {
		return data;
	}

	public void setData(DataSignInResponse data) {
		this.data = data;
	}
	
	
}
