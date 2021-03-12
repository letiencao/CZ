package com.letiencao.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.letiencao.response.BaseResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.impl.BaseService;

@WebServlet("/api/logout")
public class LogoutAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GenericService genericService;
	public LogoutAPI() {
		genericService = new BaseService();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		String jwt = request.getHeader("Authorization");
		Gson gson = new Gson();
		System.out.println("validate token =  "+genericService.validateToken(jwt));
		System.out.println("telephone : "+genericService.getPhoneNumberFromToken(jwt));
		BaseResponse baseResponse = new BaseResponse();
		if(genericService.getPhoneNumberFromToken(jwt) != null) {
			baseResponse.setCode(1000);
			baseResponse.setMessage("OK");
		}else {
			baseResponse.setCode(9998);
			baseResponse.setMessage("Exception error");
		}
		response.getWriter().print(gson.toJson(baseResponse));
	}

}
