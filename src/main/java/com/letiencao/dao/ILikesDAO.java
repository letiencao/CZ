package com.letiencao.dao;

import java.util.List;

import com.letiencao.model.LikesModel;

public interface ILikesDAO extends GenericDAO<LikesModel> {
	Long insertOne(LikesModel likesModel);
	List<LikesModel> findByPostId(Long postId);
//	List<Long> listAccountIdLiked(Long postId);
	boolean disLike(Long postId,Long accountId);
}
