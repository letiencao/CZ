package com.letiencao.model;

public class ChatModel extends BaseModel {
	private String content;
	private Long idA;
	private Long idB;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getIdA() {
		return idA;
	}
	public void setIdA(Long idA) {
		this.idA = idA;
	}
	public Long getIdB() {
		return idB;
	}
	public void setIdB(Long idB) {
		this.idB = idB;
	}
	
}
