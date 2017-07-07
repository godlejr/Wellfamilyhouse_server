package com.demand.server.well_family_house.exercisestory.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.EnvironmentPhoto;
import com.demand.server.well_family_house.common.dto.ExerciseStoryComment;
import com.demand.server.well_family_house.common.dto.ExerciseStory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisStoryComment;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.flag.AwsS3Flag;
import com.demand.server.well_family_house.common.flag.FallDiagnosisFlag;
import com.demand.server.well_family_house.common.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.common.util.AndroidPushConnection;
import com.demand.server.well_family_house.exercisestory.service.ExerciseStoryService;
import com.demand.server.well_family_house.notification.service.impl.NotificationMapper;

@Service
public class ExerciseStoryServiceImpl implements ExerciseStoryService {
	@Autowired
	private ExerciseStoryMapper exerciseStoryMapper;

	@Autowired
	private NotificationMapper notificationMapper;

	@Autowired
	private AndroidPushConnection androidPushConnection;

	@Override
	public int insertExerciseStory(ExerciseStory exerciseStory, Notification notification) throws Exception {
		exerciseStoryMapper.insertExerciseStory(exerciseStory);

		int story_id = exerciseStory.getId();
		notification.setIntent_id(story_id);
		notification.setContent_name("낙상예방운동");

		notificationMapper.insertNotification(notification);
		androidPushConnection.insertFCM(notification);

		return story_id;
	}

	@Override
	public ExerciseStory selectExerciseStory(int exercise_story_id) throws Exception {
		return exerciseStoryMapper.selectExerciseStory(exercise_story_id);
	}

	@Override
	public int selectExerciseStoryCommentCount(int exercise_story_id) throws Exception {
		return exerciseStoryMapper.selectExerciseStoryCommentCount(exercise_story_id);
	}

	@Override
	public int selectExerciseStoryLikeCount(int exercise_story_id) throws Exception {
		return exerciseStoryMapper.selectExerciseStoryLikeCount(exercise_story_id);
	}

	@Override
	public void insertExerciseStoryLikeUp(int user_id, int exercise_story_id, Notification notification)
			throws Exception {
		int exercise_story_user_id = exerciseStoryMapper.selectUser(notification.getIntent_id());

		if (user_id != exercise_story_user_id) {
			notification.setReceiver_id(exercise_story_user_id);
			notificationMapper.insertNotification(notification);
			androidPushConnection.insertFCM(notification);
		}

		exerciseStoryMapper.insertExerciseStoryLikeUp(user_id, exercise_story_id);

	}

	@Override
	public void deleteExerciseStoryLikeDown(int user_id, int exercise_story_id) throws Exception {
		exerciseStoryMapper.deleteExerciseStoryLikeDown(user_id, exercise_story_id);
	}

	@Override
	public int selectExerciseStoryLikeCheck(int user_id, int exercise_story_id) throws Exception {
		return exerciseStoryMapper.selectExerciseStoryLikeCheck(user_id, exercise_story_id);
	}

	@Override
	public void updateExerciseStoryHit(int exercise_story_id) throws Exception {
		exerciseStoryMapper.updateExerciseStoryHit(exercise_story_id);
	}

	@Override
	public ArrayList<CommentInfo> selectExerciseStoryCommentList(int exercise_story_id) throws Exception {
		return exerciseStoryMapper.selectExerciseStoryCommentList(exercise_story_id);
	}

	@Override
	public ExerciseStoryComment insertExerciseStoryComment(ExerciseStoryComment exerciseStoryComment, Notification notification)
			throws Exception {
		int exercise_story_user_id = exerciseStoryMapper.selectUser(notification.getIntent_id());

		if (exercise_story_user_id != exerciseStoryComment.getUser_id()) {
			notification.setReceiver_id(exercise_story_user_id);
			notificationMapper.insertNotification(notification);
			androidPushConnection.insertFCM(notification);
		}

		exerciseStoryMapper.insertExerciseStoryComment(exerciseStoryComment);
		return exerciseStoryMapper.selectExerciseStoryComment(exerciseStoryComment.getId());
	}

	@Override
	public void deleteExerciseStory(int exercise_story_id) throws Exception {
		exerciseStoryMapper.deleteExerciseStory(exercise_story_id);

	}

}
