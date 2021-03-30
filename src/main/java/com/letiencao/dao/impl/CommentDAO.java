package com.letiencao.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.letiencao.dao.ICommentDAO;
import com.letiencao.mapping.CommentMapping;
import com.letiencao.model.CommentModel;

public class CommentDAO extends BaseDAO<CommentModel> implements ICommentDAO {

	@Override
	public Long insertOne(CommentModel commentModel) {
		String sql = "INSERT INTO comment(deleted,createddate,createdby,content,postid,accountid) VALUES(?,?,?,?,?,?)";
		return insertOne(sql, commentModel.isDeleted(), commentModel.getCreatedDate(), commentModel.getCreatedBy(),
				commentModel.getContent(), commentModel.getPostId(), commentModel.getAccountId());
	}

	@Override
	public List<CommentModel> findByPostId(Long postId) {
		List<CommentModel> commentModels = new ArrayList<CommentModel>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT * FROM comment WHERE postid = ?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, postId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				CommentModel commentModel = new CommentModel();
				commentModel.setAccountId(resultSet.getLong("accountid"));
				commentModel.setCreatedBy(resultSet.getString("createdby"));
				commentModel.setCreatedDate(resultSet.getTimestamp("createddate"));
				commentModel.setDeleted(resultSet.getBoolean("deleted"));
				commentModel.setId(resultSet.getLong("id"));
				commentModel.setModifiedBy(resultSet.getString("modifiedby"));
				commentModel.setModifiedDate(resultSet.getTimestamp("modifieddate"));
				commentModel.setPostId(resultSet.getLong("postid"));
				commentModel.setContent(resultSet.getString("content"));
				commentModels.add(commentModel);
			}
			return commentModels;
		} catch (SQLException e) {
			System.out.println("findByPostId commentDAO 1 : "+e.getMessage());
			return null;
		}finally {
			try {
				if(preparedStatement != null) {
					preparedStatement.close();
				}
				if(resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				System.out.println("findByPostId commentDAO 2 : "+e2.getMessage());
				return null;
			}
		}
	}

	@Override
	public boolean deleteComment(Long postId, Long commentId) {
		String sql = "DELETE FROM comment WHERE id = ? AND postid = ?";
		return delete(sql, commentId,postId);
	}

	@Override
	public List<CommentModel> findAll() {
		String sql = "SELECT * FROM comment";
		return findAll(sql,new CommentMapping());
	}

	@Override
	public CommentModel findById(Long id) {
		String sql = "SELECT * FROM comment WHERE id = ?";
		try {
			return findOne(sql,new CommentMapping(),id);	
		} catch (ClassCastException e) {
			System.out.println("Failed findById CommentDAO : "+e.getMessage());
			return null;
		}
	}

	@Override
	public boolean update(Long id,String content) {
		String sql = "UPDATE comment SET content = ? WHERE id = ?";
		return update(sql, content,id);
	}
}
