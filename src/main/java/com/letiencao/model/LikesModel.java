package com.letiencao.model;

public class LikesModel extends BaseModel {
	private Long postId;
	private Long accountId;
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
}
