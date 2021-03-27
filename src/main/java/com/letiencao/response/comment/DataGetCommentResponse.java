package com.letiencao.response.comment;

public class DataGetCommentResponse {
	private Long id;
	private String content;
	private Long created;
	private PosterResponse posterResponse;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getCreated() {
		return created;
	}
	public void setCreated(Long created) {
		this.created = created;
	}
	public PosterResponse getPosterResponse() {
		return posterResponse;
	}
	public void setPosterResponse(PosterResponse posterResponse) {
		this.posterResponse = posterResponse;
	}
	
}
