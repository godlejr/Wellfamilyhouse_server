package com.demand.server.well_family_house.family.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.demand.server.well_family_house.common.dto.Family;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.Photo;
import com.demand.server.well_family_house.common.dto.StoryInfo;
import com.demand.server.well_family_house.common.dto.User;
import com.demand.server.well_family_house.common.dto.UserInfoForFamilyJoin;

public interface FamilyService {

	Family selectFamily(int family_id) throws Exception;

	ArrayList<User> selectFamilyUsersInfo(int family_id, int user_id) throws Exception;

	ArrayList<StoryInfo> selectContentList(int family_id) throws Exception;

	ArrayList<Photo> selectPhotoList(int family_id) throws Exception;

	void updateFamilyAvatar(InputStream base64InputStream, int family_id) throws IOException, Exception;

	void insertUserIntoFamily(int family_id, int user_id, int join_flag, Notification notification) throws Exception;

	void deleteUserFromFamily(int family_id, int user_id) throws Exception;

	void updateFamilyInfo(int family_id, String name, String content) throws Exception;

	ArrayList<UserInfoForFamilyJoin> selectUserSearchList(int family_id,String search) throws Exception;

	void updateUserForFamilyJoin(int family_id,int user_id, Notification notification) throws Exception;
}
