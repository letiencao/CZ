package com.letiencao.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.letiencao.dao.IAccountDAO;
import com.letiencao.mapping.AccountMapping;
import com.letiencao.model.AccountModel;
import com.letiencao.request.account.SignInRequest;

public class AccountDAO extends BaseDAO<AccountModel> implements IAccountDAO {

	@Override
	public boolean signUp(AccountModel accountModel) {
		String sql = "INSERT INTO account(deleted,createddate,createdby,name,password,phonenumber,avatar,uuid) VALUES (?,?,?,?,?,?,?,?)";
		Long id = insertOne(sql, accountModel.isDeleted(), accountModel.getCreatedDate(), accountModel.getCreatedBy(),
				accountModel.getName(), accountModel.getPassword(), accountModel.getPhoneNumber(),
				accountModel.getAvatar(), accountModel.getUuid());
		boolean check = false;
		if (id > 0) {
			check = true;
		}
		return check;
	}

	@Override
	public AccountModel signIn(SignInRequest signInRequest) {
		String sql = "SELECT * FROM account WHERE phonenumber = ? AND password = ? AND deleted = false";
		try {
			return findOne(sql, new AccountMapping(), signInRequest.getPhoneNumber(), signInRequest.getPassword());
		} catch (ClassCastException e) {
			return null;
		}

	}

	@Override
	public AccountModel findByPhoneNumber(String phoneNumber) {
		try {
			String sql = "SELECT * FROM account WHERE phonenumber = ? AND deleted = false";
			AccountModel accountModel = findOne(sql, new AccountMapping(), phoneNumber);
			if (accountModel != null) {
				return accountModel;
			}
			return null;
		} catch (ClassCastException e) {
			return null;
		}
	}

	@Override
	public AccountModel findById(Long id) {
		String sql = "SELECT * FROM account WHERE id = ?";
		try {
			AccountModel accountModel = findOne(sql, new AccountMapping(), id);
			if (accountModel != null) {
				return accountModel;
			}
		} catch (ClassCastException e) {
			return null;
		}
		return null;
	}

	@Override
	public List<AccountModel> findListAccountByKeyword(String keyword) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<AccountModel> list = new ArrayList<AccountModel>();
		try {
			String sql = "SELECT * FROM account WHERE deleted = false AND (name LIKE '%" + keyword
					+ "%' OR phonenumber LIKE '%" + keyword + "%')";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
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
				list.add(model);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Failed findListAccountByKeyword AccountDAO 1 : " + e.getMessage());
			return null;
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				System.out.println("Failed findListAccountByKeyword AccountDAO 2 : " + e2.getMessage());
				return null;
			}
		}
	}

	@Override
	public boolean changePassword(AccountModel accountModel) {
		String sql = "UPDATE account SET createddate = ?,modifieddate = ?,modifiedby = ?,password = ? WHERE id = ?";
		return update(sql, accountModel.getCreatedDate(), accountModel.getModifiedDate(), accountModel.getModifiedBy(),
				accountModel.getPassword(), accountModel.getId());
	}

}
