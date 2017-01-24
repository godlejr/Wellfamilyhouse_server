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
	ArrayList<Photo> getContentPhotoList(int story_id);
	ArrayList<Photo> getPhotoList(int family_id);
	ArrayList<CommentCount> getCommentCount(int story_id);
	ArrayList<LikeCount> getLikeCount(int story_id);
	void updateLikeUp(int user_id, int story_id);
	void updateLikeDown(int user_id, int story_id);
	ArrayList<CheckBox> checkLike(int user_id, int story_id);
	ArrayList<User> getUserInfo(int user_id);
	ArrayList<Comment> getCommentList(int story_id);
}
