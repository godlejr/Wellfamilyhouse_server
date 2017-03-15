package com.demand.server.well_family_house.common.dto;

public class Range {
	private int id;
	private String name;
	
	public Range() {
		super();
	}
	public Range(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
