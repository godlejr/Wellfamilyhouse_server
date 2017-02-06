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
	ArrayList<Check> checkLike(int user_id, int story_id);
	ArrayList<User> getUserInfo(int user_id);
	ArrayList<CommentInfo> getCommentList(int story_id);
	void insertComment(Comment comment);
	void insertStory(Story identifier);
	void insertPhoto(Photo photo);
	ArrayList<Story> getStory(int story_id);
	ArrayList<Comment> getComment(int comment_id);
	ArrayList<CommentCount> getSongCommentCount(int song_id);
	ArrayList<LikeCount> getSongLikeCount(int song_id);
	ArrayList<SongCategory> getSongCategoryList();
	ArrayList<Song> getSongListByHits();
	ArrayList<Song> getRandomSong(int song_id);
	ArrayList<Song> getSongListByCategory(int category_id);
	ArrayList<Song> insertSongHit(int song_id);
	ArrayList<CommentInfo> getSongCommentList(int song_id);
	void updateSongLikeUp(int user_id, int song_id);
	void updateSongLikeDown(int user_id, int song_id);
	ArrayList<Check> checkSongLike(int user_id, int song_id);
	void insertSongComment(SongComment songComment);
	ArrayList<SongComment> getSongComment(int song_id);
	ArrayList<Range> getSongRangeList();
	void insertSongStory(SongStory songStory);
	ArrayList<SongStory> getSongStory(int id);
	void insertSongPhoto(SongPhoto songPhoto);
	void insertAudio(int parseInt, String file_name);
	ArrayList<CommentCount> getSongStoryCommentCount(int song_story_id);
	ArrayList<LikeCount> getSongStoryLikeCount(int song_story_id);
	void updateSongStoryLikeUp(int user_id, int song_story_id);
	void updateSongStoryLikeDown(int user_id, int song_story_id);
	ArrayList<Check> checkSongStoryLike(int user_id, int song_story_id);
	ArrayList<Check> checkFamily(int user_id, int story_user_id);
	ArrayList<SongStory> getSongStoryPublicList(int story_user_id);
	ArrayList<SongStory> getSongStoryFamilyList(int story_user_id);
	ArrayList<SongStory> getSongStoryMeList(int story_user_id);
	ArrayList<SongPhoto> getSongStoryPhotoList(int song_story_id);
	ArrayList<CommentInfo> getSongStoryCommentList(int song_story_id);
	void insertSongStoryComment(SongStoryComment songStoryComment);
	ArrayList<SongStoryComment> getSongStoryComment(int song_story_id);
	
	
}
