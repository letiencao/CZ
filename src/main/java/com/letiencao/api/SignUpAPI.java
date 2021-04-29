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
import com.letiencao.request.account.SignUpRequest;
import com.letiencao.response.account.DataSignUpResponse;
import com.letiencao.response.account.SignUpResponse;
import com.letiencao.service.IAccountService;
import com.letiencao.service.impl.AccountService;

@WebServlet("/api/signup")
public class SignUpAPI extends HttpServlet {

	/***************************
	 * Created By Cao LT Created Date 31/03/2021
	 * 
	 *//////////////////////////
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
//		SignUpRequest signUpRequest = gson.fromJson(request.getReader(), SignUpRequest.class);
		SignUpRequest signUpRequest = new SignUpRequest();
		String phoneNumberQuery = request.getParameter("phoneNumber");
		String passwordQuery = request.getParameter("password");
		String uuidQuery = request.getParameter("uuid");
		signUpRequest.setPhoneNumber(phoneNumberQuery);
		signUpRequest.setPassword(passwordQuery);
		signUpRequest.setUuid(uuidQuery);
		try {
			if (signUpRequest.getPhoneNumber() != null && signUpRequest.getPassword() != null
					&& signUpRequest.getUuid() != null) {

				String phoneNumber = signUpRequest.getPhoneNumber();
				String password = signUpRequest.getPassword();
				String uuid = signUpRequest.getUuid();
				Pattern p = Pattern.compile("[^A-Za-z0-9]");
				Matcher m = p.matcher(password);

				if (phoneNumber.length() > 0 && password.length() > 0 && uuid.length() > 0) {
					// Modified By : Cao LT
					// Modified Date 31/03/2021

//					signUpResponse.setCode(1003);
//					signUpResponse.setMessage("Parameter type is invalid");
					if (!m.find() && phoneNumber.length() == 10 && phoneNumber.charAt(0) == '0'
							&& password.length() >= 6 && password.length() <= 10
							&& !phoneNumber.equalsIgnoreCase(password)) {
						AccountModel accountModel = accountService.signUp(signUpRequest);
						if (accountModel != null) {
							signUpResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
							signUpResponse.setMessage(BaseHTTP.MESSAGE_1000);
							DataSignUpResponse dataSignUpResponse = new DataSignUpResponse(accountModel.getId(),
									accountModel.isDeleted(), accountModel.getCreatedDateLong(),
									accountModel.getCreatedBy(), accountModel.getModifiedBy(),
									accountModel.getModifiedDateLong(), accountModel.getName(),
									accountModel.getPhoneNumber(), accountModel.getAvatar(), accountModel.getUuid());
							signUpResponse.setData(dataSignUpResponse);
							response.getWriter().print(gson.toJson(signUpResponse));
							return;
						} else {
							signUpResponse.setCode(String.valueOf(BaseHTTP.CODE_9996));
							signUpResponse.setMessage(BaseHTTP.MESSAGE_9996);
						}
					} else {
						valueInValid(signUpResponse);
					}

				} else {
					valueInValid(signUpResponse);
				}
			} else {
				signUpResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
				signUpResponse.setMessage(BaseHTTP.MESSAGE_1002);
			}
		} 
		catch (NullPointerException e) {
			signUpResponse.setCode(String.valueOf(BaseHTTP.CODE_9994));// 9994
			signUpResponse.setMessage(BaseHTTP.MESSAGE_9994);
		}
		signUpResponse.setData(null);
		response.getWriter().print(gson.toJson(signUpResponse));
	}

	public void valueInValid(SignUpResponse signUpResponse) {
		signUpResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
		signUpResponse.setMessage(BaseHTTP.MESSAGE_1004);
		signUpResponse.setData(null);
	}

}
