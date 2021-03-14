package com.letiencao.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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
import com.letiencao.model.FileModel;
import com.letiencao.request.AddPostRequest;
import com.letiencao.response.AddPostResponse;
import com.letiencao.response.DataPostResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IFileService;
import com.letiencao.service.IPostService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.FileService;
import com.letiencao.service.impl.PostService;

@WebServlet("/api/add_post")
//@MultipartConfig
public class PostAPI extends HttpServlet {

	/**
	 * 
	 */
	private IPostService postService;
	private IFileService fileService;
	private GenericService genericService;

	public PostAPI() {
		postService = new PostService();
		fileService = new FileService();
		genericService = new BaseService();
	}

	public static final int MAX_REQUEST_FILE = 1024 * 1024 * 50; // 50MB
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setCharacterEncoding("multipart/form-data");
		response.setContentType("application/json");
		AddPostResponse addPostResponse = new AddPostResponse();
		String token = request.getHeader("Authorization");
		String describedRequest = null;
		List<String> files = new ArrayList<String>();
		Gson gson = new Gson();
		try {
			// get path upload folder
			String root = uploadFolder();
			File file = new File(root);
			if (file.exists() == false) {
				file.mkdirs();
			}
			ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> list;
			list = servletFileUpload.parseRequest(request);
//				System.out.println("List size = " + list.size());
//				System.out.println(list.get(1).getString());
			boolean image = false, video = false;
			// co file image thi image = true
			// co file video thi video = true
			int listFilesSize = 0;
			List<FileItem> items = new ArrayList<FileItem>();
			for (FileItem item : list) {
				// request co chua File thi getName != null,con khong thi getName == null
				if (item.getName() != null) {
					if (item.getName().endsWith(".mp4")) {
						if (image == true) {
							continue;
						} else {
							video = true;
							listFilesSize += item.getSize();
						}
					} else if (item.getName().endsWith(".jpg") || item.getName().endsWith(".svg")
							|| item.getName().endsWith(".JPEG")) {
						if (video == true) {
							continue;
						} else {
							image = true;
							listFilesSize += item.getSize();
						}
					}
				} else {
					// get text trong request
					if (item.getFieldName().equalsIgnoreCase("described")) {
						String described = item.getString();
						if (described.length() > 0 && described.length() <= 10000) {
							describedRequest = described;
						} else {
							addPostResponse.setCode(1004);
							addPostResponse.setDataPostResponse(null);
							addPostResponse.setMessage("Parameter value is invalid");
							response.getWriter().print(gson.toJson(addPostResponse));
							return;
						}
					} else {
						// no File no text
						addPostResponse.setCode(1002);
						addPostResponse.setDataPostResponse(null);
						addPostResponse.setMessage("Parameter is not enough");
						response.getWriter().print(gson.toJson(addPostResponse));
						return;
					}
				}
				// check max size
				if (listFilesSize > MAX_REQUEST_FILE) {
					addPostResponse.setCode(1006);
					addPostResponse.setMessage("File size is too big");
					addPostResponse.setDataPostResponse(null);
					items.clear();
					response.getWriter().print(gson.toJson(addPostResponse));
					return;
				} else {
					// create 1 list moi de chua cac file ,neu tong size < MAX_REQUEST_FILE thi add
					// vao, het vong lap lay ra
//						System.out.println("Item = "+item);
					if (!item.isFormField()) {
						items.add(item);
					}
				}
			}
//				System.out.println("size = " + items.size());
			for (FileItem item : items) {
				// neu getName != null thi save
				try {

					if (item.getName() != null) {
						String fileName = item.getName();
						item.write(new File(root + "//" + fileName));
						files.add(fileName);
					}

				} catch (Exception e) {
					System.out.println("Exception : " + e.getLocalizedMessage());
					addPostResponse.setCode(9999);
					addPostResponse.setDataPostResponse(null);
					addPostResponse.setMessage("Exception Error");
				}
			}
//				System.out.println("Tong = " + listFilesSize);

		} catch (FileUploadException e) {
			addPostResponse.setCode(9994);
			addPostResponse.setDataPostResponse(null);
			addPostResponse.setMessage("No Data or end of list data");
			System.out.println("Error = " + e.getMessage());
		}
		// add post
		AddPostRequest addPostRequest = new AddPostRequest();
		addPostRequest.setDescribed(describedRequest);
		addPostRequest.setToken(token);
		addPostRequest.setFiles(files);
		Long id = postService.insertOne(addPostRequest);
		System.out.println("ID  111 = " + id);
		FileModel fileModel = new FileModel();
		try {
			fileModel.setPostId(id);
		} catch (NullPointerException e) {
			addPostResponse.setCode(1002);
			addPostResponse.setDataPostResponse(null);
			addPostResponse.setMessage("Parameter is not enough");
			response.getWriter().print(gson.toJson(addPostResponse));
			return;
		}
		fileModel.setCreatedBy(genericService.getPhoneNumberFromToken(token));
		for (String s : files) {
			fileModel.setContent(s);
			fileService.insertOne(fileModel);
		}
		DataPostResponse dataPostResponse = new DataPostResponse();
		dataPostResponse.setId(id);
		dataPostResponse.setUrl("/CZone/api/post?id=" + id);
		addPostResponse.setCode(1000);
//		addPostResponse.setId(1L);
		addPostResponse.setDataPostResponse(dataPostResponse);
		addPostResponse.setMessage("OK");
		response.getWriter().print(gson.toJson(addPostResponse));
	}

	public String uploadFolder() {
		String root = System.getProperty("user.dir") + "\\uploadFiles";
		System.out.println("root = " + root);
		return root;

	}

}
