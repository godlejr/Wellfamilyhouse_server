package com.demand.server.well_family_house.family.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.Family;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.Photo;
import com.demand.server.well_family_house.common.dto.StoryInfo;
import com.demand.server.well_family_house.common.dto.User;
import com.demand.server.well_family_house.common.dto.UserInfoForFamilyJoin;
import com.demand.server.well_family_house.common.flag.AwsS3Flag;
import com.demand.server.well_family_house.common.flag.LogFlag;
import com.demand.server.well_family_house.common.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.common.util.AndroidPushConnection;
import com.demand.server.well_family_house.common.util.AwsS3Connection;
import com.demand.server.well_family_house.family.service.FamilyService;
import com.demand.server.well_family_house.notification.service.impl.NotificationMapper;

@Service("familyServiceImpl")
public class FamilyServiceImpl implements FamilyService {

	@Autowired
	private FamilyMapper familyMapper;

	@Autowired
	private NotificationMapper notificationMapper;

	@Autowired
	private AndroidPushConnection androidPushConnection;

	@Autowired
	private AwsS3Connection awsS3Connection;

	private static final Logger logger = LoggerFactory.getLogger(FamilyServiceImpl.class);

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
	public Family selectFamily(int family_id) throws Exception {
		return familyMapper.selectFamily(family_id);
	}

	@Override
	public ArrayList<User> selectFamilyUsersInfo(int family_id, int user_id) throws Exception {
		return familyMapper.selectFamilyUsersInfo(family_id, user_id);
	}

	@Override
	public ArrayList<StoryInfo> selectContentList(int family_id) throws Exception {
		return familyMapper.selectContentList(family_id);
	}

	@Override
	public ArrayList<Photo> selectPhotoList(int family_id) throws Exception {
		return familyMapper.selectPhotoList(family_id);
	}

	@Override
	public void updateFamilyAvatar(InputStream base64InputStream, int family_id) throws IOException, Exception {
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

		// delete prior avatar
		String fileName = familyMapper.selectFamilyAvatar(family_id);
		if (!fileName.equals(AwsS3Flag.FAMILY_AVATAR_DEFAULT)) {
			awsS3Connection.deleteFileFromAWSS3(AwsS3Flag.FAMILY_AVATAR_ENDPOINT, fileName, "");
		}

		try {
			file_name = awsS3Connection.uploadFileToAWSS3(stringBuilder.toString(), AwsS3Flag.FAMILY_AVATAR_ENDPOINT,
					AwsS3Flag.IMAGE_EXT);
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}

		familyMapper.updateFamilyAvatar(family_id, file_name + AwsS3Flag.IMAGE_EXT);
	}

	@Override
	public void insertUserIntoFamily(int family_id, int user_id, int join_flag, Notification notification)
			throws Exception {
		int invitor_id = familyMapper.selectFamilyUserId(family_id);
		notification.setUser_id(invitor_id);

		notificationMapper.insertNotification(notification);
		androidPushConnection.insertFCM(notification);

		familyMapper.insertUserIntoFamily(family_id, user_id, join_flag);
	}

	@Override
	public void deleteUserFromFamily(int family_id, int user_id) throws Exception {
		familyMapper.deleteUserFromFamily(family_id, user_id);
	}

	@Override
	public void updateFamilyInfo(int family_id, String name, String content) throws Exception {
		familyMapper.updateFamilyInfo(family_id, name, content);
	}

	@Override
	public ArrayList<UserInfoForFamilyJoin> selectUserSearchList(int family_id, String search) throws Exception {
		return familyMapper.selectUserSearchList(family_id, search);
	}

	@Override
	public void updateUserForFamilyJoin(int family_id, int user_id, Notification notification) throws Exception {
		String name = familyMapper.selectFamilyName(family_id);
		notification.setContent_name(name);

		notificationMapper.insertNotification(notification);
		androidPushConnection.insertFCM(notification);

		familyMapper.updateUserForFamilyJoin(family_id, user_id);
	}

	@Override
	public ArrayList<UserInfoForFamilyJoin> selectFamilyJoinerList(int family_id) throws Exception {
		return familyMapper.selectFamilyJoinerList(family_id);
	}

	@Override
	public void deleteFamily(int family_id) throws Exception {

		boolean transaction_flag = true;
		ArrayList<String> photoList = null;
		String avatar = null;
		int photoSize = 0;

		try {
			avatar = familyMapper.selectFamilyAvatar(family_id);
			photoList = familyMapper.selectPhotoNameList(family_id);
			familyMapper.deleteFamily(family_id);
			photoSize = photoList.size();

			notificationMapper.deleteNotificationForDeleteCascade(NotificationINTENTFlag.FAMILY, family_id);
			notificationMapper.deleteNotificationForDeleteCascade(NotificationINTENTFlag.MANAGE_FAMILY, family_id);
			notificationMapper.deleteNotificationForDeleteCascade(NotificationINTENTFlag.MANAGE_FAMILY_DETAIL, family_id);

		} catch (Exception e) {
			transaction_flag = false;
		}

		if (transaction_flag) {
			if (!avatar.equals(AwsS3Flag.FAMILY_AVATAR_DEFAULT)) {
				awsS3Connection.deleteFileFromAWSS3(AwsS3Flag.FAMILY_AVATAR_ENDPOINT, avatar, "");
			}

			if (photoSize > 0) {
				for (int i = 0; i < photoSize; i++) {
					awsS3Connection.deleteFileFromAWSS3(AwsS3Flag.STORY_IMAGE_ENDPOINT, photoList.get(i),
							AwsS3Flag.IMAGE_EXT);
				}
			}
		}

	}

}
