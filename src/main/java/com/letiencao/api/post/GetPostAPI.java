package com.letiencao.api.post;

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
import com.letiencao.model.FileModel;
import com.letiencao.model.PostModel;
import com.letiencao.request.post.GetPostRequest;
import com.letiencao.response.BaseResponse;
import com.letiencao.response.post.AuthorGetPostResponse;
import com.letiencao.response.post.DataGetPostReponse;
import com.letiencao.response.post.GetPostResponse;
import com.letiencao.response.post.ImageGetPostResponse;
import com.letiencao.response.post.VideoGetPostResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IBlocksService;
import com.letiencao.service.ICommentService;
import com.letiencao.service.IFileService;
import com.letiencao.service.ILikesService;
import com.letiencao.service.IPostService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.BlocksService;
import com.letiencao.service.impl.CommentService;
import com.letiencao.service.impl.FileService;
import com.letiencao.service.impl.LikesService;
import com.letiencao.service.impl.PostService;

@WebServlet("/api/get-post")
public class GetPostAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IPostService postService;
	private ILikesService likesService;
	private ICommentService commentService;
	private GenericService genericService;
	private IAccountService accountService;
	private IBlocksService blocksService;
	private IFileService fileService;

	public GetPostAPI() {
		postService = new PostService();
		likesService = new LikesService();
		commentService = new CommentService();
		genericService = new BaseService();
		accountService = new AccountService();
		blocksService = new BlocksService();
		fileService = new FileService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		Gson gson = new Gson();
		BaseResponse baseResponse = new BaseResponse();
		try {
			String idStr = request.getParameter("id");
			if (idStr == null) {
				baseResponse.setCode(9994);
				baseResponse.setMessage("No data or end of list data");
			} else {
				Long id = Long.valueOf(idStr);
				System.out.println("id = " + id);
				PostModel model = postService.findPostById(id);
				if (model == null) {
					baseResponse.setCode(9995);
					baseResponse.setMessage("User is not validated");
					response.getWriter().print(gson.toJson(baseResponse));
					return;
				} else {
					response.getWriter().print(gson.toJson(model));
					return;
				}

			}
		} catch (NumberFormatException e) {
			baseResponse.setCode(1003);
			baseResponse.setMessage("Parameter type is invalid");
		}
		response.getWriter().print(gson.toJson(baseResponse));

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		DataGetPostReponse dataGetPostReponse = new DataGetPostReponse();
		GetPostResponse getPostResponse = new GetPostResponse();
		try {
			GetPostRequest getPostRequest = gson.fromJson(request.getReader(), GetPostRequest.class);
			if (getPostRequest == null) {
				getPostResponse.setCode(9994);
				getPostResponse.setDataGetPostReponse(null);
				getPostResponse.setMessage("No data or end of list data");
				response.getWriter().print(gson.toJson(getPostResponse));
				return;
			} else {
				// get token
				String jwt = request.getHeader(BaseHTTP.Authorization);
				Long postId = getPostRequest.getId();
				if (postId == null) {
					getPostResponse.setCode(1002);
					getPostResponse.setDataGetPostReponse(null);
					getPostResponse.setMessage("Parameter is not enough");
					response.getWriter().print(gson.toJson(getPostResponse));
					return;
				} else {
					dataGetPostReponse.setId(postId);
					// search post by id
//					PostModel postModel = postService.findPostById(postId);// get author
					PostModel postModel = new PostModel();
					if (postService.findPostById(postId).getId() == null) {
						postModel = postService.findById(postId);
					} else {
						postModel = postService.findPostById(postId);
					}
					System.out.println("postModel = " + postModel.getId());
					dataGetPostReponse.setDescribed(postModel.getContent());
					dataGetPostReponse.setCreated(String.valueOf(postModel.getCreatedDate()));
					dataGetPostReponse.setModified(String.valueOf(postModel.getModifiedDate()));
					// amount of like
					int like = likesService.findByPostId(postId);
					dataGetPostReponse.setLike(like);
					// amount of comment
					int comment = commentService.findByPostId(postId);
					dataGetPostReponse.setComment(comment);
					// check this user liked this post
					String phoneNumber = genericService.getPhoneNumberFromToken(jwt);
					AccountModel accountModel = accountService.findByPhoneNumber(phoneNumber);// jwt
					Long accountId = accountModel.getId();
					boolean checkThisUserLiked = likesService.checkThisUserLiked(accountId, postId);
					dataGetPostReponse.setIs_liked(checkThisUserLiked);
					// is_blocked
					BlocksModel blocksModel = blocksService.findOne(postModel.getAccountId(), accountId);
					if (blocksModel == null) {
						dataGetPostReponse.setIs_blocked("UnBlocked");
					} else {
						dataGetPostReponse.setIs_blocked("Blocked");
					}
					// can_edit
					if (accountId == postModel.getAccountId()) {
						dataGetPostReponse.setCan_edit("Can Edit");
					} else {
						dataGetPostReponse.setCan_edit("Can't Edit");
					}
					if (dataGetPostReponse.getIs_blocked().equalsIgnoreCase("Blocked")) {
						dataGetPostReponse.setCan_comment("Can't Comment");
					} else {
						dataGetPostReponse.setCan_comment("Can Comment");
					}
					// get file
					List<ImageGetPostResponse> imageGetPostResponses = new ArrayList<ImageGetPostResponse>();
					List<VideoGetPostResponse> videoGetPostResponses = new ArrayList<VideoGetPostResponse>();
					if (fileService.findByPostId(postId) != null) {
						List<FileModel> list = fileService.findByPostId(postId);
						System.out.println("size = " + fileService.findByPostId(postId).size());
						for (FileModel fileModel : list) {
							if (fileModel.getContent().endsWith(".jpg") || fileModel.getContent().endsWith(".svg")
									|| fileModel.getContent().endsWith(".JPEG")
									|| fileModel.getContent().endsWith(".png")) {
								ImageGetPostResponse imageGetPostResponse = new ImageGetPostResponse();
								imageGetPostResponse.setId(fileModel.getId());
								imageGetPostResponse.setUrl(fileModel.getContent());
								imageGetPostResponses.add(imageGetPostResponse);
							} else if (fileModel.getContent().endsWith(".mp4")) {
								VideoGetPostResponse videoGetPostResponse = new VideoGetPostResponse();
								videoGetPostResponse.setId(fileModel.getId());
								videoGetPostResponse.setUrl(fileModel.getContent());
								videoGetPostResponses.add(videoGetPostResponse);
							}
						}
					} else {
						imageGetPostResponses = null;
						videoGetPostResponses = null;
					}
					// get author
					AuthorGetPostResponse authorGetPostResponse = new AuthorGetPostResponse();
					authorGetPostResponse.setId(postModel.getAccountId());
					authorGetPostResponse.setName(accountService.findById(postModel.getAccountId()).getName());
					authorGetPostResponse.setAvatar(accountService.findById(postModel.getAccountId()).getAvatar());
					dataGetPostReponse.setAuthorGetPostResponse(authorGetPostResponse);
					dataGetPostReponse.setListImage(imageGetPostResponses);
					dataGetPostReponse.setListVideo(videoGetPostResponses);
					getPostResponse.setDataGetPostReponse(dataGetPostReponse);
					getPostResponse.setCode(1000);
					getPostResponse.setMessage("OK");
				}
			}
		} catch (NumberFormatException | JsonSyntaxException e) {
			getPostResponse.setCode(1003);
			getPostResponse.setMessage("Parameter type is invalid");
			getPostResponse.setDataGetPostReponse(null);
		} catch (NullPointerException e) {
			getPostResponse.setCode(9992);
			getPostResponse.setMessage("Post is not existed");
			getPostResponse.setDataGetPostReponse(null);
		}
		response.getWriter().print(gson.toJson(getPostResponse));
	}

}
