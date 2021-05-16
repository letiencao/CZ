package com.letiencao.api.friend;

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
import com.letiencao.model.BlocksModel;
import com.letiencao.model.FriendModel;
import com.letiencao.request.friend.FriendIdRequest;
import com.letiencao.response.friend.SetFriendResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IBlocksService;
import com.letiencao.service.IFriendService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.BlocksService;
import com.letiencao.service.impl.FriendService;

@WebServlet("/api/set-request-friend")
public class SetRequestFriendAPI extends HttpServlet {

	/**
	 * Created By : Cao LT Created Date : 02/04/2021
	 * 
	 */
	private IAccountService accountService;
	private GenericService genericService;
	private IFriendService friendService;
	private IBlocksService blocksService;

	public SetRequestFriendAPI() {
		accountService = new AccountService();
		genericService = new BaseService();
		friendService = new FriendService();
		blocksService = new BlocksService();
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		SetFriendResponse setFriendResponse = new SetFriendResponse();
		try {
//			FriendIdRequest friendIdRequest = gson.fromJson(request.getReader(), FriendIdRequest.class);
		String userIdQuery = request.getParameter("userId");
		FriendIdRequest friendIdRequest = new FriendIdRequest();
		friendIdRequest.setUserId(Long.valueOf(userIdQuery));

		Long userId = friendIdRequest.getUserId();
		if (userId != null) {
			if (userId.toString().length() > 0) {
				AccountModel accountModel = accountService.findById(userId);
				if (accountModel != null) {
					// get userId , id through token
					String jwt = request.getHeader(BaseHTTP.Authorization);
					String phoneNumber = genericService.getPhoneNumberFromToken(jwt);
					AccountModel model = accountService.findByPhoneNumber(phoneNumber);
					// check accountId == userId
					if (model.getId() != userId) {
						// Check Block
						BlocksModel blocksModel = blocksService.findOne(model.getId(), userId);
						BlocksModel blocksModel2 = blocksService.findOne(userId, model.getId());
						if (blocksModel == null && blocksModel2 == null) {
							boolean checkRequestExsisted = friendService.checkRequestExisted(model.getId(), userId,
									false);
							boolean checkRequestExsisted1 = friendService.checkRequestExisted(userId, model.getId(),
									false);
							if (checkRequestExsisted == false && checkRequestExsisted1 == false) {
								Long id = friendService.insertOne(model.getId(), userId);
								// get list requests of accountId from token
								List<FriendModel> list = friendService.findListFriendRequestById(model.getId());
								System.out.println("Size Of List = " + list.size());
								setFriendResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
								setFriendResponse.setMessage(BaseHTTP.MESSAGE_1000);
								setFriendResponse.setRequestedFriends(list.size());
							} else {
								// Exception
								setFriendResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
								setFriendResponse.setMessage(BaseHTTP.MESSAGE_9999);
								setFriendResponse.setRequestedFriends(-1);
							}
						} else {
							// Exception
							setFriendResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
							setFriendResponse.setMessage(BaseHTTP.MESSAGE_9999);
							setFriendResponse.setRequestedFriends(-1);
						}

					} else {
						// exception
						setFriendResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
						setFriendResponse.setMessage(BaseHTTP.MESSAGE_9999);
						setFriendResponse.setRequestedFriends(-1);
					}

				} else {
					// User is not validate
					setFriendResponse.setCode(String.valueOf(BaseHTTP.CODE_9995));
					setFriendResponse.setMessage(BaseHTTP.MESSAGE_9995);
					setFriendResponse.setRequestedFriends(-1);
				}
			} else {
				// value invalid
				setFriendResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
				setFriendResponse.setMessage(BaseHTTP.MESSAGE_1004);
				setFriendResponse.setRequestedFriends(-1);
			}
		} else {
			// not enough
			setFriendResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
			setFriendResponse.setMessage(BaseHTTP.MESSAGE_1002);
			setFriendResponse.setRequestedFriends(-1);
		}
//			} else {
//				// No data
//				setFriendResponse.setCode(String.valueOf(BaseHTTP.CODE_9994));
//				setFriendResponse.setMessage(BaseHTTP.MESSAGE_9994);
//				setFriendResponse.setRequestedFriends(-1);
//			}
		response.getWriter().print(gson.toJson(setFriendResponse));
		} catch (JsonSyntaxException | NumberFormatException e) {
			setFriendResponse.setCode(String.valueOf(BaseHTTP.CODE_1003));
			setFriendResponse.setMessage(BaseHTTP.MESSAGE_1003);
			setFriendResponse.setRequestedFriends(-1);
			response.getWriter().print(gson.toJson(setFriendResponse));
		}
	}

}
