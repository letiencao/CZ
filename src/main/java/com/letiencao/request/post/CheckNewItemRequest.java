package com.letiencao.request.post;

public class CheckNewItemRequest {
	private Long last_id;
	private int category_id;

	public Long getLast_id() {
		return last_id;
	}

	public void setLast_id(Long last_id) {
		this.last_id = last_id;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

}
