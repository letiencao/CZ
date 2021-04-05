package com.letiencao.model;

import java.sql.Timestamp;

public class BaseModel {
	private Long id;
	private boolean deleted;
	private Timestamp createdDate;
	private long createdDateLong;
	private String createdBy;
	private Timestamp modifiedDate;
	private String modifiedBy;
	private Long modifiedDateLong;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public long getCreatedDateLong() {
		return createdDateLong;
	}
	public void setCreatedDateLong(long createdDateLong) {
		this.createdDateLong = createdDateLong;
	}
	public Long getModifiedDateLong() {
		return modifiedDateLong;
	}
	public void setModifiedDateLong(Long modifiedDateLong) {
		this.modifiedDateLong = modifiedDateLong;
	}
	
}
