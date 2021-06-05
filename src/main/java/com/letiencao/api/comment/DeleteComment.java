package com.letiencao.api.comment;

import java.io.IOException;

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
import com.letiencao.request.comment.DeleteCommentRequest;
import com.letiencao.response.BaseResponse;
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

@WebServlet("/api/del-comment")
public class DeleteComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ICommentService commentService;
	private IAccountService accountService;
	private GenericService genericService;
	private IBlocksService blocksService;
	private IPostService postService;

	public DeleteComment() {
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
		String commentIdQuery = request.getParameter("commentId");
		String postIdQuery = request.getParameter("postId");
		DeleteCommentRequest deleteCommentRequest = new DeleteCommentRequest();
		deleteCommentRequest.setCommentId(Long.valueOf(commentIdQuery));
		deleteCommentRequest.setPostId(Long.valueOf(postIdQuery));
//		DeleteCommentRequest deleteCommentRequest = gson.fromJson(request.getReader(), DeleteCommentRequest.class);
		BaseResponse baseResponse = new BaseResponse();
		Long commentId = deleteCommentRequest.getCommentId();
		Long postId = deleteCommentRequest.getPostId();
		if (commentId == null || postId == null) {
			parameterNotEnough(baseResponse);
		} else {
			if (commentId.toString().length() == 0 || postId.toString().length() == 0) {
				valueInValid(baseResponse);
			} else {

				String jwt = request.getHeader("Authorization");
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
							// check comment from commentId of post from postId
							if (commentModel.getPostId() == postId) {
								// Check author
								if (commentModel.getAccountId() == accountModel.getId()) {
									commentService.deleteComment(postId, commentId);
									ok(baseResponse);
								} else {
									// Not author
									notAccess(baseResponse);
								}
							} else {
								// Comment existed but this post does not have
								System.out.println("11111111111111111");
								exceptionError(baseResponse);
							}
						} else {
							// Comment not existed
							System.out.println("222222222222222");
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
