package com.demand.server.well_family_house.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.dao.IDao;
import com.demand.server.well_family_house.dto.CommentCount;
import com.demand.server.well_family_house.dto.Family;
import com.demand.server.well_family_house.dto.LikeCount;
import com.demand.server.well_family_house.dto.Photo;
import com.demand.server.well_family_house.dto.StoryInfo;
import com.demand.server.well_family_house.dto.User;

@RestController
public class FAMILYController {

	@Autowired
	SqlSession well_family_house_sqlSession;
	
	
	//intro 
	@RequestMapping(value = "/family/email_check", method =  RequestMethod.POST )
	public ArrayList<User> email_check(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.email_check(request.getParameter("email"));
	}

	@RequestMapping(value = "/family/login", method = RequestMethod.POST )
	public ArrayList<User> login(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.login(request.getParameter("email"), request.getParameter("password"));
	}

	@RequestMapping(value = "/family/join", method = RequestMethod.POST)
	public void join(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		System.out.println( request.getParameter("name"));
		dao.join(request.getParameter("email"), request.getParameter("password"), request.getParameter("name"),
				request.getParameter("birth"), request.getParameter("phone"));
	}
	
	
	//main
	@RequestMapping(value = "/family/{id}/family_Info", method = {RequestMethod.GET,RequestMethod.POST})
	public ArrayList<Family> family_Info(@PathVariable String id ) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilyInfo(Integer.parseInt(id));
	}
	
	
	//family_main
	@RequestMapping(value = "/family/{family_id}/family_user_Info", method = {RequestMethod.GET,RequestMethod.POST})
	public ArrayList<User> family_user_Info(HttpServletRequest request,@PathVariable String family_id ) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilyUserInfo(Integer.parseInt(family_id),Integer.parseInt(request.getParameter("user_id")));
	}
	
	@RequestMapping(value = "/family/{family_id}/family_content_List", method = {RequestMethod.GET,RequestMethod.POST})
	public ArrayList<StoryInfo> family_content_List(@PathVariable String family_id ) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getContentList(Integer.parseInt(family_id));
	}
	
	@RequestMapping(value = "/family/{family_id}/family_photo_List", method = {RequestMethod.GET,RequestMethod.POST})
	public ArrayList<Photo> family_photo_List(@PathVariable String family_id ) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getPhotoList(Integer.parseInt(family_id));
	}
	
	//content_info (family_main)
	@RequestMapping(value = "/family/{story_id}/family_comment_Count", method = {RequestMethod.GET,RequestMethod.POST})
	public ArrayList<CommentCount> family_comment_Count(@PathVariable String story_id ) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getCommentCount(Integer.parseInt(story_id));
	}
	
	@RequestMapping(value = "/family/{story_id}/family_like_Count", method = {RequestMethod.GET,RequestMethod.POST})
	public ArrayList<LikeCount> family_like_Count(@PathVariable String story_id ) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getLikeCount(Integer.parseInt(story_id));
	}
	
	@RequestMapping(value = "/family/{story_id}/family_content_photo_List", method = {RequestMethod.GET,RequestMethod.POST})
	public ArrayList<Photo> family_content_photo_List(@PathVariable String story_id ) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getContentPhotoList(Integer.parseInt(story_id));
	}
	
	@RequestMapping(value = "/family/{story_id}/family_content_like_up", method = {RequestMethod.GET,RequestMethod.POST})
	public void family_content_like_up(HttpServletRequest request,@PathVariable String story_id ) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateLikeUp(Integer.parseInt(request.getParameter("user_id")),Integer.parseInt(story_id));
	}
	
	@RequestMapping(value = "/family/{story_id}/family_content_like_down", method = {RequestMethod.GET,RequestMethod.POST})
	public void family_content_like_down(HttpServletRequest request,@PathVariable String story_id ) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.updateLikeDown(Integer.parseInt(request.getParameter("user_id")),Integer.parseInt(story_id));
	}
	
	@RequestMapping(value = "/family/{story_id}/family_content_like_check", method = {RequestMethod.GET,RequestMethod.POST})
	public void family_content_like_check(HttpServletRequest request,@PathVariable String story_id ) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		dao.checkLike(Integer.parseInt(request.getParameter("user_id")),Integer.parseInt(story_id));
	}
	
	
}
