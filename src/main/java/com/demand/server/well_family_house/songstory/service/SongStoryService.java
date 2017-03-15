package com.demand.server.well_family_house.songstory.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.SongPhoto;
import com.demand.server.well_family_house.common.dto.SongStoryComment;
import com.demand.server.well_family_house.common.dto.SongStoryEmotionData;

public interface SongStoryService {
	int selectSongStoryCommentCount(int song_story_id) throws Exception;

	int selectSongStoryLikeCount(int song_story_id) throws Exception;

	void insertSongStoryLikeUp(int user_id, int song_story_id) throws Exception;

	void deleteSongStoryLikeDown(int user_id, int song_story_id) throws Exception;

	int selectSongStoryLikeCheck(int user_id, int song_story_id) throws Exception;

	ArrayList<SongPhoto> selectSongStoryPhotoList(int song_story_id) throws Exception;

	ArrayList<CommentInfo> selectSongStoryCommentList(int song_story_id) throws Exception;

	String selectSongStoryAvatar(int song_id) throws Exception;

	void insertEmotionIntoSongStory(int song_story_id, int song_story_emotion_id)
			throws NumberFormatException, Exception;

	ArrayList<SongStoryEmotionData> selectSongStoryEmotionData(int song_story_id) throws Exception;

	void insertSongStoryPhoto(InputStream base64InputStream, int song_story_id) throws IOException, Exception;

	void insertSongStoryAudio(InputStream base64InputStream, int song_story_id) throws IOException, Exception;

	SongStoryComment insertSongStoryComment(SongStoryComment songStoryComment) throws Exception;

}
