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
import com.letiencao.response.SignUpResponse;
import com.letiencao.service.IAccountService;
import com.letiencao.service.impl.AccountService;

@WebServlet("/api/account")
public class AccountAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IAccountService accountService;
	public AccountAPI() {
		// TODO Auto-generated constructor stub
		accountService = new AccountService();
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
