package com.letiencao.api;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IPostService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.PostService;

@WebServlet("/api/add_post")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 50, // 50MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class PostAPI extends HttpServlet{

	/*
	 * 
	 */
	private IPostService postService;
	private IAccountService accountService;
	private GenericService genericService;
	
	public PostAPI() {
		postService = new PostService();
		accountService = new AccountService();
		genericService = new BaseService();
	}
	private static final String SAVE_DIR = "uploadFiles";
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
	
	}

}
