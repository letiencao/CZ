package com.letiencao.model;

public class AccountModel extends BaseModel {
	private String name;
	private String password;
	private String phoneNumber;
	private String avatar;
	private String uuid;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Override
	public String toString() {
		return "AccountModel [name=" + name + ", password=" + password + ", phoneNumber=" + phoneNumber + ", avatar="
				+ avatar + "]";
	}
	
}
