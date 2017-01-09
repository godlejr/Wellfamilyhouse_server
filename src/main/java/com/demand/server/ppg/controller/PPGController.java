package com.demand.server.ppg.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.ppg.dao.IDao;
import com.demand.server.ppg.dto.User;

@RestController
public class PPGController {
	@Autowired
	private SqlSession ppg_sqlSession;

	@RequestMapping(value = "/ppg/login", method = { RequestMethod.GET, RequestMethod.POST })
	public User test(HttpServletRequest request) {
		IDao dao = ppg_sqlSession.getMapper(IDao.class);
		return dao.login_check(request.getParameter("email"), request.getParameter("password"));
	}

	@RequestMapping(value = "/ppg/{id}/insert_result", method = { RequestMethod.GET, RequestMethod.POST })
	public void test(@PathVariable String id, HttpServletRequest request) {

		IDao dao = ppg_sqlSession.getMapper(IDao.class);
		dao.insert_result(Integer.parseInt(id), request.getParameter("aa"), request.getParameter("sns"),
				request.getParameter("psns"), request.getParameter("ans"), request.getParameter("hrv"),
				request.getParameter("stress"));
	}
}
