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
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.Photo;
import com.demand.server.well_family_house.common.dto.StoryInfo;
import com.demand.server.well_family_house.common.dto.User;
import com.demand.server.well_family_house.common.dto.UserInfoForFamilyJoin;
import com.demand.server.well_family_house.common.flag.FamilyJoinFlag;
import com.demand.server.well_family_house.common.flag.NotificationBEHAVIORFlag;
import com.demand.server.well_family_house.common.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.common.flag.NotificationTOFlag;
import com.demand.server.well_family_house.family.service.FamilyService;
import com.demand.server.well_family_house.family.service.impl.FamilyServiceImpl;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/families")
public class FAMILYController {

	@Autowired
	private FamilyService familyService;

	// main
	@RequestMapping(value = "/{family_id}", method = RequestMethod.GET)
	public Family family(@PathVariable int family_id) throws Exception {
		return familyService.selectFamily(family_id);
	}

	// family_edit
	@RequestMapping(value = "/{family_id}", method = RequestMethod.PUT)
	public void update_family_info(HttpServletRequest request, @PathVariable int family_id) throws Exception {
		familyService.updateFamilyInfo(family_id, request.getParameter("name"), request.getParameter("content"));
	}

	// family_delete
	@RequestMapping(value = "/{family_id}", method = RequestMethod.DELETE)
	public void delete_family(HttpServletRequest request, @PathVariable int family_id) throws Exception {
		familyService.deleteFamily(family_id);
	}

	// family_main
	@RequestMapping(value = "/{family_id}/usersBut/{user_id}", method = RequestMethod.GET)
	public ArrayList<User> family_user_Info(HttpServletRequest request, @PathVariable int family_id,
			@PathVariable int user_id) throws Exception {
		return familyService.selectFamilyUsersInfo(family_id, user_id);
	}

	@RequestMapping(value = "/{family_id}/contents", method = RequestMethod.GET)
	public ArrayList<StoryInfo> family_content_List(@PathVariable int family_id) throws Exception {
		return familyService.selectContentList(family_id);
	}

	@RequestMapping(value = "/{family_id}/photos", method = RequestMethod.GET)
	public ArrayList<Photo> family_photo_List(@PathVariable int family_id) throws Exception {
		return familyService.selectPhotoList(family_id);
	}

	@RequestMapping(value = "/{family_id}/family_joiners", method = RequestMethod.GET)
	public ArrayList<UserInfoForFamilyJoin> family_joiners(@PathVariable int family_id) throws Exception {
		return familyService.selectFamilyJoinerList(family_id);
	}

	@RequestMapping(value = "/{family_id}/avatars", method = RequestMethod.PUT)
	public void update_family_avatar(HttpServletRequest request, @PathVariable int family_id)
			throws IOException, Exception {
		familyService.updateFamilyAvatar(request.getInputStream(), family_id);
	}

	// find user info for invitation
	@RequestMapping(value = "/{family_id}/find_user", method = RequestMethod.GET)
	public ArrayList<UserInfoForFamilyJoin> find_user(HttpServletRequest request, @PathVariable int family_id)
			throws Exception {
		String search = request.getParameter("search");

		return familyService.selectUserSearchList(family_id, search);
	}

	@RequestMapping(value = "/{family_id}/users/{user_id}", method = RequestMethod.PUT)
	public void update_user_for_familyjoin(@PathVariable int family_id, @PathVariable int user_id)
			throws NumberFormatException, Exception {
		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.FAMILY);
		notification.setBehavior_id(NotificationBEHAVIORFlag.JOIN);
		notification.setReceiver_id(family_id);
		notification.setIntent_id(family_id);
		notification.setIntent_flag(NotificationINTENTFlag.FAMILY);

		familyService.updateUserForFamilyJoin(family_id, user_id, notification);
	}

	// insert user to family for invite
	@RequestMapping(value = "/{family_id}/users", method = RequestMethod.POST)
	public void insert_user_into_family(HttpServletRequest request, @PathVariable int family_id)
			throws NumberFormatException, Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));

		Notification notification = new Notification();
		notification.setReceive_category_id(NotificationTOFlag.INVITEE);
		notification.setContent_name("??????");
		notification.setBehavior_id(NotificationBEHAVIORFlag.INVITED);
		notification.setReceiver_id(user_id);
		notification.setIntent_id(family_id);
		notification.setIntent_flag(NotificationINTENTFlag.MANAGE_FAMILY);

		familyService.insertUserIntoFamily(family_id, user_id, FamilyJoinFlag.FAMILY_TO_USER, notification);
	}

	// delete user from family
	@RequestMapping(value = "/{family_id}/users/{user_id}", method = RequestMethod.DELETE)
	public void delete_user_from_family(@PathVariable int family_id, @PathVariable int user_id)
			throws NumberFormatException, Exception {
		familyService.deleteUserFromFamily(family_id, user_id);
	}

}
