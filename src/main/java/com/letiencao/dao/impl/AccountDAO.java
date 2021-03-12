package com.letiencao.dao.impl;

import com.letiencao.dao.IAccountDAO;
import com.letiencao.mapping.AccountMapping;
import com.letiencao.model.AccountModel;
import com.letiencao.request.PhoneNumberRequest;
import com.letiencao.request.SignInRequest;

public class AccountDAO extends BaseDAO<AccountModel> implements IAccountDAO {

	@Override
	public boolean signUp(AccountModel accountModel) {
		String sql = "INSERT INTO account(deleted,createddate,createdby,name,password,phonenumber,avatar,uuid) VALUES (?,?,?,?,?,?,?,?)";
		return insertOne(sql, accountModel.isDeleted(),accountModel.getCreatedDate(),accountModel.getCreatedBy(),accountModel.getName(),accountModel.getPassword(),
				accountModel.getPhoneNumber(),accountModel.getAvatar(),accountModel.getUuid());
	}

	@Override
	public AccountModel signIn(SignInRequest signInRequest) {
		String sql = "SELECT * FROM account WHERE phonenumber = ? AND password = ?";
		
		return findOne(sql, new AccountMapping(), signInRequest.getPhoneNumber(),signInRequest.getPassword());
	}

	@Override
	public AccountModel findByPhoneNumber(PhoneNumberRequest phoneNumberRequest) {
		try {
			String sql = "SELECT * FROM account WHERE phonenumber = ?";
			AccountModel accountModel = findOne(sql, new AccountMapping(), phoneNumberRequest.getPhoneNumber());
			if(accountModel != null) {
				return accountModel;
			}
			return null;
		} catch (ClassCastException e) {
			return null;
		}
	}

}
