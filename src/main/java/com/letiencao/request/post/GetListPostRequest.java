package com.letiencao.request.post;

public class GetListPostRequest {
	private Long accountId; // neu != null tra ve cac bai viet cua account do 
	private Long last_id;//id cua bai viet moi nhat
	private Long index; // start select
	private int count; //default = 20
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getLast_id() {
		return last_id;
	}
	public void setLast_id(Long last_id) {
		this.last_id = last_id;
	}
	public Long getIndex() {
		return index;
	}
	public void setIndex(Long index) {
		this.index = index;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}
