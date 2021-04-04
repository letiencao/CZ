package com.letiencao.model;

public class FriendModel extends BaseModel {
	private Long idA;
	private Long idB;
	private boolean isFriend;
	
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
	public boolean isFriend() {
		return isFriend;
	}
	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}
	@Override
	public String toString() {
		return "FriendModel [idA=" + idA + ", idB=" + idB + ", isFriend=" + isFriend + "]";
	}
	

}
