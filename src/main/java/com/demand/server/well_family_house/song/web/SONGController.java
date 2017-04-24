package com.demand.server.well_family_house.song.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.common.dto.CommentInfo;
import com.demand.server.well_family_house.common.dto.Notification;
import com.demand.server.well_family_house.common.dto.Range;
import com.demand.server.well_family_house.common.dto.Song;
import com.demand.server.well_family_house.common.dto.SongCategory;
import com.demand.server.well_family_house.common.dto.SongComment;
import com.demand.server.well_family_house.common.dto.SongStory;
import com.demand.server.well_family_house.common.dto.SongStoryEmotionInfo;
import com.demand.server.well_family_house.common.flag.NotificationBEHAVIORFlag;
import com.demand.server.well_family_house.common.flag.NotificationINTENTFlag;
import com.demand.server.well_family_house.common.flag.NotificationTOFlag;
import com.demand.server.well_family_house.common.flag.SongStoryRANGEFlag;
import com.demand.server.well_family_house.song.service.SongService;
import com.demand.server.well_family_house.song.service.impl.SongServiceImpl;

@RestController
@Secured("ROLE_USER")
@RequestMapping("/songs")
public class SONGController {

	@Autowired
	private SongService songService;

	public static int randomRange(int n1, int n2) {
		return (int) (Math.random() * (n2 - n1 + 1)) + n1;
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ArrayList<SongCategory> song_category_List() throws Exception {
		return songService.selectSongCategoryList();
	}

	@RequestMapping(value = "/{song_id}/comment_count", method = RequestMethod.GET)
	public int song_comment_Count(@PathVariable int song_id) throws Exception {
		return songService.selectSongCommentCount(song_id);
	}

	@RequestMapping(value = "/{song_id}/like_count", method = RequestMethod.GET)
	public int song_like_Count(@PathVariable int song_id) throws Exception {
		return songService.selectSongLikeCount(song_id);
	}

	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	public ArrayList<Song> song_list_by_Hits() throws Exception {
		return songService.selectSongListByHits();
	}

	@RequestMapping(value = "/randoms", method = RequestMethod.GET)
	public Song song_random() throws Exception {
		return songService.selectRandomSong(randomRange(149, 295));
	}

	@RequestMapping(value = "/categories/{category_id}", method = RequestMethod.GET)
	public ArrayList<Song> song_list_by_Category(@PathVariable int category_id) throws Exception {
		return songService.selectSongListByCategory(category_id);
	}

	@RequestMapping(value = "/{song_id}/hits", method = RequestMethod.PUT)
	public void Insert_Song_hit(@PathVariable int song_id) throws Exception {
		songService.updateSongHit(song_id);
	}

	@RequestMapping(value = "/{song_id}/comments", method = RequestMethod.GET)
	public ArrayList<CommentInfo> song_comment_List(@PathVariable int song_id) throws Exception {
		return songService.selectSongCommentList(song_id);
	}

	@RequestMapping(value = "/{song_id}/likes", method = RequestMethod.POST)
	public void song_like_up(HttpServletRequest request, @PathVariable int song_id)
			throws NumberFormatException, Exception {
		songService.insertSongLikeUp(Integer.parseInt(request.getParameter("user_id")), song_id);
	}

	@RequestMapping(value = "/{song_id}/likes/{user_id}", method = RequestMethod.DELETE)
	public void song_like_down(HttpServletRequest request, @PathVariable int song_id, @PathVariable int user_id)
			throws Exception {
		songService.deleteSongLikeDown(user_id, song_id);
	}

	@RequestMapping(value = "/{song_id}/like_check/{user_id}", method = RequestMethod.GET)
	public int song_like_check(HttpServletRequest request, @PathVariable int song_id, @PathVariable int user_id)
			throws Exception {
		return songService.selectSongLikeCheck(user_id, song_id);
	}

	@RequestMapping(value = "/ranges", method = RequestMethod.GET)
	public ArrayList<Range> song_range_List() throws Exception {
		return songService.selectSongRangeList();
	}

	@RequestMapping(value = "/emotions", method = RequestMethod.GET)
	public ArrayList<SongStoryEmotionInfo> song_story_emotion_List() throws Exception {
		return songService.selectEmotionList();
	}

	@RequestMapping(value = "/{song_id}/comments", method = RequestMethod.POST)
	public SongComment insert_song_comment(HttpServletRequest request, @PathVariable int song_id) throws Exception {
		SongComment songComment = new SongComment();
		songComment.setUser_id(Integer.parseInt(request.getParameter("user_id")));
		songComment.setSong_id(song_id);
		songComment.setContent(request.getParameter("content"));

		return songService.insertSongComment(songComment);
	}

	@RequestMapping(value = "/stories", method = RequestMethod.POST)
	public SongStory insert_song_story(HttpServletRequest request) throws Exception {
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		int range_id = Integer.parseInt(request.getParameter("range_id"));

		SongStory songStory = new SongStory();
		songStory.setUser_id(user_id);
		songStory.setRange_id(range_id);
		songStory.setSong_id(Integer.parseInt(request.getParameter("song_id")));
		songStory.setSong_title(request.getParameter("song_title"));
		songStory.setSong_singer(request.getParameter("song_singer"));
		songStory.setContent(request.getParameter("content"));
		songStory.setLocation(request.getParameter("location"));

		if (range_id != SongStoryRANGEFlag.ME) {
			Notification notification = new Notification();
			notification.setUser_id(user_id);
			notification.setReceive_category_id(NotificationTOFlag.FAMILIES);
			notification.setReceiver_id(user_id);
			notification.setContent_name("추억소리");
			notification.setIntent_flag(NotificationINTENTFlag.SONG_STORY_DETAIL);
			notification.setBehavior_id(NotificationBEHAVIORFlag.WRITING_THE_STORY);

			return songService.insertSongStory(songStory, notification);
		} else {
			return songService.insertSongStory(songStory);
		}
	}

	@RequestMapping(value = "/{song_id}/avatars", method = RequestMethod.GET)
	public String song_story_avatar(@PathVariable int song_id) throws NumberFormatException, Exception {
		return songService.selectSongAvatar(song_id);
	}
}
