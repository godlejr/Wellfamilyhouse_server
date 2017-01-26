package com.demand.server.well_family_house.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
import com.demand.server.well_family_house.dto.CommentCount;
import com.demand.server.well_family_house.dto.Family;
import com.demand.server.well_family_house.dto.LikeCount;
import com.demand.server.well_family_house.dto.Photo;
import com.demand.server.well_family_house.dto.Result;
import com.demand.server.well_family_house.dto.StoryInfo;
import com.demand.server.well_family_house.dto.User;
import com.demand.server.well_family_house.util.ImageS3;

@RestController
public class FAMILYController {

	private static final String ACCESS_KEY = "AKIAIUGMLWN3S757JDVA";
	private static final String SECRET_KEY = "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";

	private static final String END_POINT_URL = "http://s3.ap-northeast-2.amazonaws.com";// e.g
	// http://s3.amazonaws.com
	private static final String BUCKET = "demand.files";
	// private static final String IMAGE_LOCATION = "xxx";
	// private static final String S3_CACHE = "xxx"; // e.g 60
	private static AmazonS3 s3;

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
	public ArrayList<Comment> family_detail_comment_List(@PathVariable String story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCommentList(Integer.parseInt(story_id));
	}

	@RequestMapping(value = "/family/{story_id}/insert_comment", method = { RequestMethod.GET, RequestMethod.POST })
	public void insert_comment(HttpServletRequest request, @PathVariable String story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertComment(Integer.parseInt(request.getParameter("user_id")), Integer.parseInt(story_id),
				request.getParameter("content"));
	}

	@RequestMapping(value = "/family/insert_photos", method = { RequestMethod.GET, RequestMethod.POST })
	public void insert_photos(HttpServletRequest request) {
		// ImageS3 imageS3 = new ImageS3();
	
			try {
				uploadImageToAWSS3(request.getParameter("base"), "apps/well_family_house/images/stories");
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		// ArrayList<Result> result = new ArrayList<Result>();
		// result.add(new Result(request.getParameter("s")));
		// return request.getParameter("base64_image0");
	}

	public static void uploadImageToAWSS3(String base64Data, String location)
			throws IllegalStateException, IOException {
		String fileName = null;
		try {

			byte[] base64Image = Base64.decodeBase64(base64Data);

			// String
			// directory=ImageS3.class.getResource("").getPath()+"/sample.jpg";
			//
			//
			// new FileOutputStream(directory).write(imageByte);

//			 byte[] base64Image = org.apache.commons.codec.binary.Base64
//			 .decodeBase64((base64Data).getBytes());
			InputStream imagefile = new ByteArrayInputStream(base64Image);

			AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
			// java.security.Security.setProperty("networkaddress.cache.ttl",
			// S3_CACHE);
			s3 = new AmazonS3Client(credentials);
			s3.setEndpoint(END_POINT_URL);

			// InputStream stream = multipartFile.getInputStream();
			fileName = System.currentTimeMillis() + ".jpg";
			ObjectMetadata objectMetadata = new ObjectMetadata();
		
			PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, location + "/" + fileName, imagefile,
					objectMetadata);
			// skip if do not want to access the image directly from S3
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			// skip if do not want to access the image directly from S3
			s3.putObject(putObjectRequest);
			
			
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}
	}

}
