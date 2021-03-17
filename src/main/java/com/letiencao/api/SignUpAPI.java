package com.letiencao.api;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.letiencao.model.AccountModel;
import com.letiencao.request.SignUpRequest;
import com.letiencao.response.SignUpResponse;
import com.letiencao.service.IAccountService;
import com.letiencao.service.impl.AccountService;

@WebServlet("/api/signup")
public class SignUpAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IAccountService accountService;

	public SignUpAPI() {
		// TODO Auto-generated constructor stub
		accountService = new AccountService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		SignUpResponse signUpResponse = new SignUpResponse();
		SignUpRequest signUpRequest = gson.fromJson(request.getReader(), SignUpRequest.class);
		try {
			String phoneNumber = signUpRequest.getPhoneNumber();
			String password = signUpRequest.getPassword();
			String uuid = signUpRequest.getUuid();
			Pattern p = Pattern.compile("[^A-Za-z0-9]");
			Matcher m = p.matcher(password);
			AccountModel accountModel = new AccountModel();

			if (phoneNumber.length() == 0 || password.length() == 0 || uuid.length() == 0) {
				signUpResponse.setCode(1002);
				signUpResponse.setMessage("Parameter is not enough");
				signUpResponse.setAccountModel(null);
			} else {
				if (!m.find() && phoneNumber.length() == 10 && phoneNumber.charAt(0) == '0' && password.length() >= 6
						&& password.length() <= 10 && !phoneNumber.equalsIgnoreCase(password)) {
					accountModel = accountService.signUp(signUpRequest);
					if (accountModel != null) {
						signUpResponse.setCode(1000);
						signUpResponse.setMessage("OK");
						signUpResponse.setAccountModel(accountModel);
					} else {
						signUpResponse.setCode(9996);
						signUpResponse.setMessage("User existed");
						signUpResponse.setAccountModel(null);
					}
				} else {
					signUpResponse.setCode(1004);
					signUpResponse.setMessage("Parameter value is invalid");
					signUpResponse.setAccountModel(null);
				}
			}
		} catch (NullPointerException e) {
			signUpResponse.setCode(1003);
			signUpResponse.setMessage("Parameter type is invalid");
			signUpResponse.setAccountModel(null);
		}
		response.getWriter().print(gson.toJson(signUpResponse));

	}

}
