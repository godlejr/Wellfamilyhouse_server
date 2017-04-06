package com.demand.server.well_family_house.notification.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.NotificationInfo;
import com.demand.server.well_family_house.common.dto.Token;

@Repository
public interface NotificationMapper {

	void updateNotificationCheck(int notification_id) throws Exception;

	void insertNotification(Notification notification) throws Exception;

	void insertUserNotification(int id, int notification_id) throws Exception;

	String selectBodyForNotification(int notification_id) throws Exception;
	
	// notify for android
	NotificationInfo selectNotificationForCreatingFamilyAndJoinAndWantToJoin(int notification_id) throws Exception;

	NotificationInfo selectNotificationForWritingStory(int notification_id) throws Exception;

	NotificationInfo selectNotificationForWritingCommentAndLike(int notification_id) throws Exception;
	
	NotificationInfo selectNotificationForWritingSongStory(int notification_id) throws Exception;

	
	// get tokens
	ArrayList<Notification> selectNotification(int user_id) throws Exception;

	ArrayList<Token> selectTokenForUser(int receiver_id) throws Exception;

	ArrayList<Token> selectTokenForFamily(int receiver_id, int user_id) throws Exception;

	ArrayList<Token> selectTokenForFamiles(int receiver_ref_id);
	

}
