package com.letiencao.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.letiencao.model.AccountModel;
import com.letiencao.request.LikesRequest;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.ILikesService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.LikesService;

@WebServlet("/api/likes")
public class LikesAPI extends HttpServlet {

	/**
	 * 
	 */
	private ILikesService likesService;
	private IAccountService accountService;
	private GenericService genericService;
	public LikesAPI() {
		likesService = new LikesService();
		genericService = new BaseService();
		accountService = new AccountService();
	}
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		LikesRequest likesRequest = gson.fromJson(request.getReader(), LikesRequest.class);
		String jwt = request.getHeader("Authorization");
		AccountModel accountModel = accountService.findByPhoneNumber((genericService.getPhoneNumberFromToken(jwt)));
		likesRequest.setAccountId(accountModel.getId());
		Long id = likesService.insertOne(likesRequest);
		response.getWriter().print(gson.toJson(id));;
		
		
	}

}
