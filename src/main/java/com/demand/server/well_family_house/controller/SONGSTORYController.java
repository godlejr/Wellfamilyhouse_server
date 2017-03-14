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
import com.demand.server.well_family_house.dto.CommentInfo;
import com.demand.server.well_family_house.dto.SongPhoto;
import com.demand.server.well_family_house.dto.SongStoryComment;
import com.demand.server.well_family_house.dto.SongStoryEmotionData;
import com.demand.server.well_family_house.flag.LogFlag;
import com.demand.server.well_family_house.util.AndroidPushConnection;
import com.demand.server.well_family_house.util.AwsS3Connection;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/songstories")
public class SONGSTORYController {

	@Autowired
	private SqlSession well_family_house_sqlSession;

	@Autowired
	private AndroidPushConnection androidPushConnection;

	@Autowired
	private AwsS3Connection awsS3Connection;

	private static final Logger logger = LoggerFactory.getLogger(SONGSTORYController.class);

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

	@RequestMapping(value = "/{song_story_id}/photos", method = RequestMethod.POST)
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
			file_name = awsS3Connection.uploadFileToAWSS3(stringBuilder.toString(),
					"apps/well_family_house/images/songstories", ".jpg");
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

	@RequestMapping(value = "/{song_story_id}/audios", method = RequestMethod.PUT)
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
			file_name = awsS3Connection.uploadFileToAWSS3(stringBuilder.toString(),
					"apps/well_family_house/songs/records", ".mp3");
		} catch (IllegalStateException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}
		dao.insertAudio(song_story_id, file_name + ".mp3");
	}

	// count (memory_sound my page)
	@RequestMapping(value = "/{song_story_id}/comment_count", method = RequestMethod.GET)
	public int song_story_comment_Count(@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryCommentCount(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/like_count", method = RequestMethod.GET)
	public int song_story_like_Count(@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryLikeCount(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/likes", method = RequestMethod.POST)
	public void song_story_like_up(HttpServletRequest request, @PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateSongStoryLikeUp(Integer.parseInt(request.getParameter("user_id")), song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/likes/{user_id}", method = RequestMethod.DELETE)
	public void song_story_like_down(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateSongStoryLikeDown(user_id, song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/like_check/{user_id}", method = RequestMethod.GET)
	public int song_story_like_check(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.checkSongStoryLike(user_id, song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/photos", method = RequestMethod.GET)
	public ArrayList<SongPhoto> song_story_photo_List(@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryPhotoList(song_story_id);
	}

	// songstory comment
	@RequestMapping(value = "/{song_story_id}/comments", method = RequestMethod.GET)
	public ArrayList<CommentInfo> song_story_comment_List(@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryCommentList(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/comments", method = RequestMethod.POST)
	public SongStoryComment insert_song_story_comment(HttpServletRequest request,
			@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		
		SongStoryComment songStoryComment = new SongStoryComment();
		songStoryComment.setUser_id(Integer.parseInt(request.getParameter("user_id")));
		songStoryComment.setSong_story_id(song_story_id);
		songStoryComment.setContent(request.getParameter("content"));

		dao.insertSongStoryComment(songStoryComment);
		return dao.getSongStoryComment(songStoryComment.getId());
	}

	@RequestMapping(value = "/{song_story_id}/avatars", method = RequestMethod.GET)
	public String song_story_avatar(HttpServletRequest request, @PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryAvatar(Integer.parseInt(request.getParameter("song_id")));
	}
	
	
	//insert emotions
	@RequestMapping(value = "/{song_story_id}/emotions", method = RequestMethod.POST)
	public void insert_emotion_into_song_story(HttpServletRequest request, @PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertEmotionIntoSongStory(song_story_id, Integer.parseInt(request.getParameter("song_story_emotion_id")));
	}

	// songstory emotions view
	@RequestMapping(value = "/{song_story_id}/emotions", method = RequestMethod.GET)
	public ArrayList<SongStoryEmotionData> song_story_emotion_data_List(@PathVariable int song_story_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongStoryEmotionData(song_story_id);
	}
	
	
}
