package com.letiencao.service;

import com.letiencao.model.AccountModel;
import com.letiencao.request.PhoneNumberRequest;
import com.letiencao.request.SignInRequest;
import com.letiencao.request.SignUpRequest;

public interface IAccountService {
	AccountModel signUp(SignUpRequest signUpRequest);
	String signIn(SignInRequest signInRequest);
	AccountModel findByPhoneNumber(String phoneNumber);
}
