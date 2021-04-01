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

	@SuppressWarnings("static-access")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		GetListPostResponse getListPostResponse = new GetListPostResponse();
		try {
			GetListPostRequest getListPostRequest = gson.fromJson(request.getReader(), GetListPostRequest.class);
			List<DataGetPostReponse> dataGetPostReponses = new ArrayList<DataGetPostReponse>();
			if (getListPostRequest != null) {
				if (String.valueOf(getListPostRequest.getCount()) == null) {
					getListPostResponse.setCode(1002);
					getListPostResponse.setMessage("Parameter is not enough");
					getListPostResponse.setLast_id(-1L);
					getListPostResponse.setList(null);
					getListPostResponse.setNew_items(-1);
				} else if (String.valueOf(getListPostRequest.getCount()).length() == 0) {
					getListPostResponse.setCode(1004);
					getListPostResponse.setMessage("Parameter value is invalid");
					getListPostResponse.setLast_id(-1L);
					getListPostResponse.setList(null);
					getListPostResponse.setNew_items(-1);
				} else {
					// tim tat ca cac bai viet cua ca nhan
					long index = GetListPostRequest.getIndex();
					int count = getListPostRequest.getCount();
					String jwt = request.getHeader(BaseHTTP.Authorization);
					AccountModel accountModel = accountService
							.findByPhoneNumber(genericService.getPhoneNumberFromToken(jwt));
					Long accountId = accountModel.getId();
					List<PostModel> list = postService.findPostByAccountId(accountId);
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
						List<FileModel> listFiles = fileService.findByPostId(postId);
						if (listFiles == null) {
							imageGetPostResponses = null;
							videoGetPostResponses = null;
						} else {
							System.out.println("size = " + fileService.findByPostId(postId).size());
							for (FileModel fileModel : listFiles) {
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
						}
						// get author
						AuthorGetPostResponse authorGetPostResponse = new AuthorGetPostResponse();
						authorGetPostResponse.setId(postModel.getAccountId());
						authorGetPostResponse.setName(accountService.findById(postModel.getAccountId()).getName());
						authorGetPostResponse.setAvatar(accountService.findById(postModel.getAccountId()).getAvatar());
						dataGetPostReponse.setAuthorGetPostResponse(authorGetPostResponse);
						System.out.println("Size = " + list.size());
						System.out.println("IDDDD = " + list.get(i).getId());
						dataGetPostReponse.setListImage(imageGetPostResponses);
						dataGetPostReponse.setListVideo(videoGetPostResponses);
						dataGetPostReponses.add(dataGetPostReponse);
					}
					GetListPostRequest.setLast_id(list.get((int) index + count - 1).getId());
					getListPostResponse.setCode(1000);
					getListPostResponse.setMessage("OK");
					getListPostResponse.setLast_id(GetListPostRequest.getLast_id());
					GetListPostRequest.setIndex(count + GetListPostRequest.getIndex());
					getListPostResponse.setList(dataGetPostReponses);
					getListPostResponse.setNew_items(count);

				}
			} else {
				getListPostResponse.setCode(9994);
				getListPostResponse.setMessage("No data or end of list data");
				getListPostResponse.setLast_id(-1L);
				getListPostResponse.setList(null);
				getListPostResponse.setNew_items(-1);
			}
			response.getWriter().print(gson.toJson(getListPostResponse));
		} catch (NumberFormatException | JsonSyntaxException e) {
			getListPostResponse.setCode(1003);
			getListPostResponse.setMessage("Parameter value is invalid");
			getListPostResponse.setLast_id(-1L);
			getListPostResponse.setList(null);
			getListPostResponse.setNew_items(-1);
			response.getWriter().print(gson.toJson(getListPostResponse));
		}

	}

}
