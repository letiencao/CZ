package com.letiencao.model;

public class PostModel extends BaseModel {
	private String content;
	private Long accountId;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
}
