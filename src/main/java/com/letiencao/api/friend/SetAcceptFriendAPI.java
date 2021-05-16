package com.letiencao.api.friend;

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
import com.letiencao.model.FriendModel;
import com.letiencao.request.friend.FriendAcceptRequest;
import com.letiencao.response.BaseResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IFriendService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.FriendService;

@WebServlet("/api/set-accept-friend")
public class SetAcceptFriendAPI extends HttpServlet {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IFriendService friendService;
	private IAccountService accountService;
	private GenericService genericService;

	public SetAcceptFriendAPI() {
		genericService = new BaseService();
		friendService = new FriendService();
		accountService = new AccountService();

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		BaseResponse baseResponse = new BaseResponse();
		try {
//			FriendAcceptRequest friendAcceptRequest = gson.fromJson(request.getReader(), FriendAcceptRequest.class);
//			if (friendAcceptRequest != null) {
			String userIdQuery = request.getParameter("userId");
			String isAcceptQuery = request.getParameter("isAccept");

			if (userIdQuery != null && isAcceptQuery != null) {
				if (userIdQuery.length() > 0 && isAcceptQuery.length() > 0) {
					FriendAcceptRequest friendAcceptRequest = new FriendAcceptRequest();
					friendAcceptRequest.setUserId(Long.valueOf(userIdQuery));
					friendAcceptRequest.setAccept(Boolean.valueOf(isAcceptQuery));
					Long userId = friendAcceptRequest.getUserId();
					boolean isAccept = friendAcceptRequest.isAccept();
					// check user existed
					AccountModel accountModel = accountService.findById(userId);

					if (accountModel != null) {
						// get token
						String jwt = request.getHeader(BaseHTTP.Authorization);
						AccountModel model = accountService
								.findByPhoneNumber(genericService.getPhoneNumberFromToken(jwt));
						// idRequest == idRequested
						if (model.getId() != userId) {
							// check request existed
							boolean checkRequested = friendService.checkRequestExisted(userId, model.getId(), false);
							if (checkRequested == true) {
								if (isAccept == true) {
									// if existed => is_friend == true
									friendService.setIsFriend(userId, model.getId());
									baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
									baseResponse.setMessage(BaseHTTP.MESSAGE_1000);

								} else if (isAccept == false) {
									// if isAccept == 0 => remove request
									FriendModel friendModel = friendService.findOne(userId, model.getId());
									if (friendModel.isFriend() == false) {
										// if is_friend == false ,can delete
										friendService.deleteRequest(userId, model.getId());
										baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
										baseResponse.setMessage(BaseHTTP.MESSAGE_1000);
									} else {
										// exception
										baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
										baseResponse.setMessage(BaseHTTP.MESSAGE_9999);
									}
								}

							} else {
								// if not existed => exception
								baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
								baseResponse.setMessage(BaseHTTP.MESSAGE_9999);
							}

						} else {
							// Exception
							baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
							baseResponse.setMessage(BaseHTTP.MESSAGE_9999);
						}

					} else {
						// user not validate
						baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9995));
						baseResponse.setMessage(BaseHTTP.MESSAGE_9995);
					}

				} else {
					// value invalid
					baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
					baseResponse.setMessage(BaseHTTP.MESSAGE_1004);
				}
			} else {
				// not enough
				baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
				baseResponse.setMessage(BaseHTTP.MESSAGE_1002);
			}

//			} else {
//				// no data
//				baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9994));
//				baseResponse.setMessage(BaseHTTP.MESSAGE_9994);
//			}
			response.getWriter().print(gson.toJson(baseResponse));
		} catch (NumberFormatException | JsonSyntaxException e) {
			baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1003));
			baseResponse.setMessage(BaseHTTP.MESSAGE_1003);
			response.getWriter().print(gson.toJson(baseResponse));
		}
	}
}
