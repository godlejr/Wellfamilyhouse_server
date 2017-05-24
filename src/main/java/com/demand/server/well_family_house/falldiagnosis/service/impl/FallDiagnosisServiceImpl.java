package com.demand.server.well_family_house.falldiagnosis.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.FallDiagnosisCategory;
import com.demand.server.well_family_house.common.dto.SelfDiagnosisCategory;
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
	public ArrayList<SelfDiagnosisCategory> selectSelfDiagnosisCategoryList(int fall_diagnosis_category_id)
			throws Exception {
		return fallDiagnosisMapper.selectSelfDiagnosisCategoryList(fall_diagnosis_category_id);
	}

}
