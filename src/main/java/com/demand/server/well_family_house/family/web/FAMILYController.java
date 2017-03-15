package com.demand.server.well_family_house.family.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.Family;
import com.demand.server.well_family_house.common.dto.Photo;
import com.demand.server.well_family_house.common.dto.StoryInfo;
import com.demand.server.well_family_house.common.dto.User;
import com.demand.server.well_family_house.family.service.impl.FamilyServiceImpl;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/families")
public class FAMILYController {

	@Autowired
	private FamilyServiceImpl familyServiceImpl;

	// main
	@RequestMapping(value = "/{family_id}", method = RequestMethod.GET)
	public Family family(@PathVariable int family_id) throws Exception {
		return familyServiceImpl.selectFamily(family_id);
	}

	// family_main
	@RequestMapping(value = "/{family_id}/usersBut/{user_id}", method = RequestMethod.GET)
	public ArrayList<User> family_user_Info(HttpServletRequest request, @PathVariable int family_id,
			@PathVariable int user_id) throws Exception {
		return familyServiceImpl.selectFamilyUsersInfo(family_id, user_id);
	}

	@RequestMapping(value = "/{family_id}/contents", method = RequestMethod.GET)
	public ArrayList<StoryInfo> family_content_List(@PathVariable int family_id) throws Exception {
		return familyServiceImpl.selectContentList(family_id);
	}

	@RequestMapping(value = "/{family_id}/photos", method = RequestMethod.GET)
	public ArrayList<Photo> family_photo_List(@PathVariable int family_id) throws Exception {
		return familyServiceImpl.selectPhotoList(family_id);
	}

	@RequestMapping(value = "/{family_id}/avatars", method = RequestMethod.PUT)
	public void update_family_avatar(HttpServletRequest request, @PathVariable int family_id) throws IOException, Exception {	
		familyServiceImpl.updateFamilyAvatar(request.getInputStream(),family_id);
	}

	// insert user to family
	@RequestMapping(value = "/{family_id}/users", method = RequestMethod.POST)
	public void insert_user_into_family(HttpServletRequest request, @PathVariable int family_id) throws NumberFormatException, Exception {
		familyServiceImpl.insertUserIntoFamily(family_id, Integer.parseInt(request.getParameter("user_id")));
	}

	// delete user from family
	@RequestMapping(value = "/{family_id}/users", method = RequestMethod.DELETE)
	public void delete_user_from_family(HttpServletRequest request, @PathVariable int family_id) throws NumberFormatException, Exception {
		familyServiceImpl.deleteUserFromFamily(family_id, Integer.parseInt(request.getParameter("user_id")));
	}

	// family_edit
	@RequestMapping(value = "/{family_id}", method = RequestMethod.PUT)
	public void update_family_info(HttpServletRequest request, @PathVariable int family_id) throws Exception {
		familyServiceImpl.updateFamilyInfo(family_id, request.getParameter("name"), request.getParameter("content"));
	}
}
