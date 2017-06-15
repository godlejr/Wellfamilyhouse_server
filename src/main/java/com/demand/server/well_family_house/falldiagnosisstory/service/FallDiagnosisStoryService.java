package com.demand.server.well_family_house.falldiagnosisstory.service;

import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.ServletInputStream;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.EnvironmentEvaluationCategory;
import com.demand.server.well_family_house.common.dto.EnvironmentEvaluationStatus;
import com.demand.server.well_family_house.common.dto.EnvironmentPhoto;
import com.demand.server.well_family_house.common.dto.FallDiagnosisContentCategory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisStory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisStoryComment;
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

	ArrayList<FallDiagnosisContentCategory> selectSelfDiagnosisList(int fall_diagnosis_story_id) throws Exception;

	PhysicalEvaluationScore selectPhysicalEvaluationScore(int fall_diagnosis_story_id) throws Exception;

	ArrayList<EnvironmentPhoto> selectEnvironmentPhotoList(int fall_diagnosis_story_id) throws Exception;

	ArrayList<EnvironmentEvaluationCategory> selectEnvironmentEvaluationList(int fall_diagnosis_story_id)
			throws Exception;

	void insertEnvironmentEvaluationStatus(EnvironmentEvaluationStatus environmentEvaluationStatus) throws Exception;

	ArrayList<CommentInfo> selectFalldiagnosisStoryCommentList(int fall_diagnosis_story_id) throws Exception;

	FallDiagnosisStoryComment insertFalldiagnosisStoryComment(FallDiagnosisStoryComment fallDiagnosisStoryComment,
			Notification notification) throws Exception;

	void deleteFalldiagnosisStory(int fall_diagnosis_story_id) throws Exception;
}
