package com.demand.server.well_family_house.story.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.Comment;
import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.Photo;
import com.demand.server.well_family_house.common.dto.Story;
import com.demand.server.well_family_house.common.dto.StoryInfoForNotification;
import com.demand.server.well_family_house.common.flag.NotificationBEHAVIORFlag;
import com.demand.server.well_family_house.common.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.common.flag.NotificationTOFlag;
import com.demand.server.well_family_house.story.service.StoryService;
import com.demand.server.well_family_house.story.service.impl.StoryServiceImpl;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/stories")
public class STORYController {
	@Autowired
	private StoryService storyService;

	@RequestMapping(value = "/{story_id}/comment_count", method = RequestMethod.GET)
	public int family_comment_Count(@PathVariable int story_id) throws Exception {
		return storyService.selectCommentCount(story_id);
	}

	@RequestMapping(value = "/{story_id}/like_count", method = RequestMethod.GET)
	public int family_like_Count(@PathVariable int story_id) throws Exception {
		return storyService.selectLikeCount(story_id);
	}

	@RequestMapping(value = "/{story_id}/photos", method = RequestMethod.GET)
	public ArrayList<Photo> family_content_photo_List(@PathVariable int story_id) throws Exception {
		return storyService.selectContentPhotoList(story_id);
	}

	@RequestMapping(value = "/{story_id}/likes", method = RequestMethod.POST)
	public void family_content_like_up(HttpServletRequest request, @PathVariable int story_id)
			throws NumberFormatException, Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));

		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.WRITER);
		notification.setContent_name("회원님의 게시글");
		notification.setIntent_flag(NotificationINTENTFlag.STORY_DETAIL);
		notification.setBehavior_id(NotificationBEHAVIORFlag.LIKE);
		notification.setIntent_id(story_id);

		storyService.insertLikeUp(user_id, story_id, notification);
	}

	@RequestMapping(value = "/{story_id}/likes/{user_id}", method = RequestMethod.DELETE)
	public void family_content_like_down(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable int story_id) throws Exception {
		storyService.deleteLikeDown(user_id, story_id);
	}

	@RequestMapping(value = "/{story_id}/like_check/{user_id}", method = RequestMethod.GET)
	public int family_content_like_check(HttpServletRequest request, @PathVariable int story_id,
			@PathVariable int user_id) throws Exception {
		return storyService.selectLikeCheck(user_id, story_id);
	}

	@RequestMapping(value = "/{story_id}/hits", method = RequestMethod.PUT)
	public void insert_song_story_hit(@PathVariable int story_id) throws Exception {
		storyService.updateStoryHit(story_id);
	}

	@RequestMapping(value = "/{story_id}/comments", method = RequestMethod.GET)
	public ArrayList<CommentInfo> family_detail_comment_List(@PathVariable int story_id) throws Exception {
		return storyService.selectCommentList(story_id);
	}

	@RequestMapping(value = "/{story_id}/comments", method = RequestMethod.POST)
	public Comment insert_comment(HttpServletRequest request, @PathVariable int story_id) throws Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));

		Comment comment = new Comment();
		comment.setUser_id(user_id);
		comment.setStory_id(story_id);
		comment.setContent(request.getParameter("content"));

		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.WRITER);
		notification.setContent_name("회원님의 게시글");
		notification.setIntent_flag(NotificationINTENTFlag.STORY_DETAIL);
		notification.setBehavior_id(NotificationBEHAVIORFlag.WRITING_THE_COMMENT);
		notification.setIntent_id(story_id);

		return storyService.insertComment(comment, notification);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Story insert_story(HttpServletRequest request) throws Exception {
		int family_id = Integer.parseInt(request.getParameter("family_id"));
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		String content = request.getParameter("content");
		String family_name = request.getParameter("family_name");

		Story story = new Story();
		story.setUser_id(user_id);
		story.setFamily_id(family_id);
		story.setContent(content);

		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.FAMILY);
		notification.setReceiver_id(family_id);
		notification.setContent_name(family_name);
		notification.setIntent_flag(NotificationINTENTFlag.STORY_DETAIL);
		notification.setBehavior_id(NotificationBEHAVIORFlag.WRITING_THE_STORY);

		return storyService.insertStory(story, notification);
	}

	@RequestMapping(value = "/{story_id}", method = RequestMethod.PUT)
	public void update_story(HttpServletRequest request, @PathVariable int story_id) throws Exception {
		String content = request.getParameter("content");
		storyService.updateStory(story_id, content);
	}

	@RequestMapping(value = "/{story_id}", method = RequestMethod.DELETE)
	public void delete_story(@PathVariable int story_id) throws Exception {
		storyService.deleteStory(story_id);
	}

	@RequestMapping(value = "/{story_id}", method = RequestMethod.GET)
	public StoryInfoForNotification storyDetailForNotification(@PathVariable int story_id)
			throws IOException, Exception {
		return storyService.selectStoryInfo(story_id);
	}

	@RequestMapping(value = "/{story_id}/photos", method = RequestMethod.POST)
	public void insert_photos(HttpServletRequest request, @PathVariable int story_id) throws IOException, Exception {
		storyService.insertPhoto(request.getInputStream(), story_id);
	}
}
