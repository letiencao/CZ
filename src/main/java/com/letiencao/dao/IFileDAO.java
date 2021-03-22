package com.letiencao.dao;

import java.util.List;

import com.letiencao.model.FileModel;

public interface IFileDAO extends GenericDAO<FileModel> {
	FileModel insertOne(FileModel fileModel);
	FileModel findOne(Long id);
	List<FileModel> findByPostId(Long postId);
}
