package com.demand.server.well_family_house.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.json.JSONObject;
import com.demand.server.HomeController;
import com.demand.server.well_family_house.dao.IDao;
import com.demand.server.well_family_house.dto.Check;
import com.demand.server.well_family_house.dto.Comment;
import com.demand.server.well_family_house.dto.CommentCount;
import com.demand.server.well_family_house.dto.CommentInfo;
import com.demand.server.well_family_house.dto.Family;
import com.demand.server.well_family_house.dto.Category;
import com.demand.server.well_family_house.dto.Identification;
import com.demand.server.well_family_house.dto.LikeCount;
import com.demand.server.well_family_house.dto.Notification;
import com.demand.server.well_family_house.dto.Photo;
import com.demand.server.well_family_house.dto.Range;
import com.demand.server.well_family_house.dto.Song;
import com.demand.server.well_family_house.dto.SongCategory;
import com.demand.server.well_family_house.dto.SongComment;
import com.demand.server.well_family_house.dto.SongPhoto;
import com.demand.server.well_family_house.dto.SongStory;
import com.demand.server.well_family_house.dto.SongStoryAvatar;
import com.demand.server.well_family_house.dto.SongStoryComment;
import com.demand.server.well_family_house.dto.SongStoryEmotionData;
import com.demand.server.well_family_house.dto.SongStoryEmotionInfo;
import com.demand.server.well_family_house.dto.Story;
import com.demand.server.well_family_house.dto.StoryInfo;
import com.demand.server.well_family_house.dto.User;
import com.demand.server.well_family_house.log.LogFlag;

@RestController
@RequestMapping("/family")
public class FAMILYController {

	@Autowired
	private SqlSession well_family_house_sqlSession;

	private static final String ACCESS_KEY = "AKIAIUGMLWN3S757JDVA";
	private static final String SECRET_KEY = "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";
	private static final String END_POINT_URL = "http://s3.ap-northeast-2.amazonaws.com";
	private static final String BUCKET = "demand.files";
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

