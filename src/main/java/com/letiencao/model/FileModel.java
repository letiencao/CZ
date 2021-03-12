package com.letiencao.model;

public class FileModel extends BaseModel{
	private String content;
	private Long postId;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	

}
