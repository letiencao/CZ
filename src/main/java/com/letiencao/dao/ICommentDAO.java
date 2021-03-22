package com.letiencao.dao;

import java.util.List;

import com.letiencao.model.CommentModel;

public interface ICommentDAO extends GenericDAO<CommentModel>{
	Long insertOne(CommentModel commentModel);
	List<CommentModel> findByPostId(Long postId);

}
