package com.letiencao.service;

import com.letiencao.request.LikesRequest;

public interface ILikesService {
	Long insertOne(LikesRequest likesRequest);
	int findByPostId(Long postId);
	boolean checkThisUserLiked(Long accountId,Long postId);

}
