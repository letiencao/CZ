package com.letiencao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.letiencao.model.ReportModel;

public class ReportMapping implements IRowMapping<ReportModel> {

	@Override
	public ReportModel mapRow(ResultSet resultSet) {
		try {

			ReportModel model = new ReportModel();
			
			model.setId(resultSet.getLong("id"));
			model.setDeleted(resultSet.getBoolean("deleted"));
			model.setCreatedDate(resultSet.getTimestamp("createddate"));
			model.setCreatedBy(resultSet.getString("createdby"));
			model.setAccountId(resultSet.getLong("accountid"));
			model.setPostId(resultSet.getLong("postid"));
			model.setTypeReportId(resultSet.getLong("typereportid"));
			model.setModifiedBy(resultSet.getString("modifiedby"));
			model.setModifiedDate(resultSet.getTimestamp("modifieddate"));
			model.setDetails(resultSet.getString("details"));
			return model;
		} catch (SQLException e) {
			System.out.println("Failed TypeReport Mapping : " + e.getMessage());
		}
		return null;
	}

}
