package com.letiencao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.letiencao.model.FileModel;

public class FileMapping implements IRowMapping<FileModel> {

	@Override
	public FileModel mapRow(ResultSet resultSet) {
		FileModel fileModel = new FileModel();
		try {
			fileModel.setId(resultSet.getLong("id"));
			fileModel.setContent(resultSet.getString("content"));
			fileModel.setDeleted(resultSet.getBoolean("deleted"));
			fileModel.setCreatedBy(resultSet.getString("createdby"));
			fileModel.setCreatedDate(resultSet.getTimestamp("createddate"));
			fileModel.setModifiedBy(resultSet.getString("modifiedby"));
			fileModel.setModifiedDate(resultSet.getTimestamp("modifieddate"));
			fileModel.setPostId(resultSet.getLong("postid"));
		} catch (SQLException e) {
			return null;
		}
		return fileModel;
	}

}
