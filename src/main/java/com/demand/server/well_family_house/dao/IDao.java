package com.demand.server.well_family_house.dao;

import java.util.ArrayList;

import com.demand.server.well_family_house.dto.*;

public interface IDao {
	ArrayList<User> email_check(String email);
	ArrayList<User> login(String email, String password);
	void join(String email,String password, String name, String birth, String phone);
	ArrayList<Family> getFamilyInfo(int user_id);
	ArrayList<FamilyUserInfo> getFamilyUserInfo(int family_id);
}
