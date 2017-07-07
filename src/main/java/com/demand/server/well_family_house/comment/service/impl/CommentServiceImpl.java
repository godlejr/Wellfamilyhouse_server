package com.demand.server.well_family_house.comment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.comment.service.CommentService;
import com.demand.server.well_family_house.common.flag.CommentINTENTFlag;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentMapper commentMapper;

	@Override
	public void updateComment(int flag, int comment_id, String content) throws Exception {
		if (flag == CommentINTENTFlag.STORY) {
			commentMapper.updateComment(comment_id, content);
		}

		if (flag == CommentINTENTFlag.SONG_PLAYER) {
			commentMapper.updateSongComment(comment_id, content);
		}

		if (flag == CommentINTENTFlag.SONG_STORY) {
			commentMapper.updateSongStoryComment(comment_id, content);
		}

		if (flag == CommentINTENTFlag.FALL_DIAGNOSIS_STORY) {
			commentMapper.updateFallDiagnosisStoryComment(comment_id, content);
		}
		
		if (flag == CommentINTENTFlag.EXERCISE_STORY) {
			commentMapper.updateExerciseStoryComment(comment_id, content);
		}
	}

	@Override
	public void deleteComment(int flag, int comment_id) throws Exception {
		if (flag == CommentINTENTFlag.STORY) {
			commentMapper.deleteComment(comment_id);
		}
		if (flag == CommentINTENTFlag.SONG_PLAYER) {
			commentMapper.deleteSongComment(comment_id);
		}
		if (flag == CommentINTENTFlag.SONG_STORY) {
			commentMapper.deleteSongStoryComment(comment_id);
		}

		if (flag == CommentINTENTFlag.FALL_DIAGNOSIS_STORY) {
			commentMapper.deleteFallDiagnosisStoryComment(comment_id);
		}
		
		if (flag == CommentINTENTFlag.EXERCISE_STORY) {
			commentMapper.deleteExerciseStoryComment(comment_id);
		}
	}
}
