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
import com.demand.server.well_family_house.dto.Family;
import com.demand.server.well_family_house.dto.Photo;
import com.demand.server.well_family_house.dto.StoryInfo;
import com.demand.server.well_family_house.dto.User;
import com.demand.server.well_family_house.flag.LogFlag;
import com.demand.server.well_family_house.util.AwsS3Connection;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/families")
public class FAMILYController {

	@Autowired
	private SqlSession well_family_house_sqlSession;
	
	@Autowired
	private AwsS3Connection awsS3Connection;

	private static final Logger logger = LoggerFactory.getLogger(FAMILYController.class);

	
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

	// main
	@RequestMapping(value = "/{family_id}", method = RequestMethod.GET)
	public Family family(@PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamily(family_id);
	}

	// family_main
	@RequestMapping(value = "/{family_id}/usersBut/{user_id}", method = RequestMethod.GET)
	public ArrayList<User> family_user_Info(HttpServletRequest request, @PathVariable int family_id,@PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilyUserInfo(family_id,user_id);
	}

	@RequestMapping(value = "/{family_id}/contents", method = RequestMethod.GET)
	public ArrayList<StoryInfo> family_content_List(@PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getContentList(family_id);
	}

	@RequestMapping(value = "/{family_id}/photos", method = RequestMethod.GET)
	public ArrayList<Photo> family_photo_List(@PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getPhotoList(family_id);
	}
	
	@RequestMapping(value = "/{family_id}/avatars", method = RequestMethod.PUT)
	public void update_family_avatar(HttpServletRequest request, @PathVariable int family_id) {
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
		String fileName = dao.getFamilyAvatar(family_id).getAvatar();
		if (!fileName.equals("family_avatar.jpg")) {
			awsS3Connection.deleteFileFromAWSS3("apps/well_family_house/images/avatars/familys", fileName, "");
		}

		try {
			file_name = awsS3Connection.uploadFileToAWSS3(stringBuilder.toString(), "apps/well_family_house/images/avatars/familys",
					".jpg");
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}

		dao.updateFamilyAvatar(family_id, file_name + ".jpg");
	}
	
	// insert user to family
	@RequestMapping(value = "/{family_id}/users", method = RequestMethod.POST)
	public void insert_user_into_family(HttpServletRequest request, @PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertUserIntoFamily(family_id, Integer.parseInt(request.getParameter("user_id")));
	}

	// delete user from family
	@RequestMapping(value = "/{family_id}/users", method = RequestMethod.DELETE)
	public void delete_user_from_family(HttpServletRequest request, @PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.deleteUserFromFamily(family_id, Integer.parseInt(request.getParameter("user_id")));
	}

	// family_edit
	@RequestMapping(value = "/{family_id}", method = RequestMethod.PUT)
	public void update_family_info(HttpServletRequest request, @PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateFamilyInfo(family_id, request.getParameter("name"), request.getParameter("content"));
	}
}
