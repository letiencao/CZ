package com.letiencao.response.comment;

import java.util.List;

import com.letiencao.response.BaseResponse;

public class GetListCommentResponse extends BaseResponse {
	private boolean is_blocked;
	private List<DataGetCommentResponse> list;
	public boolean isIs_blocked() {
		return is_blocked;
	}
	public void setIs_blocked(boolean is_blocked) {
		this.is_blocked = is_blocked;
	}
	public List<DataGetCommentResponse> getList() {
		return list;
	}
	public void setList(List<DataGetCommentResponse> list) {
		this.list = list;
	}
	

}
