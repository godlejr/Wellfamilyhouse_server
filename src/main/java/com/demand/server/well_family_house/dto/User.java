package com.demand.server.well_family_house.dto;

public class User {
	private int id;
	private int family_id;
	
	public User() {
		super();
	}
	public User(int id, int family_id) {
		super();
		this.id = id;
		this.family_id = family_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFamily_id() {
		return family_id;
	}
	public void setFamily_id(int family_id) {
		this.family_id = family_id;
	}
	
	
}
