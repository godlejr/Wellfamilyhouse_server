package com.demand.server.well_family_house.family.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.Family;
import com.demand.server.well_family_house.common.dto.Photo;
import com.demand.server.well_family_house.common.dto.StoryInfo;
import com.demand.server.well_family_house.common.dto.User;
import com.demand.server.well_family_house.common.dto.UserInfoForFamilyJoin;


@Repository
public interface FamilyMapper {
	Family selectFamily(int family_id) throws Exception;

	ArrayList<User> selectFamilyUsersInfo(int family_id, int user_id) throws Exception;

	ArrayList<StoryInfo> selectContentList(int family_id) throws Exception;

	ArrayList<Photo> selectPhotoList(int family_id) throws Exception;

	String selectFamilyAvatar(int family_id) throws Exception;

	void updateFamilyAvatar(int family_id, String file_name) throws Exception;
	
	int selectFamilyUserId(int family_id) throws Exception;

	void insertUserIntoFamily(int family_id, int user_id,int join_flag) throws Exception;

	void deleteUserFromFamily(int family_id, int user_id) throws Exception;

	void updateFamilyInfo(int family_id, String name, String content) throws Exception;

}
