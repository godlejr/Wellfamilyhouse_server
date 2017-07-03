package com.demand.server.well_family_house.exercisestory.service;

import com.demand.server.well_family_house.common.dto.ExerciseStory;
import com.demand.server.well_family_house.common.dto.Notification;

public interface ExerciseStoryService {

	int insertExerciseStory(ExerciseStory exerciseStory, Notification notification) throws Exception;

	ExerciseStory selectExerciseStory(int exercise_story_id) throws Exception;

}
