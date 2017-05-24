package com.demand.server.well_family_house.falldiagnosis.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.FallDiagnosisCategory;
import com.demand.server.well_family_house.falldiagnosis.service.FallDiagnosisService;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/fall_diagnosis")
public class FallDiagnosisController {

	@Autowired
	private FallDiagnosisService fallDiagnosisService;
	
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ArrayList<FallDiagnosisCategory> categoryList() throws Exception {
		return fallDiagnosisService.selectFallDiagnosisCategoryList();
	}

}
