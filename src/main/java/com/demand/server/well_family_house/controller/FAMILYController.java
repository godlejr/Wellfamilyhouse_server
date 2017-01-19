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
import com.demand.server.well_family_house.dto.Family;
import com.demand.server.well_family_house.dto.FamilyUserInfo;
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
	@RequestMapping(value = "/family/{id}/family_user_Info", method = {RequestMethod.GET,RequestMethod.POST})
	public ArrayList<FamilyUserInfo> family_user_Info(@PathVariable String id ) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getFamilyUserInfo(Integer.parseInt(id));
	}
}
