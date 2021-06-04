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

@WebServlet("/api/set-comment")
public class SetCommentAPI extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ICommentService commentService;
	private IAccountService accountService;
	private GenericService genericService;
	private IBlocksService blocksService;
	private IPostService postService;

	public SetCommentAPI() {
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
//		private Long postId
//		private String content;
		GetCommentResponse commentResponse = new GetCommentResponse();
		String postIdQuery = request.getParameter("postId");
		String contentQuery = request.getParameter("content");

		AddCommentRequest addCommentRequest = new AddCommentRequest();
		addCommentRequest.setPostId(Long.valueOf(postIdQuery));
		addCommentRequest.setContent(contentQuery);

//		AddCommentRequest addCommentRequest = gson.fromJson(request.getReader(), AddCommentRequest.class);

		// Request = ..............

		// get AccountId From token
		String jwt = request.getHeader(BaseHTTP.Authorization);
		AccountModel accountModel = accountService.findByPhoneNumber((genericService.getPhoneNumberFromToken(jwt)));
		// set accountId For Request
		addCommentRequest.setAccountId(accountModel.getId());
		List<CommentModel> commentModels = commentService.findAll();
		// set index and count for Request
		if (commentModels.size() == 0) {
			addCommentRequest.setIndex(0L);
		} else {
			addCommentRequest.setIndex(commentModels.get(0).getId());
		}
		addCommentRequest.setCount(commentModels.size());

		Long postId = addCommentRequest.getPostId();
		String content = addCommentRequest.getContent();
		Long accountId = addCommentRequest.getAccountId();

		if (postId == null || content == null || accountId == null) {
			commentResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
			commentResponse.setMessage(BaseHTTP.MESSAGE_1002);
		} else {
			if (postId.toString().length() == 0 || content.length() == 0 || accountId.toString().length() == 0) {
				commentResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
				commentResponse.setMessage(BaseHTTP.MESSAGE_1004);
			} else {
				try {
					PostModel postModel = postService.findById(postId);
					// Check Block
					BlocksModel blocksModel = blocksService.findOne(postModel.getAccountId(), accountModel.getId());
					if (blocksModel == null) {
						// insert Comment
						Long id = commentService.insertOne(addCommentRequest);
						CommentModel commentModel = commentService.findById(id);
						PosterResponse posterResponse = new PosterResponse();
						// get information of author
						Long authorId = commentModel.getAccountId();
						AccountModel model = accountService.findById(authorId);
						posterResponse.setId(model.getId());
						posterResponse.setName(model.getName());
						posterResponse.setAvatar(model.getAvatar());
						DataGetCommentResponse dataGetCommentResponse = new DataGetCommentResponse();
						dataGetCommentResponse.setContent(commentModel.getContent());
						dataGetCommentResponse.setId(commentModel.getId());
						dataGetCommentResponse.setPosterResponse(posterResponse);
						// Convert Date to seconds
						dataGetCommentResponse
								.setCreated(genericService.convertTimestampToSeconds(commentModel.getCreatedDate()));
						commentResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
						commentResponse.setMessage(BaseHTTP.MESSAGE_1000);
						commentResponse.setDataGetCommentResponse(dataGetCommentResponse);
						commentResponse.setBlocked(false);
					} else {
						// This User was blocked by The Author
						commentResponse.setDataGetCommentResponse(null);
						commentResponse.setBlocked(true);
						commentResponse.setCode(String.valueOf(BaseHTTP.CODE_1009));
						commentResponse.setMessage(BaseHTTP.MESSAGE_1009);
					}
				} catch (NullPointerException e) {
					// post deleted or not existed
					commentResponse.setCode(String.valueOf(BaseHTTP.CODE_9992));
					commentResponse.setMessage(BaseHTTP.MESSAGE_9992);
				}

			}
		}
//		else {
//			//Request ..............
//			commentResponse.setCode(String.valueOf(BaseHTTP.CODE_9994));
//			commentResponse.setMessage(BaseHTTP.MESSAGE_9994);
//		}
		response.getWriter().print(gson.toJson(commentResponse));

	}

}
