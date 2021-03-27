package com.letiencao.response.like;

import com.letiencao.response.BaseResponse;

public class LikesResponse extends BaseResponse{
	private DataLikesResponse dataLikesResponse;

	public DataLikesResponse getDataLikesResponse() {
		return dataLikesResponse;
	}

	public void setDataLikesResponse(DataLikesResponse dataLikesResponse) {
		this.dataLikesResponse = dataLikesResponse;
	}
	
}
