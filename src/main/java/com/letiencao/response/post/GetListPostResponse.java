package com.letiencao.response.post;

import java.util.List;

import com.letiencao.response.BaseResponse;

public class GetListPostResponse extends BaseResponse {
	private List<DataGetPostReponse> posts;
	private int new_items;
	private Long last_id;

	public int getNew_items() {
		return new_items;
	}

	public void setNew_items(int new_items) {
		this.new_items = new_items;
	}

	public Long getLast_id() {
		return last_id;
	}

	public void setLast_id(Long last_id) {
		this.last_id = last_id;
	}

	public List<DataGetPostReponse> getList() {
		return posts;
	}

	public void setList(List<DataGetPostReponse> list) {
		this.posts = list;
	}

}