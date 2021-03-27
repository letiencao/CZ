package com.letiencao.dao;

import com.letiencao.model.AccountModel;
import com.letiencao.request.account.PhoneNumberRequest;
import com.letiencao.request.account.SignInRequest;

public interface IAccountDAO extends GenericDAO<AccountModel> {
	boolean signUp(AccountModel accountModel);
	AccountModel signIn(SignInRequest signInRequest);
	AccountModel findByPhoneNumber(String phoneNumber);
	AccountModel findById(Long id);
}
