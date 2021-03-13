package com.letiencao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.letiencao.model.PostModel;

public class PostMapping implements IRowMapping<PostModel> {

	@Override
	public PostModel mapRow(ResultSet resultSet) {
		try {
			PostModel model = new PostModel();
			model.setId(resultSet.getLong("id"));
			model.setDeleted(resultSet.getBoolean("deleted"));
			model.setContent(resultSet.getString("content"));
			model.setCreatedBy(resultSet.getString("createdby"));
			model.setCreatedDate(resultSet.getTimestamp("createddate"));
			model.setModifiedBy(resultSet.getString("modifiedby"));
			model.setModifiedDate(resultSet.getTimestamp("modifieddate"));
			model.setAccountId(resultSet.getLong("accountid"));
			return model;
		} catch (SQLException e) {
			System.out.println("Failed_PostMapping : "+e.getMessage());
		}
		return null;
	}

}
