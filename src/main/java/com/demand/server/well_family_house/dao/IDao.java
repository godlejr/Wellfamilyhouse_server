package com.demand.server.well_family_house.dao;

import java.util.ArrayList;

import com.demand.server.well_family_house.dto.*;

public interface IDao {
	ArrayList<User> email_check(String email);
	ArrayList<User> login(String email, String password);
	void join(String email,String password, String name, String birth, String phone);
	ArrayList<Family> getFamilyInfo(int user_id);
	ArrayList<User> getFamilyUserInfo(int family_id,int user_id);
	ArrayList<StoryInfo> getContentList(int family_id);
	ArrayList<ContentCount> getContentCount(int story_id);
	ArrayList<Photo> getPhotoList(int story_id);
}
