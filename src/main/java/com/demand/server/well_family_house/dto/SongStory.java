package com.demand.server.well_family_house.dto;

public class SongStory {
	private int id;
	private int user_id;
	private int range_id;
	private String content;
	private String location;
	private String hits;
	private String created_at;
	private String updated_at;

	public SongStory() {
		super();
	}

	public SongStory(int id, int user_id, int range_id, String content, String location, String hits, String created_at,
			String updated_at) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.range_id = range_id;
		this.content = content;
		this.location = location;
		this.hits = hits;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getRange_id() {
		return range_id;
	}

	public void setRange_id(int range_id) {
		this.range_id = range_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHits() {
		return hits;
	}

	public void setHits(String hits) {
		this.hits = hits;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

}
