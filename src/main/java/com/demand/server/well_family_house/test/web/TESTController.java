package com.demand.server.well_family_house.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.test.service.TestService;
import com.demand.server.well_family_house.test.service.impl.TestServiceImpl;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/test")
	
public class TESTController {
	@Autowired
	TestService testService;
	
	@RequestMapping("/")
	public void insert() throws Exception{
		testService.insertTest("동주");
	}
}
