package com.letiencao.api.comment;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
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

@WebServlet("/api/comment")
public class CommentAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ICommentService commentService;
	private IAccountService accountService;
	private GenericService genericService;
	private IBlocksService blocksService;
	private IPostService postService;

	public CommentAPI() {
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
		AddCommentRequest addCommentRequest = gson.fromJson(request.getReader(), AddCommentRequest.class);
		GetCommentResponse commentResponse = new GetCommentResponse();
		if (addCommentRequest != null) {
			String jwt = request.getHeader("Authorization");
			AccountModel accountModel = accountService.findByPhoneNumber((genericService.getPhoneNumberFromToken(jwt)));
			addCommentRequest.setAccountId(accountModel.getId());
			// get account model thong qua account id,get comment model,check blocks
			// set count ,index
			List<CommentModel> commentModels = commentService.findAll();
			if (commentModels.size() == 0) {
				addCommentRequest.setIndex(0L);
				addCommentRequest.setCount(commentModels.size());
			} else {
				addCommentRequest.setIndex(commentModels.get(0).getId());
				addCommentRequest.setCount(commentModels.size());
			}
			//
			Long postId = addCommentRequest.getPostId();
			String content = addCommentRequest.getContent();
			Long accountId = addCommentRequest.getAccountId();

			if (postId == null || content == null || accountId == null) {
				commentResponse.setCode(1002);
				commentResponse.setMessage("Parameter is not enough");
			} else {
				if (postId.toString().length() == 0 || content.length() == 0 || accountId.toString().length() == 0) {
					commentResponse.setCode(1004);
					commentResponse.setMessage("Parameter value is invalid");
				} else {
					// Check block
					try {
						PostModel postModel = postService.findById(postId);
						BlocksModel blocksModel = blocksService.findOne(postModel.getAccountId(), accountModel.getId());
						if (blocksModel == null) {
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
							dataGetCommentResponse.setCreated(1000L);
							commentResponse.setCode(1000);
							commentResponse.setMessage("OK");
							commentResponse.setDataGetCommentResponse(dataGetCommentResponse);
							commentResponse.setIs_blocked(false);
						} else {
							commentResponse.setDataGetCommentResponse(null);
							commentResponse.setIs_blocked(true);
							commentResponse.setCode(1009);
							commentResponse.setMessage("Not Access");
						}
					} catch (NullPointerException e) {
						// bai viet da bi xoa
						commentResponse.setCode(9992);
						commentResponse.setMessage("Post is not existed");
					}

				}
			}
		} else {
			commentResponse.setCode(9994);
			commentResponse.setMessage("No data or end of list data");
		}
		response.getWriter().print(gson.toJson(commentResponse));

	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		DeleteCommentRequest deleteCommentRequest = gson.fromJson(request.getReader(), DeleteCommentRequest.class);
		BaseResponse baseResponse = new BaseResponse();
		String jwt = request.getHeader("Authorization");
		if (deleteCommentRequest != null) {
			Long commentId = deleteCommentRequest.getCommentId();
			Long postId = deleteCommentRequest.getPostId();
			if (commentId == null || postId == null) {
				baseResponse.setCode(1002);
				baseResponse.setMessage("Parameter is not enough");
			} else {
				if (commentId.toString().length() == 0 || postId.toString().length() == 0) {
					baseResponse.setCode(1004);
					baseResponse.setMessage("Parameter value is invalid");
				} else {

					// Check ton tai bai viet
					AccountModel accountModel = accountService
							.findByPhoneNumber(genericService.getPhoneNumberFromToken(jwt));
					PostModel postModel = postService.findById(postId);
					if (postModel != null) {
						// Check quyen truy cap bai viet
						BlocksModel blocksModel = blocksService.findOne(postModel.getAccountId(), accountModel.getId());
						if (blocksModel == null) {
							// Check ton tai comment
							CommentModel commentModel = commentService.findById(commentId);
							if (commentModel.getPostId() == postId) {
								// Check quyen tac gia comment
								System.out.println("Account Id = " + postModel.getAccountId());
								System.out.println("Id Account = " + accountModel.getId());
								if (commentModel.getAccountId() == accountModel.getId()
										|| postModel.getAccountId() == accountModel.getId()) {
									commentService.deleteComment(postId, commentId);
									baseResponse.setCode(1000);
									baseResponse.setMessage("OK");
								} else {
									baseResponse.setCode(1009);
									baseResponse.setMessage("Not Access");
								}
							} else {
								baseResponse.setCode(9999);
								baseResponse.setMessage("Exception Error");
							}
						} else {
							baseResponse.setCode(1009);
							baseResponse.setMessage("Not Access");
						}
					} else {
						baseResponse.setCode(9992);
						baseResponse.setMessage("Post is not existed");
					}
				}
			}
		} else {
			baseResponse.setCode(9994);
			baseResponse.setMessage("No data or end of list data");
		}
		response.getWriter().print(gson.toJson(baseResponse));

	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		EditCommentRequest editCommentRequest = gson.fromJson(request.getReader(), EditCommentRequest.class);
		BaseResponse baseResponse = new BaseResponse();
		String jwt = request.getHeader("Authorization");
		if (editCommentRequest != null) {
			Long commentId = editCommentRequest.getCommentId();
			Long postId = editCommentRequest.getPostId();
			String contentUpdate = editCommentRequest.getContentUpdate();
			if (commentId == null || postId == null || contentUpdate == null) {
				baseResponse.setCode(1002);
				baseResponse.setMessage("Parameter is not enough");
			} else {
				if (commentId.toString().length() == 0 || contentUpdate.length() == 0
						|| postId.toString().length() == 0) {
					baseResponse.setCode(1004);
					baseResponse.setMessage("Parameter value is invalid");
				} else {

					// Check ton tai bai viet
					AccountModel accountModel = accountService
							.findByPhoneNumber(genericService.getPhoneNumberFromToken(jwt));
					PostModel postModel = postService.findById(postId);
					if (postModel != null) {
						// Check quyen truy cap bai viet
						BlocksModel blocksModel = blocksService.findOne(postModel.getAccountId(), accountModel.getId());
						if (blocksModel == null) {
							// Check ton tai comment
							CommentModel commentModel = commentService.findById(commentId);
							if (commentModel.getPostId() == postId) {
								// Check quyen tac gia comment
								System.out.println("Account Id = " + postModel.getAccountId());
								System.out.println("Id Account = " + accountModel.getId());
								if (commentModel.getAccountId() == accountModel.getId()) {
									commentService.update(commentId, contentUpdate);
									baseResponse.setCode(1000);
									baseResponse.setMessage("OK");
								} else {
									baseResponse.setCode(1009);
									baseResponse.setMessage("Not Access");
								}
							} else {
								baseResponse.setCode(9999);
								baseResponse.setMessage("Exception Error");
							}
						} else {
							baseResponse.setCode(1009);
							baseResponse.setMessage("Not Access");
						}
					} else {
						baseResponse.setCode(9992);
						baseResponse.setMessage("Post is not existed");
					}
				}
			}
		} else {
			baseResponse.setCode(9994);
			baseResponse.setMessage("No data or end of list data");
		}
		response.getWriter().print(gson.toJson(baseResponse));

	}

}
