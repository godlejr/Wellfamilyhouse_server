package com.demand.server.well_family_house.story.service.impl;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.Comment;
import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.Photo;
import com.demand.server.well_family_house.common.dto.Story;
import com.demand.server.well_family_house.common.dto.StoryInfoForNotification;

@Repository
public interface StoryMapper {

	int selectCommentCount(int story_id) throws Exception;

	int selectLikeCount(int story_id) throws Exception;

	ArrayList<Photo> selectContentPhotoList(int story_id) throws Exception;

	void insertLikeUp(int user_id, int story_id) throws NumberFormatException, Exception;

	void deleteLikeDown(int user_id, int story_id) throws Exception;

	int selectLikeCheck(int user_id, int story_id) throws Exception;

	ArrayList<CommentInfo> selectCommentList(int story_id) throws Exception;

	void insertComment(Comment comment) throws Exception;

	Comment selectComment(int comment_id) throws Exception;

	void insertStory(Story identifier) throws Exception;

	Story selectStory(int story_id) throws Exception;

	void insertPhoto(Photo photo) throws IOException,Exception;
	
	int selectUser(int story_id) throws Exception;

	StoryInfoForNotification selectStoryInfo(int story_id) throws Exception;
	
	void updateStory(int story_id, String content) throws Exception;
	
	ArrayList<String> selectPhotoName(int story_id) throws Exception;
	
	void deletePhotos(int story_id) throws Exception;
}
