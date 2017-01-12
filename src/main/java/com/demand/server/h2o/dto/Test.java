package com.demand.server.h2o.dto;

public class Test {
	private int id;
	private String photo;
	private String photo_ext;
	private String music;
	private String music_secret;
	private String music_ext;
	private String category;

	public Test() {
		super();
	}

	public Test(int id, String photo, String photo_ext, String music, String music_secret, String music_ext,
			String category) {
		super();
		this.id = id;
		this.photo = photo;
		this.photo_ext = photo_ext;
		this.music = music;
		this.music_secret = music_secret;
		this.music_ext = music_ext;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoto_ext() {
		return photo_ext;
	}

	public void setPhoto_ext(String photo_ext) {
		this.photo_ext = photo_ext;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public String getMusic_secret() {
		return music_secret;
	}

	public void setMusic_secret(String music_secret) {
		this.music_secret = music_secret;
	}

	public String getMusic_ext() {
		return music_ext;
	}

	public void setMusic_ext(String music_ext) {
		this.music_ext = music_ext;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
