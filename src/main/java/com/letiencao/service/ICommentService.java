package com.letiencao.service;

import com.letiencao.request.AddCommentRequest;

public interface ICommentService {
	Long insertOne(AddCommentRequest addCommentRequest);
	int findByPostId(Long postId);
}
