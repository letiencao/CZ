package com.letiencao.request.post;

public class GetListPostRequest {
	private static long last_id;//id cua bai viet moi nhat
	private static long index; // start select
	private int count; //default = 20
	public static long getLast_id() {
		return last_id;
	}
	public static void setLast_id(long last_id) {
		GetListPostRequest.last_id = last_id;
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
	
	
	
	
	

}
