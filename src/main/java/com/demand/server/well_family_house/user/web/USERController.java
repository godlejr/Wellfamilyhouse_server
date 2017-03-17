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
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.SongStory;
import com.demand.server.well_family_house.common.dto.User;
import com.demand.server.well_family_house.common.flag.NotificationBEHAVIORFlag;
import com.demand.server.well_family_house.common.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.common.flag.NotificationTOFlag;
import com.demand.server.well_family_house.user.service.impl.UserServiceImpl;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/users")
public class USERController {
	@Autowired
	private UserServiceImpl userServiceImpl;


	@RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
	public User user_Info(@PathVariable int user_id) throws Exception {
		return userServiceImpl.selectUserInfo(user_id);
	}

	@RequestMapping(value = "/{user_id}/tokens/{token}/deviceids/{device_id}", method = RequestMethod.PUT)
	public void update_deviceId_token(HttpServletRequest request, @PathVariable int user_id, @PathVariable String token,
			@PathVariable String device_id) throws Exception {
		userServiceImpl.updateDeviceIdToken(user_id, device_id, token);
	}

	@RequestMapping(value = "/{user_id}/deviceids/{device_id}", method = RequestMethod.GET)
	public int check_device_id(HttpServletRequest request, @PathVariable int user_id, @PathVariable String device_id) throws Exception {
		return userServiceImpl.selectDeviceIdCheck(user_id, device_id);
	}

	@RequestMapping(value = "/{user_id}/tokens/{token}", method = RequestMethod.PUT)
	public void update_token(HttpServletRequest request, @PathVariable int user_id, @PathVariable String token) throws Exception {
		userServiceImpl.updateToken(user_id, token);
	}

	@RequestMapping(value = "/{user_id}/families", method = RequestMethod.GET)
	public ArrayList<Family> family_Info(@PathVariable int user_id) throws Exception {
		return userServiceImpl.selectFamiliesInfo(user_id);
	}

	// user_check from total families
	@RequestMapping(value = "/{story_user_id}/family_check/{user_id}", method = RequestMethod.GET)
	public int family_check(HttpServletRequest request, @PathVariable int story_user_id, @PathVariable int user_id) throws Exception {
		return userServiceImpl.selectFamiliesUserCheck(user_id, story_user_id);
	}

	@RequestMapping(value = "/{story_user_id}/public_songstories", method = RequestMethod.GET)
	public ArrayList<SongStory> song_story_List_Public(@PathVariable int story_user_id) throws Exception {
		return userServiceImpl.selectSongStoryPublicList(story_user_id);
	}

	@RequestMapping(value = "/{story_user_id}/family_songstories", method = RequestMethod.GET)
	public ArrayList<SongStory> song_story_List_Family(@PathVariable int story_user_id) throws Exception {
		return userServiceImpl.selectSongStoryFamilyList(story_user_id);
	}

	@RequestMapping(value = "/{story_user_id}/me_songstories", method = RequestMethod.GET)
	public ArrayList<SongStory> song_story_List_Me(@PathVariable int story_user_id) throws Exception {
		return userServiceImpl.selectSongStoryMeList(story_user_id);
	}

	@RequestMapping(value = "/{user_id}/find_family/{search}", method = RequestMethod.GET)
	public ArrayList<Family> find_family(@PathVariable int user_id, @PathVariable String search) throws Exception {
		return userServiceImpl.selectFamilySearchList(search);
	}

	@RequestMapping(value = "/{user_id}/find_user", method = RequestMethod.GET)
	public ArrayList<User> find_user(HttpServletRequest request,@PathVariable int user_id) throws Exception {
		return userServiceImpl.selectUserSearchList(request.getParameter("search"));
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

		return userServiceImpl.insertFamily(family,notification);
	}

