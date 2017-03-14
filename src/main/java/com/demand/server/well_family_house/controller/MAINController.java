package com.demand.server.well_family_house.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.dao.IDao;
import com.demand.server.well_family_house.dto.Category;
import com.demand.server.well_family_house.dto.User;
import com.demand.server.well_family_house.util.JwtUtil;

@RestController
@RequestMapping("/main")
public class MAINController {
	@Autowired
	private SqlSession well_family_house_sqlSession;

	@Autowired
	JwtUtil jwtUtil;

	@RequestMapping(value = "/check/{email}", method = RequestMethod.GET)
	public User email_check(@PathVariable String email) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.email_check(email);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		User user = dao.login(request.getParameter("email"), request.getParameter("password"));
		
		if (user != null) {
			user.setAccess_token(jwtUtil.generateToken(user.getLevel()));
			return user;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public void join(HttpServletRequest request) {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		System.out.println(request.getParameter("name"));
		dao.join(request.getParameter("email"), request.getParameter("password"), request.getParameter("name"),
				request.getParameter("birth"), request.getParameter("phone"),
				Integer.parseInt(request.getParameter("login_category_id")));
	}

	// reports
	@Secured("ROLE_USER")
	@RequestMapping(value = "/report_categories", method = RequestMethod.GET)
	public ArrayList<Category> report_category_List() {
		IDao dao = well_family_house_sqlSession.getMapper(IDao.class);
		return dao.getReportCategoryList();
	}

}
