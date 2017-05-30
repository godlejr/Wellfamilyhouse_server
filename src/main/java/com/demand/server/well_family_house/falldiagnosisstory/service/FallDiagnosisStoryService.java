package com.demand.server.well_family_house.falldiagnosisstory.service;

import com.demand.server.well_family_house.common.dto.FallDiagnosisStory;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluation;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluationScore;

public interface FallDiagnosisStoryService {

	int insertFallDiagnosisStory(FallDiagnosisStory fallDiagnosisStory, Notification notification) throws Exception;

	void insertSelfDiagnosis(int fall_diagnosis_story_id, int user_id, int fall_diagnosis_content_category_id) throws Exception;

	void insertPhysicalEvaluation(PhysicalEvaluation physicalEvaluation) throws Exception;

	void insertPhysicalEvaluationScore(PhysicalEvaluationScore physicalEvaluationScore) throws Exception;
}
