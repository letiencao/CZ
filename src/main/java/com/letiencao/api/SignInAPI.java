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
import com.letiencao.request.account.SignInRequest;
import com.letiencao.response.account.SignInResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;

@WebServlet("/api/login")
public class SignInAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IAccountService accountService;
	private GenericService genericService;

	public SignInAPI() {
		// TODO Auto-generated constructor stub
		accountService = new AccountService();
		genericService = new BaseService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		SignInRequest signInRequest = gson.fromJson(request.getReader(), SignInRequest.class);
		SignInResponse signInResponse = new SignInResponse();
		if (signInRequest == null) {
			signInResponse.setCode(9994);
			signInResponse.setMessage("No data or end of list data");
			signInResponse.setAccountModel(null);
		} else {
			try {
				String phoneNumber = signInRequest.getPhoneNumber();
				String password = signInRequest.getPassword();
				Pattern p = Pattern.compile("[^A-Za-z0-9]");
				Matcher m = p.matcher(password);
				if (!m.find() && phoneNumber.length() == 10 && phoneNumber.charAt(0) == '0' && password.length() >= 6
						&& password.length() <= 10 && !phoneNumber.equalsIgnoreCase(password)) {
					if (accountService.findByPhoneNumber(phoneNumber) == null) {
						signInResponse.setCode(9995);
						signInResponse.setMessage("User is not validated");
						signInResponse.setToken(null);
						signInResponse.setAccountModel(null);
					} else {
						String jwt = accountService.signIn(signInRequest);
						if(jwt == null) {
							System.out.println("Hellloooo Nullllll");
							signInResponse.setCode(9995);
							signInResponse.setMessage("User is not validated");
							signInResponse.setToken(null);
							signInResponse.setAccountModel(null);
						}else {
							phoneNumber = genericService.getPhoneNumberFromToken(jwt);
							AccountModel accountModel = accountService.findByPhoneNumber(phoneNumber);
							signInResponse.setCode(1000);
							signInResponse.setMessage("OK");
							signInResponse.setToken(jwt);
							signInResponse.setAccountModel(accountModel);
						}
					}
				} else {
					signInResponse.setCode(1004);
					signInResponse.setMessage("Parameter value is invalid");
					signInResponse.setAccountModel(null);
				}
			} catch (NullPointerException e) {
				signInResponse.setCode(1002);
				signInResponse.setMessage("Parameter is not enough");
				signInResponse.setAccountModel(null);
			}
		}
		response.getWriter().print(gson.toJson(signInResponse));
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		response.setContentType("application/json");
//		String jwt = request.getHeader("Authorization");
//		response.getWriter().print("");
	}

}
