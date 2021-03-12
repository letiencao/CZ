package com.letiencao.service;

import com.letiencao.model.AccountModel;
import com.letiencao.request.PhoneNumberRequest;
import com.letiencao.request.SignInRequest;

public interface IAccountService {
	AccountModel signUp(AccountModel accountModel);
	String signIn(SignInRequest signInRequest);
	AccountModel findByPhoneNumber(PhoneNumberRequest phoneNumberRequest);
}