	public static String uploadFileToAWSS3(String base64Data, String location, String ext)
			throws IllegalStateException, IOException {
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

			PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, location + "/" + fileName + ext, imagefile,
					objectMetadata);
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			s3.putObject(putObjectRequest);

		} catch (AmazonServiceException e) {
			log(e);
		}

		return fileName;
	}

	public static void deleteFileFromAWSS3(String location, String fileName, String ext) {
		AmazonS3 s3;

		AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		s3 = new AmazonS3Client(credentials);
		s3.setEndpoint(END_POINT_URL);

		try {
			s3.deleteObject(new DeleteObjectRequest(BUCKET, location + "/" + fileName + ext));
		} catch (AmazonServiceException ase) {
			log(ase);
		} catch (AmazonClientException ace) {
			log(ace);
		}
	}

	public static void sendFCM(int id, int story_id) {

	}

	public void sendFCM(int id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		
//		JSONObject fcm = new JSONObject();
//		if(id== 1){
//			
//			fcm.put("to", value)
//		}
	}

	// intro
	@RequestMapping(value = "/email_check", method = RequestMethod.POST)
	public ArrayList<User> email_check(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.email_check(request.getParameter("email"));
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ArrayList<User> login(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.login(request.getParameter("email"), request.getParameter("password"));
	}

	@RequestMapping(value = "/{user_id}/update_deviceId_token", method = RequestMethod.PUT)
	public void update_deviceId_token(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateDeviceIdToken(user_id, request.getParameter("device_id"), request.getParameter("token"));
	}

	@RequestMapping(value = "/{user_id}/check_device_id", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Check> check_device_id(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.checkDeviceId(user_id, request.getParameter("device_id"));
	}

	@RequestMapping(value = "/{user_id}/update_token", method = RequestMethod.PUT)
	public void update_token(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateToken(user_id, request.getParameter("token"));
	}

	@RequestMapping(value = "/{login_category_id}/join", method = RequestMethod.POST)
	public void join(HttpServletRequest request, @PathVariable int login_category_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		System.out.println(request.getParameter("name"));
		dao.join(request.getParameter("email"), request.getParameter("password"), request.getParameter("name"),
				request.getParameter("birth"), request.getParameter("phone"), login_category_id);
	}

	// main
	@RequestMapping(value = "/{user_id}/family_Info", method = RequestMethod.GET)
	public ArrayList<Family> family_Info(@PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilyInfo(user_id);
	}

	@RequestMapping(value = "/{family_id}/family", method = RequestMethod.GET)
	public ArrayList<Family> family(@PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamily(family_id);
	}

	@RequestMapping(value = "/{family_id}/family_info_by_creator", method = RequestMethod.GET)
	public ArrayList<Family> family_info_by_creator(@PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilyInfoByCreator(family_id);
	}

	// family_main
	@RequestMapping(value = "/{family_id}/family_user_Info", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<User> family_user_Info(HttpServletRequest request, @PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilyUserInfo(family_id, Integer.parseInt(request.getParameter("user_id")));
	}

	@RequestMapping(value = "/{family_id}/family_content_List", method = RequestMethod.GET)
	public ArrayList<StoryInfo> family_content_List(@PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getContentList(family_id);
	}

	@RequestMapping(value = "/{family_id}/family_photo_List", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Photo> family_photo_List(@PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getPhotoList(family_id);
	}

	// content_info (family_main)
	@RequestMapping(value = "/{story_id}/family_comment_Count", method = RequestMethod.GET)
	public ArrayList<CommentCount> family_comment_Count(@PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCommentCount(story_id);
	}

	@RequestMapping(value = "/{story_id}/family_like_Count", method = RequestMethod.GET)
	public ArrayList<LikeCount> family_like_Count(@PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getLikeCount(story_id);
	}

	@RequestMapping(value = "/{story_id}/family_content_photo_List", method = RequestMethod.GET)
	public ArrayList<Photo> family_content_photo_List(@PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getContentPhotoList(story_id);
	}

	@RequestMapping(value = "/{story_id}/family_content_like_up", method = RequestMethod.POST)
	public void family_content_like_up(HttpServletRequest request, @PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateLikeUp(Integer.parseInt(request.getParameter("user_id")), story_id);
	}

	@RequestMapping(value = "/{story_id}/family_content_like_down", method = RequestMethod.DELETE)
	public void family_content_like_down(HttpServletRequest request, @PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateLikeDown(Integer.parseInt(request.getParameter("user_id")), story_id);
	}

	@RequestMapping(value = "/{story_id}/family_content_like_check", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Check> family_content_like_check(HttpServletRequest request, @PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.checkLike(Integer.parseInt(request.getParameter("user_id")), story_id);
	}

	// user_info (user_id)
	@RequestMapping(value = "/user_Info", method = RequestMethod.POST)
	public ArrayList<User> user_Info(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getUserInfo(Integer.parseInt(request.getParameter("user_id")));
	}

	// comment
	@RequestMapping(value = "/{story_id}/family_detail_comment_List", method = RequestMethod.GET)
	public ArrayList<CommentInfo> family_detail_comment_List(@PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCommentList(story_id);
	}

	@RequestMapping(value = "/{story_id}/insert_comment", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Comment> insert_comment(HttpServletRequest request, @PathVariable int story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);

		Comment comment = new Comment();
		comment.setUser_id(Integer.parseInt(request.getParameter("user_id")));
		comment.setStory_id(story_id);
		comment.setContent(request.getParameter("content"));

		dao.insertComment(comment);
		return dao.getComment(comment.getId());
	}

	// insert story
	@RequestMapping(value = "/{user_id}/insert_story", method = RequestMethod.POST)
	public ArrayList<Story> insert_story(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		Story story = new Story();
		story.setUser_id(user_id);
		story.setFamily_id(Integer.parseInt(request.getParameter("family_id")));
		story.setContent(request.getParameter("content"));

		dao.insertStory(story);

		return dao.getStory(story.getId());
	}

	@RequestMapping(value = "/{story_id}/insert_photos", method = RequestMethod.POST)
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
			file_name = uploadFileToAWSS3(stringBuilder.toString(), "apps/well_family_house/images/stories", ".jpg");
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

	// memory_sound main
	@RequestMapping(value = "/song_category_List", method = RequestMethod.GET)
	public ArrayList<SongCategory> song_category_List() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongCategoryList();
	}

	// count (memory_sound main)
	@RequestMapping(value = "/{song_id}/song_comment_Count", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<CommentCount> song_comment_Count(@PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongCommentCount(song_id);
	}

	@RequestMapping(value = "/{song_id}/song_like_Count", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<LikeCount> song_like_Count(@PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongLikeCount(song_id);
	}

	@RequestMapping(value = "/song_list_by_Hits", method = RequestMethod.GET)
	public ArrayList<Song> song_list_by_Hits() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongListByHits();
	}

	@RequestMapping(value = "/song_random", method = RequestMethod.GET)
	public ArrayList<Song> song_random() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getRandomSong(randomRange(149, 295));
	}

	public static int randomRange(int n1, int n2) {
		return (int) (Math.random() * (n2 - n1 + 1)) + n1;
	}

	@RequestMapping(value = "/{category_id}/song_list_by_Category", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Song> song_list_by_Category(@PathVariable int category_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongListByCategory(category_id);
	}

	@RequestMapping(value = "/{song_id}/Insert_Song_hit", method = RequestMethod.PUT)
	public void Insert_Song_hit(@PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertSongHit(song_id);
	}

	// memory sound comment
	@RequestMapping(value = "/{song_id}/song_comment_List", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<CommentInfo> song_comment_List(@PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongCommentList(song_id);
	}

	@RequestMapping(value = "/{song_id}/song_like_up", method = { RequestMethod.GET, RequestMethod.POST })
	public void song_like_up(HttpServletRequest request, @PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateSongLikeUp(Integer.parseInt(request.getParameter("user_id")), song_id);
	}

	@RequestMapping(value = "/{song_id}/song_like_down", method = RequestMethod.DELETE)
	public void song_like_down(HttpServletRequest request, @PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateSongLikeDown(Integer.parseInt(request.getParameter("user_id")), song_id);
	}

	@RequestMapping(value = "/{song_id}/song_like_check", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Check> song_like_check(HttpServletRequest request, @PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.checkSongLike(Integer.parseInt(request.getParameter("user_id")), song_id);
	}

	@RequestMapping(value = "/{song_id}/insert_song_comment", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<SongComment> insert_song_comment(HttpServletRequest request, @PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		SongComment songComment = new SongComment();
		songComment.setUser_id(Integer.parseInt(request.getParameter("user_id")));
		songComment.setSong_id(song_id);
		songComment.setContent(request.getParameter("content"));

		dao.insertSongComment(songComment);
		return dao.getSongComment(songComment.getId());
	}

	@RequestMapping(value = "/song_range_List", method = RequestMethod.GET)
	public ArrayList<Range> song_range_List() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongRangeList();
	}

	// insert story
	@RequestMapping(value = "/{user_id}/insert_song_story", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<SongStory> insert_song_story(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		SongStory songStory = new SongStory();
		songStory.setUser_id(user_id);
		songStory.setRange_id(Integer.parseInt(request.getParameter("range_id")));
		songStory.setSong_id(Integer.parseInt(request.getParameter("song_id")));
		songStory.setSong_title(request.getParameter("song_title"));
		songStory.setSong_singer(request.getParameter("song_singer"));
		songStory.setContent(request.getParameter("content"));
		songStory.setLocation(request.getParameter("location"));
		dao.insertSongStory(songStory);
		return dao.getSongStory(songStory.getId());
	}

	@RequestMapping(value = "/{song_story_id}/insert_song_photos", method = { RequestMethod.GET, RequestMethod.POST })
	public void insert_song_photos(HttpServletRequest request, @PathVariable int song_story_id) {
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
			file_name = uploadFileToAWSS3(stringBuilder.toString(), "apps/well_family_house/images/songstories",
					".jpg");
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}

		SongPhoto songPhoto = new SongPhoto();
		songPhoto.setSong_story_id(song_story_id);
		songPhoto.setType(0);
		songPhoto.setName(file_name);
		songPhoto.setExt("jpg");

		dao.insertSongPhoto(songPhoto);
	}

	@RequestMapping(value = "/{song_story_id}/insert_song_audio", method = RequestMethod.PUT)
	public void insert_song_audio(HttpServletRequest request, @PathVariable int song_story_id) {
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
			file_name = uploadFileToAWSS3(stringBuilder.toString(), "apps/well_family_house/songs/records", ".mp3");
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}
		dao.insertAudio(song_story_id, file_name + ".mp3");
	}

	// count (memory_sound my page)
	@RequestMapping(value = "/{song_story_id}/song_story_comment_Count", method = RequestMethod.GET)
	public ArrayList<CommentCount> song_story_comment_Count(@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryCommentCount(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/song_story_like_Count", method = RequestMethod.GET)
	public ArrayList<LikeCount> song_story_like_Count(@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryLikeCount(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/song_story_like_up", method = { RequestMethod.GET, RequestMethod.POST })
	public void song_story_like_up(HttpServletRequest request, @PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateSongStoryLikeUp(Integer.parseInt(request.getParameter("user_id")), song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/song_story_like_down", method = RequestMethod.DELETE)
	public void song_story_like_down(HttpServletRequest request, @PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateSongStoryLikeDown(Integer.parseInt(request.getParameter("user_id")), song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/song_story_like_check", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ArrayList<Check> song_story_like_check(HttpServletRequest request, @PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.checkSongStoryLike(Integer.parseInt(request.getParameter("user_id")), song_story_id);
	}

	@RequestMapping(value = "/{story_user_id}/family_check", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Check> family_check(HttpServletRequest request, @PathVariable int story_user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.checkFamily(Integer.parseInt(request.getParameter("user_id")), story_user_id);
	}

	@RequestMapping(value = "/{story_user_id}/song_story_List_Public", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ArrayList<SongStory> song_story_List_Public(@PathVariable int story_user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryPublicList(story_user_id);
	}

	@RequestMapping(value = "/{story_user_id}/song_story_List_Family", method = RequestMethod.GET)
	public ArrayList<SongStory> song_story_List_Family(@PathVariable int story_user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryFamilyList(story_user_id);
	}

	@RequestMapping(value = "/{story_user_id}/song_story_List_Me", method = RequestMethod.GET)
	public ArrayList<SongStory> song_story_List_Me(@PathVariable int story_user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryMeList(story_user_id);
	}

	@RequestMapping(value = "/{song_story_id}/song_story_photo_List", method = RequestMethod.GET)
	public ArrayList<SongPhoto> song_story_photo_List(@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryPhotoList(song_story_id);
	}

	// songstory comment
	@RequestMapping(value = "/{song_story_id}/song_story_comment_List", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ArrayList<CommentInfo> song_story_comment_List(@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryCommentList(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/insert_song_story_comment", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ArrayList<SongStoryComment> insert_song_story_comment(HttpServletRequest request,
			@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		SongStoryComment songStoryComment = new SongStoryComment();
		songStoryComment.setUser_id(Integer.parseInt(request.getParameter("user_id")));
		songStoryComment.setSong_story_id(song_story_id);
		songStoryComment.setContent(request.getParameter("content"));

		dao.insertSongStoryComment(songStoryComment);
		return dao.getSongStoryComment(songStoryComment.getId());
	}

	@RequestMapping(value = "/{song_story_id}/song_story_avatar", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<SongStoryAvatar> song_story_avatar(HttpServletRequest request, @PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryAvatar(Integer.parseInt(request.getParameter("song_id")));
	}

	@RequestMapping(value = "/{user_id}/find_family", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Family> find_family(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilySearchList(request.getParameter("search"));
	}

	@RequestMapping(value = "/{user_id}/find_user", method = RequestMethod.POST)
	public ArrayList<User> find_user(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getUserSearchList(request.getParameter("search"));
	}

	// insert_family
	@RequestMapping(value = "/{user_id}/insert_family", method = { RequestMethod.GET, RequestMethod.POST })
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
		notification.setReceive_category_id(1); // notify for me
		notification.setReceiver_id(user_id);
		notification.setContent_name(request.getParameter("family_name")); // family_name
		notification.setBehavior_id(1);

		dao.insertNotification(notification); // alarm
		sendFCM(notification.getId());

		return identificationList;
	}

	@RequestMapping(value = "/{family_id}/update_family_avatar", method = RequestMethod.PUT)
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
			deleteFileFromAWSS3("apps/well_family_house/images/avatars/familys", fileName, "");
		}

		try {
			file_name = uploadFileToAWSS3(stringBuilder.toString(), "apps/well_family_house/images/avatars/familys",
					".jpg");
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}

		dao.updateFamilyAvatar(family_id, file_name + ".jpg");
	}

	// invite user
	@RequestMapping(value = "/{family_id}/insert_user_into_family", method = RequestMethod.POST)
	public void insert_user_into_family(HttpServletRequest request, @PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertUserIntoFamily(family_id, Integer.parseInt(request.getParameter("user_id")));
	}

	// delete user from family
	@RequestMapping(value = "/{family_id}/delete_user_from_family", method = RequestMethod.DELETE)
	public void delete_user_from_family(HttpServletRequest request, @PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.deleteUserFromFamily(family_id, Integer.parseInt(request.getParameter("user_id")));
	}

	// emotions
	@RequestMapping(value = "/song_story_emotion_List", method = RequestMethod.GET)
	public ArrayList<SongStoryEmotionInfo> song_story_emotion_List() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getEmotionList();
	}

	// invite user
	@RequestMapping(value = "/{song_story_id}/insert_emotion_into_song_story", method = RequestMethod.POST)
	public void insert_emotion_into_song_story(HttpServletRequest request, @PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertEmotionIntoSongStory(song_story_id, Integer.parseInt(request.getParameter("song_story_emotion_id")));
	}

	// songstory emotions
	@RequestMapping(value = "/{song_story_id}/song_story_emotion_data_List", method = RequestMethod.GET)
	public ArrayList<SongStoryEmotionData> song_story_emotion_data_List(@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryEmotionData(song_story_id);
	}

	// family_user_check
	@RequestMapping(value = "/{other_user_id}/family_user_check", method = RequestMethod.POST)
	public ArrayList<Check> family_user_check(HttpServletRequest request, @PathVariable int other_user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilyUserCheck(Integer.parseInt(request.getParameter("family_id")),
				Integer.parseInt(request.getParameter("user_id")), other_user_id);
	}

	// profile edit
	@RequestMapping(value = "/favorite_category_List", method = RequestMethod.GET)
	public ArrayList<Category> favorite_category_List() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFavoriteCategoryList();
	}

	@RequestMapping(value = "/{user_id}/check_gender", method = RequestMethod.GET)
	public ArrayList<Check> check_gender(@PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCheckGender(user_id);
	}

	@RequestMapping(value = "/{user_id}/check_favorite", method = RequestMethod.POST)
	public ArrayList<Check> check_favorite(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCheckFavorite(user_id, Integer.parseInt(request.getParameter("favorite_category_id")));
	}

	@RequestMapping(value = "/{user_id}/check_song_category", method = RequestMethod.POST)
	public ArrayList<Check> check_song_category(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCheckSongCategory(user_id, Integer.parseInt(request.getParameter("song_category_id")));
	}

	@RequestMapping(value = "/{user_id}/update_user_avatar", method = RequestMethod.PUT)
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
			deleteFileFromAWSS3("apps/well_family_house/images/avatars/users", fileName, "");
		}

		try {
			file_name = uploadFileToAWSS3(stringBuilder.toString(), "apps/well_family_house/images/avatars/users",
					".jpg");
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}

		dao.updateUserAvatar(user_id, file_name + ".jpg");
	}

	@RequestMapping(value = "/delete_favorite", method = RequestMethod.DELETE)
	public void delete_favorite(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.deleteFavorite(Integer.parseInt(request.getParameter("user_id")));
	}

	@RequestMapping(value = "/{user_id}/insert_favorite", method = RequestMethod.POST)
	public void insert_favorite(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertFavorite(user_id, Integer.parseInt(request.getParameter("favorite_id")));
	}

	@RequestMapping(value = "/delete_song_category", method = RequestMethod.DELETE)
	public void delete_song_category(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.deleteSongCategory(Integer.parseInt(request.getParameter("user_id")));
	}

	@RequestMapping(value = "/{user_id}/insert_song_category", method = RequestMethod.POST)
	public void insert_song_category(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertSongCategory(user_id, Integer.parseInt(request.getParameter("song_category_id")));
	}

	@RequestMapping(value = "/{user_id}/udpate_user_info", method = RequestMethod.PUT)
	public void udpate_user_info(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		String name = request.getParameter("name");
		String birth = request.getParameter("birth");
		String phone = request.getParameter("phone");
		int gender = Integer.parseInt(request.getParameter("gender"));

		dao.udpateUserInfo(user_id, name, birth, phone, gender);
	}

	// family_edit
	@RequestMapping(value = "/{family_id}/update_family_info", method = RequestMethod.PUT)
	public void update_family_info(HttpServletRequest request, @PathVariable int family_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateFamilyInfo(family_id, request.getParameter("name"), request.getParameter("content"));
	}

	// comment_edit
	@RequestMapping(value = "/{comment_id}/update_comment", method = RequestMethod.PUT)
	public void update_comment(HttpServletRequest request, @PathVariable int comment_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		int flag = Integer.parseInt(request.getParameter("flag"));
		if (flag == 1) {
			dao.updateComment(comment_id, request.getParameter("content"));
		}

		if (flag == 2) {
			dao.updateSongComment(comment_id, request.getParameter("content"));
		}

		if (flag == 3) {
			dao.updateSongStoryComment(comment_id, request.getParameter("content"));
		}
	}

	@RequestMapping(value = "/delete_comment", method = RequestMethod.DELETE)
	public void delete_comment(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		int flag = Integer.parseInt(request.getParameter("flag"));
		if (flag == 1) {
			dao.deleteComment(Integer.parseInt(request.getParameter("comment_id")));
		}
		if (flag == 2) {
			dao.deleteSongComment(Integer.parseInt(request.getParameter("comment_id")));
		}
		if (flag == 3) {
			dao.deleteSongStoryComment(Integer.parseInt(request.getParameter("comment_id")));
		}
	}

	// report
	@RequestMapping(value = "/report_category_List", method = RequestMethod.GET)
	public ArrayList<Category> report_category_List() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getReportCategoryList();
	}

	// comment_report
	@RequestMapping(value = "/{user_id}/insert_comment_report", method = RequestMethod.POST)
	public void insert_comment_report(HttpServletRequest request, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertCommentReport(user_id, Integer.parseInt(request.getParameter("comment_category_id")),
				Integer.parseInt(request.getParameter("report_category_id")),
				Integer.parseInt(request.getParameter("comment_id")));
	}

}
