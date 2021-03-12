package com.letiencao.dao.impl;

import com.letiencao.dao.IPostDAO;
import com.letiencao.model.PostModel;

public class PostDAO extends BaseDAO<PostModel> implements IPostDAO{

	@Override
	public PostModel insertOne(PostModel postModel) {
		String sql = "INSERT INTO post(deleted,createddate,createdby,content,accountid) VALUES(?,?,?,?,?)";
//		insertOne(sql, false,new Timestamp(System.currentTimeMillis()),)
		return null;
	}

	
}
