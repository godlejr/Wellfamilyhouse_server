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
import com.demand.server.well_family_house.common.dto.Range;
import com.demand.server.well_family_house.common.dto.Song;
import com.demand.server.well_family_house.common.dto.SongCategory;
import com.demand.server.well_family_house.common.dto.SongComment;
import com.demand.server.well_family_house.common.dto.SongStory;
import com.demand.server.well_family_house.common.dto.SongStoryEmotionInfo;
import com.demand.server.well_family_house.song.service.impl.SongServiceImpl;

@RestController
@Secured("ROLE_USER")
@RequestMapping("/songs")
public class SONGController {

	@Autowired
	private SongServiceImpl songServiceImpl;

	public static int randomRange(int n1, int n2) {
		return (int) (Math.random() * (n2 - n1 + 1)) + n1;
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ArrayList<SongCategory> song_category_List() throws Exception {
		return songServiceImpl.selectSongCategoryList();
	}

	@RequestMapping(value = "/{song_id}/comment_count", method = RequestMethod.GET)
	public int song_comment_Count(@PathVariable int song_id) throws Exception {
		return songServiceImpl.selectSongCommentCount(song_id);
	}

	@RequestMapping(value = "/{song_id}/like_count", method = RequestMethod.GET)
	public int song_like_Count(@PathVariable int song_id) throws Exception {
		return songServiceImpl.selectSongLikeCount(song_id);
	}

	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	public ArrayList<Song> song_list_by_Hits() throws Exception {
		return songServiceImpl.selectSongListByHits();
	}

	@RequestMapping(value = "/randoms", method = RequestMethod.GET)
	public Song song_random() throws Exception {
		return songServiceImpl.selectRandomSong(randomRange(149, 295));
	}

	@RequestMapping(value = "/categories/{category_id}", method = RequestMethod.GET)
	public ArrayList<Song> song_list_by_Category(@PathVariable int category_id) throws Exception {
		return songServiceImpl.selectSongListByCategory(category_id);
	}

	@RequestMapping(value = "/{song_id}/hits", method = RequestMethod.PUT)
	public void Insert_Song_hit(@PathVariable int song_id) throws Exception {
		songServiceImpl.updateSongHit(song_id);
	}

	@RequestMapping(value = "/{song_id}/comments", method = RequestMethod.GET)
	public ArrayList<CommentInfo> song_comment_List(@PathVariable int song_id) throws Exception {
		return songServiceImpl.selectSongCommentList(song_id);
	}

	@RequestMapping(value = "/{song_id}/likes", method = RequestMethod.POST)
	public void song_like_up(HttpServletRequest request, @PathVariable int song_id)
			throws NumberFormatException, Exception {
		songServiceImpl.insertSongLikeUp(Integer.parseInt(request.getParameter("user_id")), song_id);
	}

	@RequestMapping(value = "/{song_id}/likes/{user_id}", method = RequestMethod.DELETE)
	public void song_like_down(HttpServletRequest request, @PathVariable int song_id, @PathVariable int user_id)
			throws Exception {
		songServiceImpl.deleteSongLikeDown(user_id, song_id);
	}

	@RequestMapping(value = "/{song_id}/like_check/{user_id}", method = RequestMethod.GET)
	public int song_like_check(HttpServletRequest request, @PathVariable int song_id, @PathVariable int user_id)
			throws Exception {
		return songServiceImpl.selectSongLikeCheck(user_id, song_id);
	}

	@RequestMapping(value = "/ranges", method = RequestMethod.GET)
	public ArrayList<Range> song_range_List() throws Exception {
		return songServiceImpl.selectSongRangeList();
	}

	@RequestMapping(value = "/emotions", method = RequestMethod.GET)
	public ArrayList<SongStoryEmotionInfo> song_story_emotion_List() throws Exception {
		return songServiceImpl.selectEmotionList();
	}

	@RequestMapping(value = "/{song_id}/comments", method = RequestMethod.POST)
	public SongComment insert_song_comment(HttpServletRequest request, @PathVariable int song_id) throws Exception {
		SongComment songComment = new SongComment();
		songComment.setUser_id(Integer.parseInt(request.getParameter("user_id")));
		songComment.setSong_id(song_id);
		songComment.setContent(request.getParameter("content"));

		return songServiceImpl.insertSongComment(songComment);
	}

	@RequestMapping(value = "/stories", method = RequestMethod.POST)
	public SongStory insert_song_story(HttpServletRequest request) throws Exception {
		SongStory songStory = new SongStory();
		songStory.setUser_id(Integer.parseInt(request.getParameter("user_id")));
		songStory.setRange_id(Integer.parseInt(request.getParameter("range_id")));
		songStory.setSong_id(Integer.parseInt(request.getParameter("song_id")));
		songStory.setSong_title(request.getParameter("song_title"));
		songStory.setSong_singer(request.getParameter("song_singer"));
		songStory.setContent(request.getParameter("content"));
		songStory.setLocation(request.getParameter("location"));

		return songServiceImpl.insertSongStory(songStory);
	}
	
	@RequestMapping(value = "/{song_id}/avatars", method = RequestMethod.GET)
	public String song_story_avatar(@PathVariable int song_id) throws NumberFormatException, Exception {
		return songServiceImpl.selectSongAvatar(song_id);
	}

}
