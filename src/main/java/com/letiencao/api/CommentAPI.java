package com.letiencao.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.letiencao.model.AccountModel;
import com.letiencao.request.AddCommentRequest;
import com.letiencao.request.LikesRequest;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.ICommentService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.CommentService;

@WebServlet("/api/comment")
public class CommentAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ICommentService commentService;
	private IAccountService accountService;
	private GenericService genericService;

	public CommentAPI() {
		commentService = new CommentService();
		genericService = new BaseService();
		accountService = new AccountService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		AddCommentRequest addCommentRequest = gson.fromJson(request.getReader(), AddCommentRequest.class);
		String jwt = request.getHeader("Authorization");
		AccountModel accountModel = accountService.findByPhoneNumber((genericService.getPhoneNumberFromToken(jwt)));
		addCommentRequest.setAccountId(accountModel.getId());
		Long id = commentService.insertOne(addCommentRequest);
		response.getWriter().print(gson.toJson(id));

	}

}
