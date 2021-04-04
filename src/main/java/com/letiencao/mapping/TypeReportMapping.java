package com.letiencao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.letiencao.model.TypeReportModel;

public class TypeReportMapping implements IRowMapping<TypeReportModel> {

	@Override
	public TypeReportModel mapRow(ResultSet resultSet) {
		try {

			TypeReportModel model = new TypeReportModel();
			model.setId(resultSet.getLong("id"));
			model.setDeleted(resultSet.getBoolean("deleted"));
			model.setName(resultSet.getString("name"));
			return model;
		} catch (SQLException e) {
			System.out.println("Failed TypeReport Mapping : " + e.getMessage());
		}
		return null;
	}

}
