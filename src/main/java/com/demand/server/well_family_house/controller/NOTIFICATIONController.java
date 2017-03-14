package com.demand.server.well_family_house.controller;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.dao.IDao;
import com.demand.server.well_family_house.dto.NotificationInfo;
import com.demand.server.well_family_house.flag.LogFlag;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/notifications")
public class NOTIFICATIONController {
	@Autowired
	private SqlSession well_family_house_sqlSession;

	private static final Logger logger = LoggerFactory.getLogger(NOTIFICATIONController.class);

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

	@RequestMapping(value = "/{notification_id}/family_formation", method = RequestMethod.GET)
	public NotificationInfo NotificationForCreatingFamily(@PathVariable int notification_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getNotificationForCreatingFamily(notification_id);
	}

	@RequestMapping(value = "/{notification_id}/writing_stories", method = RequestMethod.GET)
	public NotificationInfo NotificationForWritingStory(@PathVariable int notification_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getNotificationForWritingStory(notification_id);
	}

	@RequestMapping(value = "/{notification_id}", method = RequestMethod.PUT)
	public void notificationInfo(@PathVariable int notification_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateNotificationCheck(notification_id);
	}

}
