package com.letiencao.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.letiencao.model.AccountModel;
import com.letiencao.request.PhoneNumberRequest;
import com.letiencao.response.BaseResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;

@WebServlet("/api/findByPhoneNumber")
public class FindByPhoneNumber extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IAccountService accountService;
	private GenericService genericService;
	public FindByPhoneNumber() {
		accountService = new AccountService();
		genericService = new BaseService();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new  Gson();
		String jwt = request.getHeader("Authorization");
		System.out.println("Token = "+jwt);
		
		if(genericService.validateToken(jwt)) {
			String phoneNumber = genericService.getPhoneNumberFromToken(jwt);
			PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest();
			phoneNumberRequest.setPhoneNumber(phoneNumber);
			AccountModel accountModel = accountService.findByPhoneNumber(phoneNumberRequest);
			response.getWriter().print(gson.toJson(accountModel));
	    }else {
	    	BaseResponse baseResponse = new BaseResponse();
	    	baseResponse.setMessage("Token is invalid");
	    	baseResponse.setCode(9998);
	    	response.getWriter().print(gson.toJson(baseResponse));
	    }
	}

}
