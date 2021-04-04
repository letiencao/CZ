package com.letiencao.service.impl;

import com.letiencao.dao.ITypeReportDAO;
import com.letiencao.dao.impl.TypeReportDAO;
import com.letiencao.model.TypeReportModel;
import com.letiencao.service.ITypeReportService;

public class TypeReportService implements ITypeReportService {
	private ITypeReportDAO typeReportDAO;
	public TypeReportService() {
		typeReportDAO = new TypeReportDAO();
	}
	@Override
	public TypeReportModel findOne(Long typeReportId) {
		return typeReportDAO.findOne(typeReportId);
	}

}
