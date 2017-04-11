package com.demand.server.well_family_house.user.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.Category;
import com.demand.server.well_family_house.common.dto.Family;
import com.demand.server.well_family_house.common.dto.FamilyInfoForFamilyJoin;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.SongStory;
import com.demand.server.well_family_house.common.dto.User;
import com.demand.server.well_family_house.common.dto.UserInfoForFamilyJoin;

@Repository
public interface UserMapper {

	ArrayList<Family> selectFamiliesInfo(int user_id) throws Exception;

	User selectUserInfo(int user_id) throws Exception;

	int selectFamiliesUserCheck(int user_id, int story_user_id) throws Exception;

	ArrayList<SongStory> selectSongStoryPublicList(int story_user_id) throws Exception;

	ArrayList<SongStory> selectSongStoryFamilyList(int story_user_id) throws Exception;

	ArrayList<SongStory> selectSongStoryMeList(int story_user_id) throws Exception;

	ArrayList<FamilyInfoForFamilyJoin> selectFamilySearchList(int user_id, String search) throws Exception;

	void insertFamily(Family family) throws Exception;

	void insertFamilyJoiner(int family_id, int user_id, int join_flag) throws Exception;

	int selectFamilyUserCheck(int family_id, int user_id, int other_user_id) throws Exception;

	int selectDeviceIdCheck(int user_id, String device_id) throws Exception;

	void updateToken(int user_id, String token) throws Exception;

	void updateDeviceIdToken(int user_id, String device_id, String token) throws Exception;

	ArrayList<Category> selectFavoriteCategoryList() throws Exception;

	int selectGenderCheck(int user_id) throws Exception;

	int selectFavoriteCheck(int user_id, int favorite_category_id) throws Exception;

	int selectSongCategoryCheck(int user_id, int song_category_id) throws Exception;

	String selectUserAvatar(int user_id) throws Exception;

	void updateUserAvatar(int user_id, String fileName) throws Exception;

	void deleteFavorite(int user_id) throws Exception;

	void deleteSongCategory(int user_id) throws Exception;

	void insertFavorite(int user_id, int favorite_category_id) throws Exception;

	void insertSongCategory(int user_id, int song_category_id) throws Exception;

	void updateUserInfo(int user_id, String name, String birth, String phone, int gender) throws Exception;

	ArrayList<Family> getFamilyInfoByCreator(int family_id) throws Exception;

	void insertCommentReport(int user_id, int comment_category_id, int report_category_id, int comment_id)
			throws Exception;

	ArrayList<Family> selectManageFamilies(int user_id) throws Exception;

	ArrayList<FamilyInfoForFamilyJoin> selectJoinFamilies(int user_id) throws Exception;
	
	String selectToken(int user_id) throws Exception;
	
	String selectDeviceId(int user_id) throws Exception;


}
