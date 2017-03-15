package com.demand.server.well_family_house.main.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.Category;
import com.demand.server.well_family_house.common.dto.User;

@Repository
public interface MainMapper {
	User selectEmailCheck(String email);

	User selectLogin(String email, String password);

	void insertJoin(String email, String password, String name, String birth, String phone, int login_category_id);

	ArrayList<Category> selectReportCategoryList();

}
