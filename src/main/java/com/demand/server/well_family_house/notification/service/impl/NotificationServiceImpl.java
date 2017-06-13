package com.demand.server.well_family_house.notification.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.NotificationInfo;
import com.demand.server.well_family_house.notification.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	private NotificationMapper notificationMapper;

	@Override
	public void updateNotificationCheck(int notification_id) throws Exception {
		notificationMapper.updateNotificationCheck(notification_id);
	}

	@Override
	public NotificationInfo selectNotificationForCreatingFamilyAndJoinAndWantToJoin(int notification_id) throws Exception {
		return notificationMapper.selectNotificationForCreatingFamilyAndJoinAndWantToJoin(notification_id);
	}

	@Override
	public NotificationInfo selectNotificationForWritingStory(int notification_id) throws Exception {
		return notificationMapper.selectNotificationForWritingStory(notification_id);
	}

	@Override
	public NotificationInfo selectNotificationForWritingCommentAndLike(int notification_id) throws Exception {
		return notificationMapper.selectNotificationForWritingCommentAndLike(notification_id);
	}

	@Override
	public NotificationInfo selectNotificationForWritingSongStory(int notification_id) throws Exception {
		return notificationMapper.selectNotificationForWritingSongStory(notification_id);
	}

	@Override
	public NotificationInfo selectNotificationForFallDiagnosisStroy(int notification_id) throws Exception {
		return notificationMapper.selectNotificationForFallDiagnosisStroy(notification_id);
	}

}
