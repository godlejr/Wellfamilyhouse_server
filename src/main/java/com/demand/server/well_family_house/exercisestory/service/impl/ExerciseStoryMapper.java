package com.demand.server.well_family_house.exercisestory.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.ExerciseStoryComment;
import com.demand.server.well_family_house.common.dto.ExerciseStory;
import com.demand.server.well_family_house.common.dto.Notification;

@Repository
public interface ExerciseStoryMapper {

	void insertExerciseStory(ExerciseStory exerciseStory) throws Exception;

	ExerciseStory selectExerciseStory(int exercise_story_id) throws Exception;

	int selectExerciseStoryCommentCount(int exercise_story_id) throws Exception;

	int selectExerciseStoryLikeCount(int exercise_story_id) throws Exception;

	void insertExerciseStoryLikeUp(int user_id, int exercise_story_id, Notification notification) throws Exception;

	void deleteExerciseStoryLikeDown(int user_id, int exercise_story_id) throws Exception;

	int selectExerciseStoryLikeCheck(int user_id, int exercise_story_id) throws Exception;

	void updateExerciseStoryHit(int exercise_story_id) throws Exception;

	int selectUser(int exercise_story_id) throws Exception;

	void insertExerciseStoryLikeUp(int user_id, int exercise_story_id) throws Exception;

	void insertExerciseStoryComment(ExerciseStoryComment exerciseStoryComment) throws Exception;

	ExerciseStoryComment selectExerciseStoryComment(int exercise_story_id) throws Exception;

	void deleteExerciseStory(int exercise_story_id) throws Exception;

	ArrayList<CommentInfo> selectExerciseStoryCommentList(int exercise_story_id) throws Exception;

}
