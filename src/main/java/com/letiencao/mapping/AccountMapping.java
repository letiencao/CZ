package com.letiencao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.letiencao.model.AccountModel;

public class AccountMapping implements IRowMapping<AccountModel> {

	@Override
	public AccountModel mapRow(ResultSet resultSet) {
		
		try {
			AccountModel model = new AccountModel();
			model.setId(resultSet.getLong("id"));
			model.setDeleted(resultSet.getBoolean("deleted"));
			model.setCreatedDate(resultSet.getTimestamp("createddate"));
			model.setCreatedBy(resultSet.getString("createdby"));
			model.setModifiedDate(resultSet.getTimestamp("modifieddate"));
			model.setModifiedBy(resultSet.getString("modifiedby"));
			model.setName(resultSet.getString("name"));
			model.setPhoneNumber(resultSet.getString("phonenumber"));
			model.setPassword(resultSet.getString("password"));
			model.setAvatar(resultSet.getString("avatar"));
			model.setUuid(resultSet.getString("uuid"));
			return model;
		} catch (SQLException e) {
			System.out.println("Failed__User Mapping");
			System.out.println("Failed__User Mapping = "+e.getMessage());
			return null;
		}
		
		
	}

}
