package com.letiencao.api.comment;

import java.io.IOException;
import java.util.ArrayList;
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
import com.letiencao.request.comment.GetListCommentRequest;
import com.letiencao.response.comment.DataGetCommentResponse;
import com.letiencao.response.comment.GetListCommentResponse;
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

@WebServlet("/api/get-comment")
public class GetListCommentAPI extends HttpServlet {

	/**
	 * Created By Cao LT Created Date : 06/04/2021
	 * 
	 */
	private IBlocksService blocksService;
	private IAccountService accountService;
	private IPostService postService;
	private GenericService genericService;
	private ICommentService commentService;

	public GetListCommentAPI() {
		blocksService = new BlocksService();
		accountService = new AccountService();
		postService = new PostService();
		genericService = new BaseService();
		commentService = new CommentService();
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
//		GetListCommentRequest getListCommentRequest = gson.fromJson(request.getReader(), GetListCommentRequest.class);
		GetListCommentResponse listCommentResponse = new GetListCommentResponse();
		String jwt = request.getHeader(BaseHTTP.Authorization);
//		if (getListCommentRequest != null) {
		GetListCommentRequest getListCommentRequest = new GetListCommentRequest();
		String postIdQuery = request.getParameter("postId");
		String indexQuery = request.getParameter("index");
		String countQuery = request.getParameter("count");
		getListCommentRequest.setCount(Integer.parseInt(countQuery));
		getListCommentRequest.setIndex(Integer.parseInt(indexQuery));
		getListCommentRequest.setPostId(Long.valueOf(postIdQuery));
		Long postId = getListCommentRequest.getPostId();
		int index = getListCommentRequest.getIndex();
		int count = getListCommentRequest.getCount();
//			System.out.println("String index = " + String.valueOf(index));
//			System.out.println("String count = " + String.valueOf(count));
		if (postId == null || String.valueOf(index) == null || String.valueOf(count) == null) {
			// parameter not enough
			listCommentResponse.setList(null);
			listCommentResponse.setBlocked(false);
			listCommentResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
			listCommentResponse.setMessage(BaseHTTP.MESSAGE_1002);

		} else {
			if (postId.toString().length() > 0 && String.valueOf(index).length() > 0
				 && String.valueOf(count).length() > 0 && count >= 0 && index <= 0) {
				// check block -> not access
				PostModel postModel = postService.findById(postId);
				if (postModel != null) {
					Long authorPostId = postModel.getAccountId();
					AccountModel accountModel = accountService
							.findByPhoneNumber(genericService.getPhoneNumberFromToken(jwt));
					Long accountId = accountModel.getId();
					BlocksModel blocksModel = blocksService.findOne(authorPostId, accountId);
					if (blocksModel == null) {
						List<CommentModel> list = commentService.getListCommentByPostId(postId);
						List<DataGetCommentResponse> commentResponses = new ArrayList<DataGetCommentResponse>();
						if ((count + index) > list.size()) {
							// parameter value is invalid
							listCommentResponse.setList(null);
							listCommentResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
							listCommentResponse.setMessage(BaseHTTP.MESSAGE_1004);
						} else {
							for (int i = index; i < index + count; i++) {
								CommentModel commentModel = list.get(i);
								// lay thong tin ve author comment
								AccountModel model = accountService.findById(commentModel.getAccountId());
								PosterResponse posterResponse = new PosterResponse();
								posterResponse.setAvatar(model.getAvatar());
								posterResponse.setId(model.getId());
								posterResponse.setName(model.getName());
								DataGetCommentResponse commentResponse = new DataGetCommentResponse();
								// lay thong tin ve comment
								commentResponse.setContent(commentModel.getContent());
								commentResponse.setCreated(
										genericService.convertTimestampToSeconds(commentModel.getCreatedDate()));
								commentResponse.setId(commentModel.getId());
								commentResponse.setPosterResponse(posterResponse);
								commentResponses.add(commentResponse);
							}
							listCommentResponse.setList(commentResponses);
							listCommentResponse.setBlocked(false);
							listCommentResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
							listCommentResponse.setMessage(BaseHTTP.MESSAGE_1000);
						}
					} else {
						// not access
						listCommentResponse.setList(null);
						listCommentResponse.setBlocked(true);
						listCommentResponse.setCode(String.valueOf(BaseHTTP.CODE_1009));
						listCommentResponse.setMessage(BaseHTTP.MESSAGE_1009);
					}
				} else {
					// check bai da xoa -> post is not existed
					listCommentResponse.setList(null);
					listCommentResponse.setCode(String.valueOf(BaseHTTP.CODE_9992));
					listCommentResponse.setMessage(BaseHTTP.MESSAGE_9992);
				}

				
				
				
			} else {
				// parameter value is invalid
				listCommentResponse.setList(null);
				listCommentResponse.setBlocked(false);
				listCommentResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
				listCommentResponse.setMessage(BaseHTTP.MESSAGE_1004);
			}
		}

		response.getWriter().print(gson.toJson(listCommentResponse));
	}

}
