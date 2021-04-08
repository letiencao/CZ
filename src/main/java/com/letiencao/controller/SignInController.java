package com.letiencao.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.letiencao.request.account.SignInRequest;
import com.letiencao.service.IAccountService;
import com.letiencao.service.impl.AccountService;

@WebServlet("/sign-in")
public class SignInController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IAccountService  accountService;
	public SignInController() {
		accountService = new AccountService();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/signin/signIn.jsp");
		dispatcher.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phoneNumber = request.getParameter("phoneNumber");
		String password = request.getParameter("password");
		SignInRequest signInRequest = new SignInRequest();
		signInRequest.setPassword(password);
		signInRequest.setPhoneNumber(phoneNumber);
		String token = accountService.signIn(signInRequest);
		if(token != null) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("token", token);
			httpSession.setMaxInactiveInterval(200);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/websocket/index.jsp");
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect("sign-in");
		}
	}

}
