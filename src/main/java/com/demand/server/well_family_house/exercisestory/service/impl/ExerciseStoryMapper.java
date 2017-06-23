package com.demand.server.well_family_house.exercisestory.service.impl;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.ExerciseStory;

@Repository
public interface ExerciseStoryMapper {

	void insertExerciseStory(ExerciseStory exerciseStory) throws Exception;

}
