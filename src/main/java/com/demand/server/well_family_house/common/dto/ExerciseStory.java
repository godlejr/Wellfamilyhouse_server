package com.demand.server.well_family_house.common.dto;

public class ExerciseStory {
	private int id;
	private int user_id;
	private int exercise_category_id;
	private int score;
	private String created_at;
	private String updated_at;

	public ExerciseStory() {
		super();
	}

	public ExerciseStory(int id, int user_id, int exercise_category_id, int score, String created_at,
			String updated_at) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.exercise_category_id = exercise_category_id;
		this.score = score;
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

	public int getExercise_category_id() {
		return exercise_category_id;
	}

	public void setExercise_category_id(int exercise_category_id) {
		this.exercise_category_id = exercise_category_id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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
