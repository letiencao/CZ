package com.letiencao.service;

import java.util.List;

import com.letiencao.model.PostModel;
import com.letiencao.request.post.AddPostRequest;

public interface IPostService {
	Long insertOne(AddPostRequest addPostRequest);
	PostModel findPostById(Long id);
	PostModel findById(Long id);
	boolean deleteById(Long id);
	List<PostModel> findAll();
	Long findAccountIdByPostId(Long id);
	List<PostModel> findPostByAccountId(Long accountId);
}
