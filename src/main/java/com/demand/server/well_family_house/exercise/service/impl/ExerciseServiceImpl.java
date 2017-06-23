package com.demand.server.well_family_house.exercise.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.ExerciseCategory;
import com.demand.server.well_family_house.exercise.service.ExerciseService;

@Service
public class ExerciseServiceImpl implements ExerciseService {
	
	@Autowired
	private ExerciseMapper exerciseMapper;

	@Override
	public ArrayList<ExerciseCategory> selectExerciseCategoryList() throws Exception {
		return exerciseMapper.selectExerciseCategoryList();
	}
}
