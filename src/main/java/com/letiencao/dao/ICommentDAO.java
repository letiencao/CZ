package com.letiencao.dao;

import java.util.List;

import com.letiencao.model.CommentModel;

public interface ICommentDAO extends GenericDAO<CommentModel>{
	Long insertOne(CommentModel commentModel);
	List<CommentModel> findByPostId(Long postId);
	boolean deleteComment(Long postId,Long commentId);
	List<CommentModel> findAll();
	CommentModel findById (Long id);
	boolean update(Long id,String content);

}
