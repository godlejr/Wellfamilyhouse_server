package com.demand.server.well_family_house.main.service;

import java.util.ArrayList;

import com.demand.server.well_family_house.common.dto.Category;
import com.demand.server.well_family_house.common.dto.User;

public interface MainService {

	int selectEmailCheck(String email) throws Exception;

	User selectLogin(String email, String password) throws Exception;

	void insertJoin(String email, String password, String name, String birth, String phone, int login_category_id)
			throws Exception;

	ArrayList<Category> selectReportCategoryList() throws Exception;

}
