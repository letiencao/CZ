package com.letiencao.request.post;

public class GetListPostRequest {
	private static long lastId;//id cua bai viet moi nhat
	private static long index; // start select
	private int count; //default = 20
	private Long userId;
	
	
	public static long getLastId() {
		return lastId;
	}
	public static void setLastId(long lastId) {
		GetListPostRequest.lastId = lastId;
	}
	public static long getIndex() {
		return index;
	}
	public static void setIndex(long index) {
		GetListPostRequest.index = index;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	
	

}
