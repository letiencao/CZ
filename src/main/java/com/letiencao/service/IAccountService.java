package com.letiencao.service;

import java.util.List;

import com.letiencao.model.AccountModel;
import com.letiencao.request.account.SignInRequest;
import com.letiencao.request.account.SignUpRequest;

public interface IAccountService {
	AccountModel signUp(SignUpRequest signUpRequest);
	String signIn(SignInRequest signInRequest);
	AccountModel findByPhoneNumber(String phoneNumber);
	AccountModel findById(Long id);
	List<AccountModel> findListAccountByKeyword(String keyword,String token);
	boolean changePassword(String token,String password,String newPassword);
}
