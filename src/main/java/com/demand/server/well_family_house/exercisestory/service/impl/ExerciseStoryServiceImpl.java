package com.demand.server.well_family_house.exercisestory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.ExerciseStory;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.flag.FallDiagnosisFlag;
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

}
