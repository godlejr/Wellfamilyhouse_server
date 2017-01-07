package com.demand.server.test.controller;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demand.server.test.dao.IDao;
import com.demand.server.test.dto.User;

@Controller
public class TestController {
	@Autowired
	private SqlSession sqlSession2;

	@RequestMapping(value = "/test", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ArrayList<User> test() {
		IDao dao = sqlSession2.getMapper(IDao.class);
		return dao.listDao();
	}

}
