package com.demand.server.well_family_house.falldiagnosisstory.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.FallDiagnosisStory;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.flag.NotificationBEHAVIORFlag;
import com.demand.server.well_family_house.common.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.common.flag.NotificationTOFlag;
import com.demand.server.well_family_house.falldiagnosisstory.service.FallDiagnosisStoryService;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/fall_diagnosis_story")
public class FallDiagnosisStoryController {
	
	@Autowired
	private FallDiagnosisStoryService falldiagnosisStoryService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public int insertFallDiagnosisStory(@RequestBody FallDiagnosisStory fallDiagnosisStory) throws Exception {
		int user_id = fallDiagnosisStory.getUser_id();
		
		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.FAMILIES);
		notification.setReceiver_id(user_id);
		notification.setIntent_flag(NotificationINTENTFlag.FALL_DIAGNOSIS_STORY);
		notification.setBehavior_id(NotificationBEHAVIORFlag.WRITING_THE_STORY);

		return falldiagnosisStoryService.insertFallDiagnosisStory(fallDiagnosisStory, notification);		
	}
	
	
	@RequestMapping(value = "/{fall_diagnosis_story_id}", method = RequestMethod.POST)
	public void insertSelfDiagnosis(@PathVariable int fall_diagnosis_story_id, HttpServletRequest request) throws Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		int self_diagnosis_category_id = Integer.parseInt(request.getParameter("self_diagnosis_category_id"));
		
		falldiagnosisStoryService.insertSelfDiagnosis(fall_diagnosis_story_id,user_id,self_diagnosis_category_id);
	}
}
