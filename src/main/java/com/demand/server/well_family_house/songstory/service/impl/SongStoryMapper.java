package com.demand.server.well_family_house.songstory.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.SongPhoto;
import com.demand.server.well_family_house.common.dto.SongStoryComment;
import com.demand.server.well_family_house.common.dto.SongStoryEmotionData;
import com.demand.server.well_family_house.common.dto.SongStoryInfoForNotification;

@Repository
public interface SongStoryMapper {

	void insertSongPhoto(SongPhoto songPhoto) throws Exception;

	void updateAudio(int parseInt, String file_name) throws Exception;

	int selectSongStoryCommentCount(int song_story_id) throws Exception;

	int selectSongStoryLikeCount(int song_story_id) throws Exception;

	void insertSongStoryLikeUp(int user_id, int song_story_id) throws Exception;

	void deleteSongStoryLikeDown(int user_id, int song_story_id) throws Exception;

	int selectSongStoryLikeCheck(int user_id, int song_story_id) throws Exception;

	ArrayList<SongPhoto> selectSongStoryPhotoList(int song_story_id) throws Exception;

	ArrayList<CommentInfo> selectSongStoryCommentList(int song_story_id) throws Exception;

	void insertSongStoryComment(SongStoryComment songStoryComment) throws Exception;

	SongStoryComment selectSongStoryComment(int song_story_id) throws Exception;

	void insertEmotionIntoSongStory(int song_story_id, int song_story_emotion_id)
			throws NumberFormatException, Exception;

	ArrayList<SongStoryEmotionData> selectSongStoryEmotionData(int song_story_id) throws Exception;

	int selectUser(int story_id) throws Exception;

	SongStoryInfoForNotification selectSongStoryInfo(int song_story_id) throws Exception;

	void updateStory(int song_story_id, String content, String location) throws Exception;

	ArrayList<String> selectPhotoName(int song_story_id) throws Exception;

	void deletePhotos(int song_story_id) throws Exception;

	void deleteStory(int song_story_id) throws Exception;
	
	String selectRecordFileName(int song_story_id) throws Exception;
	
}
