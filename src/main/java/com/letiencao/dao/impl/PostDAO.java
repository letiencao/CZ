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

	@SuppressWarnings("unused")
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
			String sql = "SELECT post.id,post.deleted,post.content,post.createdby,post.createddate,post.modifiedby,post.modifieddate,post.accountid,file.content FROM post LEFT JOIN file ON post.id = file.postid  WHERE post.id = ?  AND post.deleted = false";
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
				model.setFiles(files);
			}
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

	@Override
	public PostModel findById(Long id) {
		try {
			String sql = "SELECT * FROM post WHERE id = ? AND deleted = false";
			PostModel postModel = findOne(sql, new PostMapping(), id);
			if (postModel != null) {
				return postModel;
			}
			return null;
		} catch (ClassCastException e) {
			return null;
		}
	}

	@Override
	public boolean deleteById(Long id) {
		String sql = "UPDATE post SET deleted = true WHERE id = ?";
		return update(sql, id);
	}

	@Override
	public Long findAccountIdByPostId(Long id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		if (id < 1 || id > findAll().size()) {
			return -1L;
		}
		try {
			connection = getConnection();
			String sql = "SELECT accountid FROM post  WHERE id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			Long accountId = -1L;
			while (resultSet.next()) {
				accountId = resultSet.getLong("accountid");
			}
			return accountId;

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
				return -1L;
			}
		}
		return -1L;
	}

	@Override
	public List<PostModel> findPostByAccountId(Long accountId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			String sql = "SELECT post.id,post.deleted,post.content,post.createdby,post.createddate,post.modifiedby,post.modifieddate,post.accountid,file.content FROM post LEFT JOIN file ON post.id = file.postid  WHERE post.accountid = ?  AND post.deleted = false";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, accountId);
			resultSet = preparedStatement.executeQuery();
			List<PostModel> list = new ArrayList<PostModel>();
			List<String> files = new ArrayList<String>();
			while (resultSet.next()) {
				PostModel model = new PostModel();
				model.setId(resultSet.getLong("post.id"));
				model.setDeleted(resultSet.getBoolean("post.deleted"));
				model.setContent(resultSet.getString("post.content"));
				model.setCreatedBy(resultSet.getString("post.createdby"));
				model.setCreatedDate(resultSet.getTimestamp("post.createddate"));
				model.setModifiedBy(resultSet.getString("post.modifiedby"));
				model.setModifiedDate(resultSet.getTimestamp("post.modifieddate"));
				model.setAccountId(resultSet.getLong("post.accountid"));
				files.add(resultSet.getString("file.content"));
				model.setFiles(files);
				list.add(model);
			}
//			System.out.println("model = " + model.getId());
			return list;

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

}
