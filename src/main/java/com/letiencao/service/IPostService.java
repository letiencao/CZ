package com.letiencao.service;

import com.letiencao.model.PostModel;
import com.letiencao.request.AddPostRequest;

public interface IPostService {
	Long insertOne(AddPostRequest addPostRequest);
	PostModel findPostById(Long id);
}
