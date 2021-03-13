package com.letiencao.dao;

import com.letiencao.model.AccountModel;
import com.letiencao.request.PhoneNumberRequest;
import com.letiencao.request.SignInRequest;

public interface IAccountDAO extends GenericDAO<AccountModel> {
	boolean signUp(AccountModel accountModel);
	AccountModel signIn(SignInRequest signInRequest);
	AccountModel findByPhoneNumber(String phoneNumber);
}
