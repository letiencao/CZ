package com.letiencao.model;

import java.util.List;

public class PostModel extends BaseModel {
	private String content;
	private Long accountId;
	private List<String> files;
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
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	@Override
	public String toString() {
		return "PostModel [content=" + content + ", accountId=" + accountId + ", files=" + files + "]";
	}
	
}
