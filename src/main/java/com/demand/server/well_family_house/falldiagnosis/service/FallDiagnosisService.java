package com.demand.server.well_family_house.falldiagnosis.service;

import java.util.ArrayList;

import com.demand.server.well_family_house.common.dto.FallDiagnosisCategory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisContentCategory;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluationCategory;

public interface FallDiagnosisService {

	ArrayList<FallDiagnosisCategory> selectFallDiagnosisCategoryList() throws Exception;

	ArrayList<FallDiagnosisContentCategory> selectFallDiagnosisContentCategoryList(int fall_diagnosis_category_id) throws Exception;

	ArrayList<PhysicalEvaluationCategory> selectPhysicalEvaluationCategoryList(int fall_diagnosis_category_id)throws Exception;

	

}
