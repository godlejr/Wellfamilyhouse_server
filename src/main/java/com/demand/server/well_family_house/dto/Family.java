package com.demand.server.well_family_house.dto;

public class Family {
	private int id;
	private String name;
	private String content;
	private String avatar;
	
	public Family(int id, String name, String content, String avatar) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.avatar = avatar;
	}

	public Family() {
		super();
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
	
	
}
