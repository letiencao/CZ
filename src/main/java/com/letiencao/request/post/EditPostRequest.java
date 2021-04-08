package com.letiencao.request.post;

import java.util.List;

public class EditPostRequest {
	private Long id;
	private String described;
	private List<String> image;
	private List<Long> imageDel;
	private List<Integer> imageSort;
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
	
	public List<Long> getImageDel() {
		return imageDel;
	}
	public void setImageDel(List<Long> imageDel) {
		this.imageDel = imageDel;
	}
	public List<Integer> getImageSort() {
		return imageSort;
	}
	public void setImageSort(List<Integer> imageSort) {
		this.imageSort = imageSort;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	
}
