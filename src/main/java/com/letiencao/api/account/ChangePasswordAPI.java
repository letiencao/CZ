package com.letiencao.api.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.google.gson.Gson;
import com.letiencao.api.BaseHTTP;
import com.letiencao.response.BaseResponse;
import com.letiencao.response.account.ChangePasswordResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;

@WebServlet("/api/change-password")
public class ChangePasswordAPI extends HttpServlet {
	private GenericService genericService;
	private IAccountService accountService;

	public ChangePasswordAPI() {
		// phân biệt hàm khi có hàm trùng nhau...
		genericService = new BaseService();
		accountService = new AccountService();
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		ChangePasswordResponse changePasswordResponse = new ChangePasswordResponse();
		Gson gson = new Gson();
		// Query String
		String passwordQuery = request.getParameter("password");
		String newPasswordQuery = request.getParameter("newPassword");
		if (passwordQuery != null && newPasswordQuery != null) {
			if (passwordQuery.length() >= 6 && passwordQuery.length() <= 10 && newPasswordQuery.length() >= 6
					&& newPasswordQuery.length() <= 10) {
				if (!passwordQuery.equals(newPasswordQuery)) {
					String token = request.getHeader(BaseHTTP.Authorization);
					boolean changePassword = accountService.changePassword(token, passwordQuery, newPasswordQuery);
					if (changePassword) {
						changePasswordResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
						changePasswordResponse.setMessage(BaseHTTP.MESSAGE_1000);
						changePasswordResponse.setData(String.valueOf(changePassword));

					} else {
						// passwordQuery truyen len khong trung voi password da luu trong database truoc
						// do
						// Exeption error
						changePasswordResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
						changePasswordResponse.setMessage(BaseHTTP.MESSAGE_9999);
						changePasswordResponse.setData(String.valueOf(changePassword));
					}
				} else {
					// passwordQuery = newPasswordQuery
					// exception error
					changePasswordResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
					changePasswordResponse.setMessage(BaseHTTP.MESSAGE_9999);
					changePasswordResponse.setData(String.valueOf(false));
				}

			} else {
				// param value invalid
				changePasswordResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
				changePasswordResponse.setMessage(BaseHTTP.MESSAGE_1004);
				changePasswordResponse.setData(String.valueOf(false));
			}
		} else {
			// param not enough
			changePasswordResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
			changePasswordResponse.setMessage(BaseHTTP.MESSAGE_1002);
			changePasswordResponse.setData(String.valueOf(false));
		}
		response.getWriter().print(gson.toJson(changePasswordResponse));

	}

}
