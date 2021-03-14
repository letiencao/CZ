package com.letiencao.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.letiencao.dao.IPostDAO;
import com.letiencao.mapping.PostMapping;
import com.letiencao.model.PostModel;

public class PostDAO extends BaseDAO<PostModel> implements IPostDAO {

	@Override
	public Long insertOne(PostModel postModel) {
		String sql = "INSERT INTO post(deleted,createddate,createdby,content,accountid) VALUES(?,?,?,?,?)";
		Long id = insertOne(sql, postModel.isDeleted(), postModel.getCreatedDate(), postModel.getCreatedBy(),
				postModel.getContent(), postModel.getAccountId());
		return id;
	}

	@Override
	public PostModel findPostById(Long id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		if (id < 1 || id > findAll().size()) {
			return null;
		}
		try {
			connection = getConnection();
			String sql = "SELECT post.id,post.deleted,post.content,post.createdby,post.createddate,post.modifiedby,post.modifieddate,post.accountid,file.content FROM post INNER JOIN file ON post.id = file.postid  WHERE post.id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			PostModel model = new PostModel();
			System.out.println("huhu");
			List<String> files = new ArrayList<String>();
			while (resultSet.next()) {
				model.setId(resultSet.getLong("post.id"));
				model.setDeleted(resultSet.getBoolean("post.deleted"));
				model.setContent(resultSet.getString("post.content"));
				model.setCreatedBy(resultSet.getString("post.createdby"));
				model.setCreatedDate(resultSet.getTimestamp("post.createddate"));
				model.setModifiedBy(resultSet.getString("post.modifiedby"));
				model.setModifiedDate(resultSet.getTimestamp("post.modifieddate"));
				model.setAccountId(resultSet.getLong("post.accountid"));
				files.add(resultSet.getString("file.content"));
			}
			model.setFiles(files);
			System.out.println("model = "+model.getId());
			return model;

		} catch (SQLException e) {
			System.out.println("" + e.getMessage());
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<PostModel> findAll() {
		String sql = "SELECT * FROM post";
		return findAll(sql, new PostMapping());
	}

}
