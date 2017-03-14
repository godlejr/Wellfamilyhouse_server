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
import com.demand.server.well_family_house.dto.Category;
import com.demand.server.well_family_house.dto.Check;
import com.demand.server.well_family_house.dto.Family;
import com.demand.server.well_family_house.dto.Identification;
import com.demand.server.well_family_house.dto.Notification;
import com.demand.server.well_family_house.dto.SongStory;
import com.demand.server.well_family_house.dto.User;
import com.demand.server.well_family_house.flag.LogFlag;
import com.demand.server.well_family_house.flag.NotificationBEHAVIORFlag;
import com.demand.server.well_family_house.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.flag.NotificationTOFlag;
import com.demand.server.well_family_house.util.AndroidPushConnection;
import com.demand.server.well_family_house.util.AwsS3Connection;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/users")
public class USERController {
	@Autowired
	private SqlSession well_family_house_sqlSession;

	@Autowired
	private AndroidPushConnection androidPushConnection;

	@Autowired
	private AwsS3Connection awsS3Connection;

	private static final Logger logger = LoggerFactory.getLogger(USERController.class);

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

	@RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
	public ArrayList<User> user_Info(@PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getUserInfo(user_id);
	}

	@RequestMapping(value = "/{user_id}/tokens/{token}/deviceids/{device_id}", method = RequestMethod.PUT)
	public void update_deviceId_token(HttpServletRequest request, @PathVariable int user_id, @PathVariable String token,
			@PathVariable String device_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateDeviceIdToken(user_id, device_id, token);
	}

	@RequestMapping(value = "/{user_id}/deviceids/{device_id}", method = RequestMethod.GET)
	public ArrayList<Check> check_device_id(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable String device_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.checkDeviceId(user_id, device_id);
	}

