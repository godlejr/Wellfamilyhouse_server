package com.demand.server.well_family_house.util;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.dao.IDao;
import com.demand.server.well_family_house.dto.Data;
import com.demand.server.well_family_house.dto.FirebaseResponse;
import com.demand.server.well_family_house.dto.Message;
import com.demand.server.well_family_house.dto.Notification;
import com.demand.server.well_family_house.dto.Token;
import com.demand.server.well_family_house.flag.LogFlag;


@Service
public class AndroidPushConnection {
	@Autowired
	private SqlSession well_family_house_sqlSession;

	@Autowired
	private AndroidPushNotification androidPushNotificationsService;

	private static final Logger logger = LoggerFactory.getLogger(AndroidPushConnection.class);

	public AndroidPushConnection() {
		super();
	}
	public void sendFCM(Notification notification) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		int notification_id = notification.getId();
		int user_id = notification.getUser_id();// ÇàÀ§ÀÚ
		int check = notification.getReceive_category_id();

		if (check == 1) {
			// receiver = me;
			ArrayList<Token> token = dao.getTokenForMe(notification.getReceiver_id());
			setMessage(notification_id, token);
		}

		if (check == 3) {
			ArrayList<Token> token = dao.getTokenForFamily(notification.getReceiver_id(), user_id);
			setMessage(notification_id, token);
		}

	}

	public void setMessage(int notification_id, ArrayList<Token> token) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		int tokenSize = token.size();

		for (int i = 0; i < tokenSize; i++) {
			dao.insertUserNotification(token.get(i).getId(), notification_id);

			Message message = new Message();
			message.setTo(token.get(i).getToken());

			String body = dao.getBodyForNotification(notification_id);
			Data data = new Data(body);
			message.setData(data);

			CompletableFuture<FirebaseResponse> pushNotification = androidPushNotificationsService.send(message);
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
