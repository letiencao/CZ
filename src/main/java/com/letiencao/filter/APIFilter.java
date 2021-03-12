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
import com.letiencao.response.BaseResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;

@WebFilter(urlPatterns = { "/api/findByPhoneNumber", "/api/logout" })
public class APIFilter implements Filter {

	private final static String TOKEN_HEADER = "Authorization";

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
		String authToken = httpRequest.getHeader(TOKEN_HEADER);
		System.out.println("authToken = " + authToken);
		String url = httpRequest.getRequestURI();
		System.out.println("url = " + url);
		Gson gson = new Gson();
		BaseResponse baseResponse = new BaseResponse();
		
		try {
			if (authToken != null) {
				if (genericService.validateToken(authToken) && genericService.getPhoneNumberFromToken(authToken) != null) {
					chain.doFilter(request, response);
				}else {
					baseResponse.setCode(9998);
					baseResponse.setMessage("Token is invalid");
					httpResponse.getWriter().print(gson.toJson(baseResponse));
				}
			}
		} catch (IllegalArgumentException e) {
			//token == null
			baseResponse.setCode(9994);
			baseResponse.setMessage("No data or end of list data");
			httpResponse.getWriter().print(gson.toJson(baseResponse));
		}
		

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
