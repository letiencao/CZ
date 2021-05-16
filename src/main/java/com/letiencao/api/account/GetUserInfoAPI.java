package com.letiencao.api.account;

import java.io.IOException;
import java.util.ArrayList;
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
import com.letiencao.request.account.GetUserInfoRequest;
import com.letiencao.response.account.GetUserInforResponse;
import com.letiencao.response.account.UserInfoResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IBlocksService;
import com.letiencao.service.IFriendService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.BlocksService;
import com.letiencao.service.impl.FriendService;

@WebServlet("/api/get-user-info")
public class GetUserInfoAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IAccountService accountService;
	private IBlocksService blocksService;
	private GenericService genericService;
	private IFriendService friendService;

	public GetUserInfoAPI() {
		// TODO Auto-generated constructor stub
		accountService = new AccountService();
		blocksService = new BlocksService();
		genericService = new BaseService();
		friendService = new FriendService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// check userId != null
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
//		GetUserInfoRequest getUserInfoRequest = gson.fromJson(request.getReader(), GetUserInfoRequest.class);
		String userIdQuery = request.getParameter("userId");
		GetUserInfoRequest getUserInfoRequest = new GetUserInfoRequest();
		getUserInfoRequest.setUserId(Long.valueOf(userIdQuery));
		UserInfoResponse userInfoResponse = new UserInfoResponse();
		GetUserInforResponse getUserInforResponse = new GetUserInforResponse();
		List<FriendModel> list = new ArrayList<FriendModel>();
		String jwt = request.getHeader(BaseHTTP.Authorization);
		// get information account from token
		AccountModel accountModel = accountService.findByPhoneNumber(genericService.getPhoneNumberFromToken(jwt));
		try {
			if (userIdQuery != null) {
				Long userId = getUserInfoRequest.getUserId();
				if (userId.toString() != null) {
					if (userId.toString().length() > 0) {
						// Check userId = accountid(Token)
						if (userId != accountModel.getId()) {
							// Check userId existed
							AccountModel a = accountService.findById(userId);
							if (a != null) {
								// Check Blocks
								BlocksModel blocksModel1 = blocksService.findOne(userId, accountModel.getId());
								BlocksModel blocksModel2 = blocksService.findOne(accountModel.getId(), userId);
								if (blocksModel1 == null && blocksModel2 == null) {
									// get information of userId
									// get size of list friend userId
									list = friendService.findListFriendById(userId);
									userInfoResponse = setResponse(a, list);
									// Check isFriend
									boolean isFriend = friendService.checkFriendExisted(userId, accountModel.getId(), true);
									userInfoResponse.setFriend(isFriend);
									getUserInforResponse.setUserInfoResponse(userInfoResponse);
									getUserInforResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
									getUserInforResponse.setMessage(BaseHTTP.MESSAGE_1000);
								} else {
									// Not Access
									getUserInforResponse.setUserInfoResponse(null);
									getUserInforResponse.setCode(String.valueOf(BaseHTTP.CODE_1009));
									getUserInforResponse.setMessage(BaseHTTP.MESSAGE_1009);

								}
							} else {
								// User invalidate
								getUserInforResponse.setUserInfoResponse(null);
								getUserInforResponse.setCode(String.valueOf(BaseHTTP.CODE_9995));
								getUserInforResponse.setMessage(BaseHTTP.MESSAGE_9995);
							}
						} else {
							// Exception Error
							getUserInforResponse.setUserInfoResponse(null);
							getUserInforResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
							getUserInforResponse.setMessage(BaseHTTP.MESSAGE_9999);
						}

					} else {
						// value invalid
						getUserInforResponse.setUserInfoResponse(null);
						getUserInforResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
						getUserInforResponse.setMessage(BaseHTTP.MESSAGE_1004);
					}
				} else {
					// Parameter not enough
					getUserInforResponse.setUserInfoResponse(null);
					getUserInforResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
					getUserInforResponse.setMessage(BaseHTTP.MESSAGE_1002);
				}
			} 
			else {

				// get size of list friend userId(token)
				list = friendService.findListFriendById(accountModel.getId());
				// get information of userId(token)
				userInfoResponse = setResponse(accountModel, list);
				userInfoResponse.setFriend(false);
				getUserInforResponse.setUserInfoResponse(userInfoResponse);
				getUserInforResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
				getUserInforResponse.setMessage(BaseHTTP.MESSAGE_1000);
			}
		} catch (NumberFormatException | JsonSyntaxException e) {
			getUserInforResponse.setUserInfoResponse(null);
			getUserInforResponse.setCode(String.valueOf(BaseHTTP.CODE_1003));
			getUserInforResponse.setMessage(BaseHTTP.MESSAGE_1003);
		}
		
		response.getWriter().print(gson.toJson(getUserInforResponse));
	}
	//get data response
	public UserInfoResponse setResponse(AccountModel accountModel, List<FriendModel> list) {
		UserInfoResponse userInfoResponse = new UserInfoResponse();
		userInfoResponse.setId(accountModel.getId());
		userInfoResponse.setPhoneNumber(accountModel.getPhoneNumber());
		userInfoResponse.setAvatar(accountModel.getAvatar());
		long created = genericService.convertTimestampToSeconds(accountModel.getCreatedDate());
		userInfoResponse.setCreated(created);
		userInfoResponse.setDescription("");
		userInfoResponse.setAvatar(accountModel.getAvatar());
		userInfoResponse.setCoverImage("");
		userInfoResponse.setLink("");
		userInfoResponse.setAddress("");
		userInfoResponse.setCity("");
		userInfoResponse.setCountry("");
		// get size of list friend userId
		userInfoResponse.setSizeListFriend(list.size());
		userInfoResponse.setOnline("");
		return userInfoResponse;
	}

}
