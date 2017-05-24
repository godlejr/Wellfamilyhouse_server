package com.demand.server.well_family_house.falldiagnosis.service;

import java.util.ArrayList;

import com.demand.server.well_family_house.common.dto.FallDiagnosisCategory;
import com.demand.server.well_family_house.common.dto.SelfDiagnosisCategory;

public interface FallDiagnosisService {

	ArrayList<FallDiagnosisCategory> selectFallDiagnosisCategoryList() throws Exception;

	ArrayList<SelfDiagnosisCategory> selectSelfDiagnosisCategoryList(int fall_diagnosis_category_id) throws Exception;

	

}
