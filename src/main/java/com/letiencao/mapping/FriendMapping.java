package com.letiencao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.letiencao.model.FriendModel;

public class FriendMapping implements IRowMapping<FriendModel> {

	@Override
	public FriendModel mapRow(ResultSet resultSet) {
		try {
			FriendModel friendModel = new FriendModel();
			friendModel.setId(resultSet.getLong("id"));
			friendModel.setDeleted(resultSet.getBoolean("deleted"));
			friendModel.setCreatedBy(resultSet.getString("createdby"));
			friendModel.setCreatedDate(resultSet.getTimestamp("createddate"));
			friendModel.setFriend(resultSet.getBoolean("is_friend"));
			friendModel.setIdA(resultSet.getLong("idA"));
			friendModel.setIdB(resultSet.getLong("idB"));
			friendModel.setModifiedBy(resultSet.getString("modifiedby"));
			friendModel.setModifiedDate(resultSet.getTimestamp("modifieddate"));
			return friendModel;
		} catch (SQLException e) {
			System.out.println("Failed Friend Maaping : " + e.getMessage());
			return null;
		}

	}

}
