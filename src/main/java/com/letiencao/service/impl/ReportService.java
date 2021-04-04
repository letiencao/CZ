package com.letiencao.service.impl;

import java.sql.Timestamp;

import com.letiencao.dao.IReportDAO;
import com.letiencao.dao.impl.ReportDAO;
import com.letiencao.model.ReportModel;
import com.letiencao.service.IReportService;

public class ReportService implements IReportService {
	private IReportDAO reportDAO;
	public ReportService() {
		reportDAO = new ReportDAO();
	}
	@Override
	public Long insertOne(Long accountId, Long postId, Long typeReportId, String details) {
		ReportModel reportModel = new ReportModel();
		reportModel.setCreatedBy(accountId.toString());
		reportModel.setAccountId(accountId);
		reportModel.setPostId(postId);
		reportModel.setTypeReportId(typeReportId);
		reportModel.setDetails(details);
		reportModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		reportModel.setDeleted(false);
		return reportDAO.insertOne(reportModel);
	}
	@Override
	public boolean checkReported(Long accountId, Long postId) {
		ReportModel reportModel = reportDAO.findOne(accountId, postId);
		if(reportModel != null) {
			return true;
		}
		return false;
	}
	

}
