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
import com.letiencao.model.PostModel;
import com.letiencao.request.post.CheckNewItemRequest;
import com.letiencao.response.post.CheckNewItemResponse;
import com.letiencao.service.IPostService;
import com.letiencao.service.impl.PostService;

@WebServlet("/api/check-new-item")
public class CheckNewItemAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IPostService postService;

	public CheckNewItemAPI() {
		postService = new PostService();
	}

	@SuppressWarnings("unused")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		List<Long> models = new ArrayList<Long>(); // list data new items
//		CheckNewItemRequest checkNewItemRequest = gson.fromJson(request.getReader(), CheckNewItemRequest.class);
		CheckNewItemResponse checkNewItemResponse = new CheckNewItemResponse();
		CheckNewItemRequest checkNewItemRequest = new CheckNewItemRequest();
		String last_idQuery = request.getParameter("lastId");
		try {
			if (last_idQuery != null) {
				checkNewItemRequest.setLastId(Long.valueOf(last_idQuery));
//				if (checkNewItemRequest != null) {
				Long last_id = checkNewItemRequest.getLastId();
				if (String.valueOf(checkNewItemRequest.getCategoryId()) == null) {
					checkNewItemRequest.setCategoryId(0);
				}
				if (last_id.toString().length() > 0) {
					// Check last_id
					PostModel postModel = postService.findPostById(last_id);
					if (postModel != null) {
						// get all post in DB
						List<PostModel> list = postService.findAll();
						if (list != null) {
							if (last_id <= list.get(list.size() - 1).getId()) {
								for (int i = 0; i < list.size(); i++) {
									Long id = list.get(i).getId();
									if (id <= last_id) {
										continue;
									} else {
										models.add(id);
									}
								}
								checkNewItemResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
								checkNewItemResponse.setMessage(BaseHTTP.MESSAGE_1000);
								checkNewItemResponse.setData(models);
							} else {
								checkNewItemResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
								checkNewItemResponse.setMessage(BaseHTTP.MESSAGE_9999);
								checkNewItemResponse.setData(null);
							}

						} else {
							// exception
							// all posts is deleted
							checkNewItemResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
							checkNewItemResponse.setMessage(BaseHTTP.MESSAGE_9999);
							checkNewItemResponse.setData(null);
						}
					} else {
						// post is not existed
						checkNewItemResponse.setCode(String.valueOf(BaseHTTP.CODE_9992));
						checkNewItemResponse.setMessage(BaseHTTP.MESSAGE_9992);
						checkNewItemResponse.setData(null);
					}

				} else {
					// value invalid
					checkNewItemResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
					checkNewItemResponse.setMessage(BaseHTTP.MESSAGE_1004);
					checkNewItemResponse.setData(null);
				}
			} else {
				// parameter not enough
				checkNewItemResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
				checkNewItemResponse.setMessage(BaseHTTP.MESSAGE_1002);
				checkNewItemResponse.setData(null);
			}

		} catch (NumberFormatException | JsonSyntaxException e) {
			checkNewItemResponse.setCode(String.valueOf(BaseHTTP.CODE_1003));
			checkNewItemResponse.setMessage(BaseHTTP.MESSAGE_1003);
			checkNewItemResponse.setData(null);
		}
//		} else {
//			// empty body
//			checkNewItemResponse.setCode(String.valueOf(BaseHTTP.CODE_9994));
//			checkNewItemResponse.setMessage(BaseHTTP.MESSAGE_9994);
//			checkNewItemResponse.setData(null);
//		}
		response.getWriter().print(gson.toJson(checkNewItemResponse));
	}

}
