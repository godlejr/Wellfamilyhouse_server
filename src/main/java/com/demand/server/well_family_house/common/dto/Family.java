package com.demand.server.well_family_house.common.dto;

public class Family {
	private int id;
	private String name;
	private String content;
	private String avatar;
	private int user_id;
	private String created_at;

	public Family() {
		super();
	}

	public Family(int id, String name, String content, String avatar, int user_id, String created_at) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.avatar = avatar;
		this.user_id = user_id;
		this.created_at = created_at;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

}
