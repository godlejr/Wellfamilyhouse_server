package com.demand.server.well_family_house.user.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.Category;
import com.demand.server.well_family_house.common.dto.Family;
import com.demand.server.well_family_house.common.dto.FamilyInfoForFamilyJoin;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.SongStory;
import com.demand.server.well_family_house.common.dto.User;
import com.demand.server.well_family_house.common.dto.UserInfoForFamilyJoin;
import com.demand.server.well_family_house.common.flag.NotificationBEHAVIORFlag;
import com.demand.server.well_family_house.common.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.common.flag.NotificationTOFlag;
import com.demand.server.well_family_house.user.service.UserService;
import com.demand.server.well_family_house.user.service.impl.UserServiceImpl;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/users")
public class USERController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
	public User user_Info(@PathVariable int user_id) throws Exception {
		return userService.selectUserInfo(user_id);
	}

	@RequestMapping(value = "/{user_id}/tokenAndDeviceid", method = RequestMethod.PUT)
	public void update_deviceId_token(HttpServletRequest request, @PathVariable int user_id) throws Exception {
		String device_id = request.getParameter("device_id");
		String token = request.getParameter("token");
		
		userService.updateDeviceIdToken(user_id, device_id, token);
	}
	
	@RequestMapping(value = "/{user_id}/password", method = RequestMethod.PUT)
	public void updatePassword(HttpServletRequest request, @PathVariable int user_id)
			throws Exception {
		String password = request.getParameter("password");
		userService.updatePassword(user_id,password);
	}


	@RequestMapping(value = "/{user_id}/deviceids", method = RequestMethod.GET)
	public int check_device_id(HttpServletRequest request, @PathVariable int user_id)
			throws Exception {
		String device_id = request.getParameter("device_id");
		return userService.selectDeviceIdCheck(user_id, device_id);
	}

	@RequestMapping(value = "/{user_id}/tokens/{token}", method = RequestMethod.PUT)
	public void update_token(HttpServletRequest request, @PathVariable int user_id, @PathVariable String token)
			throws Exception {
		userService.updateToken(user_id, token);
	}

	@RequestMapping(value = "/{user_id}/families", method = RequestMethod.GET)
	public ArrayList<Family> family_Info(@PathVariable int user_id) throws Exception {
		return userService.selectFamiliesInfo(user_id);
	}

	@RequestMapping(value = "/{user_id}/manage_families", method = RequestMethod.GET)
	public ArrayList<Family> manage_families(@PathVariable int user_id) throws Exception {
		return userService.selectManageFamilies(user_id);
	}

	@RequestMapping(value = "/{user_id}/join_families", method = RequestMethod.GET)
	public ArrayList<FamilyInfoForFamilyJoin> join_families(@PathVariable int user_id) throws Exception {
		return userService.selectJoinFamilies(user_id);
	}

	// user_check from total families
	@RequestMapping(value = "/{story_user_id}/family_check/{user_id}", method = RequestMethod.GET)
	public int family_check(HttpServletRequest request, @PathVariable int story_user_id, @PathVariable int user_id)
			throws Exception {
		return userService.selectFamiliesUserCheck(user_id, story_user_id);
	}

	@RequestMapping(value = "/{story_user_id}/public_songstories", method = RequestMethod.GET)
	public ArrayList<SongStory> song_story_List_Public(@PathVariable int story_user_id) throws Exception {
		return userService.selectSongStoryPublicList(story_user_id);
	}

	@RequestMapping(value = "/{story_user_id}/family_songstories", method = RequestMethod.GET)
	public ArrayList<SongStory> song_story_List_Family(@PathVariable int story_user_id) throws Exception {
		return userService.selectSongStoryFamilyList(story_user_id);
	}

	@RequestMapping(value = "/{story_user_id}/me_songstories", method = RequestMethod.GET)
	public ArrayList<SongStory> song_story_List_Me(@PathVariable int story_user_id) throws Exception {
		return userService.selectSongStoryMeList(story_user_id);
	}

	@RequestMapping(value = "/{user_id}/find_family", method = RequestMethod.GET)
	public ArrayList<FamilyInfoForFamilyJoin> find_family(@PathVariable int user_id, HttpServletRequest request)
			throws Exception {
		return userService.selectFamilySearchList(user_id, request.getParameter("search"));
	}

	// like to join the family
	@RequestMapping(value = "/{user_id}/join_family", method = RequestMethod.POST)
	public void join_family(HttpServletRequest request, @PathVariable int user_id) throws Exception {
		int family_id  = Integer.parseInt(request.getParameter("family_id"));
		int creator_id = Integer.parseInt(request.getParameter("creator_id"));
		String family_name = request.getParameter("family_name");
		

		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.FAMILY_OWNER);
		notification.setReceiver_id(creator_id);
		notification.setContent_name(family_name);
		notification.setIntent_flag(NotificationINTENTFlag.MANAGE_FAMILY_DETAIL);
		notification.setBehavior_id(NotificationBEHAVIORFlag.WANT_TO_JOIN);
		notification.setIntent_id(family_id);

		
		userService.insertFamilyJoiner(user_id,family_id, notification);
	}

	// insert_family
	@RequestMapping(value = "/{user_id}/families", method = RequestMethod.POST)
	public int insert_family(HttpServletRequest request, @PathVariable int user_id) throws Exception {
		String family_name = request.getParameter("family_name");
		String content = request.getParameter("family_content");

		Family family = new Family();
		family.setName(family_name);
		family.setContent(content);
		family.setUser_id(user_id);

		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.ME);
		notification.setReceiver_id(user_id);
		notification.setContent_name(family_name);
		notification.setIntent_flag(NotificationINTENTFlag.FAMILY);
		notification.setBehavior_id(NotificationBEHAVIORFlag.CREATING_THE_FAMILY);

		return userService.insertFamily(family, notification);
	}

	// user_check from particular family
	@RequestMapping(value = "/{other_user_id}/sole_family_check/{user_id}", method = RequestMethod.GET)
	public int family_user_check(HttpServletRequest request, @PathVariable int other_user_id, @PathVariable int user_id)
			throws NumberFormatException, Exception {
		return userService.selectFamilyUserCheck(Integer.parseInt(request.getParameter("family_id")), user_id,
				other_user_id);
	}

	// edit profile
	@RequestMapping(value = "/favorite_categories", method = RequestMethod.GET)
	public ArrayList<Category> favorite_category_List() throws Exception {
		return userService.selectFavoriteCategoryList();
	}

	@RequestMapping(value = "/{user_id}/check_genders", method = RequestMethod.GET)
	public int check_gender(@PathVariable int user_id) throws Exception {
		return userService.selectGenderCheck(user_id);
	}

	@RequestMapping(value = "/{user_id}/favorite_check/{favorite_category_id}", method = RequestMethod.GET)
	public int check_favorite(@PathVariable int user_id, @PathVariable int favorite_category_id) throws Exception {
		return userService.selectFavoriteCheck(user_id, favorite_category_id);
	}

	@RequestMapping(value = "/{user_id}/song_favorite_check/{song_category_id}", method = RequestMethod.GET)
	public int check_song_category(@PathVariable int user_id, @PathVariable int song_category_id) throws Exception {
		return userService.selectSongCategoryCheck(user_id, song_category_id);
	}

	@RequestMapping(value = "/{user_id}/avatars", method = RequestMethod.PUT)
	public void update_user_avatar(HttpServletRequest request, @PathVariable int user_id)
			throws IOException, Exception {
		userService.updateUserAvatar(request.getInputStream(), user_id);
	}

	@RequestMapping(value = "/{user_id}/favorites", method = RequestMethod.DELETE)
	public void delete_favorite(HttpServletRequest request, @PathVariable int user_id) throws Exception {
		userService.deleteFavorite(user_id);
	}

	@RequestMapping(value = "/{user_id}/favorites", method = RequestMethod.POST)
	public void insert_favorite(HttpServletRequest request, @PathVariable int user_id)
			throws NumberFormatException, Exception {
		userService.insertFavorite(user_id, Integer.parseInt(request.getParameter("favorite_id")));
	}

	@RequestMapping(value = "/{user_id}/song_categories", method = RequestMethod.DELETE)
	public void delete_song_category(@PathVariable int user_id) throws Exception {
		userService.deleteSongCategory(user_id);
	}

	@RequestMapping(value = "/{user_id}/song_categories", method = RequestMethod.POST)
	public void insert_song_category(HttpServletRequest request, @PathVariable int user_id)
			throws NumberFormatException, Exception {
		userService.insertSongCategory(user_id, Integer.parseInt(request.getParameter("song_category_id")));
	}

	@RequestMapping(value = "/{user_id}", method = RequestMethod.PUT)
	public void udpate_user_info(HttpServletRequest request, @PathVariable int user_id) throws Exception {
		String name = request.getParameter("name");
		String birth = request.getParameter("birth");
		String phone = request.getParameter("phone");
		int gender = Integer.parseInt(request.getParameter("gender"));

		userService.updateUserInfo(user_id, name, birth, phone, gender);
	}

	@RequestMapping(value = "/{user_id}/comment_reports", method = RequestMethod.POST)
	public void insert_comment_report(HttpServletRequest request, @PathVariable int user_id)
			throws NumberFormatException, Exception {
		userService.insertCommentReport(user_id, Integer.parseInt(request.getParameter("comment_category_id")),
				Integer.parseInt(request.getParameter("report_category_id")),
				Integer.parseInt(request.getParameter("comment_id")));
	}
	
	@RequestMapping(value = "/{user_id}/story_reports", method = RequestMethod.POST)
	public void insert_story_report(HttpServletRequest request, @PathVariable int user_id)
			throws NumberFormatException, Exception {
		userService.insertStoryReport(user_id, Integer.parseInt(request.getParameter("story_category_id")),
				Integer.parseInt(request.getParameter("report_category_id")),
				Integer.parseInt(request.getParameter("story_id")));
	}


	// notification
	@RequestMapping(value = "/{user_id}/notifications", method = RequestMethod.GET)
	public ArrayList<Notification> notifications(@PathVariable int user_id) throws Exception {
		return userService.selectNotification(user_id);
	}

}
