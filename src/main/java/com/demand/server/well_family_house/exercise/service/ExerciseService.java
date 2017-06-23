package com.demand.server.well_family_house.exercise.service;

import java.util.ArrayList;

import com.demand.server.well_family_house.common.dto.ExerciseCategory;

public interface ExerciseService {

	ArrayList<ExerciseCategory> selectExerciseCategoryList() throws Exception;

}
