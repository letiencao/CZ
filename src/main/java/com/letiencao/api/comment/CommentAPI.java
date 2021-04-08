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

@WebServlet("/api/comment")
public class CommentAPI extends HttpServlet {

	/*************************
	 * Created By Cao LT
	 * Created Date 31/03/2021
	 * 
	 *////////////////////////
	
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
		// Request = ..............
		if (addCommentRequest != null) {
			//get AccountId From token
			String jwt = request.getHeader(BaseHTTP.Authorization);
			AccountModel accountModel = accountService.findByPhoneNumber((genericService.getPhoneNumberFromToken(jwt)));
			// set accountId For Request
			addCommentRequest.setAccountId(accountModel.getId());
			List<CommentModel> commentModels = commentService.findAll();
			//set index and count for Request
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
				commentResponse.setCode(1002);
				commentResponse.setMessage("Parameter is not enough");
			} else {
				if (postId.toString().length() == 0 || content.length() == 0 || accountId.toString().length() == 0) {
					commentResponse.setCode(1004);
					commentResponse.setMessage("Parameter value is invalid");
				} else {
					try {
						PostModel postModel = postService.findById(postId);
						//Check Block
						BlocksModel blocksModel = blocksService.findOne(postModel.getAccountId(), accountModel.getId());
						if (blocksModel == null) {
							//insert Comment
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
							//Convert Date to seconds
							dataGetCommentResponse.setCreated(genericService.convertTimestampToSeconds(commentModel.getCreatedDate()));
							commentResponse.setCode(1000);
							commentResponse.setMessage("OK");
							commentResponse.setDataGetCommentResponse(dataGetCommentResponse);
							commentResponse.setBlocked(false);
						} else {
							// This User was blocked by The Author
							commentResponse.setDataGetCommentResponse(null);
							commentResponse.setBlocked(true);
							commentResponse.setCode(1009);
							commentResponse.setMessage("Not Access");
						}
					} catch (NullPointerException e) {
						// post deleted or not existed
						commentResponse.setCode(9992);
						commentResponse.setMessage("Post is not existed");
					}

				}
			}
		} else {
			//Request ..............
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
		
		if (deleteCommentRequest != null) {
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
					//Check post existed
					if (postModel != null) {
						//Check Block
						BlocksModel blocksModel = blocksService.findOne(postModel.getAccountId(), accountModel.getId());
						if (blocksModel == null) {
							// Check comment existed
							CommentModel commentModel = commentService.findById(commentId);
							if (commentModel != null) {
								// check comment from commentId of post from postId
								if (commentModel.getPostId() == postId) {
									// Check author
									if (commentModel.getAccountId() == accountModel.getId()
											|| postModel.getAccountId() == accountModel.getId()) {
										commentService.deleteComment(postId, commentId);
										ok(baseResponse);
									} else {
										//Not author
										notAccess(baseResponse);
									}
								} else {
									//Comment existed but this post does not have
									System.out.println("11111111111111111");
									exceptionError(baseResponse);
								}
							} else {
								//Comment not existed
								System.out.println("222222222222222");
								exceptionError(baseResponse);
							}
						} else {
							//Blocked
							notAccess(baseResponse);
						}
					} else {
						postNotExisted(baseResponse);
					}
				}
			}
		} else {
			noData(baseResponse);
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
				parameterNotEnough(baseResponse);
			} else {
				if (commentId.toString().length() == 0 || contentUpdate.length() == 0
						|| postId.toString().length() == 0) {
					valueInValid(baseResponse);
				} else {

					
					AccountModel accountModel = accountService
							.findByPhoneNumber(genericService.getPhoneNumberFromToken(jwt));
					PostModel postModel = postService.findById(postId);
					//Check post existed
					if (postModel != null) {
						//Check Block
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
										//Not author
										notAccess(baseResponse);
									}
								} else {
									//Comment existed but this post does not have
									exceptionError(baseResponse);
								}

							} else {
								//Comment not existed
								exceptionError(baseResponse);
							}
						} else {
							//Blocked
							notAccess(baseResponse);
						}
					} else {
						postNotExisted(baseResponse);
					}
				}
			}
		} else {
			noData(baseResponse);
		}
		response.getWriter().print(gson.toJson(baseResponse));

	}
	private void ok(BaseResponse baseResponse) {
		baseResponse.setCode(1000);
		baseResponse.setMessage("OK");
	}
	private void noData(BaseResponse baseResponse) {
		baseResponse.setCode(9994);
		baseResponse.setMessage("No data or end of list data");
	}
	private void postNotExisted(BaseResponse baseResponse) {
		baseResponse.setCode(9992);
		baseResponse.setMessage("Post is not existed");
	}
	private void notAccess(BaseResponse baseResponse) {
		baseResponse.setCode(1009);
		baseResponse.setMessage("Not Access");
	}
	private void exceptionError(BaseResponse baseResponse) {
		baseResponse.setCode(9999);
		baseResponse.setMessage("Exception Error");
	}
	private void valueInValid(BaseResponse baseResponse) {
		baseResponse.setCode(1004);
		baseResponse.setMessage("Parameter value is invalid");
	}
	private void parameterNotEnough(BaseResponse baseResponse) {
		baseResponse.setCode(1002);
		baseResponse.setMessage("Parameter is not enough");
	}
	

}
