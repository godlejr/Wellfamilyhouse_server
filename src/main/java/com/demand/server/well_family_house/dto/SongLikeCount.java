package com.demand.server.well_family_house.dto;

public class SongLikeCount {
	private int like_count;
	
	public SongLikeCount(int like_count) {
		super();
		this.like_count = like_count;
	}

	public SongLikeCount() {
		super();
	}

	public int getLike_count() {
		return like_count;
	}

	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
 
}
