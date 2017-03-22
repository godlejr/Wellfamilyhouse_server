package com.demand.server.well_family_house.main.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.Category;
import com.demand.server.well_family_house.common.dto.User;

@Repository
public interface MainMapper {
	int selectEmailCheck(String email) throws Exception;;

	User selectLogin(String email, String password) throws Exception;;

	void insertJoin(String email, String password, String name, String birth, String phone, int login_category_id)
			throws Exception;;

	ArrayList<Category> selectReportCategoryList() throws Exception;;

	void updateTemperaryPassword(int user_id, String temperary_password) throws Exception;
	
	User selectUserInfoFromEmail(String email)throws Exception;
}
