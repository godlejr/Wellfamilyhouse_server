package com.demand.server.well_family_house.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.test.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	TestMapper testMapper;

	@Override
	public void insertTest(String name) throws Exception {
		testMapper.insertTest(name);
		testMapper.insertTest(null);
	}
	
	
}
