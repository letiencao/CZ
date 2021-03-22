package com.letiencao.service;

import java.util.List;

import com.letiencao.model.FileModel;

public interface IFileService {
	FileModel insertOne(FileModel fileModel);
	FileModel findOne(Long id);
	List<FileModel> findByPostId(Long postId);
}
