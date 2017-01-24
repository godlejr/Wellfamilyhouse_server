package com.demand.server.well_family_house.dto;

public class Comment {
	private int comment_id;
	private int user_id;
	private String user_name;
	private String avatar;
	private String content;
	private String created_at;

	public Comment() {
		super();
	}

	public Comment(int comment_id, int user_id, String user_name, String avatar, String content, String created_at) {
		super();
		this.comment_id = comment_id;
		this.user_id = user_id;
		this.user_name = user_name;
		this.avatar = avatar;
		this.content = content;
		this.created_at = created_at;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

}
