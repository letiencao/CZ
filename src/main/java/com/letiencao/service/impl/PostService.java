package com.letiencao.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.letiencao.dao.IAccountDAO;
import com.letiencao.dao.IPostDAO;
import com.letiencao.dao.impl.AccountDAO;
import com.letiencao.dao.impl.PostDAO;
import com.letiencao.model.AccountModel;
import com.letiencao.model.PostModel;
import com.letiencao.request.post.AddPostRequest;
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
		System.out.println(postDAO.findPostById(id));
		return postDAO.findPostById(id);
	}

	@Override
	public PostModel findById(Long id) {
		return postDAO.findById(id);
	}

	@Override
	public boolean deleteById(Long id) {
		PostModel model = findById(id);
		try {
			if(model.isDeleted() == false) {
				return postDAO.deleteById(id);
				
			}else {
				return false;
			}	
		} catch (NullPointerException e) {
			return false;
		}
	}

	@Override
	public List<PostModel> findAll() {
		return postDAO.findAll();
	}

	@Override
	public Long findAccountIdByPostId(Long id) {
		return postDAO.findAccountIdByPostId(id);
	}

	@Override
	public List<PostModel> findPostByAccountId(Long accountId) {
		List<PostModel> list = postDAO.findPostByAccountId(accountId);
		List<PostModel> list2 = new ArrayList<PostModel>();
		for(int i = 0;i<list.size()-1;i++) {
			PostModel model = list.get(i);
			if(model.getId() == list.get(i+1).getId()) {
				continue;
			}else {
				list2.add(model);
			}
			
		}
		list2.add(list.get(list.size()-1));
		return list2;
	}

}
