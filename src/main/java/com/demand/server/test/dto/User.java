package com.demand.server.test.dto;

public class User {
	private String name;
	private String phone;

	public User(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}

	public User() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
