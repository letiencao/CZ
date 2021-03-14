package com.letiencao.service.impl;

import java.sql.Timestamp;

import com.letiencao.dao.IAccountDAO;
import com.letiencao.dao.IPostDAO;
import com.letiencao.dao.impl.AccountDAO;
import com.letiencao.dao.impl.PostDAO;
import com.letiencao.model.AccountModel;
import com.letiencao.model.PostModel;
import com.letiencao.request.AddPostRequest;
import com.letiencao.service.IPostService;

public class PostService extends BaseService implements IPostService {

	private IPostDAO postDAO;
	private IAccountDAO accountDAO;

	public PostService() {
		postDAO = new PostDAO();
		accountDAO = new AccountDAO();
	}

	@SuppressWarnings("unused")
	@Override
	public Long insertOne(AddPostRequest addPostRequest) {
		String described = addPostRequest.getDescribed();
		String phoneNumber = getPhoneNumberFromToken(addPostRequest.getToken());
		AccountModel model = accountDAO.findByPhoneNumber(phoneNumber);
		PostModel postModel = new PostModel();
		Long accountId = model.getId();
		postModel.setAccountId(accountId);
		postModel.setContent(described);
		postModel.setCreatedBy(model.getPhoneNumber());
		postModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		postModel.setDeleted(false);
		Long id = postDAO.insertOne(postModel);
		return id;
	}

	@Override
	public PostModel findPostById(Long id) {
		return postDAO.findPostById(id);
	}

}
