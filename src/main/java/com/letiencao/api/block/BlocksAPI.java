package com.letiencao.api.block;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.letiencao.model.AccountModel;
import com.letiencao.model.BlocksModel;
import com.letiencao.request.blocks.AddBlocksRequest;
import com.letiencao.response.BaseResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IBlocksService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.BlocksService;

@WebServlet("/api/blocks")
public class BlocksAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IBlocksService blocksService;
	private IAccountService accountService;
	private GenericService genericService;

	public BlocksAPI() {
		blocksService = new BlocksService();
		accountService = new AccountService();
		genericService = new BaseService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		String jwt = request.getHeader("Authorization");
		BaseResponse baseResponse = new BaseResponse();
		try {
			AddBlocksRequest addBlocksRequest = gson.fromJson(request.getReader(), AddBlocksRequest.class);
			if (addBlocksRequest == null) {
				//....
				baseResponse.setCode(9994);
				baseResponse.setMessage("No data or end of list data");
				response.getWriter().print(gson.toJson(baseResponse));
				return;
			} else {
				String phoneNumber = genericService.getPhoneNumberFromToken(jwt);
				AccountModel accountModel = accountService.findByPhoneNumber(phoneNumber);
				Long idBlocks = accountModel.getId();
				Long idBlocked = addBlocksRequest.getIdBlocked();
				System.out.println("idBlocked = " + idBlocked);
				if (idBlocked != null) {
					@SuppressWarnings("unused")
					Long id = 0L;
					if (idBlocks == idBlocked) {
						id = -1L;
						System.out.println("1");
						baseResponse.setCode(1004);
						baseResponse.setMessage("Parameter value is invalid");
					} else if (accountService.findById(idBlocked) == null) {
						id = -1L;
						System.out.println("2");
						baseResponse.setCode(9995);
						baseResponse.setMessage("User is not validated");

					} else {
						//Check Da Block
						BlocksModel blocksModel = blocksService.findOne(idBlocks, addBlocksRequest.getIdBlocked());
						if(blocksModel == null) {
							id = blocksService.insertOne(idBlocks, addBlocksRequest.getIdBlocked());
							baseResponse.setCode(1000);
							baseResponse.setMessage("OK");
						}else {
							baseResponse.setCode(1010);
							baseResponse.setMessage("Action has been done previously by this user");
						}
						
					}
				} else {
					//{
					//....
					//}
					baseResponse.setCode(1002);
					baseResponse.setMessage("Parameter is not enough");
				}
				response.getWriter().print(gson.toJson(baseResponse));
			}
		} catch (NumberFormatException | JsonSyntaxException e) {
			// sai kieu
			baseResponse.setCode(1003);
			baseResponse.setMessage("Parameter type is invalid");
			response.getWriter().print(gson.toJson(baseResponse));
		}
	}
}
