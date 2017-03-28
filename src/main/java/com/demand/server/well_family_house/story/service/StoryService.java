package com.demand.server.well_family_house.story.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.demand.server.well_family_house.common.dto.Comment;
import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.Photo;
import com.demand.server.well_family_house.common.dto.Story;
import com.demand.server.well_family_house.common.dto.StoryInfoForNotification;

public interface StoryService {
	int selectCommentCount(int story_id) throws Exception;

	int selectLikeCount(int story_id) throws Exception;

	ArrayList<Photo> selectContentPhotoList(int story_id) throws Exception;

	void insertLikeUp(int user_id, int story_id, Notification notification) throws NumberFormatException, Exception;

	void deleteLikeDown(int user_id, int story_id) throws Exception;

	int selectLikeCheck(int user_id, int story_id) throws Exception;

	ArrayList<CommentInfo> selectCommentList(int story_id) throws Exception;

	Comment insertComment(Comment comment, Notification notification) throws Exception;

	Story insertStory(Story story, Notification notification) throws Exception;

	void insertPhoto(InputStream base64InputStream, int story_id) throws IOException, Exception;

	StoryInfoForNotification selectStoryInfo(int story_id) throws Exception;

	void updateStory(int story_id, String content) throws Exception;

}
