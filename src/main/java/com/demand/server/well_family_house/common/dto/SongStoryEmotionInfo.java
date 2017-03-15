package com.demand.server.well_family_house.common.dto;

public class SongStoryEmotionInfo {
	private int id;
	private String name;
	private int emotion_category_id;
	private String avatar;

	public SongStoryEmotionInfo(int id, String name, int emotion_category_id, String avatar) {
		super();
		this.id = id;
		this.name = name;
		this.emotion_category_id = emotion_category_id;
		this.avatar = avatar;
	}

	public SongStoryEmotionInfo() {
		super();
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

	public int getEmotion_category_id() {
		return emotion_category_id;
	}

	public void setEmotion_category_id(int emotion_category_id) {
		this.emotion_category_id = emotion_category_id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
