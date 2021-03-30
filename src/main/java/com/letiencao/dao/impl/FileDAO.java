package com.letiencao.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.letiencao.dao.IFileDAO;
import com.letiencao.mapping.FileMapping;
import com.letiencao.model.FileModel;

public class FileDAO extends BaseDAO<FileModel> implements IFileDAO {

	@Override
	public FileModel insertOne(FileModel fileModel) {
		String sql = "INSERT INTO file(deleted,createddate,createdby,content,postid) VALUES(?,?,?,?,?)";
		Long id = insertOne(sql, fileModel.isDeleted(),fileModel.getCreatedDate(),fileModel.getCreatedBy(),fileModel.getContent(),fileModel.getPostId());
		if(id > 0) {
			return findOne(id);
		}else {
			return null;
		}
	}

	@Override
	public FileModel findOne(Long id) {
		String sql = "SELECT * FROM file WHERE id = ?";
		
		return findOne(sql, new FileMapping(), id);
	}

	@Override
	public List<FileModel> findByPostId(Long postId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<FileModel> list = new ArrayList<FileModel>();
		try {
			
			String sql = "SELECT * FROM file WHERE postid = ?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, postId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				FileModel fileModel = new FileMapping().mapRow(resultSet);
				list.add(fileModel);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Failed FindByPostID FileDAO : "+e.getMessage());
			return null;
		}
	}

}
