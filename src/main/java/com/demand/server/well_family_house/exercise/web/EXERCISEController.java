package com.demand.server.well_family_house.exercise.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.ExerciseCategory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisCategory;
import com.demand.server.well_family_house.exercise.service.ExerciseService;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/exercises")
public class EXERCISEController {

	@Autowired
	private ExerciseService exerciseService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ArrayList<ExerciseCategory> selectExerciseCategoryList() throws Exception {
		return exerciseService.selectExerciseCategoryList();
	}
}
