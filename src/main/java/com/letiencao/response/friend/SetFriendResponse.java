package com.letiencao.response.friend;

import com.letiencao.response.BaseResponse;

public class SetFriendResponse extends BaseResponse {
	private int requestedFriends;

	public int getRequestedFriends() {
		return requestedFriends;
	}

	public void setRequestedFriends(int requestedFriends) {
		this.requestedFriends = requestedFriends;
	}
	
	
}
