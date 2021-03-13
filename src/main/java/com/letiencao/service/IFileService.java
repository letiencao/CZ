package com.letiencao.service;

import com.letiencao.model.FileModel;

public interface IFileService {
	FileModel insertOne(FileModel fileModel);
	FileModel findOne(Long id);
}
