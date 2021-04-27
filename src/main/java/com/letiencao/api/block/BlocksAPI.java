package com.letiencao.api.block;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.letiencao.api.BaseHTTP;
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

	/*********************************
	 * Created By Cao LT
	 * Created Date 31/03/2021
	 * 
	 *////////////////////////////////
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
		String jwt = request.getHeader(BaseHTTP.Authorization);
		BaseResponse baseResponse = new BaseResponse();
		try {
			AddBlocksRequest addBlocksRequest = gson.fromJson(request.getReader(), AddBlocksRequest.class);
			if (addBlocksRequest == null) {
				//....
				baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9994)); //ví dụ nhé đây là gõ tay này,còn bản cập nhật sẽ như này
//				baseResponse.setMessage("No data or end of list data");
				baseResponse.setMessage(BaseHTTP.MESSAGE_9994);// do sua nhu the thoi,dc chua ok
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
						baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));//lam di ok r
						baseResponse.setMessage(BaseHTTP.MESSAGE_1004);
					} else if (accountService.findById(idBlocked) == null) {
						id = -1L;
						System.out.println("2");
						baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9995));
						baseResponse.setMessage(BaseHTTP.MESSAGE_9995);

					} else {
						//Check Block
						BlocksModel blocksModel = blocksService.findOne(idBlocks, addBlocksRequest.getIdBlocked());
						if(blocksModel == null) {
							id = blocksService.insertOne(idBlocks, addBlocksRequest.getIdBlocked());
							baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
							baseResponse.setMessage(BaseHTTP.MESSAGE_1000);
						}else {
							baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1010));
							baseResponse.setMessage(BaseHTTP.MESSAGE_1010);
						}
						
					}
				} else {
					//{
					//....
					//}
					baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
					baseResponse.setMessage(BaseHTTP.MESSAGE_1002);
				}
				response.getWriter().print(gson.toJson(baseResponse));
			}
		} catch (NumberFormatException | JsonSyntaxException e) {
			// sai kieu
			baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1003));
			baseResponse.setMessage(BaseHTTP.MESSAGE_1003);
			response.getWriter().print(gson.toJson(baseResponse));
		}
	}
}
