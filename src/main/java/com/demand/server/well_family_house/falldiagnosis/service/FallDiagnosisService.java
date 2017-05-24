package com.demand.server.well_family_house.falldiagnosis.service;

import java.util.ArrayList;

import com.demand.server.well_family_house.common.dto.FallDiagnosisCategory;

public interface FallDiagnosisService {

	ArrayList<FallDiagnosisCategory> selectFallDiagnosisCategoryList() throws Exception;

}
