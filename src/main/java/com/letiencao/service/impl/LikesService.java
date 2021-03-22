package com.letiencao.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.letiencao.dao.IAccountDAO;
import com.letiencao.dao.ILikesDAO;
import com.letiencao.dao.impl.AccountDAO;
import com.letiencao.dao.impl.LikesDAO;
import com.letiencao.model.AccountModel;
import com.letiencao.model.LikesModel;
import com.letiencao.request.LikesRequest;
import com.letiencao.service.ILikesService;

public class LikesService extends BaseService implements ILikesService {
	private ILikesDAO likesDAO;
	private IAccountDAO accountDAO;
	public LikesService() {
		likesDAO = new LikesDAO();
		accountDAO = new AccountDAO();
	}
	@Override
	public Long insertOne(LikesRequest likesRequest) {
		LikesModel likesModel = new LikesModel();
		likesModel.setAccountId(likesRequest.getAccountId());
		likesModel.setPostId(likesRequest.getPostId());
		likesModel.setDeleted(false);
		likesModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		AccountModel accountModel = accountDAO.findById(likesRequest.getAccountId());
		likesModel.setCreatedBy(accountModel.getPhoneNumber());
		return likesDAO.insertOne(likesModel);
	}
	@Override
	public int findByPostId(Long postId) {
		List<LikesModel> list = likesDAO.findByPostId(postId);
		return list.size();
	}
	@SuppressWarnings("unused")
	@Override
	public boolean checkThisUserLiked(Long accountId,Long postId) {
		List<LikesModel> list = likesDAO.findByPostId(postId);
		for(int i = 0;i<list.size();i++) {
			if(accountId == list.get(i).getAccountId()) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}

}
