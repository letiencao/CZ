package com.letiencao.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.letiencao.dao.IFileDAO;
import com.letiencao.dao.impl.FileDAO;
import com.letiencao.model.FileModel;
import com.letiencao.service.IFileService;

public class FileService implements IFileService {
	private IFileDAO fileDAO;
	public FileService() {
		fileDAO = new FileDAO();
	}

	@Override
	public FileModel insertOne(FileModel fileModel) {
		fileModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		fileModel.setDeleted(false);
		return fileDAO.insertOne(fileModel);
	}

	@Override
	public FileModel findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FileModel> findByPostId(Long postId) {
		return fileDAO.findByPostId(postId);
	}

}
