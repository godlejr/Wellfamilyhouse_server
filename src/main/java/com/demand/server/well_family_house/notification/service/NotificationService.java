package com.demand.server.well_family_house.notification.service;

import com.demand.server.well_family_house.common.dto.NotificationInfo;

public interface NotificationService {

	NotificationInfo selectNotificationForCreatingFamilyAndJoinAndWantToJoin(int notification_id) throws Exception;

	NotificationInfo selectNotificationForWritingStory(int notification_id) throws Exception;

	NotificationInfo selectNotificationForWritingCommentAndLike(int notification_id) throws Exception;
	
	NotificationInfo selectNotificationForWritingSongStory(int notification_id) throws Exception;

	void updateNotificationCheck(int notification_id) throws Exception;

	NotificationInfo selectNotificationForFallDiagnosisStroy(int notification_id) throws Exception;

	NotificationInfo selectNotificationForExerciseStroy(int notification_id) throws Exception;
}
