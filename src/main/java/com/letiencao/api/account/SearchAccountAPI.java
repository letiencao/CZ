package com.letiencao.api.account;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.letiencao.api.BaseHTTP;
import com.letiencao.model.AccountModel;
import com.letiencao.request.account.SearchAccountRequest;
import com.letiencao.response.account.SearchAccountResponse;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IBlocksService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BlocksService;

@WebServlet("/api/search-account")
public class SearchAccountAPI extends HttpServlet {
	/**
	 * Created By : Cao LT
	 * Created Date : 09/04/2021
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IAccountService accountService;
	private IBlocksService blocksService;
	public SearchAccountAPI() {
		accountService = new AccountService();
		blocksService = new BlocksService();
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		SearchAccountRequest searchAccountRequest = gson.fromJson(request.getReader(), SearchAccountRequest.class);
		SearchAccountResponse searchAccountResponse = new SearchAccountResponse();
		String jwt = request.getHeader(BaseHTTP.Authorization);
		if(searchAccountRequest != null) {
			String keyword = searchAccountRequest.getKeyword();
			int index = searchAccountRequest.getIndex();
			int count = searchAccountRequest.getCount();
			if(keyword != null && String.valueOf(index) != null && String.valueOf(count) != null) {
				if(keyword.length() > 0 && String.valueOf(index).length() > 0 && String.valueOf(count).length() > 0 && index > -1 && count > -1) {
					List<AccountModel> list = accountService.findListAccountByKeyword(keyword, jwt);
					if(index + count > list.size()) {
						count = list.size();
					}else {
						
					}
				}else {
					//value invalid
				}
			}else {
				//param not enough
			}
		}else {
			//no data
		}
	}
}
