package com.demand.server.well_family_house.dto;

public class ContentCount {
	private int story_id;
	private int like_count;
	private int comment_count;
	

	public ContentCount() {
		super();
	}
	public ContentCount(int story_id, int like_count, int comment_count) {
		super();
		this.story_id = story_id;
		this.like_count = like_count;
		this.comment_count = comment_count;
	}
	public int getStory_id() {
		return story_id;
	}
	public void setStory_id(int story_id) {
		this.story_id = story_id;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	
	
}	
