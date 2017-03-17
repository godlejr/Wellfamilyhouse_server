package com.demand.server.well_family_house.main.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.Category;
import com.demand.server.well_family_house.common.dto.User;
import com.demand.server.well_family_house.main.service.impl.MainServiceImpl;

@RestController
@RequestMapping("/main")
public class MAINController {
	@Autowired
	private MainServiceImpl mainServiceImpl;

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public int email_check(HttpServletRequest request) throws Exception {
		return mainServiceImpl.selectEmailCheck(request.getParameter("email"));
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(HttpServletRequest request) throws Exception {
		return mainServiceImpl.selectLogin(request.getParameter("email"), request.getParameter("password"));
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public void join(HttpServletRequest request) throws NumberFormatException, Exception {
		mainServiceImpl.insertJoin(request.getParameter("email"), request.getParameter("password"), request.getParameter("name"),
				request.getParameter("birth"), request.getParameter("phone"),
				Integer.parseInt(request.getParameter("login_category_id")));
	}

	// reports
	@Secured("ROLE_USER")
	@RequestMapping(value = "/report_categories", method = RequestMethod.GET)
	public ArrayList<Category> report_category_List() throws Exception {
		return mainServiceImpl.selectReportCategoryList();
	}

}
