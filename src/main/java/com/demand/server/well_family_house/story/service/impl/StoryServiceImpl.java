package com.demand.server.well_family_house.story.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.Comment;
import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.Photo;
import com.demand.server.well_family_house.common.dto.Story;
import com.demand.server.well_family_house.common.dto.StoryInfoForNotification;
import com.demand.server.well_family_house.common.flag.LogFlag;
import com.demand.server.well_family_house.common.util.AndroidPushConnection;
import com.demand.server.well_family_house.common.util.AwsS3Connection;
import com.demand.server.well_family_house.notification.service.impl.NotificationMapper;
import com.demand.server.well_family_house.story.service.StoryService;

@Service
public class StoryServiceImpl implements StoryService {

	@Autowired
	private StoryMapper storyMapper;

	@Autowired
	private NotificationMapper notificationMapper;

	@Autowired
	private AndroidPushConnection androidPushConnection;

	@Autowired
	private AwsS3Connection awsS3Connection;

	private static final Logger logger = LoggerFactory.getLogger(StoryServiceImpl.class);

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
	public int selectCommentCount(int story_id) throws Exception {
		return storyMapper.selectCommentCount(story_id);
	}

	@Override
	public int selectLikeCount(int story_id) throws Exception {
		return storyMapper.selectLikeCount(story_id);
	}

	@Override
	public ArrayList<Photo> selectContentPhotoList(int story_id) throws Exception {
		return storyMapper.selectContentPhotoList(story_id);
	}

	@Override
	public void insertLikeUp(int user_id, int story_id, Notification notification)
			throws NumberFormatException, Exception {
		int story_user_id = storyMapper.selectUser(notification.getIntent_id());

		if (user_id != story_user_id) {
			notification.setReceiver_id(story_user_id);
			notificationMapper.insertNotification(notification);
			androidPushConnection.insertFCM(notification);
		}
		storyMapper.insertLikeUp(user_id, story_id);
	}

	@Override
	public void deleteLikeDown(int user_id, int story_id) throws Exception {
		storyMapper.deleteLikeDown(user_id, story_id);
	}

	@Override
	public int selectLikeCheck(int user_id, int story_id) throws Exception {
		return storyMapper.selectLikeCheck(user_id, story_id);
	}

	@Override
	public ArrayList<CommentInfo> selectCommentList(int story_id) throws Exception {
		return storyMapper.selectCommentList(story_id);
	}

	@Override
	public Comment insertComment(Comment comment, Notification notification) throws Exception {
		storyMapper.insertComment(comment);
		int story_user_id = storyMapper.selectUser(notification.getIntent_id());

		if (story_user_id != comment.getUser_id()) {
			notification.setReceiver_id(story_user_id);
			notificationMapper.insertNotification(notification);
			androidPushConnection.insertFCM(notification);
		}
		return storyMapper.selectComment(comment.getId());
	}

	@Override
	public Story insertStory(Story story, Notification notification) throws Exception {
		storyMapper.insertStory(story);

		notification.setIntent_id(story.getId());
		notificationMapper.insertNotification(notification);

		androidPushConnection.insertFCM(notification);

		return storyMapper.selectStory(story.getId());
	}

	@Override
	public void insertPhoto(InputStream base64InputStream, int story_id) throws IOException, Exception {
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
					"apps/well_family_house/images/stories", ".jpg");
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}

		Photo photo = new Photo();
		photo.setStory_id(story_id);
		photo.setType(0);
		photo.setName(file_name);
		photo.setExt("jpg");
		storyMapper.insertPhoto(photo);
	}

	@Override
	public StoryInfoForNotification selectStoryInfo(int story_id) throws Exception {
		return storyMapper.selectStoryInfo(story_id);
	}

	@Override
	public void updateStory(int story_id, String content) throws Exception {
		boolean transaction_flag = true;
		ArrayList<String> photoList = null;
		int photoSize =0;
		
		try {
			storyMapper.updateStory(story_id, content);
			photoList = storyMapper.selectPhotoName(story_id);
			photoSize = photoList.size();
			
			if (photoSize > 0) {
				storyMapper.deletePhotos(story_id);
			}
			
		} catch (Exception e) {
			transaction_flag = false;
		}
		
		if (transaction_flag) {
			if (photoSize > 0) {
				for (int i = 0; i < photoSize; i++) {
					awsS3Connection.deleteFileFromAWSS3("apps/well_family_house/images/stories", photoList.get(i),
							"jpg");
				}
			}
		}
	}
}
