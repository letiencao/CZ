package com.letiencao.service.impl;

import java.sql.Timestamp;

import com.letiencao.dao.IAccountDAO;
import com.letiencao.dao.impl.AccountDAO;
import com.letiencao.model.AccountModel;
import com.letiencao.request.PhoneNumberRequest;
import com.letiencao.request.SignInRequest;
import com.letiencao.request.SignUpRequest;
import com.letiencao.service.IAccountService;

public class AccountService extends BaseService implements IAccountService {
	
	private IAccountDAO accountDAO;
	public AccountService() {
		// TODO Auto-generated constructor stub
		accountDAO = new AccountDAO();
	}
	@SuppressWarnings("null")
	@Override
	public AccountModel signUp(SignUpRequest signUpRequest) {
		String phoneNumber = signUpRequest.getPhoneNumber();
		PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest();
		phoneNumberRequest.setPhoneNumber(phoneNumber);
		AccountModel model = accountDAO.findByPhoneNumber(phoneNumberRequest);
		if(model != null) {
			return null;
		}else{
			AccountModel accountModel = new AccountModel();
			accountModel.setPhoneNumber(signUpRequest.getPhoneNumber());
			accountModel.setName(signUpRequest.getPhoneNumber());
			accountModel.setDeleted(true);
			accountModel.setAvatar(" ");
			accountModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			accountModel.setCreatedBy(signUpRequest.getPhoneNumber());
			accountModel.setPassword(getMD5(signUpRequest.getPassword()));
			accountModel.setUuid(signUpRequest.getUuid());
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
