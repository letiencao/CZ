package com.letiencao.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.letiencao.api.BaseHTTP;
import com.letiencao.response.BaseResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;

@WebFilter(urlPatterns = { "/api/logout", "/api/add-post", "/api/set-comment","/api/edit-comment","/api/del-comment", "/api/get-post", "/api/blocks", "/api/like",
		"/api/delete-post", "/api/get-comment", "/api/get-list-posts", "/api/report", "/api/set-accept-friend",

		"/api/set-request-friend", "/api/get-user-infor","/api/change-password",

		"/api/set-request-friend", "/api/get-user-info" })

public class APIFilter implements Filter {

	private GenericService genericService;

	private IAccountService accountService;

	public APIFilter() {
		genericService = new BaseService();
		accountService = new AccountService();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		httpRequest.setCharacterEncoding("utf-8");
		httpResponse.setContentType("application/json");
		BaseResponse baseResponse = new BaseResponse();
		Gson gson = new Gson();
		String authToken = httpRequest.getHeader(BaseHTTP.Authorization);
//		try {

		System.out.println("authToken = " + authToken);
		String url = httpRequest.getRequestURI();
		System.out.println("url = " + url);
		try {
			if (genericService.validateToken(authToken) && genericService.getPhoneNumberFromToken(authToken) != null) {
				chain.doFilter(request, response);
			}
		} catch (IllegalArgumentException e) {
			if(authToken == "") {
				System.out.println("Exception = "+e.getMessage());
				baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9998));

				baseResponse.setMessage(BaseHTTP.MESSAGE_9998);
				httpResponse.getWriter().print(gson.toJson(baseResponse));
			}else {
				baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
				baseResponse.setMessage(BaseHTTP.MESSAGE_1002);
				httpResponse.getWriter().print(gson.toJson(baseResponse));
			}
		}

//		} catch (IllegalArgumentException e) {
//			// token == null
////			baseResponse.setCode(9994);
//			baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
//			baseResponse.setMessage(BaseHTTP.MESSAGE_1002);
//			httpResponse.getWriter().print(gson.toJson(baseResponse));
//		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
