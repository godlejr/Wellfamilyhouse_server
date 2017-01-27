package com.demand.server.well_family_house.dto;

public class Story {
	private int id;
	private int user_id;
	private int family_id;
	private String content;

	public Story() {
		super();
	}

	public Story(int id, int user_id, int family_id, String content) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.family_id = family_id;
		this.content = content;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getFamily_id() {
		return family_id;
	}

	public void setFamily_id(int family_id) {
		this.family_id = family_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
