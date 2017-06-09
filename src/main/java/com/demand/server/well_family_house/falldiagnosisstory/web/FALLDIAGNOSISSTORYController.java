package com.demand.server.well_family_house.falldiagnosisstory.web;

import java.io.IOException;
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
import com.demand.server.well_family_house.common.dto.EnvironmentEvaluationCategory;
import com.demand.server.well_family_house.common.dto.EnvironmentEvaluationStatus;
import com.demand.server.well_family_house.common.dto.EnvironmentPhoto;
import com.demand.server.well_family_house.common.dto.FallDiagnosisContentCategory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisStory;
import com.demand.server.well_family_house.common.dto.FallDiagnosisStoryComment;
import com.demand.server.well_family_house.common.dto.FallDiagnosisStoryInfo;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluation;
import com.demand.server.well_family_house.common.dto.PhysicalEvaluationScore;
import com.demand.server.well_family_house.common.dto.SongStoryComment;
import com.demand.server.well_family_house.common.flag.NotificationBEHAVIORFlag;
import com.demand.server.well_family_house.common.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.common.flag.NotificationTOFlag;
import com.demand.server.well_family_house.falldiagnosisstory.service.FallDiagnosisStoryService;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/fall_diagnosis_stories")
public class FALLDIAGNOSISSTORYController {

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

	@RequestMapping(value = "/{fall_diagnosis_story_id}/self_diagnosis", method = RequestMethod.POST)
	public void insertSelfDiagnosis(@PathVariable int fall_diagnosis_story_id, HttpServletRequest request)
			throws Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		int fall_diagnosis_content_category_id = Integer
				.parseInt(request.getParameter("fall_diagnosis_content_category_id"));

