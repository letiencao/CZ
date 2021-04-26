package com.letiencao.dao;

import java.util.List;

import com.letiencao.model.FriendModel;

public interface IFriendDAO {
	Long insertOne(FriendModel friendModel);
	List<FriendModel> findListFriendRequestById(Long id);
//	FriendModel checkFriendExisted(Long idRequest,Long idRequested);
//	FriendModel checkRequestExisted(Long idRequest,Long idRequested);
	List<FriendModel> findAll();
	boolean setIsFriend(Long idRequest,Long idRequested);
	boolean deleteRequest(Long idRequest,Long idRequested);
	FriendModel findOne(Long idRequest,Long idRequested,boolean isFriend);
	FriendModel findOne(Long idRequest,Long idRequested);
	List<FriendModel> findListFriendById(Long id);
}
