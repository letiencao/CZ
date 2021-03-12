package com.letiencao.service.impl;

import java.sql.Timestamp;

import com.letiencao.dao.IAccountDAO;
import com.letiencao.dao.impl.AccountDAO;
import com.letiencao.model.AccountModel;
import com.letiencao.request.PhoneNumberRequest;
import com.letiencao.request.SignInRequest;
import com.letiencao.service.IAccountService;

public class AccountService extends BaseService implements IAccountService {
	
	private IAccountDAO accountDAO;
	public AccountService() {
		// TODO Auto-generated constructor stub
		accountDAO = new AccountDAO();
	}
	@Override
	public AccountModel signUp(AccountModel accountModel) {
		String p = accountModel.getPhoneNumber();
		PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest();
		phoneNumberRequest.setPhoneNumber(p);
		AccountModel model = accountDAO.findByPhoneNumber(phoneNumberRequest);
		if(model != null) {
			return null;
		}else{
			accountModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			accountModel.setCreatedBy(accountModel.getName());
			accountModel.setPassword(getMD5(accountModel.getPassword()));
			accountModel.setAvatar("hello");
			boolean b = accountDAO.signUp(accountModel);
			if(b) {
				return accountModel;
			}
			return null;
		}
		
	}

	@Override
	public String signIn(SignInRequest signInRequest) {
		String jwt = createJWT(signInRequest.getPhoneNumber());
		signInRequest.setPassword(getMD5(signInRequest.getPassword()));
		if(accountDAO.signIn(signInRequest) != null) {
			return jwt;
		}
		return null;
	}
	@Override
	public AccountModel findByPhoneNumber(PhoneNumberRequest phoneNumberRequest) {
		
		return accountDAO.findByPhoneNumber(phoneNumberRequest);
	}

	

}
