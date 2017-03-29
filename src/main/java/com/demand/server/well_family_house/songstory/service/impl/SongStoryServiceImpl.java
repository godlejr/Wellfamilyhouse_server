package com.demand.server.well_family_house.songstory.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.SongPhoto;
import com.demand.server.well_family_house.common.dto.SongStoryComment;
import com.demand.server.well_family_house.common.dto.SongStoryEmotionData;
import com.demand.server.well_family_house.common.dto.SongStoryInfoForNotification;
import com.demand.server.well_family_house.common.flag.LogFlag;
import com.demand.server.well_family_house.common.util.AndroidPushConnection;
import com.demand.server.well_family_house.common.util.AwsS3Connection;
import com.demand.server.well_family_house.notification.service.impl.NotificationMapper;
import com.demand.server.well_family_house.songstory.service.SongStoryService;

@Service
public class SongStoryServiceImpl implements SongStoryService {
	@Autowired
	private SongStoryMapper songStoryMapper;

	@Autowired
	private NotificationMapper notificationMapper;

	@Autowired
	private AndroidPushConnection androidPushConnection;
	
	@Autowired
	private AwsS3Connection awsS3Connection;

	private static final Logger logger = LoggerFactory.getLogger(SongStoryServiceImpl.class);

	public static void log(Exception e) {
		StackTraceElement[] ste = e.getStackTrace();
		String className = ste[0].getClassName();
		String methodName = ste[0].getMethodName();
		int lineNumber = ste[0].getLineNumber();
		String fileName = ste[0].getFileName();

		if (LogFlag.printFlag) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception: " + e.getMessage());
				logger.info(className + "." + methodName + " " + fileName + " " + lineNumber + " " + "line");
			}
		}
	}

	@Override
	public int selectSongStoryCommentCount(int song_story_id) throws Exception {
		return songStoryMapper.selectSongStoryCommentCount(song_story_id);
	}

	@Override
	public int selectSongStoryLikeCount(int song_story_id) throws Exception {
		return songStoryMapper.selectSongStoryLikeCount(song_story_id);
	}

	@Override
	public void insertSongStoryLikeUp(int user_id, int song_story_id,Notification notification) throws Exception {
		
		int song_story_user_id = songStoryMapper.selectUser(notification.getIntent_id());

		if (user_id != song_story_user_id) {
			notification.setReceiver_id(song_story_user_id);
			notificationMapper.insertNotification(notification);
			androidPushConnection.insertFCM(notification);
		}
		songStoryMapper.insertSongStoryLikeUp(user_id, song_story_id);
	}

	@Override
	public void deleteSongStoryLikeDown(int user_id, int song_story_id) throws Exception {
		songStoryMapper.deleteSongStoryLikeDown(user_id, song_story_id);
	}

	@Override
	public int selectSongStoryLikeCheck(int user_id, int song_story_id) throws Exception {
		return songStoryMapper.selectSongStoryLikeCheck(user_id, song_story_id);
	}

	@Override
	public ArrayList<SongPhoto> selectSongStoryPhotoList(int song_story_id) throws Exception {
		return songStoryMapper.selectSongStoryPhotoList(song_story_id);
	}

	@Override
	public ArrayList<CommentInfo> selectSongStoryCommentList(int song_story_id) throws Exception {
		return songStoryMapper.selectSongStoryCommentList(song_story_id);
	}

	@Override
	public void insertEmotionIntoSongStory(int song_story_id, int song_story_emotion_id)
			throws NumberFormatException, Exception {
		songStoryMapper.insertEmotionIntoSongStory(song_story_id, song_story_emotion_id);
	}

	@Override
	public ArrayList<SongStoryEmotionData> selectSongStoryEmotionData(int song_story_id) throws Exception {
		return songStoryMapper.selectSongStoryEmotionData(song_story_id);
	}

	@Override
	public void insertSongStoryPhoto(InputStream base64InputStream, int song_story_id) throws IOException, Exception {
		String file_name = null;
		StringBuilder stringBuilder = null;
		try {
			if (base64InputStream != null) {
				stringBuilder = new StringBuilder();
				String line;
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(base64InputStream, "UTF-8"));
					while ((line = reader.readLine()) != null) {
						stringBuilder.append(line).append("\n");
					}
				} catch (Exception e) {
					log(e);
				} finally {
					base64InputStream.close();
				}
			}
		} catch (IOException e) {
			log(e);
		}

		try {
			file_name = awsS3Connection.uploadFileToAWSS3(stringBuilder.toString(),
					"apps/well_family_house/images/songstories", ".jpg");
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}

		SongPhoto songPhoto = new SongPhoto();
		songPhoto.setSong_story_id(song_story_id);
		songPhoto.setType(0);
		songPhoto.setName(file_name);
		songPhoto.setExt("jpg");

		songStoryMapper.insertSongPhoto(songPhoto);
	}

	@Override
	public void insertSongStoryAudio(InputStream base64InputStream, int song_story_id) throws IOException, Exception {
		String file_name = null;
		StringBuilder stringBuilder = null;
		try {
			if (base64InputStream != null) {
				stringBuilder = new StringBuilder();
				String line;
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(base64InputStream, "UTF-8"));
					while ((line = reader.readLine()) != null) {
						stringBuilder.append(line).append("\n");
					}
				} catch (Exception e) {
					log(e);
				} finally {
					base64InputStream.close();
				}
			}
		} catch (IOException e) {
			log(e);
		}

		try {
			file_name = awsS3Connection.uploadFileToAWSS3(stringBuilder.toString(),
					"apps/well_family_house/songs/records", ".mp3");
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}
		songStoryMapper.updateAudio(song_story_id, file_name + ".mp3");
	}

	@Override
	public SongStoryComment insertSongStoryComment(SongStoryComment songStoryComment, Notification notification) throws Exception {
		int song_story_user_id = songStoryMapper.selectUser(notification.getIntent_id());

		if (song_story_user_id != songStoryComment.getUser_id()) {
			notification.setReceiver_id(song_story_user_id);
			notificationMapper.insertNotification(notification);
			androidPushConnection.insertFCM(notification);
		}
		songStoryMapper.insertSongStoryComment(songStoryComment);
		return songStoryMapper.selectSongStoryComment(songStoryComment.getId());
	}

	@Override
	public SongStoryInfoForNotification selectSongStoryInfo(int song_story_id) throws Exception {
		return songStoryMapper.selectSongStoryInfo(song_story_id);
	}

	
	@Override
	public void updateStory(int song_story_id, String content, String location) throws Exception {
		boolean transaction_flag = true;
		ArrayList<String> photoList = null;
		int photoSize =0;
		
		try {
			songStoryMapper.updateStory(song_story_id, content, location);
			photoList = songStoryMapper.selectPhotoName(song_story_id);
			photoSize = photoList.size();
			
			if (photoSize > 0) {
				songStoryMapper.deletePhotos(song_story_id);
			}
			
		} catch (Exception e) {
			transaction_flag = false;
		}
		
		if (transaction_flag) {
			if (photoSize > 0) {
				for (int i = 0; i < photoSize; i++) {
					awsS3Connection.deleteFileFromAWSS3("apps/well_family_house/images/songstories", photoList.get(i),
							"jpg");
				}
			}
		}
	}

	@Override
	public void deleteStory(int song_story_id) throws Exception {
		boolean transaction_flag = true;
		ArrayList<String> photoList = null;
		int photoSize =0;
		
		try {
			photoList = songStoryMapper.selectPhotoName(song_story_id);
			photoSize = photoList.size();
			
			if (photoSize > 0) {
				songStoryMapper.deletePhotos(song_story_id);
			}
		
			songStoryMapper.deleteStory(song_story_id);
		
		} catch (Exception e) {
			transaction_flag = false;
		}
		
		if (transaction_flag) {
			if (photoSize > 0) {
				for (int i = 0; i < photoSize; i++) {
					awsS3Connection.deleteFileFromAWSS3("apps/well_family_house/images/songstories", photoList.get(i),
							"jpg");
				}
			}
		}
	}

}
