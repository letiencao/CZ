package com.letiencao.response.account;

import com.letiencao.response.BaseResponse;

public class SignUpResponse extends BaseResponse {
	private DataSignUpResponse data;

	public DataSignUpResponse getDataSignUpResponse() {
		return data;
	}

	public void setDataSignUpResponse(DataSignUpResponse dataSignUpResponse) {
		this.data = dataSignUpResponse;
	}

}
