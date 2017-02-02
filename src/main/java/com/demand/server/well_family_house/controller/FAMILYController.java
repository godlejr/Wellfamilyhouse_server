package com.demand.server.well_family_house.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

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
import com.demand.server.well_family_house.dto.CommentInfo;
import com.demand.server.well_family_house.dto.Family;
import com.demand.server.well_family_house.dto.LikeCount;
import com.demand.server.well_family_house.dto.Photo;
import com.demand.server.well_family_house.dto.Range;
import com.demand.server.well_family_house.dto.Song;
import com.demand.server.well_family_house.dto.SongCategory;
import com.demand.server.well_family_house.dto.SongComment;
import com.demand.server.well_family_house.dto.SongCommentCount;
import com.demand.server.well_family_house.dto.SongLikeCount;
import com.demand.server.well_family_house.dto.SongPhoto;
import com.demand.server.well_family_house.dto.SongStory;
import com.demand.server.well_family_house.dto.Story;
import com.demand.server.well_family_house.dto.StoryInfo;
import com.demand.server.well_family_house.dto.User;

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
			file_name = uploadFileToAWSS3(stringBuilder.toString(), "apps/well_family_house/images/stories",".jpg");
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

	public static String uploadFileToAWSS3(String base64Data, String location,String ext)
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
			PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, location + "/" + fileName + ext,
					imagefile, objectMetadata);
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			s3.putObject(putObjectRequest);

		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}

		return fileName;
	}

	// memory_sound main
	@RequestMapping(value = "/family/song_category_List", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<SongCategory> song_category_List() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongCategoryList();
	}

	// count (memory_sound main)
	@RequestMapping(value = "/family/{song_id}/song_comment_Count", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<SongCommentCount> song_comment_Count(@PathVariable String song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongCommentCount(Integer.parseInt(song_id));
	}

	@RequestMapping(value = "/family/{song_id}/song_like_Count", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<SongLikeCount> song_like_Count(@PathVariable String song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongLikeCount(Integer.parseInt(song_id));
	}

	@RequestMapping(value = "/family/song_list_by_Hits", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Song> song_list_by_Hits() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongListByHits();
	}

	@RequestMapping(value = "/family/song_random", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Song> song_random() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getRandomSong(randomRange(149, 295));
	}

	public static int randomRange(int n1, int n2) {
		return (int) (Math.random() * (n2 - n1 + 1)) + n1;
	}

	@RequestMapping(value = "/family/{category_id}/song_list_by_Category", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ArrayList<Song> song_list_by_Category(@PathVariable String category_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongListByCategory(Integer.parseInt(category_id));
	}

	@RequestMapping(value = "/family/{song_id}/Insert_Song_hit", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<Song> Insert_Song_hit(@PathVariable String song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.insertSongHit(Integer.parseInt(song_id));
	}

	// memory sound comment
	@RequestMapping(value = "/family/{song_id}/song_comment_List", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<CommentInfo> song_comment_List(@PathVariable String song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongCommentList(Integer.parseInt(song_id));
	}
	
	@RequestMapping(value = "/family/{song_id}/song_like_up", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void song_like_up(HttpServletRequest request, @PathVariable String song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateSongLikeUp(Integer.parseInt(request.getParameter("user_id")), Integer.parseInt(song_id));
	}

	@RequestMapping(value = "/family/{song_id}/song_like_down", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void song_like_down(HttpServletRequest request, @PathVariable String song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateSongLikeDown(Integer.parseInt(request.getParameter("user_id")), Integer.parseInt(song_id));
	}

	@RequestMapping(value = "/family/{song_id}/song_like_check", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ArrayList<CheckBox> song_like_check(HttpServletRequest request, @PathVariable String song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.checkSongLike(Integer.parseInt(request.getParameter("user_id")), Integer.parseInt(song_id));
	}
	
	@RequestMapping(value = "/family/{song_id}/insert_song_comment", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<SongComment> insert_song_comment(HttpServletRequest request, @PathVariable String song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		SongComment songComment = new SongComment();
		songComment.setUser_id(Integer.parseInt(request.getParameter("user_id")));
		songComment.setSong_id(Integer.parseInt(song_id));
		songComment.setContent(request.getParameter("content"));

		dao.insertSongComment(songComment);
		return dao.getSongComment(songComment.getId());
	}
	
	@RequestMapping(value = "/family/song_range_List", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ArrayList<Range> song_range_List() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongRangeList();
	}
	
	// insert story
		@RequestMapping(value = "/family/{user_id}/insert_song_story", method = { RequestMethod.GET, RequestMethod.POST })
		public ArrayList<SongStory> insert_song_story(HttpServletRequest request, @PathVariable String user_id) {
			IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
			SongStory songStory = new SongStory();
			songStory.setUser_id(Integer.parseInt(user_id));
			songStory.setRange_id(Integer.parseInt(request.getParameter("range_id")));
			songStory.setSong_id(Integer.parseInt(request.getParameter("song_id")));
			songStory.setSong_title(request.getParameter("song_title"));
			songStory.setSong_singer(request.getParameter("song_singer"));
			songStory.setContent(request.getParameter("content"));
			songStory.setLocation(request.getParameter("location"));
			dao.insertSongStory(songStory);

			return dao.getSongStory(songStory.getId());
		}

		@RequestMapping(value = "/family/{song_story_id}/insert_song_photos", method = { RequestMethod.GET, RequestMethod.POST })
		public void insert_song_photos(HttpServletRequest request, @PathVariable String song_story_id) {
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
				file_name = uploadFileToAWSS3(stringBuilder.toString(), "apps/well_family_house/images/songstories",".jpg");
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			SongPhoto songPhoto = new SongPhoto();
			songPhoto.setSong_story_id(Integer.parseInt(song_story_id));
			songPhoto.setType(0);
			songPhoto.setName(file_name);
			songPhoto.setExt("jpg");

			dao.insertSongPhoto(songPhoto);
		}
		
		@RequestMapping(value = "/family/{song_story_id}/insert_song_audio", method = { RequestMethod.GET, RequestMethod.POST })
		public void insert_song_audio(HttpServletRequest request, @PathVariable String song_story_id) {
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
				file_name = uploadFileToAWSS3(stringBuilder.toString(), "apps/well_family_house/songs/files",".mp3");
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			dao.insertAudio(Integer.parseInt(song_story_id),file_name);
		}
}
