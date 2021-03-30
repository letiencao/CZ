package com.letiencao.dao;

import java.util.List;

import com.letiencao.model.PostModel;

public interface IPostDAO {
	List<PostModel> findAll();

	Long insertOne(PostModel postModel);

	PostModel findPostById(Long id);

	PostModel findById(Long id);

	boolean deleteById(Long id);
	Long findAccountIdByPostId(Long id);
	List<PostModel> findPostByAccountId(Long accountId);
}
