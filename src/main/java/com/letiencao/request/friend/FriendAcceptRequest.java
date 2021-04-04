package com.letiencao.request.friend;

public class FriendAcceptRequest {
	private Long userId;
	private boolean isAccept;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public boolean isAccept() {
		return isAccept;
	}
	public void setAccept(boolean isAccept) {
		this.isAccept = isAccept;
	}
	
	
}
