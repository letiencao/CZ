package com.letiencao.response.post;

import java.util.List;

public class DataGetPostReponse {
	private Long id;
	private String described;
	private String created;
	private String modified;
	private int like;// so luong
	private int comment;
	private boolean isLiked;
	private List<ImageGetPostResponse> listImage;
	private List<VideoGetPostResponse> listVideo;
	private AuthorGetPostResponse authorGetPostResponse;
	private String isBlocked;
	private String canEdit;
	private String canComment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescribed() {
		return described;
	}

	public void setDescribed(String described) {
		this.described = described;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public String getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(String isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(String canEdit) {
		this.canEdit = canEdit;
	}

	public String getCanComment() {
		return canComment;
	}

	public void setCanComment(String canComment) {
		this.canComment = canComment;
	}

	public List<ImageGetPostResponse> getListImage() {
		return listImage;
	}

	public void setListImage(List<ImageGetPostResponse> listImage) {
		this.listImage = listImage;
	}

	public List<VideoGetPostResponse> getListVideo() {
		return listVideo;
	}

	public void setListVideo(List<VideoGetPostResponse> listVideo) {
		this.listVideo = listVideo;
	}

	public AuthorGetPostResponse getAuthorGetPostResponse() {
		return authorGetPostResponse;
	}

	public void setAuthorGetPostResponse(AuthorGetPostResponse authorGetPostResponse) {
		this.authorGetPostResponse = authorGetPostResponse;
	}
}
