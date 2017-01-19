package com.demand.server.well_family_house.dto;

public class FamilyUserInfo {
	private int id;
	private String name;
	private String avatar;
	private String birth;
	private int level;
	
	public FamilyUserInfo() {
		super();
	}
	public FamilyUserInfo(int id, String name, String avatar, String birth,int level) {
		super();
		this.id = id;
		this.name = name;
		this.avatar = avatar;
		this.birth = birth;
		this.level = level;
	}
	
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	
}
