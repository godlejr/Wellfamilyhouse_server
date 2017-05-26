package com.demand.server.well_family_house.falldiagnosis.service;

import java.util.ArrayList;

import com.demand.server.well_family_house.common.dto.FallDiagnosisCategory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisContentCategory;

public interface FallDiagnosisService {

	ArrayList<FallDiagnosisCategory> selectFallDiagnosisCategoryList() throws Exception;

	ArrayList<FallDiagnosisContentCategory> selectSelfDiagnosisCategoryList(int fall_diagnosis_category_id) throws Exception;

	

}
