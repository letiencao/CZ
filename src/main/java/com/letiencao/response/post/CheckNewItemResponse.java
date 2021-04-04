package com.letiencao.response.post;

import java.util.List;

import com.letiencao.response.BaseResponse;

public class CheckNewItemResponse extends BaseResponse{
	private List<Long> data;

	public List<Long> getData() {
		return data;
	}

	public void setData(List<Long> data) {
		this.data = data;
	}
	
}
