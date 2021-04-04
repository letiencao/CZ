package com.letiencao.api.friend;

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
import com.letiencao.model.FriendModel;
import com.letiencao.request.friend.FriendIdRequest;
import com.letiencao.response.friend.SetFriendResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IFriendService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
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

	public SetRequestFriendAPI() {
		accountService = new AccountService();
		genericService = new BaseService();
		friendService = new FriendService();
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		FriendIdRequest friendIdRequest = gson.fromJson(request.getReader(), FriendIdRequest.class);
		SetFriendResponse setFriendResponse = new SetFriendResponse();
		if (friendIdRequest != null) {
			Long userId = friendIdRequest.getUserId();
			if (userId != null) {
				if (userId.toString().length() > 0) {
					AccountModel accountModel = accountService.findById(userId);
					if (accountModel.getId() != null) {
						// get userId , id through token
						String jwt = request.getHeader(BaseHTTP.Authorization);
						String phoneNumber = genericService.getPhoneNumberFromToken(jwt);
						AccountModel model = accountService.findByPhoneNumber(phoneNumber);
						//check accountId == userId
						if (model.getId() != userId) {
							boolean checkRequestExsisted = friendService.checkRequestExisted(model.getId(), userId);
							boolean checkRequestExsisted1 = friendService.checkRequestExisted(userId, model.getId());
							if (checkRequestExsisted == false && checkRequestExsisted1 == false) {
								Long id = friendService.insertOne(model.getId(), userId);
								// get list requests of accountId from token
								List<FriendModel> list = friendService.findListFriendRequestById(model.getId());
								System.out.println("Size Of List = " + list.size());
								setFriendResponse.setCode(BaseHTTP.CODE_1000);
								setFriendResponse.setMessage(BaseHTTP.MESSAGE_1000);
								setFriendResponse.setRequested_friends(list.size());
							} else {
								// Exception
								setFriendResponse.setCode(BaseHTTP.CODE_9999);
								setFriendResponse.setMessage(BaseHTTP.MESSAGE_9999);
								setFriendResponse.setRequested_friends(-1);
							}
						} else {
							// exception
							setFriendResponse.setCode(BaseHTTP.CODE_9999);
							setFriendResponse.setMessage(BaseHTTP.MESSAGE_9999);
							setFriendResponse.setRequested_friends(-1);
						}

					} else {
						// User is not validate
						setFriendResponse.setCode(BaseHTTP.CODE_9995);
						setFriendResponse.setMessage(BaseHTTP.MESSAGE_9995);
						setFriendResponse.setRequested_friends(-1);
					}
				} else {
					// value invalid
					setFriendResponse.setCode(BaseHTTP.CODE_1004);
					setFriendResponse.setMessage(BaseHTTP.MESSAGE_1004);
					setFriendResponse.setRequested_friends(-1);
				}
			} else {
				// not enough
				setFriendResponse.setCode(BaseHTTP.CODE_1002);
				setFriendResponse.setMessage(BaseHTTP.MESSAGE_1002);
				setFriendResponse.setRequested_friends(-1);
			}
		} else {
			// No data
			setFriendResponse.setCode(BaseHTTP.CODE_9994);
			setFriendResponse.setMessage(BaseHTTP.MESSAGE_9994);
			setFriendResponse.setRequested_friends(-1);
		}
		response.getWriter().print(gson.toJson(setFriendResponse));
	}

}
