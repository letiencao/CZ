package com.letiencao.request.post;

public class CheckNewItemRequest {
	private Long lastId;
	private int categoryId;
	public Long getLastId() {
		return lastId;
	}
	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	

}
