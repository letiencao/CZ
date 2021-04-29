package com.letiencao.response.account;

public class DataSignUpResponse {
	private Long id;
	private boolean deleted;
	private long createdDateLong;
	private String createdBy;
	private String modifiedBy;
	private Long modifiedDateLong;
	private String name;
	private String phoneNumber;
	private String avatar;
	private String uuid;
	public DataSignUpResponse(Long id, boolean deleted, long createdDateLong, String createdBy, String modifiedBy,
			Long modifiedDateLong, String name, String phoneNumber, String avatar, String uuid) {
		super();
		this.id = id;
		this.deleted = deleted;
		this.createdDateLong = createdDateLong;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.modifiedDateLong = modifiedDateLong;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.avatar = avatar;
		this.uuid = uuid;
	}
	
}
