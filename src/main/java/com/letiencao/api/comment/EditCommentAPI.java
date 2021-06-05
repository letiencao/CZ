package com.letiencao.api.comment;

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
import com.letiencao.model.BlocksModel;
import com.letiencao.model.CommentModel;
import com.letiencao.model.PostModel;
import com.letiencao.request.comment.AddCommentRequest;
import com.letiencao.request.comment.DeleteCommentRequest;
import com.letiencao.request.comment.EditCommentRequest;
import com.letiencao.response.BaseResponse;
import com.letiencao.response.comment.DataGetCommentResponse;
import com.letiencao.response.comment.GetCommentResponse;
import com.letiencao.response.comment.PosterResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IBlocksService;
import com.letiencao.service.ICommentService;
import com.letiencao.service.IPostService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.BlocksService;
import com.letiencao.service.impl.CommentService;
import com.letiencao.service.impl.PostService;

@WebServlet("/api/edit-comment")
public class EditCommentAPI extends HttpServlet {

	/*************************
	 * Created By Cao LT Created Date 31/03/2021
	 * 
	 *////////////////////////

	private static final long serialVersionUID = 1L;
	private ICommentService commentService;
	private IAccountService accountService;
	private GenericService genericService;
	private IBlocksService blocksService;
	private IPostService postService;

	public EditCommentAPI() {
		commentService = new CommentService();
		genericService = new BaseService();
		accountService = new AccountService();
		blocksService = new BlocksService();
		postService = new PostService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
//		EditCommentRequest editCommentRequest = gson.fromJson(request.getReader(), EditCommentRequest.class);
		BaseResponse baseResponse = new BaseResponse();
		String jwt = request.getHeader("Authorization");
		String commentIdQuery = request.getParameter("commentId");
		String postIdQuery = request.getParameter("postId");
		String contentUpdateQuery = request.getParameter("contentUpdate");
		EditCommentRequest editCommentRequest = new EditCommentRequest();
		editCommentRequest.setCommentId(Long.valueOf(commentIdQuery));
		editCommentRequest.setContentUpdate(contentUpdateQuery);
		editCommentRequest.setPostId(Long.valueOf(postIdQuery));
		Long commentId = editCommentRequest.getCommentId();
		Long postId = editCommentRequest.getPostId();
		String contentUpdate = editCommentRequest.getContentUpdate();
		if (commentId == null || postId == null || contentUpdate == null) {
			parameterNotEnough(baseResponse);
		} else {
			if (commentId.toString().length() == 0 || contentUpdate.length() == 0 || postId.toString().length() == 0) {
				valueInValid(baseResponse);
			} else {

				AccountModel accountModel = accountService
						.findByPhoneNumber(genericService.getPhoneNumberFromToken(jwt));
				PostModel postModel = postService.findById(postId);
				// Check post existed
				if (postModel != null) {
					// Check Block
					BlocksModel blocksModel = blocksService.findOne(postModel.getAccountId(), accountModel.getId());
					if (blocksModel == null) {
						// Check comment existed
						CommentModel commentModel = commentService.findById(commentId);
						if (commentModel != null) {
							if (commentModel.getPostId() == postId) {
								// find the comment of this account in list comment of that post
								if (commentModel.getAccountId() == accountModel.getId()) {
									commentService.update(commentId, contentUpdate);
									ok(baseResponse);
								} else {
									// Not author
									notAccess(baseResponse);
								}
							} else {
								// Comment existed but this post does not have
								exceptionError(baseResponse);
							}

						} else {
							// Comment not existed
							exceptionError(baseResponse);
						}
					} else {
						// Blocked
						notAccess(baseResponse);
					}
				} else {
					postNotExisted(baseResponse);
				}
			}
		}
		response.getWriter().print(gson.toJson(baseResponse));

	}

	private void ok(BaseResponse baseResponse) {
		baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
		baseResponse.setMessage(BaseHTTP.MESSAGE_1000);
	}

	private void postNotExisted(BaseResponse baseResponse) {
		baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9992));
		baseResponse.setMessage(BaseHTTP.MESSAGE_9992);
	}

	private void notAccess(BaseResponse baseResponse) {
		baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1009));
		baseResponse.setMessage(BaseHTTP.MESSAGE_1009);
	}

	private void exceptionError(BaseResponse baseResponse) {
		baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
		baseResponse.setMessage(BaseHTTP.MESSAGE_9999);
	}

	private void valueInValid(BaseResponse baseResponse) {
		baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
		baseResponse.setMessage(BaseHTTP.MESSAGE_1004);
	}

	private void parameterNotEnough(BaseResponse baseResponse) {
		baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
		baseResponse.setMessage(BaseHTTP.MESSAGE_1002);
	}

}
