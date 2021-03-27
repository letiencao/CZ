package com.letiencao.response.post;

import com.letiencao.response.BaseResponse;

public class AddPostResponse extends BaseResponse {
	private DataAddPostResponse dataPostResponse;

	public DataAddPostResponse getDataPostResponse() {
		return dataPostResponse;
	}

	public void setDataPostResponse(DataAddPostResponse dataPostResponse) {
		this.dataPostResponse = dataPostResponse;
	}

}
