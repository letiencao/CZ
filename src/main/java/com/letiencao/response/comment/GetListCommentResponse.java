package com.letiencao.response.comment;

import java.util.List;

import com.letiencao.response.BaseResponse;

public class GetListCommentResponse extends BaseResponse {
	private boolean isBlocked;
	private List<DataGetCommentResponse> list;
	
	public boolean isBlocked() {
		return isBlocked;
	}
	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	public List<DataGetCommentResponse> getList() {
		return list;
	}
	public void setList(List<DataGetCommentResponse> list) {
		this.list = list;
	}
	

}
