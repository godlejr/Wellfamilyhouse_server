package com.demand.server.well_family_house.common.util;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.FirebaseResponse;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.Token;
import com.demand.server.well_family_house.common.fcmDto.Data;
import com.demand.server.well_family_house.common.fcmDto.DataMessage;
import com.demand.server.well_family_house.common.fcmDto.NotificationMessage;
import com.demand.server.well_family_house.common.flag.LogFlag;
import com.demand.server.well_family_house.common.flag.NotificationTOFlag;
import com.demand.server.well_family_house.notification.service.impl.NotificationMapper;

@Service
public class AndroidPushConnection {
	@Autowired
	private NotificationMapper notificationMapper;

	@Autowired
	private AndroidPushNotification androidPushNotificationsService;

	private static final Logger logger = LoggerFactory.getLogger(AndroidPushConnection.class);

	public AndroidPushConnection() {
		super();
	}

	public void insertFCM(Notification notification) throws Exception {
		int notification_id = notification.getId();
		int user_id = notification.getUser_id();
		int check = notification.getReceive_category_id();
		int receiver_ref_id = notification.getReceiver_id();

		if (check == NotificationTOFlag.ME || check == NotificationTOFlag.WRITER || check == NotificationTOFlag.INVITEE
				|| check == NotificationTOFlag.FAMILY_OWNER) {
			ArrayList<Token> token = notificationMapper.selectTokenForUser(receiver_ref_id);
			insertDataMessage(notification_id, token);
		}

		if (check == NotificationTOFlag.FAMILY) {
			ArrayList<Token> token = notificationMapper.selectTokenForFamily(receiver_ref_id, user_id);
			insertDataMessage(notification_id, token);
		}

		if (check == NotificationTOFlag.FAMILIES) {
			ArrayList<Token> token = notificationMapper.selectTokenForFamiles(receiver_ref_id);
			insertDataMessage(notification_id, token);
		}

	}

	public void insertDataMessage(int notification_id, ArrayList<Token> token) throws Exception {
		int tokenSize = token.size();

		for (int i = 0; i < tokenSize; i++) {
			notificationMapper.insertUserNotification(token.get(i).getId(), notification_id);

			DataMessage dataMessage = new DataMessage();
			dataMessage.setTo(token.get(i).getToken());

			String body = notificationMapper.selectBodyForNotification(notification_id);
			Data data = new Data(body);
			dataMessage.setData(data);

			CompletableFuture<FirebaseResponse> pushNotification = androidPushNotificationsService.insertDataMessage(dataMessage);
			CompletableFuture.allOf(pushNotification).join();

			try {
				FirebaseResponse firebaseResponse = pushNotification.get();
				if (firebaseResponse.getSuccess() == 1) {
					if (LogFlag.printFlag) {
						if (logger.isInfoEnabled()) {
							logger.info("notification success");
						}
					}
				} else {
					if (LogFlag.printFlag) {
						if (logger.isInfoEnabled()) {
							logger.info("notification fail");
						}
					}
				}
			} catch (InterruptedException e) {
				log(e);
			} catch (ExecutionException e) {
				log(e);
			}
		}
	}

	public void insertNoficationMessage(ArrayList<Token> token, String message) throws Exception {
		int tokenSize = token.size();

		for (int i = 0; i < tokenSize; i++) {
			
			NotificationMessage notificationMessage = new NotificationMessage();
			notificationMessage.setTo(token.get(i).getToken());
			
			com.demand.server.well_family_house.common.fcmDto.Notification notification = new com.demand.server.well_family_house.common.fcmDto.Notification(message);
			notificationMessage.setNotification(notification);
			
			CompletableFuture<FirebaseResponse> pushNotification = androidPushNotificationsService.insertNotificationMessage(notificationMessage);
			CompletableFuture.allOf(pushNotification).join();

			try {
				FirebaseResponse firebaseResponse = pushNotification.get();
				if (firebaseResponse.getSuccess() == 1) {
					if (LogFlag.printFlag) {
						if (logger.isInfoEnabled()) {
							logger.info("notification success");
						}
					}
				} else {
					if (LogFlag.printFlag) {
						if (logger.isInfoEnabled()) {
							logger.info("notification fail");
						}
					}
				}
			} catch (InterruptedException e) {
				log(e);
			} catch (ExecutionException e) {
				log(e);
			}
			
		}

	}

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
}
