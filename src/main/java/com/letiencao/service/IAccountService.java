package com.letiencao.service;

import com.letiencao.model.AccountModel;
import com.letiencao.request.account.PhoneNumberRequest;
import com.letiencao.request.account.SignInRequest;
import com.letiencao.request.account.SignUpRequest;

public interface IAccountService {
	AccountModel signUp(SignUpRequest signUpRequest);
	String signIn(SignInRequest signInRequest);
	AccountModel findByPhoneNumber(String phoneNumber);
	AccountModel findById(Long id);
}
