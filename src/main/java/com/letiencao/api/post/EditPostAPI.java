package com.letiencao.api.post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.letiencao.api.BaseHTTP;
import com.letiencao.model.AccountModel;
import com.letiencao.model.PostModel;
import com.letiencao.request.post.EditPostRequest;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IPostService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.PostService;

@WebServlet("/api/edit-post")
public class EditPostAPI extends HttpServlet{

	/**
	 * 
	 */
	private IPostService postService;
	private IAccountService accountService;
	private GenericService genericService;
	public EditPostAPI() {
		postService = new PostService();
		accountService = new AccountService();
		genericService = new BaseService();
	}
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		List<String> listImageDel = new ArrayList<String>();
		ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
		try {
			List<FileItem> list = servletFileUpload.parseRequest(request);
			for(FileItem fileItem : list) {
				if(fileItem.getFieldName().equalsIgnoreCase("id")) {
					System.out.println("ID = "+fileItem.getString());
				}
				else if(fileItem.getFieldName().equalsIgnoreCase("image")) {
					System.out.println("Image File : "+fileItem.getName());	
				}else if(fileItem.getFieldName().equalsIgnoreCase("described")) {
					System.out.println("Described : "+fileItem.getString());
					
				}else if(fileItem.getFieldName().equalsIgnoreCase("image_del")) {
					// check so luong khong dc hon 4
					System.out.println("Image_del : "+fileItem.getString());
					
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		EditPostRequest editPostRequest = gson.fromJson(request.getReader(), EditPostRequest.class);
//		if(editPostRequest != null) {
//			Long id = editPostRequest.getId();
//			if(id.toString() != null) {
//				if(id.toString().length() > 0) {
//					//Find post by id
//					PostModel postModel = postService.findPostById(id);
//					if(postModel != null) {
//						//Is the account(token) an author of this post ? 
//						String jwt = request.getHeader(BaseHTTP.Authorization);
//						String phoneNumber = genericService.getPhoneNumberFromToken(jwt);
//						AccountModel accountModel = accountService.findByPhoneNumber(phoneNumber);
//						if(postModel.getAccountId() == accountModel.getId()) {
//							
//						}else {
//							//not access
//						}
//						
//					}else {
//						//Post is not existed
//					}
//				}else {
//					//value invalid
//				}
//			}else {
//				//parameter not enough
//			}
//		}else {
//			// no data
//		}
	}

}
