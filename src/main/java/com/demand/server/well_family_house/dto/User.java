package com.demand.server.well_family_house.dto;

public class User {
	private int id;
	private String name;
	private int level;
	
	public User() {
		super();
	}
	public User(int id, String name ,int level) {
		super();
		this.id = id;
		this.name = name;
		this.level =  level;
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
	
	
}
