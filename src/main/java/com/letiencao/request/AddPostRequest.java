package com.letiencao.request;

import java.util.List;

public class AddPostRequest {
	private String token;
	private List<String> image;
	private String video;
	private String described;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public List<String> getImage() {
		return image;
	}
	public void setImage(List<String> image) {
		this.image = image;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getDescribed() {
		return described;
	}
	public void setDescribed(String described) {
		this.described = described;
	}
	

}
