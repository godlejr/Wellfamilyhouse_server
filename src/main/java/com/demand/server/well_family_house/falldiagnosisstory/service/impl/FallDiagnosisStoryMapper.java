package com.demand.server.well_family_house.falldiagnosisstory.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.EnvironmentEvaluation;
import com.demand.server.well_family_house.common.dto.EnvironmentPhoto;
import com.demand.server.well_family_house.common.dto.FallDiagnosisContentCategory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisRiskCategory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisStory;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluation;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluationScore;
import com.demand.server.well_family_house.common.dto.SelfDiagnosis;

@Repository
public interface FallDiagnosisStoryMapper {

	void insertFallDiagnosisStory(FallDiagnosisStory fallDiagnosisStory) throws Exception;

	void insertSelfDiagnosis(int fall_diagnosis_story_id, int user_id, int fall_diagnosis_content_category_id)
			throws Exception;

	void insertPhysicalEvaluation(PhysicalEvaluation physicalEvaluation) throws Exception;

	void insertPhysicalEvaluationScore(PhysicalEvaluationScore physicalEvaluationScore) throws Exception;

	void insertEnvironmentEvaluation(int fall_diagnosis_story_id, int user_id, int environment_evaluation_category_id)
			throws Exception;

	void insertEnvironmentPhoto(EnvironmentPhoto environmentPhoto) throws Exception;

	int selectFallDiagnosisStoryCommentCount(int fall_diagnosis_story_id) throws Exception;

	int selectFallDiagnosisStoryLikeCount(int fall_diagnosis_story_id) throws Exception;

	int selectUser(int fall_diagnosis_story_id) throws Exception;

	void insertFallDiagnosisStoryLikeUp(int user_id, int fall_diagnosis_story_user_id) throws Exception;
	
	void deleteFallDiagnosisStoryLikeDown(int user_id, int fall_diagnosis_story_id) throws Exception;
	
	int selectFallDiagnosisStoryLikeCheck(int user_id, int fall_diagnosis_story_id) throws Exception;

	void updateFallDiagnosisStoryHit(int fall_diagnosis_story_id) throws Exception;

	String selectFallDiagnosisStoryTitleWithRisk(int fall_diagnosis_story_id) throws Exception;

	FallDiagnosisRiskCategory selectFallDiagnosisRiskCategory(int fall_diagnosis_category_id)  throws Exception;

	int selectEnvironmentEvaluationCategoryTotalCountWithStoryId(int fall_diagnosis_category_id) throws Exception;

	ArrayList<EnvironmentEvaluation> selectEnvironmentEvaluationList(int fall_diagnosis_category_id) throws Exception;

	ArrayList<FallDiagnosisContentCategory> selectFallDiagnosisContentCategoryList(int fall_diagnosis_category_id) throws Exception;

	ArrayList<SelfDiagnosis> selectSelfDiagnosisList(int story_id) throws Exception;

	PhysicalEvaluationScore selectPhysicalEvaluationScore(int story_id) throws Exception;

}
