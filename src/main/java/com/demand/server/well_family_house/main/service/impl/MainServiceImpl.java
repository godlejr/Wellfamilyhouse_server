package com.demand.server.well_family_house.main.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.Category;
import com.demand.server.well_family_house.common.dto.User;
import com.demand.server.well_family_house.common.util.JwtUtil;
import com.demand.server.well_family_house.main.service.MainService;

@Service
public class MainServiceImpl implements MainService{

	@Autowired
	private MainMapper mainMapper;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public int selectEmailCheck(String email) throws Exception {
		return mainMapper.selectEmailCheck(email);
	}

	@Override
	public User selectLogin(String email, String password) throws Exception {
		User user = mainMapper.selectLogin(email, password);

		if (user != null) {
			user.setAccess_token(jwtUtil.generateToken(user.getLevel()));
			return user;
		} else {
			return null;
		}
	}

	@Override
	public void insertJoin(String email, String password, String name, String birth, String phone,
			int login_category_id) throws Exception {
		mainMapper.insertJoin(email, password, name, birth, phone, login_category_id);
	}

	@Override
	public ArrayList<Category> selectReportCategoryList() throws Exception {
		return mainMapper.selectReportCategoryList();
	}

}
