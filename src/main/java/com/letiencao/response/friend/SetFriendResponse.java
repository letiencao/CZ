package com.letiencao.response.friend;

import com.letiencao.response.BaseResponse;

public class SetFriendResponse extends BaseResponse {
	private int requested_friends;

	public int getRequested_friends() {
		return requested_friends;
	}

	public void setRequested_friends(int requested_friends) {
		this.requested_friends = requested_friends;
	}
	
}
