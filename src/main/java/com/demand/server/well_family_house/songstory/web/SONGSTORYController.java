package com.demand.server.well_family_house.songstory.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.SongPhoto;
import com.demand.server.well_family_house.common.dto.SongStoryComment;
import com.demand.server.well_family_house.common.dto.SongStoryEmotionData;
import com.demand.server.well_family_house.common.dto.SongStoryInfoForNotification;
import com.demand.server.well_family_house.common.flag.NotificationBEHAVIORFlag;
import com.demand.server.well_family_house.common.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.common.flag.NotificationTOFlag;
import com.demand.server.well_family_house.songstory.service.SongStoryService;
import com.demand.server.well_family_house.songstory.service.impl.SongStoryServiceImpl;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/songstories")
public class SONGSTORYController {

	@Autowired
	private SongStoryService songStoryService;

	@RequestMapping(value = "/{song_story_id}/photos", method = RequestMethod.POST)
	public void insert_song_photos(HttpServletRequest request, @PathVariable int song_story_id)
			throws IOException, Exception {
		songStoryService.insertSongStoryPhoto(request.getInputStream(), song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/audios", method = RequestMethod.PUT)
	public void insert_song_audio(HttpServletRequest request, @PathVariable int song_story_id)
			throws IOException, Exception {
		songStoryService.insertSongStoryAudio(request.getInputStream(), song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/comment_count", method = RequestMethod.GET)
	public int selectSongStoryCommentCount(@PathVariable int song_story_id) throws Exception {
		return songStoryService.selectSongStoryCommentCount(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/like_count", method = RequestMethod.GET)
	public int selectSongStoryLikeCount(@PathVariable int song_story_id) throws Exception {
		return songStoryService.selectSongStoryLikeCount(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/likes", method = RequestMethod.POST)
	public void insertSongStoryLikeUp(HttpServletRequest request, @PathVariable int song_story_id)
			throws NumberFormatException, Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));

		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.WRITER);
		notification.setContent_name("???????????? ???????????? ?????????");
		notification.setIntent_flag(NotificationINTENTFlag.SONG_STORY_DETAIL);
		notification.setBehavior_id(NotificationBEHAVIORFlag.LIKE);
		notification.setIntent_id(song_story_id);
		

		songStoryService.insertSongStoryLikeUp(user_id, song_story_id, notification);
	}
	
	@RequestMapping(value = "/{song_story_id}/hits", method = RequestMethod.PUT)
	public void insert_song_story_hit(@PathVariable int song_story_id) throws Exception {
		songStoryService.updateSongStoryHit(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/likes/{user_id}", method = RequestMethod.DELETE)
	public void song_story_like_down(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable int song_story_id) throws Exception {
		songStoryService.deleteSongStoryLikeDown(user_id, song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/like_check/{user_id}", method = RequestMethod.GET)
	public int song_story_like_check(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable int song_story_id) throws Exception {
		return songStoryService.selectSongStoryLikeCheck(user_id, song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/photos", method = RequestMethod.GET)
	public ArrayList<SongPhoto> song_story_photo_List(@PathVariable int song_story_id) throws Exception {
		return songStoryService.selectSongStoryPhotoList(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/comments", method = RequestMethod.GET)
	public ArrayList<CommentInfo> song_story_comment_List(@PathVariable int song_story_id) throws Exception {
		return songStoryService.selectSongStoryCommentList(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/comments", method = RequestMethod.POST)
	public SongStoryComment insert_song_story_comment(HttpServletRequest request, @PathVariable int song_story_id)
			throws Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));

		SongStoryComment songStoryComment = new SongStoryComment();
		songStoryComment.setUser_id(user_id);
		songStoryComment.setSong_story_id(song_story_id);
		songStoryComment.setContent(request.getParameter("content"));
		Notification notification = new Notification();
		notification.setUser_id(user_id);
		notification.setReceive_category_id(NotificationTOFlag.WRITER);
		notification.setContent_name("???????????? ????????????");
		notification.setIntent_flag(NotificationINTENTFlag.SONG_STORY_DETAIL);
		notification.setBehavior_id(NotificationBEHAVIORFlag.WRITING_THE_COMMENT);
		notification.setIntent_id(song_story_id);

		return songStoryService.insertSongStoryComment(songStoryComment, notification);
	}

	@RequestMapping(value = "/{song_story_id}/emotions", method = RequestMethod.POST)
	public void insert_emotion_into_song_story(HttpServletRequest request, @PathVariable int song_story_id)
			throws NumberFormatException, Exception {
		songStoryService.insertEmotionIntoSongStory(song_story_id,
				Integer.parseInt(request.getParameter("song_story_emotion_id")));
	}

	@RequestMapping(value = "/{song_story_id}/emotions", method = RequestMethod.GET)
	public ArrayList<SongStoryEmotionData> song_story_emotion_data_List(@PathVariable int song_story_id)
			throws Exception {
		return songStoryService.selectSongStoryEmotionData(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}", method = RequestMethod.GET)
	public SongStoryInfoForNotification storyDetailForNotification(@PathVariable int song_story_id) throws Exception {
		return songStoryService.selectSongStoryInfo(song_story_id);
	}
	
	@RequestMapping(value = "/{song_story_id}", method = RequestMethod.PUT)
	public void update_story(HttpServletRequest request, @PathVariable int song_story_id) throws Exception {
		String content = request.getParameter("content");
		String location = request.getParameter("location");
		songStoryService.updateStory(song_story_id,content,location);
	}
	
	@RequestMapping(value = "/{song_story_id}", method = RequestMethod.DELETE)
	public void delete_story(@PathVariable int song_story_id) throws  Exception {
		songStoryService.deleteStory(song_story_id);
	}

}
