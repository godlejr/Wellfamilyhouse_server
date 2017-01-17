package com.demand.server.h2o.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.h2o.dao.IDao;
import com.demand.server.h2o.dto.Test;
import com.demand.server.h2o.dto.User;

@RestController
public class H2OController {

	@Autowired
	SqlSession h2o_sqlSession;
	
	@RequestMapping(value = "/h2o/login", method = { RequestMethod.GET, RequestMethod.POST })
	public ArrayList<User> login(HttpServletRequest request) {
		IDao dao = h2o_sqlSession.getMapper(IDao.class);
		return dao.login_check(request.getParameter("email"), request.getParameter("password"));
	}
	
	@RequestMapping(value="/h2o/{id}/demand_list" ,method = {RequestMethod.GET, RequestMethod.POST})
	public ArrayList<Test> demand_list(@PathVariable String id){
		IDao dao = h2o_sqlSession.getMapper(IDao.class);
		return dao.show_list();
	} 
}
