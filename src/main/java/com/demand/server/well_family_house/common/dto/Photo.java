package com.demand.server.well_family_house.common.dto;

public class Photo {
	private int id; 
	private int story_id;
	private int type;
	private String name;
	private String ext;
		
	public Photo() {
		super();
	}
	public Photo(int id, int story_id, int type, String name, String ext) {
		super();
		this.id = id;
		this.story_id = story_id;
		this.type = type;
		this.name = name;
		this.ext = ext;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStory_id() {
		return story_id;
	}
	public void setStory_id(int story_id) {
		this.story_id = story_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	
}
