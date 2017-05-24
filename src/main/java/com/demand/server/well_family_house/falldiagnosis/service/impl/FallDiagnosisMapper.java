package com.demand.server.well_family_house.falldiagnosis.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.FallDiagnosisCategory;
import com.demand.server.well_family_house.common.dto.SelfDiagnosisCategory;

@Repository
public interface FallDiagnosisMapper {

	ArrayList<FallDiagnosisCategory> selectFallDiagnosisCategoryList() throws Exception;

	ArrayList<SelfDiagnosisCategory> selectSelfDiagnosisCategoryList(int fall_diagnosis_category_id) throws Exception;

}
