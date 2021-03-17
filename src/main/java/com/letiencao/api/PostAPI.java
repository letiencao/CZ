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

@WebServlet("/api/add-post")
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
		response.setContentType("application/json");
		AddPostResponse addPostResponse = new AddPostResponse();
		String token = request.getHeader("Authorization");
		String describedRequest = null;
		List<String> files = new ArrayList<String>();// luu ten file de save db
		Gson gson = new Gson();
		// get path upload folder
		String root = uploadFolder();
		File file = new File(root);
		if (file.exists() == false) {
			file.mkdirs();
		}
		List<FileItem> containFileItems = new ArrayList<FileItem>(); // chua cac file != null,de ghi ra folder
		ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
		boolean video = false, image = false;// xac dinh file dang nao.
		int filesSize = 0; // tong size cua cac file upload
		List<FileItem> list = new ArrayList<FileItem>();
		try {

			list = servletFileUpload.parseRequest(request);
			if (list.size() > 0 && list.size() < 3) {
				for (FileItem fileItem : list) {
					// get key files
					if (fileItem.getFieldName().equalsIgnoreCase("files")
							|| fileItem.getFieldName().equalsIgnoreCase("described")) {

						if (fileItem.getFieldName().equalsIgnoreCase("files")) {
							if (fileItem.getName().endsWith(".mp4")) {
								if (image == true) {
									continue;
								} else {
									video = true;
									filesSize += fileItem.getSize();
								}
								containFileItems.add(fileItem);
							} else if (fileItem.getName().endsWith(".jpg") || fileItem.getName().endsWith(".svg")
									|| fileItem.getName().endsWith(".JPEG")) {
								if (video == true) {
									continue;
								} else {
									image = true;
									filesSize += fileItem.getSize();
								}
								containFileItems.add(fileItem);
							} else {
								// file ko phai video || image || file.getName().length() == 0
								parameterInValid(addPostResponse);
								response.getWriter().print(gson.toJson(addPostResponse));
								return;

							}
							// get key described
						} else if (fileItem.getFieldName().equalsIgnoreCase("described")) {
							String described = fileItem.getString();
							if (described.length() > 0) {
								describedRequest = described;
							} else {
								// decribed vuot qua so tu cho phep
								parameterInValid(addPostResponse);
								response.getWriter().print(gson.toJson(addPostResponse));
								return;
							}

						}
					}
				}
			} else {
				parameterInValid(addPostResponse);
				response.getWriter().print(gson.toJson(addPostResponse));
				return;
			}

		} catch (FileUploadException e) {
			addPostResponse.setCode(9994);
			addPostResponse.setDataPostResponse(null);
			addPostResponse.setMessage("No data or end of list data");
			response.getWriter().print(gson.toJson(addPostResponse));
			return;
		}

		// check max size
		if (filesSize > MAX_REQUEST_FILE) {
			containFileItems.clear();
			addPostResponse.setCode(1006);
			addPostResponse.setDataPostResponse(null);
			addPostResponse.setMessage("File size is too big");
			response.getWriter().print(gson.toJson(addPostResponse));
			return;
		} else {
			// <= MAX = > write File
			for (FileItem item : containFileItems) {
				String itemName = item.getName();
				files.add(itemName);
			}
		}
//		 add post
		try {
			if (describedRequest.length() > 0) {
				AddPostRequest addPostRequest = new AddPostRequest();
				addPostRequest.setDescribed(describedRequest);
				addPostRequest.setToken(token);
				addPostRequest.setFiles(files);
				Long id = postService.insertOne(addPostRequest);
				FileModel fileModel = new FileModel();
				try {
					fileModel.setPostId(id);
				} catch (NullPointerException e) {
					parameterNotEnough(addPostResponse);
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
				addPostResponse.setDataPostResponse(dataPostResponse);
				addPostResponse.setMessage("OK");
				writeFile(containFileItems, addPostResponse, gson);
				response.getWriter().print(gson.toJson(addPostResponse));
			}
		} catch (NullPointerException e) {
			parameterNotEnough(addPostResponse);
			response.getWriter().print(gson.toJson(addPostResponse));
		}
	}

	public String uploadFolder() {
		String root = System.getProperty("user.dir") + "\\uploadFiles";
		System.out.println("root = " + root);
		return root;

	}

	public void parameterInValid(AddPostResponse addPostResponse) {
		addPostResponse.setCode(1004);
		addPostResponse.setDataPostResponse(null);
		addPostResponse.setMessage("Parameter value is invalid");
	}

	public void parameterNotEnough(AddPostResponse addPostResponse) {
		addPostResponse.setCode(1002);
		addPostResponse.setDataPostResponse(null);
		addPostResponse.setMessage("Parameter is not enough");
	}

	public void writeFile(List<FileItem> fileItems, AddPostResponse addPostResponse, Gson gson) {
		for (FileItem item : fileItems) {
			if (item.getName() != null) {
				try {
					item.write(new File(uploadFolder() + "//" + item.getName()));
				} catch (Exception e) {
					addPostResponse.setCode(1010);
					addPostResponse.setDataPostResponse(null);
					addPostResponse.setMessage("Action has be done previously by this user");
					System.out.println("Error = " + e.getMessage());
				}
			}
		}

	}

}