	// user_check from particular family
	@RequestMapping(value = "/{other_user_id}/sole_family_check/{user_id}", method = RequestMethod.GET)
	public int family_user_check(HttpServletRequest request, @PathVariable int other_user_id,
			@PathVariable int user_id) throws NumberFormatException, Exception {
		return userServiceImpl.selectFamilyUserCheck(Integer.parseInt(request.getParameter("family_id")), user_id, other_user_id);
	}

	// edit profile
	@RequestMapping(value = "/favorite_categories", method = RequestMethod.GET)
	public ArrayList<Category> favorite_category_List() throws Exception {
		return userServiceImpl.selectFavoriteCategoryList();
	}

	@RequestMapping(value = "/{user_id}/check_genders", method = RequestMethod.GET)
	public int check_gender(@PathVariable int user_id) throws Exception {
		return userServiceImpl.selectGenderCheck(user_id);
	}

	@RequestMapping(value = "/{user_id}/favorite_check/{favorite_category_id}", method = RequestMethod.GET)
	public int check_favorite(@PathVariable int user_id, @PathVariable int favorite_category_id) throws Exception {
		return userServiceImpl.selectFavoriteCheck(user_id, favorite_category_id);
	}

	@RequestMapping(value = "/{user_id}/song_favorite_check/{song_category_id}", method = RequestMethod.GET)
	public int check_song_category(@PathVariable int user_id, @PathVariable int song_category_id) throws Exception {
		return userServiceImpl.selectSongCategoryCheck(user_id, song_category_id);
	}

	@RequestMapping(value = "/{user_id}/avatars", method = RequestMethod.PUT)
	public void update_user_avatar(HttpServletRequest request, @PathVariable int user_id) throws IOException, Exception {
		userServiceImpl.updateUserAvatar(request.getInputStream(), user_id);
	}

	@RequestMapping(value = "/{user_id}/favorites", method = RequestMethod.DELETE)
	public void delete_favorite(HttpServletRequest request, @PathVariable int user_id) throws Exception {
		userServiceImpl.deleteFavorite(user_id);
	}

	@RequestMapping(value = "/{user_id}/favorites", method = RequestMethod.POST)
	public void insert_favorite(HttpServletRequest request, @PathVariable int user_id) throws NumberFormatException, Exception {
		userServiceImpl.insertFavorite(user_id, Integer.parseInt(request.getParameter("favorite_id")));
	}

	@RequestMapping(value = "/{user_id}/song_categories", method = RequestMethod.DELETE)
	public void delete_song_category(@PathVariable int user_id) throws Exception {
		userServiceImpl.deleteSongCategory(user_id);
	}

	@RequestMapping(value = "/{user_id}/song_categories", method = RequestMethod.POST)
	public void insert_song_category(HttpServletRequest request, @PathVariable int user_id) throws NumberFormatException, Exception {
		userServiceImpl.insertSongCategory(user_id, Integer.parseInt(request.getParameter("song_category_id")));
	}

	@RequestMapping(value = "/{user_id}", method = RequestMethod.PUT)
	public void udpate_user_info(HttpServletRequest request, @PathVariable int user_id) throws Exception {
		String name = request.getParameter("name");
		String birth = request.getParameter("birth");
		String phone = request.getParameter("phone");
		int gender = Integer.parseInt(request.getParameter("gender"));

		userServiceImpl.updateUserInfo(user_id, name, birth, phone, gender);
	}

	@RequestMapping(value = "/{user_id}/comment_reports", method = RequestMethod.POST)
	public void insert_comment_report(HttpServletRequest request, @PathVariable int user_id) throws NumberFormatException, Exception {
		userServiceImpl.insertCommentReport(user_id, Integer.parseInt(request.getParameter("comment_category_id")),
				Integer.parseInt(request.getParameter("report_category_id")),
				Integer.parseInt(request.getParameter("comment_id")));
	}

	// notification
	@RequestMapping(value = "/{user_id}/notifications", method = RequestMethod.GET)
	public ArrayList<Notification> notifications(@PathVariable int user_id) throws Exception {
		return userServiceImpl.selectNotification(user_id);
	}

}
