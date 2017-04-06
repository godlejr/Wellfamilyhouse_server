package com.demand.server.well_family_house.notification.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.NotificationInfo;
import com.demand.server.well_family_house.notification.service.impl.NotificationServiceImpl;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/notifications")
public class NOTIFICATIONController {
	@Autowired
	private NotificationServiceImpl notificationServiceImpl;

	@RequestMapping(value = "/{notification_id}/families", method = RequestMethod.GET)
	public NotificationInfo NotificationForCreatingFamily(@PathVariable int notification_id) throws Exception {
		return notificationServiceImpl.selectNotificationForCreatingFamilyAndJoinAndWantToJoin(notification_id);
	}
	
	@RequestMapping(value = "/{notification_id}/comments", method = RequestMethod.GET)
	public NotificationInfo NotificationForWritingComment(@PathVariable int notification_id) throws Exception {
		return notificationServiceImpl.selectNotificationForWritingCommentAndLike(notification_id);
	}
	
	@RequestMapping(value = "/{notification_id}/likes", method = RequestMethod.GET)
	public NotificationInfo NotificationForLike(@PathVariable int notification_id) throws Exception {
		return notificationServiceImpl.selectNotificationForWritingCommentAndLike(notification_id);
	}

	@RequestMapping(value = "/{notification_id}/stories", method = RequestMethod.GET)
	public NotificationInfo NotificationForWritingStory(@PathVariable int notification_id) throws Exception {
		return notificationServiceImpl.selectNotificationForWritingStory(notification_id);
	}
	
	@RequestMapping(value = "/{notification_id}/songstories", method = RequestMethod.GET)
	public NotificationInfo NotificationForWritingSongStory(@PathVariable int notification_id) throws Exception {
		return notificationServiceImpl.selectNotificationForWritingSongStory(notification_id);
	}

	@RequestMapping(value = "/{notification_id}", method = RequestMethod.PUT)
	public void notificationInfo(@PathVariable int notification_id) throws Exception {
		notificationServiceImpl.updateNotificationCheck(notification_id);
	}
}
