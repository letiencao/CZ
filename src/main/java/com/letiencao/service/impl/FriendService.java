package com.letiencao.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.letiencao.dao.IFriendDAO;
import com.letiencao.dao.impl.FriendDAO;
import com.letiencao.model.FriendModel;
import com.letiencao.service.IFriendService;

public class FriendService implements IFriendService {
	private IFriendDAO friendDAO;
	public FriendService() {
		friendDAO = new FriendDAO();
	}
	@Override
	public Long insertOne(Long idRequest, Long idRequested) {
		FriendModel friendModel = new FriendModel();
		friendModel.setCreatedBy(idRequest.toString());
		friendModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		friendModel.setDeleted(false);
		friendModel.setFriend(false);
		friendModel.setIdA(idRequest);
		friendModel.setIdB(idRequested);
		return friendDAO.insertOne(friendModel);
	}
	@Override
	public List<FriendModel> findListFriendRequestById(Long id) {
		return friendDAO.findListFriendRequestById(id);
	}
	@Override
	public boolean checkFriendExisted(Long idRequest, Long idRequested) {
//		friendDAO.checkFriendExisted(idRequest, idRequested)
		return false;
	}
	@Override
	public boolean checkRequestExisted(Long idRequest, Long idRequested) {
		List<FriendModel> list = friendDAO.findAll();
		for(int i = 0;i<list.size();i++) {
			if(list.get(i).getIdA() == idRequest && list.get(i).getIdB() == idRequested && list.get(i).isDeleted() == false) {
				return true;
			}
		}
		return false;
		
	}
	@Override
	public boolean setIsFriend(Long idRequest, Long idRequested) {
		return friendDAO.setIsFriend(idRequest, idRequested);
	}
	@Override
	public boolean deleteRequest(Long idRequest, Long idRequested) {
		return friendDAO.deleteRequest(idRequest, idRequested);
	}
	@Override
	public FriendModel findOne(Long idRequest, Long idRequested) {
		return friendDAO.findOne(idRequest, idRequested);
	}

}
