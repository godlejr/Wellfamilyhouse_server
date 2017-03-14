package com.demand.server.well_family_house.dao;

import java.util.ArrayList;

import com.demand.server.well_family_house.dto.Category;
import com.demand.server.well_family_house.dto.Comment;
import com.demand.server.well_family_house.dto.CommentInfo;
import com.demand.server.well_family_house.dto.Family;
import com.demand.server.well_family_house.dto.Notification;
import com.demand.server.well_family_house.dto.NotificationInfo;
import com.demand.server.well_family_house.dto.Photo;
import com.demand.server.well_family_house.dto.Range;
import com.demand.server.well_family_house.dto.Song;
import com.demand.server.well_family_house.dto.SongCategory;
import com.demand.server.well_family_house.dto.SongComment;
import com.demand.server.well_family_house.dto.SongPhoto;
import com.demand.server.well_family_house.dto.SongStory;
import com.demand.server.well_family_house.dto.SongStoryComment;
import com.demand.server.well_family_house.dto.SongStoryEmotionData;
import com.demand.server.well_family_house.dto.SongStoryEmotionInfo;
import com.demand.server.well_family_house.dto.Story;
import com.demand.server.well_family_house.dto.StoryInfo;
import com.demand.server.well_family_house.dto.Token;
import com.demand.server.well_family_house.dto.User;

public interface IDao {
	User email_check(String email);

	User login(String email, String password);

	void join(String email, String password, String name, String birth, String phone, int login_category_id);

	ArrayList<Family> getFamilyInfo(int user_id);

	ArrayList<User> getFamilyUserInfo(int family_id, int user_id);

	ArrayList<StoryInfo> getContentList(int family_id);

	ArrayList<Photo> getContentPhotoList(int story_id);

	ArrayList<Photo> getPhotoList(int family_id);

	int getCommentCount(int story_id);

	int getLikeCount(int story_id);

	void updateLikeUp(int user_id, int story_id);

	void updateLikeDown(int user_id, int story_id);

	int checkLike(int user_id, int story_id);

	User getUserInfo(int user_id);

	ArrayList<CommentInfo> getCommentList(int story_id);

	void insertComment(Comment comment);

	void insertStory(Story identifier);

	void insertPhoto(Photo photo);

	Story getStory(int story_id);

	Comment getComment(int comment_id);

	int getSongCommentCount(int song_id);

	int getSongLikeCount(int song_id);

	ArrayList<SongCategory> getSongCategoryList();

	ArrayList<Song> getSongListByHits();

	Song getRandomSong(int song_id);

	ArrayList<Song> getSongListByCategory(int category_id);

	void insertSongHit(int song_id);

	ArrayList<CommentInfo> getSongCommentList(int song_id);

	void updateSongLikeUp(int user_id, int song_id);

	void updateSongLikeDown(int user_id, int song_id);

	int checkSongLike(int user_id, int song_id);

	void insertSongComment(SongComment songComment);

	SongComment getSongComment(int song_id);

	ArrayList<Range> getSongRangeList();

	void insertSongStory(SongStory songStory);

	SongStory getSongStory(int id);

	void insertSongPhoto(SongPhoto songPhoto);

	void insertAudio(int parseInt, String file_name);

	int getSongStoryCommentCount(int song_story_id);

	int getSongStoryLikeCount(int song_story_id);

	void updateSongStoryLikeUp(int user_id, int song_story_id);

	void updateSongStoryLikeDown(int user_id, int song_story_id);

	int checkSongStoryLike(int user_id, int song_story_id);

	int checkFamily(int user_id, int story_user_id);

	ArrayList<SongStory> getSongStoryPublicList(int story_user_id);

	ArrayList<SongStory> getSongStoryFamilyList(int story_user_id);

	ArrayList<SongStory> getSongStoryMeList(int story_user_id);

	ArrayList<SongPhoto> getSongStoryPhotoList(int song_story_id);

	ArrayList<CommentInfo> getSongStoryCommentList(int song_story_id);

	void insertSongStoryComment(SongStoryComment songStoryComment);

	SongStoryComment getSongStoryComment(int song_story_id);

	String getSongStoryAvatar(int song_id);

	ArrayList<Family> getFamilySearchList(String family_name);

	void insertFamily(Family family);

	void insertFamilyJoiner(int family_id, int user_id);

	void updateFamilyAvatar(int family_id, String file_name);

	ArrayList<User> getUserSearchList(String search);

	Family getFamily(int family_id);

	void insertUserIntoFamily(int family_id, int user_id);

	void deleteUserFromFamily(int family_id, int user_id);

	ArrayList<SongStoryEmotionInfo> getEmotionList();

	void insertEmotionIntoSongStory(int song_story_id, int song_story_emotion_id);

	ArrayList<SongStoryEmotionData> getSongStoryEmotionData(int song_story_id);

	int getFamilyUserCheck(int family_id, int user_id, int other_user_id);

	int checkDeviceId(int user_id, String device_id);

	void updateToken(int user_id, String token);

	void updateDeviceIdToken(int user_id, String device_id, String token);

	ArrayList<Category> getFavoriteCategoryList();

	int getCheckGender(int user_id);

	int getCheckFavorite(int user_id, int favorite_category_id);

	int getCheckSongCategory(int user_id, int song_category_id);

	User getUserAvatar(int user_id);

	void updateUserAvatar(int user_id, String fileName);

	Family getFamilyAvatar(int family_id);

	void deleteFavorite(int user_id);

	void deleteSongCategory(int user_id);

	void insertFavorite(int user_id, int favorite_category_id);

	void insertSongCategory(int user_id, int song_category_id);

	void udpateUserInfo(int user_id, String name, String birth, String phone, int gender);

	void updateFamilyInfo(int family_id, String name, String content);

	ArrayList<Family> getFamilyInfoByCreator(int family_id);

	void updateComment(int comment_id, String content);

	void updateSongComment(int comment_id, String content);

	void updateSongStoryComment(int comment_id, String content);

	void deleteComment(int comment_id);

	void deleteSongComment(int comment_id);

	void deleteSongStoryComment(int comment_id);

	ArrayList<Category> getReportCategoryList();

	void insertCommentReport(int user_id, int comment_category_id, int report_category_id, int comment_id);

	void insertNotification(Notification notification);

	ArrayList<Token> getTokenForMe(int receiver_id);

	void insertUserNotification(int id, int notification_id);

	String getBodyForNotification(int notification_id);

	ArrayList<Notification> getNotification(int user_id);

	NotificationInfo getNotificationForCreatingFamily(int notification_id);

	void updateNotificationCheck(int notification_id);

	ArrayList<Token> getTokenForFamily(int receiver_id, int user_id);

	NotificationInfo getNotificationForWritingStory(int notification_id);

}
