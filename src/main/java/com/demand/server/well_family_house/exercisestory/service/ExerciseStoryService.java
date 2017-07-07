package com.demand.server.well_family_house.exercisestory.service;

import java.util.ArrayList;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.ExerciseStoryComment;
import com.demand.server.well_family_house.common.dto.ExerciseStory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisStoryComment;
import com.demand.server.well_family_house.common.dto.Notification;

public interface ExerciseStoryService {

	int insertExerciseStory(ExerciseStory exerciseStory, Notification notification) throws Exception;

	ExerciseStory selectExerciseStory(int exercise_story_id) throws Exception;

	int selectExerciseStoryCommentCount(int exercise_story_id) throws Exception;

	int selectExerciseStoryLikeCount(int exercise_story_id) throws Exception;

	void insertExerciseStoryLikeUp(int user_id, int exercise_story_id, Notification notification) throws Exception;

	void deleteExerciseStoryLikeDown(int user_id, int exercise_story_id) throws Exception;

	int selectExerciseStoryLikeCheck(int user_id, int exercise_story_id) throws Exception;

	void updateExerciseStoryHit(int exercise_story_id) throws Exception;

	ArrayList<CommentInfo> selectExerciseStoryCommentList(int exercise_story_id) throws Exception;

	ExerciseStoryComment insertExerciseStoryComment(ExerciseStoryComment exerciseStoryComment, Notification notification)
			throws Exception;

	void deleteExerciseStory(int exercise_story_id) throws Exception;

}
