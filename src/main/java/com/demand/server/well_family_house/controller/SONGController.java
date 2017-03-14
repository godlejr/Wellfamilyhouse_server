package com.demand.server.well_family_house.controller;

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
import com.demand.server.well_family_house.dto.CommentCount;
import com.demand.server.well_family_house.dto.CommentInfo;
import com.demand.server.well_family_house.dto.LikeCount;
import com.demand.server.well_family_house.dto.Range;
import com.demand.server.well_family_house.dto.Song;
import com.demand.server.well_family_house.dto.SongCategory;
import com.demand.server.well_family_house.dto.SongComment;
import com.demand.server.well_family_house.dto.SongStory;
import com.demand.server.well_family_house.dto.SongStoryEmotionInfo;
import com.demand.server.well_family_house.flag.LogFlag;
import com.demand.server.well_family_house.util.AndroidPushConnection;
import com.demand.server.well_family_house.util.AwsS3Connection;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/songs")
public class SONGController {
	
	@Autowired
	private SqlSession well_family_house_sqlSession;

	@Autowired
	private AndroidPushConnection androidPushConnection;
	
	@Autowired
	private AwsS3Connection awsS3Connection;

	private static final Logger logger = LoggerFactory.getLogger(SONGController.class);
	
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
	
	public static int randomRange(int n1, int n2) {
		return (int) (Math.random() * (n2 - n1 + 1)) + n1;
	}
	
	// memory_sound main
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ArrayList<SongCategory> song_category_List() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongCategoryList();
	}

	// count (memory_sound main)
	@RequestMapping(value = "/{song_id}/comment_count", method =  RequestMethod.GET)
	public ArrayList<CommentCount> song_comment_Count(@PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongCommentCount(song_id);
	}

	@RequestMapping(value = "/{song_id}/like_count", method = RequestMethod.GET)
	public ArrayList<LikeCount> song_like_Count(@PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongLikeCount(song_id);
	}

	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	public ArrayList<Song> song_list_by_Hits() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongListByHits();
	}

	@RequestMapping(value = "/randoms", method = RequestMethod.GET)
	public ArrayList<Song> song_random() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getRandomSong(randomRange(149, 295));
	}

	@RequestMapping(value = "/categories/{category_id}", method = RequestMethod.GET)
	public ArrayList<Song> song_list_by_Category(@PathVariable int category_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongListByCategory(category_id);
	}

	@RequestMapping(value = "/{song_id}/hits", method = RequestMethod.PUT)
	public void Insert_Song_hit(@PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.insertSongHit(song_id);
	}

	// memory sound comment
	@RequestMapping(value = "/{song_id}/comments", method = RequestMethod.GET)
	public ArrayList<CommentInfo> song_comment_List(@PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongCommentList(song_id);
	}

	@RequestMapping(value = "/{song_id}/likes", method = RequestMethod.POST)
	public void song_like_up(HttpServletRequest request, @PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateSongLikeUp(Integer.parseInt(request.getParameter("user_id")), song_id);
	}

	@RequestMapping(value = "/{song_id}/likes/{user_id}", method = RequestMethod.DELETE)
	public void song_like_down(HttpServletRequest request, @PathVariable int song_id,@PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateSongLikeDown(user_id, song_id);
	}

	@RequestMapping(value = "/{song_id}/like_check/{user_id}", method = RequestMethod.GET)
	public ArrayList<Check> song_like_check(HttpServletRequest request, @PathVariable int song_id, @PathVariable int user_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.checkSongLike(user_id, song_id);
	}

	@RequestMapping(value = "/{song_id}/comments", method =  RequestMethod.POST )
	public ArrayList<SongComment> insert_song_comment(HttpServletRequest request, @PathVariable int song_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		
		SongComment songComment = new SongComment();
		songComment.setUser_id(user_id);
		songComment.setSong_id(song_id);
		songComment.setContent(request.getParameter("content"));

		dao.insertSongComment(songComment);
		return dao.getSongComment(songComment.getId());
	}

	@RequestMapping(value = "/ranges", method = RequestMethod.GET)
	public ArrayList<Range> song_range_List() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getSongRangeList();
	}

	// insert story
	@RequestMapping(value = "/stories", method = RequestMethod.POST )
	public ArrayList<SongStory> insert_song_story(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		
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
	
	// emotions
	@RequestMapping(value = "/emotions", method = RequestMethod.GET)
	public ArrayList<SongStoryEmotionInfo> song_story_emotion_List() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getEmotionList();
	}
	
}
