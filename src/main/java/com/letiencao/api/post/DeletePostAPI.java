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
	 * Created By : Cao LT
	 * Created Date : 01/04/2021
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
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		String jwt = request.getHeader(BaseHTTP.Authorization);
		BaseResponse baseResponse = new BaseResponse();
		List<PostModel> list = postService.findAll();
		// check author
		try {
			DeletePostRequest deletePostRequest = gson.fromJson(request.getReader(), DeletePostRequest.class);
			if (deletePostRequest == null) {
				baseResponse.setCode(9994);
				baseResponse.setMessage("No data or end of list data");
			} else {
				if (deletePostRequest.getId() == null) {
					baseResponse.setCode(1002);
					baseResponse.setMessage("Parameter is not enough");
				} else {
					PostModel postModel = postService.findById(deletePostRequest.getId());
					if (postModel != null) {
						Long id = postService.findAccountIdByPostId(deletePostRequest.getId());
						System.out.println("ID = " + id);
						String phoneNumber = genericService.getPhoneNumberFromToken(jwt);
						AccountModel accountModel = accountService.findByPhoneNumber(phoneNumber);
						if (id == accountModel.getId()) {
							boolean b = postService.deleteById(deletePostRequest.getId());
							if (b == true) {
								baseResponse.setCode(1000);
								baseResponse.setMessage("OK");
							} else {
								baseResponse.setCode(1010);
								baseResponse.setMessage("Action has been done previously by this user");
							}

						} else {
							baseResponse.setCode(1009);
							baseResponse.setMessage("Not Access");
						}

					} else {
						baseResponse.setCode(9992);
						baseResponse.setMessage("Post is not existed");
					}

				}
			}
		} catch (JsonSyntaxException | NumberFormatException e) {
			baseResponse.setCode(1004);
			baseResponse.setMessage("Parameter value is invalid");
		}
		response.getWriter().print(gson.toJson(baseResponse));

	}

}
