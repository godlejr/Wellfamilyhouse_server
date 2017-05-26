package com.demand.server.well_family_house.falldiagnosisstory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.FallDiagnosisStory;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.flag.FallDiagnosisFlag;
import com.demand.server.well_family_house.common.util.AndroidPushConnection;
import com.demand.server.well_family_house.falldiagnosisstory.service.FallDiagnosisStoryService;
import com.demand.server.well_family_house.notification.service.impl.NotificationMapper;

@Service
public class FallDiagnosisStoryServiceImpl implements FallDiagnosisStoryService {
	@Autowired
	private NotificationMapper notificationMapper;

	@Autowired
	private AndroidPushConnection androidPushConnection;
	
	@Autowired
	private FallDiagnosisStoryMapper fallDiagnosisStoryMapper;

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
	public void insertSelfDiagnosis(int fall_diagnosis_story_id, int user_id, int self_diagnosis_category_id)
			throws Exception {
		fallDiagnosisStoryMapper.insertSelfDiagnosis(fall_diagnosis_story_id, user_id, self_diagnosis_category_id);
	}

}
