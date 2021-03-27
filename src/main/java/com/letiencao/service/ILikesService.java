package com.letiencao.service;

import com.letiencao.request.like.LikesRequest;

public interface ILikesService {
	Long insertOne(LikesRequest likesRequest);
	int findByPostId(Long postId);
	boolean checkThisUserLiked(Long accountId,Long postId);
	boolean disLike(Long postId,Long accountId);

}
