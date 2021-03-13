package com.letiencao.dao;

import com.letiencao.model.FileModel;

public interface IFileDAO extends GenericDAO<FileModel> {
	FileModel insertOne(FileModel fileModel);
	FileModel findOne(Long id);
}
