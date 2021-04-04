package com.letiencao.service;

public interface IReportService {
	Long insertOne(Long accountId,Long postId,Long typeReportId,String details);
	boolean checkReported(Long accountId,Long postId);
}
