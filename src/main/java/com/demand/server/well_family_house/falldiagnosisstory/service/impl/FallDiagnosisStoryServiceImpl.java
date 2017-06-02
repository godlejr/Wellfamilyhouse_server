package com.demand.server.well_family_house.falldiagnosisstory.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.EnvironmentPhoto;
import com.demand.server.well_family_house.common.dto.FallDiagnosisStory;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.Photo;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluation;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluationScore;
import com.demand.server.well_family_house.common.flag.AwsS3Flag;
import com.demand.server.well_family_house.common.flag.FallDiagnosisFlag;
import com.demand.server.well_family_house.common.flag.LogFlag;
import com.demand.server.well_family_house.common.util.AndroidPushConnection;
import com.demand.server.well_family_house.common.util.AwsS3Connection;
import com.demand.server.well_family_house.falldiagnosisstory.service.FallDiagnosisStoryService;
import com.demand.server.well_family_house.notification.service.impl.NotificationMapper;
import com.demand.server.well_family_house.story.service.impl.StoryServiceImpl;

@Service
public class FallDiagnosisStoryServiceImpl implements FallDiagnosisStoryService {
	@Autowired
	private NotificationMapper notificationMapper;

	@Autowired
	private AndroidPushConnection androidPushConnection;

	@Autowired
	private FallDiagnosisStoryMapper fallDiagnosisStoryMapper;

	@Autowired
	private AwsS3Connection awsS3Connection;

	private static final Logger logger = LoggerFactory.getLogger(FallDiagnosisStoryServiceImpl.class);

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
	public int insertFallDiagnosisStory(FallDiagnosisStory fallDiagnosisStory, Notification notification)
			throws Exception {

		fallDiagnosisStoryMapper.insertFallDiagnosisStory(fallDiagnosisStory);

		int story_id = fallDiagnosisStory.getId();
		int fall_diagnosis_category_id = fallDiagnosisStory.getFall_diagnosis_category_id();

		notification.setIntent_id(story_id);

		if (fall_diagnosis_category_id == FallDiagnosisFlag.SELF_DIAGNOSIS) {
			notification.setContent_name("낙상 자가진단");
		}
		if (fall_diagnosis_category_id == FallDiagnosisFlag.PHYSICAL_EVALUATION) {
			notification.setContent_name("낙상 신체평가");
		}
		if (fall_diagnosis_category_id == FallDiagnosisFlag.RISK_EVALUATION) {
			notification.setContent_name("낙상 위험환경평가");
		}

		notificationMapper.insertNotification(notification);
		androidPushConnection.insertFCM(notification);

		return story_id;
	}

	@Override
	public void insertSelfDiagnosis(int fall_diagnosis_story_id, int user_id, int fall_diagnosis_content_category_id)
			throws Exception {
		fallDiagnosisStoryMapper.insertSelfDiagnosis(fall_diagnosis_story_id, user_id,
				fall_diagnosis_content_category_id);
	}

	@Override
	public void insertPhysicalEvaluation(PhysicalEvaluation physicalEvaluation) throws Exception {
		fallDiagnosisStoryMapper.insertPhysicalEvaluation(physicalEvaluation);
	}

	@Override
	public void insertPhysicalEvaluationScore(PhysicalEvaluationScore physicalEvaluationScore) throws Exception {
		fallDiagnosisStoryMapper.insertPhysicalEvaluationScore(physicalEvaluationScore);
	}

	@Override
	public void insertEnvironmentEvaluation(int fall_diagnosis_story_id, int user_id,
			int environment_evaluation_category_id) throws Exception {
		fallDiagnosisStoryMapper.insertEnvironmentEvaluation(fall_diagnosis_story_id, user_id,
				environment_evaluation_category_id);
	}

	@Override
	public void insertEnvironmentPhoto(InputStream base64InputStream, int fall_diagnosis_story_id) throws Exception {
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
					AwsS3Flag.FALL_DIAGNOSIS_STORY_ENVIRONMENT_IMAGE_ENDPOINT, AwsS3Flag.IMAGE_EXT);
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}

		EnvironmentPhoto environmentPhoto = new EnvironmentPhoto();
		environmentPhoto.setFall_diagnosis_story_id(fall_diagnosis_story_id);
		environmentPhoto.setType(0);
		environmentPhoto.setName(file_name);
		environmentPhoto.setExt("jpg");
		fallDiagnosisStoryMapper.insertEnvironmentPhoto(environmentPhoto);

	}

	@Override
	public int selectFallDiagnosisStoryCommentCount(int fall_diagnosis_story_id) throws Exception {
		return fallDiagnosisStoryMapper.selectFallDiagnosisStoryCommentCount(fall_diagnosis_story_id);
	}

	@Override
	public int selectFallDiagnosisStoryLikeCount(int fall_diagnosis_story_id) throws Exception {
		return fallDiagnosisStoryMapper.selectFallDiagnosisStoryLikeCount(fall_diagnosis_story_id);
	}

	@Override
	public void insertFallDiagnosisStoryLikeUp(int user_id, int fall_diagnosis_story_id, Notification notification)
			throws Exception {
		int fall_diagnosis_story_user_id = fallDiagnosisStoryMapper.selectUser(notification.getIntent_id());

		if (user_id != fall_diagnosis_story_user_id) {
			notification.setReceiver_id(fall_diagnosis_story_user_id);
			notificationMapper.insertNotification(notification);
			androidPushConnection.insertFCM(notification);
		}

		fallDiagnosisStoryMapper.insertFallDiagnosisStoryLikeUp(user_id, fall_diagnosis_story_user_id);

	}

	@Override
	public void deleteFallDiagnosisStoryLikeDown(int user_id, int fall_diagnosis_story_id) throws Exception {
		fallDiagnosisStoryMapper.deleteFallDiagnosisStoryLikeDown(user_id, fall_diagnosis_story_id);
	}

	@Override
	public int selectFallDiagnosisStoryLikeCheck(int user_id, int fall_diagnosis_story_id) throws Exception {
		return fallDiagnosisStoryMapper.selectFallDiagnosisStoryLikeCheck(user_id, fall_diagnosis_story_id);
	}

	@Override
	public void updateFallDiagnosisStoryHit(int fall_diagnosis_story_id) throws Exception {
		fallDiagnosisStoryMapper.updateFallDiagnosisStoryHit(fall_diagnosis_story_id);
	}

	@Override
	public String selectFallDiagnosisStoryTitle(int fall_diagnosis_story_id, int fall_diagnosis_category_id)
			throws Exception {
		if (fall_diagnosis_category_id == FallDiagnosisFlag.SELF_DIAGNOSIS) {
			return FallDiagnosisFlag.SELF_DIAGNOSIS_NAME;
		} else if (fall_diagnosis_category_id == FallDiagnosisFlag.PHYSICAL_EVALUATION) {
			return FallDiagnosisFlag.PHYSICAL_EVALUATION_NAME;
		} else {
			String place = fallDiagnosisStoryMapper.selectFallDiagnosisStoryTitleWithRisk(fall_diagnosis_story_id);
			
			return FallDiagnosisFlag.RISK_EVALUATION_NAME +"_"+ place;
		}

	}

}
