package com.letiencao.dao.impl;

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

}
