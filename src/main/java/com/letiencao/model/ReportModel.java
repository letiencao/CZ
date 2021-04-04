package com.letiencao.model;

public class ReportModel extends BaseModel{
	private String details;
	private Long accountId;
	private Long postId;
	private Long typeReportId;
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public Long getTypeReportId() {
		return typeReportId;
	}
	public void setTypeReportId(Long typeReportId) {
		this.typeReportId = typeReportId;
	}
	

}
