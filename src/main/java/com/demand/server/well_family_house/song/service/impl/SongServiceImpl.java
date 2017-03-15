package com.demand.server.well_family_house.song.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.Range;
import com.demand.server.well_family_house.common.dto.Song;
import com.demand.server.well_family_house.common.dto.SongCategory;
import com.demand.server.well_family_house.common.dto.SongComment;
import com.demand.server.well_family_house.common.dto.SongStory;
import com.demand.server.well_family_house.common.dto.SongStoryEmotionInfo;
import com.demand.server.well_family_house.song.service.SongService;

@Service
public class SongServiceImpl implements SongService {

	@Autowired
	private SongMapper songMapper;

	@Override
	public ArrayList<SongCategory> selectSongCategoryList() throws Exception {
		return songMapper.selectSongCategoryList();
	}

	@Override
	public int selectSongCommentCount(int song_id) throws Exception {
		return songMapper.selectSongCommentCount(song_id);
	}

	@Override
	public int selectSongLikeCount(int song_id) throws Exception {
		return songMapper.selectSongLikeCount(song_id);
	}

	@Override
	public ArrayList<Song> selectSongListByHits() throws Exception {
		return songMapper.selectSongListByHits();
	}

	@Override
	public Song selectRandomSong(int song_id) throws Exception {
		return songMapper.selectRandomSong(song_id);
	}

	@Override
	public ArrayList<Song> selectSongListByCategory(int category_id) throws Exception {
		return songMapper.selectSongListByCategory(category_id);
	}

	@Override
	public void updateSongHit(int song_id) throws Exception {
		songMapper.updateSongHit(song_id);
	}

	@Override
	public ArrayList<CommentInfo> selectSongCommentList(int song_id) throws Exception {
		return songMapper.selectSongCommentList(song_id);
	}

	@Override
	public void insertSongLikeUp(int user_id, int song_id) throws Exception {
		songMapper.insertSongLikeUp(user_id, song_id);
	}

	@Override
	public void deleteSongLikeDown(int user_id, int song_id) throws Exception {
		songMapper.deleteSongLikeDown(user_id, song_id);
	}

	@Override
	public int selectSongLikeCheck(int user_id, int song_id) throws Exception {
		return songMapper.selectSongLikeCheck(user_id, song_id);
	}

	@Override
	public ArrayList<SongStoryEmotionInfo> selectEmotionList() throws Exception {
		return songMapper.selectEmotionList();
	}

	@Override
	public ArrayList<Range> selectSongRangeList() throws Exception {
		return songMapper.selectSongRangeList();
	}

	@Override
	public SongComment insertSongComment(SongComment songComment) throws Exception {
		songMapper.insertSongComment(songComment);
		return songMapper.selectSongComment(songComment.getId());
	}

	@Override
	public SongStory insertSongStory(SongStory songStory) throws Exception {
		songMapper.insertSongStory(songStory);
		return songMapper.selectSongStory(songStory.getId());
	}

}
