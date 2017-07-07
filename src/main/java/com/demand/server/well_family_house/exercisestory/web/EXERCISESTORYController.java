package com.demand.server.well_family_house.exercisestory.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.ExerciseStory;
import com.demand.server.well_family_house.common.dto.ExerciseStoryComment;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.flag.NotificationBEHAVIORFlag;
import com.demand.server.well_family_house.common.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.common.flag.NotificationTOFlag;
import com.demand.server.well_family_house.exercisestory.service.ExerciseStoryService;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/exercisestories")
public class EXERCISESTORYController {
	@Autowired
	private ExerciseStoryService exerciseStoryService;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public int insertExerciseStory(@RequestBody ExerciseStory exerciseStory) throws Exception {
		int user_id = exerciseStory.getUser_id();

		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.FAMILIES);
		notification.setReceiver_id(user_id);
		notification.setIntent_flag(NotificationINTENTFlag.EXERCISE_STORY);
		notification.setBehavior_id(NotificationBEHAVIORFlag.WRITING_THE_STORY);

		return exerciseStoryService.insertExerciseStory(exerciseStory, notification);
	}

	@RequestMapping(value = "/{exercise_story_id}", method = RequestMethod.GET)
	public ExerciseStory selectExerciseStory(@PathVariable int exercise_story_id) throws Exception {
		return exerciseStoryService.selectExerciseStory(exercise_story_id);
	}

	@RequestMapping(value = "/{exercise_story_id}/comment_count", method = RequestMethod.GET)
	public int selectExerciseStoryCommentCount(@PathVariable int exercise_story_id) throws Exception {
		return exerciseStoryService.selectExerciseStoryCommentCount(exercise_story_id);
	}

	@RequestMapping(value = "/{exercise_story_id}/like_count", method = RequestMethod.GET)
	public int selectExerciseStoryLikeCount(@PathVariable int exercise_story_id) throws Exception {
		return exerciseStoryService.selectExerciseStoryLikeCount(exercise_story_id);
	}

	@RequestMapping(value = "/{exercise_story_id}/likes", method = RequestMethod.POST)
	public void insertExerciseStoryLikeUp(HttpServletRequest request, @PathVariable int exercise_story_id)
			throws NumberFormatException, Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));

		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.WRITER);
		notification.setContent_name("낙상예방 스토리");
		notification.setIntent_flag(NotificationINTENTFlag.EXERCISE_STORY);
		notification.setBehavior_id(NotificationBEHAVIORFlag.LIKE);
		notification.setIntent_id(exercise_story_id);

		exerciseStoryService.insertExerciseStoryLikeUp(user_id, exercise_story_id, notification);
	}

	@RequestMapping(value = "/{exercise_story_id}/likes/{user_id}", method = RequestMethod.DELETE)
	public void deleteExerciseStoryLikeDown(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable int exercise_story_id) throws Exception {
		exerciseStoryService.deleteExerciseStoryLikeDown(user_id, exercise_story_id);
	}

	@RequestMapping(value = "/{exercise_story_id}/like_check/{user_id}", method = RequestMethod.GET)
	public int selectExerciseStoryLikeCheck(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable int exercise_story_id) throws Exception {
		return exerciseStoryService.selectExerciseStoryLikeCheck(user_id, exercise_story_id);
	}

	@RequestMapping(value = "/{exercise_story_id}/hits", method = RequestMethod.PUT)
	public void updateExerciseStoryHit(@PathVariable int exercise_story_id) throws Exception {
		exerciseStoryService.updateExerciseStoryHit(exercise_story_id);
	}

	@RequestMapping(value = "/{exercise_story_id}/comments", method = RequestMethod.GET)
	public ArrayList<CommentInfo> selectExerciseStoryCommentList(@PathVariable int exercise_story_id) throws Exception {
		return exerciseStoryService.selectExerciseStoryCommentList(exercise_story_id);
	}

	@RequestMapping(value = "/{exercise_story_id}/comments", method = RequestMethod.POST)
	public ExerciseStoryComment insertExerciseStoryComment(HttpServletRequest request,
			@PathVariable int exercise_story_id) throws Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		String content = request.getParameter("content");

		ExerciseStoryComment exerciseStoryComment = new ExerciseStoryComment();
		exerciseStoryComment.setUser_id(user_id);
		exerciseStoryComment.setContent(content);
		exerciseStoryComment.setExercise_story_id(exercise_story_id);

		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.WRITER);
		notification.setContent_name("회원님의 낙상예방");
		notification.setIntent_flag(NotificationINTENTFlag.EXERCISE_STORY);
		notification.setBehavior_id(NotificationBEHAVIORFlag.WRITING_THE_COMMENT);
		notification.setIntent_id(exercise_story_id);

		return exerciseStoryService.insertExerciseStoryComment(exerciseStoryComment, notification);
	}

	@RequestMapping(value = "/{exercise_story_id}", method = RequestMethod.DELETE)
	public void deleteExerciseStory(@PathVariable int exercise_story_id) throws Exception {
		exerciseStoryService.deleteExerciseStory(exercise_story_id);
	}

}
