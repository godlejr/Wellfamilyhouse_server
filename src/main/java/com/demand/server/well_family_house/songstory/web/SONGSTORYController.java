package com.demand.server.well_family_house.songstory.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.SongPhoto;
import com.demand.server.well_family_house.common.dto.SongStoryComment;
import com.demand.server.well_family_house.common.dto.SongStoryEmotionData;
import com.demand.server.well_family_house.songstory.service.impl.SongStoryServiceImpl;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/songstories")
public class SONGSTORYController {

	@Autowired
	private SongStoryServiceImpl songStoryServiceImpl;
	
	
	@RequestMapping(value = "/{song_story_id}/photos", method = RequestMethod.POST)
	public void insert_song_photos(HttpServletRequest request, @PathVariable int song_story_id) throws IOException, Exception {
		songStoryServiceImpl.insertSongStoryPhoto(request.getInputStream(), song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/audios", method = RequestMethod.PUT)
	public void insert_song_audio(HttpServletRequest request, @PathVariable int song_story_id) throws IOException, Exception {
		songStoryServiceImpl.insertSongStoryAudio(request.getInputStream(), song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/comment_count", method = RequestMethod.GET)
	public int song_story_comment_Count(@PathVariable int song_story_id) throws Exception {
		return songStoryServiceImpl.selectSongStoryCommentCount(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/like_count", method = RequestMethod.GET)
	public int song_story_like_Count(@PathVariable int song_story_id) throws Exception {
		return songStoryServiceImpl.selectSongStoryLikeCount(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/likes", method = RequestMethod.POST)
	public void song_story_like_up(HttpServletRequest request, @PathVariable int song_story_id) throws NumberFormatException, Exception {
		songStoryServiceImpl.insertSongStoryLikeUp(Integer.parseInt(request.getParameter("user_id")), song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/likes/{user_id}", method = RequestMethod.DELETE)
	public void song_story_like_down(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable int song_story_id) throws Exception {
		songStoryServiceImpl.deleteSongStoryLikeDown(user_id, song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/like_check/{user_id}", method = RequestMethod.GET)
	public int song_story_like_check(HttpServletRequest request, @PathVariable int user_id,
			@PathVariable int song_story_id) throws Exception {
		return songStoryServiceImpl.selectSongStoryLikeCheck(user_id, song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/photos", method = RequestMethod.GET)
	public ArrayList<SongPhoto> song_story_photo_List(@PathVariable int song_story_id) throws Exception {
		return songStoryServiceImpl.selectSongStoryPhotoList(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/comments", method = RequestMethod.GET)
	public ArrayList<CommentInfo> song_story_comment_List(@PathVariable int song_story_id) throws Exception {
		return songStoryServiceImpl.selectSongStoryCommentList(song_story_id);
	}

	@RequestMapping(value = "/{song_story_id}/comments", method = RequestMethod.POST)
	public SongStoryComment insert_song_story_comment(HttpServletRequest request, @PathVariable int song_story_id) throws Exception {
		SongStoryComment songStoryComment = new SongStoryComment();
		songStoryComment.setUser_id(Integer.parseInt(request.getParameter("user_id")));
		songStoryComment.setSong_story_id(song_story_id);
		songStoryComment.setContent(request.getParameter("content"));

		return songStoryServiceImpl.insertSongStoryComment(songStoryComment);
	}

	@RequestMapping(value = "/{song_story_id}/avatars", method = RequestMethod.GET)
	public String song_story_avatar(HttpServletRequest request, @PathVariable int song_story_id) throws NumberFormatException, Exception {
		return songStoryServiceImpl.selectSongStoryAvatar(Integer.parseInt(request.getParameter("song_id")));
	}

	@RequestMapping(value = "/{song_story_id}/emotions", method = RequestMethod.POST)
	public void insert_emotion_into_song_story(HttpServletRequest request, @PathVariable int song_story_id) throws NumberFormatException, Exception {
		songStoryServiceImpl.insertEmotionIntoSongStory(song_story_id, Integer.parseInt(request.getParameter("song_story_emotion_id")));
	}

	@RequestMapping(value = "/{song_story_id}/emotions", method = RequestMethod.GET)
	public ArrayList<SongStoryEmotionData> song_story_emotion_data_List(@PathVariable int song_story_id) throws Exception {
		return songStoryServiceImpl.selectSongStoryEmotionData(song_story_id);
	}

}
