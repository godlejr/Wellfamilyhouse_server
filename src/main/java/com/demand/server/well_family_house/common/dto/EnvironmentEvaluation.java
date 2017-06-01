package com.demand.server.well_family_house.common.dto;

public class EnvironmentEvaluation {
	private int id;
	private int fall_diagnosis_story_id;
	private int user_id;
	private int environment_evaluation_category_id;
	private String created_at;
	private String updated_at;

	public EnvironmentEvaluation() {
		super();
	}

	public EnvironmentEvaluation(int id, int fall_diagnosis_story_id, int user_id,
			int environment_evaluation_category_id, String created_at, String updated_at) {
		super();
		this.id = id;
		this.fall_diagnosis_story_id = fall_diagnosis_story_id;
		this.user_id = user_id;
		this.environment_evaluation_category_id = environment_evaluation_category_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFall_diagnosis_story_id() {
		return fall_diagnosis_story_id;
	}

	public void setFall_diagnosis_story_id(int fall_diagnosis_story_id) {
		this.fall_diagnosis_story_id = fall_diagnosis_story_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getEnvironment_evaluation_category_id() {
		return environment_evaluation_category_id;
	}

	public void setEnvironment_evaluation_category_id(int environment_evaluation_category_id) {
		this.environment_evaluation_category_id = environment_evaluation_category_id;
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
