package com.demand.server.well_family_house.dto;

public class SongCategory {
	private int id;
	private String name;
	private String created_at;

	public SongCategory() {
		super();
	}

	public SongCategory(int id, String name, String created_at) {
		super();
		this.id = id;
		this.name = name;
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

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

}
