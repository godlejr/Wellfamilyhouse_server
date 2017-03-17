package com.demand.server.well_family_house.song.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.Range;
import com.demand.server.well_family_house.common.dto.Song;
import com.demand.server.well_family_house.common.dto.SongCategory;
import com.demand.server.well_family_house.common.dto.SongComment;
import com.demand.server.well_family_house.common.dto.SongStory;
import com.demand.server.well_family_house.common.dto.SongStoryEmotionInfo;

@Repository
public interface SongMapper {
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

	void insertSongComment(SongComment songComment) throws Exception;

	SongComment selectSongComment(int song_id) throws Exception;

	ArrayList<Range> selectSongRangeList() throws Exception;

	void insertSongStory(SongStory songStory) throws Exception;

	SongStory selectSongStory(int id) throws Exception;
	
	ArrayList<SongStoryEmotionInfo> selectEmotionList()throws Exception;


	String selectSongAvatar(int song_id) throws Exception;

}
