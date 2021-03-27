package com.letiencao.response.comment;

import com.letiencao.response.BaseResponse;

public class GetCommentResponse extends BaseResponse {
	private DataGetCommentResponse dataGetCommentResponse;
	private boolean is_blocked;
	public DataGetCommentResponse getDataGetCommentResponse() {
		return dataGetCommentResponse;
	}
	public void setDataGetCommentResponse(DataGetCommentResponse dataGetCommentResponse) {
		this.dataGetCommentResponse = dataGetCommentResponse;
	}
	public boolean isIs_blocked() {
		return is_blocked;
	}
	public void setIs_blocked(boolean is_blocked) {
		this.is_blocked = is_blocked;
	}
	
	
	
}
