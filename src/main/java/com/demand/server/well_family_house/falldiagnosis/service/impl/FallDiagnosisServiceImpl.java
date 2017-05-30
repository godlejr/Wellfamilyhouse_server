package com.demand.server.well_family_house.falldiagnosis.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.EnvironmentEvaluationCategory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisCategory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisContentCategory;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluationCategory;
import com.demand.server.well_family_house.falldiagnosis.service.FallDiagnosisService;

@Service
public class FallDiagnosisServiceImpl implements FallDiagnosisService {

	@Autowired
	private FallDiagnosisMapper fallDiagnosisMapper;
	
	@Override
	public ArrayList<FallDiagnosisCategory> selectFallDiagnosisCategoryList() throws Exception {
		return fallDiagnosisMapper.selectFallDiagnosisCategoryList();
	}

	@Override
	public ArrayList<FallDiagnosisContentCategory> selectFallDiagnosisContentCategoryList(int fall_diagnosis_category_id)
			throws Exception {
		return fallDiagnosisMapper.selectFallDiagnosisContentCategoryList(fall_diagnosis_category_id);
	}

	@Override
	public ArrayList<PhysicalEvaluationCategory> selectPhysicalEvaluationCategoryList(int fall_diagnosis_category_id)
			throws Exception {
		return fallDiagnosisMapper.selectPhysicalEvaluationCategoryList(fall_diagnosis_category_id);
	}

	@Override
	public ArrayList<EnvironmentEvaluationCategory> selectEnvironmentEvaluationCategoryList(
			int fall_diagnosis_category_id, int fall_diagnosis_content_category_id) throws Exception {
		return fallDiagnosisMapper.selectEnvironmentEvaluationCategoryList(fall_diagnosis_category_id, fall_diagnosis_content_category_id);
	}

}
