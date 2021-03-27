package com.letiencao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.letiencao.model.CommentModel;

public class CommentMapping implements IRowMapping<CommentModel>{

	@Override
	public CommentModel mapRow(ResultSet resultSet) {

		try {
			CommentModel commentModel = new CommentModel();
			commentModel.setId(resultSet.getLong("id"));
			commentModel.setDeleted(resultSet.getBoolean("deleted"));
			commentModel.setCreatedDate(resultSet.getTimestamp("createddate"));
			commentModel.setCreatedBy(resultSet.getString("createdby"));
			commentModel.setAccountId(resultSet.getLong("accountid"));
			commentModel.setPostId(resultSet.getLong("postid"));
			commentModel.setContent(resultSet.getString("content"));
			commentModel.setModifiedBy(resultSet.getString("modifiedby"));
			commentModel.setModifiedDate(resultSet.getTimestamp("modifieddate"));
			return commentModel;
		} catch (SQLException e) {
			System.out.println("Failed Row Mapping Comment Mapping : "+e.getMessage());
			return null;
		}
	}

}
