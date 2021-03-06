package com.demand.server.well_family_house.user.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.Category;
import com.demand.server.well_family_house.common.dto.ExerciseStory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisStory;
import com.demand.server.well_family_house.common.dto.Family;
import com.demand.server.well_family_house.common.dto.FamilyInfoForFamilyJoin;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.SongStory;
import com.demand.server.well_family_house.common.dto.Token;
import com.demand.server.well_family_house.common.dto.User;
import com.demand.server.well_family_house.common.dto.UserInfoForFamilyJoin;
import com.demand.server.well_family_house.common.flag.AwsS3Flag;
import com.demand.server.well_family_house.common.flag.FamilyJoinFlag;
import com.demand.server.well_family_house.common.flag.LogFlag;
import com.demand.server.well_family_house.common.flag.NotificationMessageFlag;
import com.demand.server.well_family_house.common.util.AndroidPushConnection;
import com.demand.server.well_family_house.common.util.AwsS3Connection;
import com.demand.server.well_family_house.notification.service.impl.NotificationMapper;
import com.demand.server.well_family_house.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private NotificationMapper notificationMapper;

	@Autowired
	private AndroidPushConnection androidPushConnection;

	@Autowired
	private AwsS3Connection awsS3Connection;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
	public User selectUserInfo(int user_id) throws Exception {
		return userMapper.selectUserInfo(user_id);
	}

	@Override
	public void updateDeviceIdToken(int user_id, String device_id, String token) throws Exception {
		String pre_deviceId = userMapper.selectDeviceId(user_id);
		ArrayList<Token> tokenList = notificationMapper.selectTokenForUser(user_id);

		if (!device_id.equals(pre_deviceId)&& pre_deviceId != null && pre_deviceId.length() !=0) {
			androidPushConnection.insertNoficationMessage(tokenList, NotificationMessageFlag.CONCURRENT_ACCESS_MESSAGE);
		}

		userMapper.updateDeviceIdToken(user_id, device_id, token);
	}

	@Override
	public int selectDeviceIdCheck(int user_id, String device_id) throws Exception {
		return userMapper.selectDeviceIdCheck(user_id, device_id);
	}

	@Override
	public void updateToken(int user_id, String token) throws Exception {
		userMapper.updateToken(user_id, token);
	}

	@Override
	public ArrayList<Family> selectFamiliesInfo(int user_id) throws Exception {
		return userMapper.selectFamiliesInfo(user_id);
	}

	@Override
	public int selectFamiliesUserCheck(int user_id, int story_user_id) throws Exception {
		return userMapper.selectFamiliesUserCheck(user_id, story_user_id);
	}

	@Override
	public ArrayList<SongStory> selectSongStoryPublicList(int story_user_id) throws Exception {
		return userMapper.selectSongStoryPublicList(story_user_id);
	}

	@Override
	public ArrayList<SongStory> selectSongStoryFamilyList(int story_user_id) throws Exception {
		return userMapper.selectSongStoryFamilyList(story_user_id);
	}

	@Override
	public ArrayList<SongStory> selectSongStoryMeList(int story_user_id) throws Exception {
		return userMapper.selectSongStoryMeList(story_user_id);
	}

	@Override
	public ArrayList<FamilyInfoForFamilyJoin> selectFamilySearchList(int user_id, String search) throws Exception {
		return userMapper.selectFamilySearchList(user_id, search);
	}

	@Override
	public int selectFamilyUserCheck(int family_id, int user_id, int other_user_id) throws Exception {
		return userMapper.selectFamilyUserCheck(family_id, user_id, other_user_id);
	}

	@Override
	public ArrayList<Category> selectFavoriteCategoryList() throws Exception {
		return userMapper.selectFavoriteCategoryList();
	}

	@Override
	public int selectGenderCheck(int user_id) throws Exception {
		return userMapper.selectGenderCheck(user_id);
	}

	@Override
	public int selectFavoriteCheck(int user_id, int favorite_category_id) throws Exception {
		return userMapper.selectFavoriteCheck(user_id, favorite_category_id);
	}

	@Override
	public int selectSongCategoryCheck(int user_id, int song_category_id) throws Exception {
		return userMapper.selectSongCategoryCheck(user_id, song_category_id);
	}

	@Override
	public void deleteFavorite(int user_id) throws Exception {
		userMapper.deleteFavorite(user_id);
	}

	@Override
	public void insertFavorite(int user_id, int favorite_category_id) throws Exception {
		userMapper.insertFavorite(user_id, favorite_category_id);
	}

	@Override
	public void deleteSongCategory(int user_id) throws Exception {
		userMapper.deleteSongCategory(user_id);
	}

	@Override
	public void insertSongCategory(int user_id, int song_category_id) throws Exception {
		userMapper.insertSongCategory(user_id, song_category_id);
	}

	@Override
	public void updateUserInfo(int user_id, String name, String birth, String phone, int gender) throws Exception {
		userMapper.updateUserInfo(user_id, name, birth, phone, gender);
	}

	@Override
	public void insertCommentReport(int user_id, int comment_category_id, int report_category_id, int comment_id)
			throws Exception {
		userMapper.insertCommentReport(user_id, comment_category_id, report_category_id, comment_id);
	}

	@Override
	public ArrayList<Notification> selectNotification(int user_id) throws Exception {
		return notificationMapper.selectNotification(user_id);
	}

	@Override
	public int insertFamily(Family family, Notification notification) throws Exception {

		userMapper.insertFamily(family);
		userMapper.insertFamilyJoiner(family.getId(), family.getUser_id(), FamilyJoinFlag.FAMILY);

		notification.setIntent_id(family.getId());

		notificationMapper.insertNotification(notification);
		androidPushConnection.insertFCM(notification);

		return family.getId();
	}

	@Override
	public void updateUserAvatar(InputStream base64InputStream, int user_id) throws IOException, Exception {
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
		String fileName = userMapper.selectUserAvatar(user_id);
		if (!fileName.equals(AwsS3Flag.USER_AVATAR_DEFAULT)) {
			awsS3Connection.deleteFileFromAWSS3(AwsS3Flag.USER_AVATAR_ENDPOINT, fileName, "");
		}

		try {
			file_name = awsS3Connection.uploadFileToAWSS3(stringBuilder.toString(), AwsS3Flag.USER_AVATAR_ENDPOINT,
					AwsS3Flag.IMAGE_EXT);
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}

		userMapper.updateUserAvatar(user_id, file_name + AwsS3Flag.IMAGE_EXT);
	}

	@Override
	public ArrayList<Family> selectManageFamilies(int user_id) throws Exception {
		return userMapper.selectManageFamilies(user_id);
	}

	@Override
	public ArrayList<FamilyInfoForFamilyJoin> selectJoinFamilies(int user_id) throws Exception {
		return userMapper.selectJoinFamilies(user_id);
	}

	public void insertFamilyJoiner(int user_id, int family_id, Notification notification) throws Exception {
		userMapper.insertFamilyJoiner(family_id, user_id, FamilyJoinFlag.USER_TO_FAMILY);
		notificationMapper.insertNotification(notification);
		androidPushConnection.insertFCM(notification);
	}

	@Override
	public void updatePassword(int user_id, String password) throws Exception {
		userMapper.updatePassword(user_id, password);		
	}

	@Override
	public void insertStoryReport(int user_id, int story_category_id, int report_category_id, int story_id)
			throws Exception {
		userMapper.insertStoryReport(user_id, story_category_id, report_category_id, story_id);	
	}

	@Override
	public ArrayList<FallDiagnosisStory> selectFallDiagnosisStoryList(int user_id) throws Exception {
		return userMapper.selectFallDiagnosisStoryList(user_id);
	}

	@Override
	public ArrayList<ExerciseStory> selectExerciseStoryList(int user_id) throws Exception {
		return userMapper.selectExerciseStoryList(user_id);
	}

}
