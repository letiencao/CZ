package com.letiencao.api.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.letiencao.api.BaseHTTP;
import com.letiencao.model.AccountModel;
import com.letiencao.model.PostModel;
import com.letiencao.model.TypeReportModel;
import com.letiencao.request.post.ReportRequest;
import com.letiencao.response.BaseResponse;
import com.letiencao.service.GenericService;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IPostService;
import com.letiencao.service.IReportService;
import com.letiencao.service.ITypeReportService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BaseService;
import com.letiencao.service.impl.PostService;
import com.letiencao.service.impl.ReportService;
import com.letiencao.service.impl.TypeReportService;

@WebServlet("/api/report")
public class ReportAPI extends HttpServlet {

	/**
	 * Created By : Cao LT Created Date : 02/04/2021
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GenericService genericService;
	private IPostService postService;
	private IAccountService accountService;
	private IReportService reportService;
	private ITypeReportService typeReportService;

	public ReportAPI() {
		genericService = new BaseService();
		postService = new PostService();
		accountService = new AccountService();
		reportService = new ReportService();
		typeReportService = new TypeReportService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		BaseResponse baseResponse = new BaseResponse();
		try {
//			ReportRequest reportRequest = gson.fromJson(request.getReader(), ReportRequest.class);
//			if (reportRequest != null) {
		String idQuery = request.getParameter("id");
		String subjectQuery = request.getParameter("subject");
		String detailsQuery = request.getParameter("details");
		ReportRequest reportRequest = new ReportRequest();
		reportRequest.setDetails(detailsQuery);
		reportRequest.setId(Long.valueOf(idQuery));
		reportRequest.setSubject(Long.valueOf(subjectQuery));
		Long id = reportRequest.getId();
		Long subject = reportRequest.getSubject();// id type report
		String details = reportRequest.getDetails();
		if (id != null && subject != null && details != null) {
			if (id.toString().length() > 0 && subject.toString().length() > 0 && details.length() > 0) {
				PostModel postModel = postService.findPostById(id);
				if (postModel != null) {
					// get information of reporter
					String jwt = request.getHeader(BaseHTTP.Authorization);
					String phoneNumber = genericService.getPhoneNumberFromToken(jwt);
					AccountModel accountModel = accountService.findByPhoneNumber(phoneNumber);
					// get reporter id
					Long accountId = accountModel.getId();
					// Check typeReport id existed
					TypeReportModel typeReportModel = typeReportService.findOne(subject + 1);
					if (typeReportModel != null) {
						// Check Duplicate
						boolean checkReported = reportService.checkReported(accountId, id);
						System.out.println("checkReported : " + checkReported);
						if (checkReported == false) {
							// OK
							reportService.insertOne(accountId, id, subject + 1, details);
							baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1000));
							baseResponse.setMessage(BaseHTTP.MESSAGE_1000);
						} else {

							// Action has been done previously
							baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1010));
							baseResponse.setMessage(BaseHTTP.MESSAGE_1010);
						}
					} else {
						// Exception
						baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9999));
						baseResponse.setMessage(BaseHTTP.MESSAGE_9999);
					}

				} else {
					// post is not existed
					baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9992));
					baseResponse.setMessage(BaseHTTP.MESSAGE_9992);
				}
			} else {
				// value in valid
				baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1004));
				baseResponse.setMessage(BaseHTTP.MESSAGE_1004);
			}
		} else {
			// not enough
			baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1002));
			baseResponse.setMessage(BaseHTTP.MESSAGE_1002);
		}
//			} else {
//				// no data
//				baseResponse.setCode(String.valueOf(BaseHTTP.CODE_9994));
//				baseResponse.setMessage(BaseHTTP.MESSAGE_9994);
//			}
} catch (NumberFormatException | JsonSyntaxException e) {
	baseResponse.setCode(String.valueOf(BaseHTTP.CODE_1003));
	baseResponse.setMessage(BaseHTTP.MESSAGE_1003);
	response.getWriter().print(gson.toJson(baseResponse));
}

		response.getWriter().print(gson.toJson(baseResponse));
	}

}
