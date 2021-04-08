package com.letiencao.response.comment;

import com.letiencao.response.BaseResponse;

public class GetCommentResponse extends BaseResponse {
	private DataGetCommentResponse dataGetCommentResponse;
	private boolean isBlocked;
	public DataGetCommentResponse getDataGetCommentResponse() {
		return dataGetCommentResponse;
	}
	public void setDataGetCommentResponse(DataGetCommentResponse dataGetCommentResponse) {
		this.dataGetCommentResponse = dataGetCommentResponse;
	}
	public boolean isBlocked() {
		return isBlocked;
	}
	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	
	
	
	
}
