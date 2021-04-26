package com.letiencao.service;

import java.util.List;

import com.letiencao.model.FriendModel;

public interface IFriendService {
	Long insertOne(Long idRequest,Long idRequested);
	List<FriendModel> findListFriendRequestById(Long id);
	boolean checkFriendExisted(Long idRequest,Long idRequested,boolean isFriend); //isFriend = true
	boolean checkRequestExisted(Long idRequest,Long idRequested,boolean isFriend);//isFriend = false
	boolean setIsFriend(Long idRequest,Long idRequested);
	boolean deleteRequest(Long idRequest,Long idRequested);
	FriendModel findOne(Long idRequest,Long idRequested);
	List<FriendModel> findListFriendById(Long id);
}
