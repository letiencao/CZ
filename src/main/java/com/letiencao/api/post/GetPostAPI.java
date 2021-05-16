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
			if (idStr != null) {
				Long id = Long.valueOf(idStr);
				PostModel model = postService.findPostById(id);
				if (model != null) {
					response.getWriter().print(gson.toJson(model));
					return;
				} else {
					baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9995));
					baseResponse.setMessage(BaseHTTP.MESSAGE_9995);
					response.getWriter().print(gson.toJson(baseResponse));
					return;
				}
			} else {
				baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9994));
				baseResponse.setMessage(BaseHTTP.MESSAGE_9994);
			}
		} catch (NumberFormatException e) {
			baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1003));
			baseResponse.setMessage(BaseHTTP.MESSAGE_1003);
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
//		try {
//			GetPostRequest getPostRequest = gson.fromJson(request.getReader(), GetPostRequest.class);
//			if (getPostRequest != null) {
		GetPostRequest getPostRequest = new GetPostRequest();
		String idQuery = request.getParameter("id");
		
		// get token
		String jwt = request.getHeader(BaseHTTP.Authorization);
		
		if (idQuery != null) {
			getPostRequest.setId(Long.valueOf(idQuery));
			Long postId = getPostRequest.getId();
			if (postId > 0) {
				dataGetPostReponse.setId(postId);
				// search post by id
//						PostModel postModel = postService.findPostById(postId);// get author
				PostModel postModel = postService.findPostById(postId);
				if (postModel == null) {
					System.out.println(1);
					postModel = postService.findById(postId);
				} else {
					System.out.println(2);
					postModel = postService.findPostById(postId);
				}
				if (postModel != null) {
					System.out.println("postModel = " + postModel);
//					PostModel postModel = postService.findPostById(postId);
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
					dataGetPostReponse.setLiked(checkThisUserLiked);
					// is_blocked
					BlocksModel blocksModel = blocksService.findOne(postModel.getAccountId(), accountId);
					if (blocksModel == null) {
						dataGetPostReponse.setIsBlocked("UnBlocked");
					} else {
						dataGetPostReponse.setIsBlocked("Blocked");
					}
					// can_edit
					if (accountId == postModel.getAccountId()) {
						dataGetPostReponse.setCanEdit("Can Edit");
					} else {
						dataGetPostReponse.setCanEdit("Can't Edit");
					}
					if (dataGetPostReponse.getIsBlocked().equalsIgnoreCase("Blocked")) {
						dataGetPostReponse.setCanComment("Can't Comment");
					} else {
						dataGetPostReponse.setCanComment("Can Comment");
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
					// set created modified in long type
					dataGetPostReponse.setCreated(
							String.valueOf(genericService.convertTimestampToSeconds(postModel.getCreatedDate())));
					if (postModel.getModifiedDate() != null) {
						dataGetPostReponse.setModified(
								String.valueOf(genericService.convertTimestampToSeconds(postModel.getModifiedDate())));
					}
					getPostResponse.setDataGetPostReponse(dataGetPostReponse);
					getPostResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
					getPostResponse.setMessage(BaseHTTP.MESSAGE_1000);
				} else {
					getPostResponse.setCode(String.valueOf(BaseHTTP.CODE_9992));
					getPostResponse.setDataGetPostReponse(null);
					getPostResponse.setMessage(BaseHTTP.MESSAGE_9992);
					response.getWriter().print(gson.toJson(getPostResponse));
					return;
				}
			} else {
				getPostResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
				getPostResponse.setDataGetPostReponse(null);
				getPostResponse.setMessage(BaseHTTP.MESSAGE_1004);
			}

		} else {
			getPostResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
			getPostResponse.setDataGetPostReponse(null);
			getPostResponse.setMessage(BaseHTTP.MESSAGE_1002);
			response.getWriter().print(gson.toJson(getPostResponse));
			return;
		}
//			} else {
//				getPostResponse.setCode(String.valueOf(BaseHTTP.CODE_9994));
//				getPostResponse.setDataGetPostReponse(null);
//				getPostResponse.setMessage(BaseHTTP.MESSAGE_9994);
//				response.getWriter().print(gson.toJson(getPostResponse));
//				return;
//			}
//		} catch (NumberFormatException | JsonSyntaxException e) {
//			getPostResponse.setCode(String.valueOf(BaseHTTP.CODE_1003));
//			getPostResponse.setMessage(BaseHTTP.MESSAGE_1003);
//			getPostResponse.setDataGetPostReponse(null);
//		} catch (NullPointerException e) {
//			getPostResponse.setCode(String.valueOf(BaseHTTP.CODE_9992));
//			getPostResponse.setMessage(BaseHTTP.MESSAGE_9992);
//			getPostResponse.setDataGetPostReponse(null);
//		}
		response.getWriter().print(gson.toJson(getPostResponse));
	}

}
