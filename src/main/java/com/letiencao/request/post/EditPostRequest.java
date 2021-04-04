package com.letiencao.request.post;

import java.util.List;

public class EditPostRequest {
	private Long id;
	private String described;
	private List<String> image;
	private List<Long> image_del;
	private List<Integer> image_sort;
	private String video;
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
	public List<String> getImage() {
		return image;
	}
	public void setImage(List<String> image) {
		this.image = image;
	}
	public List<Long> getImage_del() {
		return image_del;
	}
	public void setImage_del(List<Long> image_del) {
		this.image_del = image_del;
	}
	public List<Integer> getImage_sort() {
		return image_sort;
	}
	public void setImage_sort(List<Integer> image_sort) {
		this.image_sort = image_sort;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	
}
