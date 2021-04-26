package com.letiencao.response.account;

public class UserInfoResponse {
	private Long id;
	private String phoneNumber;
	private long created;
	private String description;// null
	private String avatar;
	private String coverImage; // null
	private String link; // null
	private String address; // null
	private String city;// null
	private String country; // null
	private int sizeListFriend;
	private boolean isFriend;
	private String online;// null

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getSizeListFriend() {
		return sizeListFriend;
	}

	public void setSizeListFriend(int sizeListFriend) {
		this.sizeListFriend = sizeListFriend;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	@Override
	public String toString() {
		return "UserInfoResponse [id=" + id + ", phoneNumber=" + phoneNumber + ", created=" + created + ", description="
				+ description + ", avatar=" + avatar + ", coverImage=" + coverImage + ", link=" + link + ", address="
				+ address + ", city=" + city + ", country=" + country + ", sizeListFriend=" + sizeListFriend
				+ ", isFriend=" + isFriend + ", online=" + online + "]";
	}
	
}
