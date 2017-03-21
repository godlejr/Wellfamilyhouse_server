package com.demand.server.well_family_house.song.service;

import java.util.ArrayList;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.Range;
import com.demand.server.well_family_house.common.dto.Song;
import com.demand.server.well_family_house.common.dto.SongCategory;
import com.demand.server.well_family_house.common.dto.SongComment;
import com.demand.server.well_family_house.common.dto.SongStory;
import com.demand.server.well_family_house.common.dto.SongStoryEmotionInfo;

public interface SongService {
	ArrayList<SongCategory> selectSongCategoryList() throws Exception;

	int selectSongCommentCount(int song_id) throws Exception;

	int selectSongLikeCount(int song_id) throws Exception;

	ArrayList<Song> selectSongListByHits() throws Exception;

	Song selectRandomSong(int song_id) throws Exception;

	ArrayList<Song> selectSongListByCategory(int category_id) throws Exception;

	void updateSongHit(int song_id) throws Exception;

	ArrayList<CommentInfo> selectSongCommentList(int song_id) throws Exception;

	void insertSongLikeUp(int user_id, int song_id) throws Exception;

	void deleteSongLikeDown(int user_id, int song_id) throws Exception;

	int selectSongLikeCheck(int user_id, int song_id) throws Exception;

	ArrayList<SongStoryEmotionInfo> selectEmotionList() throws Exception;

	ArrayList<Range> selectSongRangeList() throws Exception;

	SongComment insertSongComment(SongComment songComment) throws Exception;

	SongStory insertSongStory(SongStory songStory) throws Exception;
	
	SongStory insertSongStory(SongStory songStory,Notification notification) throws Exception;

	String selectSongAvatar(int song_id) throws Exception;

}
