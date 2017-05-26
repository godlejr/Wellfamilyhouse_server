package com.demand.server.well_family_house.falldiagnosisstory.service.impl;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.FallDiagnosisStory;

@Repository
public interface FallDiagnosisStoryMapper {

	void insertFallDiagnosisStory(FallDiagnosisStory fallDiagnosisStory) throws Exception;

	void insertSelfDiagnosis(int fall_diagnosis_story_id, int user_id, int fall_diagnosis_content_category_id) throws Exception;

}
