package com.letiencao.response.post;

import java.util.List;

import com.letiencao.response.BaseResponse;

public class GetListPostResponse extends BaseResponse {
	private List<DataGetPostReponse> posts;
	private int newItems;
	private Long lastId;

	
	

	public int getNewItems() {
		return newItems;
	}

	public void setNewItems(int newItems) {
		this.newItems = newItems;
	}

	public Long getLastId() {
		return lastId;
	}

	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}

	public List<DataGetPostReponse> getList() {
		return posts;
	}

	public void setList(List<DataGetPostReponse> list) {
		this.posts = list;
	}

}
