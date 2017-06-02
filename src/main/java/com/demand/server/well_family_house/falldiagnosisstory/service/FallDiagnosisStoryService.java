package com.demand.server.well_family_house.falldiagnosisstory.service;

import java.io.InputStream;

import javax.servlet.ServletInputStream;

import com.demand.server.well_family_house.common.dto.FallDiagnosisStory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisStoryInfo;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluation;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluationScore;

public interface FallDiagnosisStoryService {

	int insertFallDiagnosisStory(FallDiagnosisStory fallDiagnosisStory, Notification notification) throws Exception;

	void insertSelfDiagnosis(int fall_diagnosis_story_id, int user_id, int fall_diagnosis_content_category_id)
			throws Exception;

	void insertPhysicalEvaluation(PhysicalEvaluation physicalEvaluation) throws Exception;

	void insertPhysicalEvaluationScore(PhysicalEvaluationScore physicalEvaluationScore) throws Exception;

	void insertEnvironmentEvaluation(int fall_diagnosis_story_id, int user_id, int environment_evaluation_category_id)
			throws Exception;

	void insertEnvironmentPhoto(InputStream base64InputStream, int fall_diagnosis_story_id) throws Exception;

	int selectFallDiagnosisStoryCommentCount(int fall_diagnosis_story_id) throws Exception;

	int selectFallDiagnosisStoryLikeCount(int fall_diagnosis_story_id) throws Exception;

	void insertFallDiagnosisStoryLikeUp(int user_id, int fall_diagnosis_story_id, Notification notification)
			throws Exception;

	void deleteFallDiagnosisStoryLikeDown(int user_id, int fall_diagnosis_story_id) throws Exception;

	int selectFallDiagnosisStoryLikeCheck(int user_id, int fall_diagnosis_story_id) throws Exception;

	void updateFallDiagnosisStoryHit(int fall_diagnosis_story_id) throws Exception;

	FallDiagnosisStoryInfo selectFallDiagnosisStoryInfo(FallDiagnosisStory fallDiagnosisStory) throws Exception;
}
