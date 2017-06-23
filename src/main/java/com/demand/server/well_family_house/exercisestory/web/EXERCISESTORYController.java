package com.demand.server.well_family_house.exercisestory.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.ExerciseStory;
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
	
}
