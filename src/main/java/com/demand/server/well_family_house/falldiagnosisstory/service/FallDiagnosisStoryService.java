package com.demand.server.well_family_house.falldiagnosisstory.service;

import com.demand.server.well_family_house.common.dto.FallDiagnosisStory;
import com.demand.server.well_family_house.common.dto.Notification;

public interface FallDiagnosisStoryService {

	int insertFallDiagnosisStory(FallDiagnosisStory fallDiagnosisStory, Notification notification) throws Exception;

	void insertSelfDiagnosis(int fall_diagnosis_story_id, int user_id, int self_diagnosis_category_id) throws Exception;
}
