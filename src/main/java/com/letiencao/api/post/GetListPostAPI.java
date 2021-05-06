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
import com.letiencao.request.post.GetListPostRequest;
import com.letiencao.response.post.AuthorGetPostResponse;
import com.letiencao.response.post.DataGetPostReponse;
import com.letiencao.response.post.GetListPostResponse;
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

@WebServlet("/api/get-list-posts")
public class GetListPostAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GenericService genericService;
	private IPostService postService;
	private IAccountService accountService;
	private ILikesService likesService;
	private ICommentService commentService;
	private IBlocksService blocksService;
	private IFileService fileService;

	public GetListPostAPI() {
		genericService = new BaseService();
		postService = new PostService();
		accountService = new AccountService();
		likesService = new LikesService();
		commentService = new CommentService();
		blocksService = new BlocksService();
		fileService = new FileService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		GetListPostResponse getListPostResponse = new GetListPostResponse();
		GetListPostRequest getListPostRequest = new GetListPostRequest();
		String countQuery = request.getParameter("count");
		getListPostRequest.setCount(Integer.parseInt(countQuery));
//		try {
//			GetListPostRequest getListPostRequest = gson.fromJson(request.getReader(), GetListPostRequest.class);
		List<DataGetPostReponse> dataGetPostReponses = new ArrayList<DataGetPostReponse>();
		if (getListPostRequest != null) {
			if (String.valueOf(getListPostRequest.getCount()) != null) {
				if (String.valueOf(getListPostRequest.getCount()).length() > 0) {
					// tim tat ca cac bai viet cua ca nhan
					long index = GetListPostRequest.getIndex();
					int count = getListPostRequest.getCount();
					String jwt = request.getHeader(BaseHTTP.Authorization);
					AccountModel accountModel = accountService
							.findByPhoneNumber(genericService.getPhoneNumberFromToken(jwt));
					Long accountId = accountModel.getId();
					List<PostModel> list = new ArrayList<PostModel>();
					// Check userId Request existed
					if (getListPostRequest.getUserId() != null) {
						System.out.println("!= null");
						list = postService.findPostByAccountId(getListPostRequest.getUserId());
						System.out.println("Size = " + list.size());
					} else {
						System.out.println("== null");
						list = postService.findPostByAccountId(accountId);
					}
//						
					if (list.size() > 0) {
						if (count + index > list.size()) {
							count = (int) (list.size() - index);
						}
						for (int i = (int) index; i < count + index; i++) {
							PostModel postModel = list.get(i);
							Long postId = postModel.getId();
							DataGetPostReponse dataGetPostReponse = new DataGetPostReponse();
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
							List<FileModel> listFiles = fileService.findByPostId(postId);
							if (listFiles == null) {
								imageGetPostResponses = null;
								videoGetPostResponses = null;
							} else {
								System.out.println("size = " + fileService.findByPostId(postId).size());
								for (FileModel fileModel : listFiles) {
									if (fileModel.getContent().endsWith(".jpg")
											|| fileModel.getContent().endsWith(".svg")
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
							}
							// get author
							AuthorGetPostResponse authorGetPostResponse = new AuthorGetPostResponse();
							authorGetPostResponse.setId(postModel.getAccountId());
							authorGetPostResponse.setName(accountService.findById(postModel.getAccountId()).getName());
							authorGetPostResponse
									.setAvatar(accountService.findById(postModel.getAccountId()).getAvatar());
							dataGetPostReponse.setAuthorGetPostResponse(authorGetPostResponse);
							System.out.println("Size = " + list.size());
							System.out.println("IDDDD = " + list.get(i).getId());
							dataGetPostReponse.setListImage(imageGetPostResponses);
							dataGetPostReponse.setListVideo(videoGetPostResponses);
							// set created modified in long type
							dataGetPostReponse.setCreated(String
									.valueOf(genericService.convertTimestampToSeconds(postModel.getCreatedDate())));
							if (postModel.getModifiedDate() != null) {
								dataGetPostReponse.setModified(String.valueOf(
										genericService.convertTimestampToSeconds(postModel.getModifiedDate())));
							}
							dataGetPostReponses.add(dataGetPostReponse);
						}
						GetListPostRequest.setLastId(list.get((int) index + count - 1).getId());
						getListPostResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
						getListPostResponse.setMessage(BaseHTTP.MESSAGE_1000);
						System.out.println("COUNT == " + count);
						getListPostResponse.setLastId(GetListPostRequest.getLastId());
						GetListPostRequest.setIndex(count + GetListPostRequest.getIndex());
						getListPostResponse.setList(dataGetPostReponses);
						getListPostResponse.setNewItems(count);
					} else {
						getListPostResponse.setCode(String.valueOf(BaseHTTP.CODE_9995));
						getListPostResponse.setMessage(BaseHTTP.MESSAGE_9995);
						getListPostResponse.setList(null);
						getListPostResponse.setNewItems(0);
					}

				} else {
					getListPostResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
					getListPostResponse.setMessage(BaseHTTP.MESSAGE_1004);
					getListPostResponse.setLastId(-1L);
					getListPostResponse.setList(null);
					getListPostResponse.setNewItems(-1);
				}
			} else {
				getListPostResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
				getListPostResponse.setMessage(BaseHTTP.MESSAGE_1002);
				getListPostResponse.setLastId(-1L);
				getListPostResponse.setList(null);
				getListPostResponse.setNewItems(-1);
			}
//			} else {
//				getListPostResponse.setCode(String.valueOf(BaseHTTP.CODE_9994));
//				getListPostResponse.setMessage(BaseHTTP.MESSAGE_9994);
//				getListPostResponse.setLastId(-1L);
//				getListPostResponse.setList(null);
//				getListPostResponse.setNewItems(-1);
//			}
			response.getWriter().print(gson.toJson(getListPostResponse));
//		} catch (NumberFormatException | JsonSyntaxException e) {
//			getListPostResponse.setCode(String.valueOf(BaseHTTP.CODE_1003));
//			getListPostResponse.setMessage(BaseHTTP.MESSAGE_1003);
//			getListPostResponse.setLastId(-1L);
//			getListPostResponse.setList(null);
//			getListPostResponse.setNewItems(-1);
//			response.getWriter().print(gson.toJson(getListPostResponse));
		}

	}

}
