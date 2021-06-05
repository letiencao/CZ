package com.letiencao.api.post;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.letiencao.api.BaseHTTP;
import com.letiencao.model.AccountModel;
import com.letiencao.model.PostModel;
import com.letiencao.request.post.DeletePostRequest;
import com.letiencao.response.BaseResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IPostService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.PostService;

@WebServlet("/api/delete-post")
public class DeletePostAPI extends HttpServlet {

	/**
	 * Created By : Cao LT Created Date : 01/04/2021
	 * 
	 */
	private IPostService postService;
	private GenericService genericService;
	private IAccountService accountService;

	public DeletePostAPI() {
		postService = new PostService();
		genericService = new BaseService();
		accountService = new AccountService();
	}

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		String jwt = request.getHeader(BaseHTTP.Authorization);
		BaseResponse baseResponse = new BaseResponse();
//		List<PostModel> list = postService.findAll();
		DeletePostRequest deletePostRequest = new DeletePostRequest();
		String idQuery = request.getParameter("id");
		deletePostRequest.setId(Long.valueOf(idQuery));
		// check author
//		try {
//			DeletePostRequest deletePostRequest = gson.fromJson(request.getReader(), DeletePostRequest.class);
//			if (deletePostRequest != null) {
//				if (deletePostRequest.getId() == null) {
//					baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
//					baseResponse.setMessage(BaseHTTP.MESSAGE_1002);
//				} else {
		PostModel postModel = postService.findById(deletePostRequest.getId());
		if (postModel != null) {
			Long id = postService.findAccountIdByPostId(deletePostRequest.getId());
			System.out.println("ID = " + id);
			String phoneNumber = genericService.getPhoneNumberFromToken(jwt);
			AccountModel accountModel = accountService.findByPhoneNumber(phoneNumber);
			if (id == accountModel.getId()) {
				boolean b = postService.deleteById(deletePostRequest.getId());
				if (b == true) {
					baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
					baseResponse.setMessage(BaseHTTP.MESSAGE_1000);
				} else {
					baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9992));
					baseResponse.setMessage(BaseHTTP.MESSAGE_9992);
				}

			} else {
				baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1009));
				baseResponse.setMessage(BaseHTTP.MESSAGE_1009);
			}

		} else {
			baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9992));
			baseResponse.setMessage(BaseHTTP.MESSAGE_9992);
		}

//			} else {
//				baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9994));
//				baseResponse.setMessage(BaseHTTP.MESSAGE_9994);
//			}
//		} catch (JsonSyntaxException | NumberFormatException e) {
//			baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
//			baseResponse.setMessage(BaseHTTP.MESSAGE_1004);
//		}
		response.getWriter().print(gson.toJson(baseResponse));

	}

}
