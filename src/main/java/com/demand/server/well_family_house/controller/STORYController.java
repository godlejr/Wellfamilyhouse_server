package com.demand.server.well_family_house.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

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
import com.demand.server.well_family_house.dto.Check;
import com.demand.server.well_family_house.dto.Comment;
import com.demand.server.well_family_house.dto.CommentCount;
import com.demand.server.well_family_house.dto.CommentInfo;
import com.demand.server.well_family_house.dto.LikeCount;
import com.demand.server.well_family_house.dto.Notification;
import com.demand.server.well_family_house.dto.Photo;
import com.demand.server.well_family_house.dto.Story;
import com.demand.server.well_family_house.flag.LogFlag;
import com.demand.server.well_family_house.flag.NotificationBEHAVIORFlag;
import com.demand.server.well_family_house.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.flag.NotificationTOFlag;
import com.demand.server.well_family_house.util.AndroidPushConnection;
import com.demand.server.well_family_house.util.AwsS3Connection;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/stories")
public class STORYController {
	@Autowired
	private SqlSession well_family_house_sqlSession;

	@Autowired
	private AndroidPushConnection androidPushConnection;

	@Autowired
	private AwsS3Connection awsS3Connection;

	private static final Logger logger = LoggerFactory.getLogger(STORYController.class);

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

	// content_info (family_main)
	@RequestMapping(value = "/{story_id}/comment_count", method = RequestMethod.GET)
	public ArrayList<CommentCount> family_comment_Count(@PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCommentCount(story_id);
	}

	@RequestMapping(value = "/{story_id}/like_count", method = RequestMethod.GET)
	public ArrayList<LikeCount> family_like_Count(@PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getLikeCount(story_id);
	}

	@RequestMapping(value = "/{story_id}/photos", method = RequestMethod.GET)
	public ArrayList<Photo> family_content_photo_List(@PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getContentPhotoList(story_id);
	}

	@RequestMapping(value = "/{story_id}/likes", method = RequestMethod.POST)
	public void family_content_like_up(HttpServletRequest request, @PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateLikeUp(Integer.parseInt(request.getParameter("user_id")), story_id);
	}

	@RequestMapping(value = "/{story_id}/likes/{user_id}", method = RequestMethod.DELETE)
	public void family_content_like_down(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateLikeDown(user_id, story_id);
	}

	@RequestMapping(value = "/{story_id}/like_check/{user_id}", method = RequestMethod.GET)
	public ArrayList<Check> family_content_like_check(HttpServletRequest request, @PathVariable int story_id,
			@PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.checkLike(user_id, story_id);
	}

	// comment
	@RequestMapping(value = "/{story_id}/comments", method = RequestMethod.GET)
	public ArrayList<CommentInfo> family_detail_comment_List(@PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCommentList(story_id);
	}

	@RequestMapping(value = "/{story_id}/comments", method = RequestMethod.POST)
	public ArrayList<Comment> insert_comment(HttpServletRequest request, @PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);

		int user_id = Integer.parseInt(request.getParameter("user_id"));
		Comment comment = new Comment();
		comment.setUser_id(user_id);
		comment.setStory_id(story_id);
		comment.setContent(request.getParameter("content"));

		dao.insertComment(comment);
		return dao.getComment(comment.getId());
	}

	// insert story
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ArrayList<Story> insert_story(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		int family_id = Integer.parseInt(request.getParameter("family_id"));
		int user_id = Integer.parseInt(request.getParameter("user_id"));

		String content = request.getParameter("content");
		Story story = new Story();
		story.setUser_id(user_id);
		story.setFamily_id(family_id);
		story.setContent(content);

		dao.insertStory(story);

		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.FAMILY); 
		notification.setReceiver_id(family_id);
		notification.setContent_name(request.getParameter("family_name")); 
		notification.setIntent_flag(NotificationINTENTFlag.STORY_DETAIL);
		notification.setIntent_id(story.getId());
		notification.setBehavior_id(NotificationBEHAVIORFlag.WRITING_THE_STORY);

		dao.insertNotification(notification); // insert notification
		androidPushConnection.sendFCM(notification);

		return dao.getStory(story.getId());
	}

	@RequestMapping(value = "/{story_id}/photos", method = RequestMethod.POST)
	public void insert_photos(HttpServletRequest request, @PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		String file_name = null;

		InputStream base64InputStream;
		StringBuilder stringBuilder = null;
		try {
			base64InputStream = request.getInputStream();
			if (base64InputStream != null) {
				stringBuilder = new StringBuilder();
				String line;
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(base64InputStream, "UTF-8"));
					while ((line = reader.readLine()) != null) {
						stringBuilder.append(line).append("\n");
					}
				} catch (Exception e) {
					log(e);
				} finally {
					base64InputStream.close();
				}
			}
		} catch (IOException e) {
			log(e);
		}

		try {
			file_name = awsS3Connection.uploadFileToAWSS3(stringBuilder.toString(),
					"apps/well_family_house/images/stories", ".jpg");
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}

		Photo photo = new Photo();
		photo.setStory_id(story_id);
		photo.setType(0);
		photo.setName(file_name);
		photo.setExt("jpg");

		dao.insertPhoto(photo);
	}
}
