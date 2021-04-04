package com.letiencao.dao.impl;

import com.letiencao.dao.ITypeReportDAO;
import com.letiencao.mapping.TypeReportMapping;
import com.letiencao.model.TypeReportModel;

public class TypeReportDAO extends BaseDAO<TypeReportModel> implements ITypeReportDAO {

	@Override
	public TypeReportModel findOne(Long typeReportId) {
		String sql = "SELECT * FROM typereport WHERE id = ?";
		try {
			return findOne(sql, new TypeReportMapping(), typeReportId);
		} catch (ClassCastException e) {
			return null;

		}

	}

}
