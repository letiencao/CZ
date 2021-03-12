package com.letiencao.service.impl;

import com.letiencao.dao.IPostDAO;
import com.letiencao.dao.impl.PostDAO;
import com.letiencao.model.PostModel;
import com.letiencao.service.IPostService;

public class PostService extends BaseService implements IPostService  {

	private IPostDAO postDAO;
	public PostService() {
		postDAO = new PostDAO();
	}
	@Override
	public PostModel insertOne(PostModel postModel) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