		falldiagnosisStoryService.insertSelfDiagnosis(fall_diagnosis_story_id, user_id,
				fall_diagnosis_content_category_id);
	}

	@RequestMapping(value = "/{fall_diagnosis_story_id}/physical_evaluation", method = RequestMethod.POST)
	public void insertPhysicalEvaluation(@PathVariable int fall_diagnosis_story_id,
			@RequestBody PhysicalEvaluation physicalEvaluation) throws Exception {
		falldiagnosisStoryService.insertPhysicalEvaluation(physicalEvaluation);
	}

	@RequestMapping(value = "/{fall_diagnosis_story_id}/physical_evaluation_score", method = RequestMethod.POST)
	public void insertPhysicalEvaluationScore(@PathVariable int fall_diagnosis_story_id,
			@RequestBody PhysicalEvaluationScore physicalEvaluationScore) throws Exception {
		falldiagnosisStoryService.insertPhysicalEvaluationScore(physicalEvaluationScore);
	}

	@RequestMapping(value = "/{fall_diagnosis_story_id}/environment_evaluation", method = RequestMethod.POST)
	public void insertEnvironmentEvaluation(@PathVariable int fall_diagnosis_story_id, HttpServletRequest request)
			throws Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		int environment_evaluation_category_id = Integer
				.parseInt(request.getParameter("environment_evaluation_category_id"));

		falldiagnosisStoryService.insertEnvironmentEvaluation(fall_diagnosis_story_id, user_id,
				environment_evaluation_category_id);
	}
	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/environment_evaluation_status", method = RequestMethod.POST)
	public void insertEnvironmentEvaluationStatus(@PathVariable int fall_diagnosis_story_id,
			@RequestBody EnvironmentEvaluationStatus environmentEvaluationStatus ) throws Exception {
		falldiagnosisStoryService.insertEnvironmentEvaluationStatus(environmentEvaluationStatus);
	}

	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/environment_photo", method = RequestMethod.POST)
	public void insertEnvironmentPhoto(HttpServletRequest request, @PathVariable int fall_diagnosis_story_id) throws IOException, Exception {
		falldiagnosisStoryService.insertEnvironmentPhoto(request.getInputStream(), fall_diagnosis_story_id);
	}
	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/comment_count", method = RequestMethod.GET)
	public int selectFallDiagnosisStoryCommentCount(@PathVariable int fall_diagnosis_story_id) throws Exception {
		return falldiagnosisStoryService.selectFallDiagnosisStoryCommentCount(fall_diagnosis_story_id);
	}

	@RequestMapping(value = "/{fall_diagnosis_story_id}/like_count", method = RequestMethod.GET)
	public int selectFallDiagnosisStoryLikeCount(@PathVariable int fall_diagnosis_story_id) throws Exception {
		return falldiagnosisStoryService.selectFallDiagnosisStoryLikeCount(fall_diagnosis_story_id);
	}
	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/likes", method = RequestMethod.POST)
	public void insertFallDiagnosisStoryLikeUp(HttpServletRequest request, @PathVariable int fall_diagnosis_story_id)
			throws NumberFormatException, Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));

		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.WRITER);
		notification.setContent_name("낙상진단 스토리");
		notification.setIntent_flag(NotificationINTENTFlag.FALL_DIAGNOSIS_STORY);
		notification.setBehavior_id(NotificationBEHAVIORFlag.LIKE);
		notification.setIntent_id(fall_diagnosis_story_id);

		falldiagnosisStoryService.insertFallDiagnosisStoryLikeUp(user_id, fall_diagnosis_story_id, notification);
	}
	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/likes/{user_id}", method = RequestMethod.DELETE)
	public void deleteFallDiagnosisStoryLikeDown(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable int fall_diagnosis_story_id) throws Exception {
		falldiagnosisStoryService.deleteFallDiagnosisStoryLikeDown(user_id, fall_diagnosis_story_id);
	}
	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/like_check/{user_id}", method = RequestMethod.GET)
	public int selectFallDiagnosisStoryLikeCheck(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable int fall_diagnosis_story_id) throws Exception {
		return falldiagnosisStoryService.selectFallDiagnosisStoryLikeCheck(user_id, fall_diagnosis_story_id);
	}
	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/hits", method = RequestMethod.PUT)
	public void updateFallDiagnosisStoryHit(@PathVariable int fall_diagnosis_story_id) throws Exception {
		falldiagnosisStoryService.updateFallDiagnosisStoryHit(fall_diagnosis_story_id);
	}
	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/infos", method = RequestMethod.GET)
	public FallDiagnosisStoryInfo selectFallDiagnosisStoryInfo(@PathVariable int fall_diagnosis_story_id, HttpServletRequest request) throws Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		int fall_diagnosis_category_id = Integer
				.parseInt(request.getParameter("fall_diagnosis_category_id"));
		int fall_diagnosis_risk_category_id = Integer
				.parseInt(request.getParameter("fall_diagnosis_risk_category_id"));

		FallDiagnosisStory fallDiagnosisStory = new FallDiagnosisStory();
		fallDiagnosisStory.setId(fall_diagnosis_story_id);
		fallDiagnosisStory.setUser_id(user_id);
		fallDiagnosisStory.setFall_diagnosis_category_id(fall_diagnosis_category_id);
		fallDiagnosisStory.setFall_diagnosis_risk_category_id(fall_diagnosis_risk_category_id);
		
		return falldiagnosisStoryService.selectFallDiagnosisStoryInfo(fallDiagnosisStory);
	}

	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/self_diagnosis", method = RequestMethod.GET)
	public  ArrayList<FallDiagnosisContentCategory> selectSelfDiagnosisList(@PathVariable int fall_diagnosis_story_id)
			throws Exception {
		return falldiagnosisStoryService.selectSelfDiagnosisList(fall_diagnosis_story_id);
	}
	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/physical_evaluation_score", method = RequestMethod.GET)
	public  PhysicalEvaluationScore selectPhysicalEvaluationScore(@PathVariable int fall_diagnosis_story_id)
			throws Exception {
		return falldiagnosisStoryService.selectPhysicalEvaluationScore(fall_diagnosis_story_id);
	}
	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/environment_photo", method = RequestMethod.GET)
	public ArrayList<EnvironmentPhoto> selectEnvironmentPhoto( @PathVariable int fall_diagnosis_story_id) throws IOException, Exception {
		return falldiagnosisStoryService.selectEnvironmentPhotoList(fall_diagnosis_story_id);
	}
	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/environment_evaluation", method = RequestMethod.GET)
	public ArrayList<EnvironmentEvaluationCategory> selectEnvironmentEvaluationList(@PathVariable int fall_diagnosis_story_id)
			throws Exception {
		return  falldiagnosisStoryService.selectEnvironmentEvaluationList(fall_diagnosis_story_id);
	}
	
	@RequestMapping(value = "/{fall_diagnosis_story_id}/comments", method = RequestMethod.GET)
	public ArrayList<CommentInfo> selectFalldiagnosisStoryCommentList(@PathVariable int fall_diagnosis_story_id) throws Exception {
		return falldiagnosisStoryService.selectFalldiagnosisStoryCommentList(fall_diagnosis_story_id);
	}

	@RequestMapping(value = "/{fall_diagnosis_story_id}/comments", method = RequestMethod.POST)
	public FallDiagnosisStoryComment insertFalldiagnosisStoryComment(HttpServletRequest request, @PathVariable int fall_diagnosis_story_id)
			throws Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		String content =  request.getParameter("content");
		
		FallDiagnosisStoryComment fallDiagnosisStoryComment = new FallDiagnosisStoryComment();
		fallDiagnosisStoryComment.setUser_id(user_id);
		fallDiagnosisStoryComment.setContent(content);
		fallDiagnosisStoryComment.setFall_diagnosis_story_id(fall_diagnosis_story_id);
	
		
		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.WRITER);
		notification.setContent_name("회원님의 낙상진단");
		notification.setIntent_flag(NotificationINTENTFlag.FALL_DIAGNOSIS_STORY);
		notification.setBehavior_id(NotificationBEHAVIORFlag.WRITING_THE_COMMENT);
		notification.setIntent_id(fall_diagnosis_story_id);

		return falldiagnosisStoryService.insertFalldiagnosisStoryComment(fallDiagnosisStoryComment, notification);
	}
		
}
