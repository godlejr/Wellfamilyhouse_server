package com.demand.server.well_family_house.controller;

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
import com.demand.server.well_family_house.flag.CommentINTENTFlag;
import com.demand.server.well_family_house.flag.LogFlag;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/comments")
public class COMMENTController {

	@Autowired
	private SqlSession well_family_house_sqlSession;


	private static final Logger logger = LoggerFactory.getLogger(COMMENTController.class);
	
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
	
	
	//comment edit for the comment integration
	@RequestMapping(value = "/{comment_id}", method = RequestMethod.PUT)
	public void update_comment(HttpServletRequest request, @PathVariable int comment_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		int flag = Integer.parseInt(request.getParameter("flag"));
		if (flag == CommentINTENTFlag.STORY) {
			dao.updateComment(comment_id, request.getParameter("content"));
		}

		if (flag == CommentINTENTFlag.SONG_PLAYER) {
			dao.updateSongComment(comment_id, request.getParameter("content"));
		}

		if (flag == CommentINTENTFlag.SONG_STORY) {
			dao.updateSongStoryComment(comment_id, request.getParameter("content"));
		}
	}

	@RequestMapping(value = "/{comment_id}", method = RequestMethod.DELETE)
	public void delete_comment(HttpServletRequest request, @PathVariable int comment_id) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		int flag = Integer.parseInt(request.getParameter("flag"));
		if (flag == CommentINTENTFlag.STORY) {
			dao.deleteComment(comment_id);
		}
		if (flag == CommentINTENTFlag.SONG_PLAYER) {
			dao.deleteSongComment(comment_id);
		}
		if (flag == CommentINTENTFlag.SONG_STORY) {
			dao.deleteSongStoryComment(comment_id);
		}
	}
	
}
