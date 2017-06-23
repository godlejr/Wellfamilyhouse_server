package com.demand.server.well_family_house.exercise.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.ExerciseCategory;

@Repository
public interface ExerciseMapper {

	ArrayList<ExerciseCategory> selectExerciseCategoryList() throws Exception;

}
