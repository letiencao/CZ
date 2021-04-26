package com.letiencao.response.account;

import com.letiencao.response.BaseResponse;

public class GetUserInforResponse extends BaseResponse {
	private UserInfoResponse userInfoResponse;

	public UserInfoResponse getUserInfoResponse() {
		return userInfoResponse;
	}

	public void setUserInfoResponse(UserInfoResponse userInfoResponse) {
		this.userInfoResponse = userInfoResponse;
	}
	

}