	@RequestMapping(value = "/{user_id}/tokens/{token}", method = RequestMethod.PUT)
	public void update_token(HttpServletRequest request, @PathVariable int user_id, @PathVariable String token) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateToken(user_id, token);
	}

	@RequestMapping(value = "/{user_id}/families", method = RequestMethod.GET)
	public ArrayList<Family> family_Info(@PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilyInfo(user_id);
	}

	// user_check from total families
	@RequestMapping(value = "/{story_user_id}/family_check/{user_id}", method = RequestMethod.GET)
	public ArrayList<Check> family_check(HttpServletRequest request, @PathVariable int story_user_id,
			@PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.checkFamily(user_id, story_user_id);
	}

	@RequestMapping(value = "/{story_user_id}/public_songstories", method = RequestMethod.GET)
	public ArrayList<SongStory> song_story_List_Public(@PathVariable int story_user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryPublicList(story_user_id);
	}

	@RequestMapping(value = "/{story_user_id}/family_songstories", method = RequestMethod.GET)
	public ArrayList<SongStory> song_story_List_Family(@PathVariable int story_user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryFamilyList(story_user_id);
	}

	@RequestMapping(value = "/{story_user_id}/me_songstories", method = RequestMethod.GET)
	public ArrayList<SongStory> song_story_List_Me(@PathVariable int story_user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryMeList(story_user_id);
	}

	@RequestMapping(value = "/{user_id}/find_family/{search}", method = RequestMethod.GET)
	public ArrayList<Family> find_family(@PathVariable int user_id, @PathVariable String search) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilySearchList(search);
	}

	@RequestMapping(value = "/{user_id}/find_user/{search}", method = RequestMethod.GET)
	public ArrayList<User> find_user(@PathVariable int user_id, @PathVariable String search) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getUserSearchList(search);
	}

	// insert_family
	@RequestMapping(value = "/{user_id}/familys", method = RequestMethod.POST)
	public ArrayList<Identification> insert_family(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);

		ArrayList<Identification> identificationList = new ArrayList<Identification>();

		Family family = new Family();
		family.setName(request.getParameter("family_name"));
		family.setContent(request.getParameter("family_content"));
		family.setUser_id(user_id);

		dao.insertFamily(family);
		dao.insertFamilyJoiner(family.getId(), user_id);
		Identification identification = new Identification(family.getId());

		identificationList.add(identification);

		// push
		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.ME); 
		notification.setReceiver_id(user_id);
		notification.setContent_name(request.getParameter("family_name"));
		notification.setIntent_flag(NotificationINTENTFlag.FAMILY);
		notification.setIntent_id(family.getId());
		notification.setBehavior_id(NotificationBEHAVIORFlag.CREATING_THE_FAMILY);

		dao.insertNotification(notification); // insert notification
		androidPushConnection.sendFCM(notification);

		return identificationList;
	}

	// user_check from particular family
	@RequestMapping(value = "/{other_user_id}/sole_family_check/{user_id}", method = RequestMethod.GET)
	public ArrayList<Check> family_user_check(HttpServletRequest request, @PathVariable int other_user_id,
			@PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilyUserCheck(Integer.parseInt(request.getParameter("family_id")), user_id, other_user_id);
	}

	// edit profile
	@RequestMapping(value = "/favorite_categories", method = RequestMethod.GET)
	public ArrayList<Category> favorite_category_List() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFavoriteCategoryList();
	}

	@RequestMapping(value = "/{user_id}/check_genders", method = RequestMethod.GET)
	public ArrayList<Check> check_gender(@PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCheckGender(user_id);
	}

	@RequestMapping(value = "/{user_id}/favorite_check/{favorite_category_id}", method = RequestMethod.GET)
	public ArrayList<Check> check_favorite(@PathVariable int user_id, @PathVariable int favorite_category_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCheckFavorite(user_id, favorite_category_id);
	}

	@RequestMapping(value = "/{user_id}/song_favorite_check/{song_category_id}", method = RequestMethod.GET)
	public ArrayList<Check> check_song_category( @PathVariable int user_id, @PathVariable int song_category_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCheckSongCategory(user_id, song_category_id);
	}

	
	@RequestMapping(value = "/{user_id}/avatars", method = RequestMethod.PUT)
	public void update_user_avatar(HttpServletRequest request, @PathVariable int user_id) {
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

		// delete prior avatar
		String fileName = dao.getUserAvatar(user_id).getAvatar();
		if (!fileName.equals("avatar.jpg")) {
			awsS3Connection.deleteFileFromAWSS3("apps/well_family_house/images/avatars/users", fileName, "");
		}

		try {
			file_name = awsS3Connection.uploadFileToAWSS3(stringBuilder.toString(),
					"apps/well_family_house/images/avatars/users", ".jpg");
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}

		dao.updateUserAvatar(user_id, file_name + ".jpg");
	}

	@RequestMapping(value = "/{user_id}/favorites", method = RequestMethod.DELETE)
	public void delete_favorite(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.deleteFavorite(user_id);
	}
	

	@RequestMapping(value = "/{user_id}/favorites", method = RequestMethod.POST)
	public void insert_favorite(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertFavorite(user_id, Integer.parseInt(request.getParameter("favorite_id")));
	}

	
	@RequestMapping(value = "/{user_id}/song_categories", method = RequestMethod.DELETE)
	public void delete_song_category(@PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.deleteSongCategory(user_id);
	}

	@RequestMapping(value = "/{user_id}/song_categories", method = RequestMethod.POST)
	public void insert_song_category(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertSongCategory(user_id, Integer.parseInt(request.getParameter("song_category_id")));
	}

	
	@RequestMapping(value = "/{user_id}", method = RequestMethod.PUT)
	public void udpate_user_info(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		String name = request.getParameter("name");
		String birth = request.getParameter("birth");
		String phone = request.getParameter("phone");
		int gender = Integer.parseInt(request.getParameter("gender"));

		dao.udpateUserInfo(user_id, name, birth, phone, gender);
	}
	
	// comment_report
	@RequestMapping(value = "/{user_id}/comment_reports", method = RequestMethod.POST)
	public void insert_comment_report(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertCommentReport(user_id, Integer.parseInt(request.getParameter("comment_category_id")),
				Integer.parseInt(request.getParameter("report_category_id")),
				Integer.parseInt(request.getParameter("comment_id")));
	}

	//notification 
	@RequestMapping(value = "/{user_id}/notifications", method = RequestMethod.GET)
	public ArrayList<Notification> notifications(@PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getNotification(user_id);
	}

}
