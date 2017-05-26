package com.demand.server.well_family_house.common.dto;

public class SelfDiagnosis {
	private int id;
	private int fall_diagnosis_story_id;
	private int user_id;
	private int self_diagnosis_category_id;
	private String created_at;
	private String updated_at;

	public SelfDiagnosis() {
		super();
	}

	public SelfDiagnosis(int id, int fall_diagnosis_story_id, int user_id, int self_diagnosis_category_id,
			String created_at, String updated_at) {
		super();
		this.id = id;
		this.fall_diagnosis_story_id = fall_diagnosis_story_id;
		this.user_id = user_id;
		this.self_diagnosis_category_id = self_diagnosis_category_id;
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

	public int getSelf_diagnosis_category_id() {
		return self_diagnosis_category_id;
	}

	public void setSelf_diagnosis_category_id(int self_diagnosis_category_id) {
		this.self_diagnosis_category_id = self_diagnosis_category_id;
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
