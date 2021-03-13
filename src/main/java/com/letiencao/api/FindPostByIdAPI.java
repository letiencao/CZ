package com.letiencao.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.letiencao.model.PostModel;
import com.letiencao.response.BaseResponse;
import com.letiencao.service.IPostService;
import com.letiencao.service.impl.PostService;

@WebServlet("/api/post")
public class FindPostByIdAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IPostService postService;

	public FindPostByIdAPI() {
		postService = new PostService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		Gson gson = new Gson();
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setCode(1002);
		baseResponse.setMessage("Parameter is not enough");
		try {
			String idStr = request.getParameter("id");
			Long id = Long.valueOf(idStr);
			System.out.println("id = " + id);
			PostModel model = postService.findPostById(id);
			if(model == null) {
				baseResponse.setCode(1004);
				baseResponse.setMessage("Parameter value is invalid");
				response.getWriter().print(gson.toJson(baseResponse));
			}else {
				response.getWriter().print(gson.toJson(model));	
			}

		} catch (NumberFormatException e) {
			response.getWriter().print(gson.toJson(baseResponse));

		}

	}

}
