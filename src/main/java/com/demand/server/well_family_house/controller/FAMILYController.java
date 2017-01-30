package com.demand.server.well_family_house.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.demand.server.well_family_house.dao.IDao;
import com.demand.server.well_family_house.dto.CheckBox;
import com.demand.server.well_family_house.dto.Comment;
import com.demand.server.well_family_house.dto.CommentInfo;
import com.demand.server.well_family_house.dto.CommentCount;
import com.demand.server.well_family_house.dto.Family;
import com.demand.server.well_family_house.dto.Story;
import com.demand.server.well_family_house.dto.LikeCount;
import com.demand.server.well_family_house.dto.Photo;
import com.demand.server.well_family_house.dto.Result;
import com.demand.server.well_family_house.dto.StoryInfo;
import com.demand.server.well_family_house.dto.User;
import com.demand.server.well_family_house.util.ImageS3;

@RestController
public class FAMILYController {

	@Autowired
	SqlSession well_family_house_sqlSession;

	// intro
	@RequestMapping(value = "/family/email_check", method = RequestMethod.POST)
	public ArrayList<User> email_check(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.email_check(request.getParameter("email"));
	}

	@RequestMapping(value = "/family/login", method = RequestMethod.POST)
	public ArrayList<User> login(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.login(request.getParameter("email"), request.getParameter("password"));
	}

	@RequestMapping(value = "/family/join", method = RequestMethod.POST)
	public void join(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		System.out.println(request.getParameter("name"));
		dao.join(request.getParameter("email"), request.getParameter("password"), request.getParameter("name"),
				request.getParameter("birth"), request.getParameter("phone"));
	}

	// main
	@RequestMapping(value = "/family/{id}/family_Info", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Family> family_Info(@PathVariable String id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilyInfo(Integer.parseInt(id));
	}

	// family_main
	@RequestMapping(value = "/family/{family_id}/family_user_Info", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<User> family_user_Info(HttpServletRequest request, @PathVariable String family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilyUserInfo(Integer.parseInt(family_id), Integer.parseInt(request.getParameter("user_id")));
	}

	@RequestMapping(value = "/family/{family_id}/family_content_List", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ArrayList<StoryInfo> family_content_List(@PathVariable String family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getContentList(Integer.parseInt(family_id));
	}

	@RequestMapping(value = "/family/{family_id}/family_photo_List", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Photo> family_photo_List(@PathVariable String family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getPhotoList(Integer.parseInt(family_id));
	}

	// content_info (family_main)
	@RequestMapping(value = "/family/{story_id}/family_comment_Count", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ArrayList<CommentCount> family_comment_Count(@PathVariable String story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCommentCount(Integer.parseInt(story_id));
	}

	@RequestMapping(value = "/family/{story_id}/family_like_Count", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<LikeCount> family_like_Count(@PathVariable String story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getLikeCount(Integer.parseInt(story_id));
	}

	@RequestMapping(value = "/family/{story_id}/family_content_photo_List", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ArrayList<Photo> family_content_photo_List(@PathVariable String story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getContentPhotoList(Integer.parseInt(story_id));
	}

	@RequestMapping(value = "/family/{story_id}/family_content_like_up", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void family_content_like_up(HttpServletRequest request, @PathVariable String story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateLikeUp(Integer.parseInt(request.getParameter("user_id")), Integer.parseInt(story_id));
	}

	@RequestMapping(value = "/family/{story_id}/family_content_like_down", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void family_content_like_down(HttpServletRequest request, @PathVariable String story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateLikeDown(Integer.parseInt(request.getParameter("user_id")), Integer.parseInt(story_id));
	}

	@RequestMapping(value = "/family/{story_id}/family_content_like_check", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ArrayList<CheckBox> family_content_like_check(HttpServletRequest request, @PathVariable String story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.checkLike(Integer.parseInt(request.getParameter("user_id")), Integer.parseInt(story_id));
	}

	// user_info (user_id)
	@RequestMapping(value = "/family/user_Info", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<User> user_Info(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getUserInfo(Integer.parseInt(request.getParameter("user_id")));
	}

	// comment
	@RequestMapping(value = "/family/{story_id}/family_detail_comment_List", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ArrayList<CommentInfo> family_detail_comment_List(@PathVariable String story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCommentList(Integer.parseInt(story_id));
	}

	@RequestMapping(value = "/family/{story_id}/insert_comment", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Comment> insert_comment(HttpServletRequest request, @PathVariable String story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		
		Comment comment = new Comment();
		comment.setUser_id(Integer.parseInt(request.getParameter("user_id")));
		comment.setStory_id(Integer.parseInt(story_id));
		comment.setContent(request.getParameter("content"));
		
		dao.insertComment(comment);
		return dao.getComment(comment.getId());
	}

	// insert story
	@RequestMapping(value = "/family/{user_id}/insert_story", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Story> insert_story(HttpServletRequest request, @PathVariable String user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		ArrayList<Story> result = new ArrayList<Story>();

		Story story = new Story();
		story.setUser_id(Integer.parseInt(user_id));
		story.setFamily_id(Integer.parseInt(request.getParameter("family_id")));
		story.setContent(request.getParameter("content"));

		dao.insertStory(story);
		
		return dao.getStory(story.getId());
	}

	@RequestMapping(value = "/family/{story_id}/insert_photos", method = { RequestMethod.GET, RequestMethod.POST })
	public void insert_photos(HttpServletRequest request, @PathVariable String story_id) {
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
				} finally {
					base64InputStream.close();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			file_name = uploadImageToAWSS3(stringBuilder.toString(), "apps/well_family_house/images/stories");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Photo photo = new Photo();
		photo.setStory_id(Integer.parseInt(story_id));
		photo.setType(0);
		photo.setName(file_name);
		photo.setExt("jpg");
		
		dao.insertPhoto(photo);
	}

	public static String uploadImageToAWSS3(String base64Data, String location)
			throws IllegalStateException, IOException {
		String ACCESS_KEY = "AKIAIUGMLWN3S757JDVA";
		String SECRET_KEY = "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";
		String END_POINT_URL = "http://s3.ap-northeast-2.amazonaws.com";
		String BUCKET = "demand.files";
		AmazonS3 s3;
		String fileName = null;

		try {
			byte[] base64Image = org.apache.commons.codec.binary.Base64.decodeBase64(base64Data);
			InputStream imagefile = new ByteArrayInputStream(base64Image);
			AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
			s3 = new AmazonS3Client(credentials);
			s3.setEndpoint(END_POINT_URL);
			fileName = System.currentTimeMillis() + "";
			ObjectMetadata objectMetadata = new ObjectMetadata();
			PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, location + "/" + fileName + ".jpg",
					imagefile, objectMetadata);
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			s3.putObject(putObjectRequest);

		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}

		return fileName;
	}

}
