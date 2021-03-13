package com.letiencao.dao;

import java.util.List;

import com.letiencao.model.PostModel;

public interface IPostDAO {
	List<PostModel> findAll();
	PostModel insertOne(PostModel postModel);
	PostModel findPostById(Long id);

}
