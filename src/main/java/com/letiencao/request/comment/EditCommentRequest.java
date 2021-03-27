package com.letiencao.request.comment;

public class EditCommentRequest {
	private Long postId;
	private Long commentId;
	private String contentUpdate;
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getContentUpdate() {
		return contentUpdate;
	}
	public void setContentUpdate(String contentUpdate) {
		this.contentUpdate = contentUpdate;
	}
	
}
