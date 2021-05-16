package com.letiencao.api.like;

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
import com.letiencao.model.PostModel;
import com.letiencao.request.like.LikesRequest;
import com.letiencao.response.BaseResponse;
import com.letiencao.response.like.DataLikesResponse;
import com.letiencao.response.like.LikesResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IBlocksService;
import com.letiencao.service.ILikesService;
import com.letiencao.service.IPostService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.BlocksService;
import com.letiencao.service.impl.LikesService;
import com.letiencao.service.impl.PostService;

@WebServlet("/api/like")
public class LikesAPI extends HttpServlet {

	/**
	 * 
	 */
	private ILikesService likesService;
	private IAccountService accountService;
	private GenericService genericService;
	private IBlocksService blocksService;
	private IPostService postService;

	public LikesAPI() {
		likesService = new LikesService();
		genericService = new BaseService();
		accountService = new AccountService();
		blocksService = new BlocksService();
		postService = new PostService();
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		String jwt = request.getHeader(BaseHTTP.Authorization);
		LikesResponse likesResponse = new LikesResponse();
//		try {
//			LikesRequest likesRequest = gson.fromJson(request.getReader(), LikesRequest.class);
//			if (likesRequest == null) {
//				likesResponse.setCode(String.valueOf(BaseHTTP.CODE_9994));
//				likesResponse.setMessage(BaseHTTP.MESSAGE_9994);
//				likesResponse.setDataLikesResponse(null);
//				response.getWriter().print(gson.toJson(likesResponse));
//				return;
//			} else {
		String postId = request.getParameter("postId");
		LikesRequest likesRequest = new LikesRequest();
		likesRequest.setPostId(Long.valueOf(postId));
		if (likesRequest.getPostId() != null) {
			AccountModel accountModel = accountService.findByPhoneNumber((genericService.getPhoneNumberFromToken(jwt)));
			likesRequest.setAccountId(accountModel.getId());
			// check accountId co bi author block ko ?
			PostModel postModel = postService.findById(likesRequest.getPostId());
			try {
				BlocksModel blocksModel = blocksService.findOne(postModel.getAccountId(), accountModel.getId());
				if (blocksModel == null) {
					// check da like chua
					boolean check = likesService.checkThisUserLiked(likesRequest.getAccountId(),
							likesRequest.getPostId());
					System.out.println("Check = " + check);
					if (check == false) {
						Long id = likesService.insertOne(likesRequest);
						if (id == -1) {
							likesResponse.setCode(String.valueOf(9992));
							likesResponse.setMessage("Post is not existed");
							likesResponse.setDataLikesResponse(null);
						} else {

							likesResponse.setCode(String.valueOf(1000));
							likesResponse.setMessage("OK");
							int like = likesService.findByPostId(likesRequest.getPostId());
							DataLikesResponse dataLikesResponse = new DataLikesResponse();
							dataLikesResponse.setLike(like);
							likesResponse.setDataLikesResponse(dataLikesResponse);

						}
					} else {
						likesResponse.setCode(String.valueOf(1010));
						likesResponse.setMessage("Action has been done previously by this user");
						likesResponse.setDataLikesResponse(null);
					}

				} else {
					likesResponse.setCode(String.valueOf(1009));
					likesResponse.setMessage("Not access");
					likesResponse.setDataLikesResponse(null);
				}
			} catch (NullPointerException e) {
				System.out.println("Null Pointer Exception LikesAPI : " + e.getMessage());
				likesResponse.setCode(String.valueOf(9992));
				likesResponse.setMessage("Post is not existed");
				likesResponse.setDataLikesResponse(null);
			}

		} else {
			likesResponse.setCode(String.valueOf(1002));
			likesResponse.setMessage("Parameter is not enough");
			likesResponse.setDataLikesResponse(null);
		}
		response.getWriter().print(gson.toJson(likesResponse));
//			}
//		} catch (NumberFormatException | JsonSyntaxException e) {
//			likesResponse.setCode(String.valueOf(1003));
//			likesResponse.setMessage("Parameter type is invalid");
//			likesResponse.setDataLikesResponse(null);
//			response.getWriter().print(gson.toJson(likesResponse));
//		}

	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		BaseResponse baseResponse = new BaseResponse();
		String jwt = request.getHeader(BaseHTTP.Authorization);
//		try {
//			LikesRequest likesRequest = gson.fromJson(request.getReader(), LikesRequest.class);
//			if (likesRequest == null) {
//				baseResponse.setCode(String.valueOf(9994));
//				baseResponse.setMessage("No data or end of list data");
//				response.getWriter().print(gson.toJson(baseResponse));
//				return;
//			} else {
		String postId = request.getParameter("postId");
		LikesRequest likesRequest = new LikesRequest();
		likesRequest.setPostId(Long.valueOf(postId));
		if (likesRequest.getPostId() != null) {
			AccountModel accountModel = accountService.findByPhoneNumber((genericService.getPhoneNumberFromToken(jwt)));
			likesRequest.setAccountId(accountModel.getId());
			PostModel postModel = postService.findById(likesRequest.getPostId());
			if (postModel != null) {
				BlocksModel blocksModel = blocksService.findOne(postModel.getAccountId(), accountModel.getId());
				if (blocksModel == null) {
					boolean check = likesService.checkThisUserLiked(likesRequest.getAccountId(),
							likesRequest.getPostId());
					System.out.println("Check = " + check);
					if (check == true) {
						boolean b = likesService.disLike(likesRequest.getPostId(), likesRequest.getAccountId());
						if (b == false) {
							baseResponse.setCode(String.valueOf(1001));
							baseResponse.setMessage("Can not connect to DB");
						} else {
							baseResponse.setCode(String.valueOf(1000));
							baseResponse.setMessage("OK");
						}
					} else {
						baseResponse.setCode(String.valueOf(1010));
						baseResponse.setMessage(BaseHTTP.MESSAGE_1010);
					}
				} else {
					baseResponse.setCode(String.valueOf(1009));
					baseResponse.setMessage("Not Access");
				}

			} else {
				baseResponse.setCode(String.valueOf(9992));
				baseResponse.setMessage("Post is not existed");
			}

		} else {
			baseResponse.setCode(String.valueOf(1002));
			baseResponse.setMessage("Parameter is not enough");
		}
		response.getWriter().print(gson.toJson(baseResponse));
//			}
//		} catch (NumberFormatException | JsonSyntaxException e) {
//			baseResponse.setCode(String.valueOf(1003));
//			baseResponse.setMessage("Parameter type is invalid");
//			response.getWriter().print(gson.toJson(baseResponse));
//		}
	}

}
