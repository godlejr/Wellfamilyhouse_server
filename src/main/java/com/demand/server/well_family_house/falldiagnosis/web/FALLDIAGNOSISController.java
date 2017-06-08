package com.demand.server.well_family_house.falldiagnosis.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.EnvironmentEvaluationCategory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisCategory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisContentCategory;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluationCategory;
import com.demand.server.well_family_house.falldiagnosis.service.FallDiagnosisService;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/fall_diagnosis")
public class FALLDIAGNOSISController {

	@Autowired
	private FallDiagnosisService fallDiagnosisService;

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ArrayList<FallDiagnosisCategory> categoryList() throws Exception {
		return fallDiagnosisService.selectFallDiagnosisCategoryList();
	}

	@RequestMapping(value = "/categories/{fall_diagnosis_category_id}", method = RequestMethod.GET)
	public ArrayList<FallDiagnosisContentCategory> fallDiagnosisContentCategoryList(
			@PathVariable int fall_diagnosis_category_id) throws Exception {
		return fallDiagnosisService.selectFallDiagnosisContentCategoryList(fall_diagnosis_category_id);
	}

	@RequestMapping(value = "/categories/{fall_diagnosis_category_id}/physicalEvaluationCategories", method = RequestMethod.GET)
	public ArrayList<PhysicalEvaluationCategory> physicalEvaluationCategoryList(
			@PathVariable int fall_diagnosis_category_id) throws Exception {
		return fallDiagnosisService.selectPhysicalEvaluationCategoryList(fall_diagnosis_category_id);
	}

	@RequestMapping(value = "/categories/{fall_diagnosis_category_id}/environmentEvaluationCategories/{fall_diagnosis_content_category_id}", method = RequestMethod.GET)
	public ArrayList<EnvironmentEvaluationCategory> environmentEvaluationCategoryList(
			@PathVariable int fall_diagnosis_category_id, @PathVariable int fall_diagnosis_content_category_id)
			throws Exception {
		return fallDiagnosisService.selectEnvironmentEvaluationCategoryList(fall_diagnosis_category_id,
				fall_diagnosis_content_category_id);
	}

}
