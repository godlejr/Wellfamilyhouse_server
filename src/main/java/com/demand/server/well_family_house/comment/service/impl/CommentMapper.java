package com.demand.server.well_family_house.comment.service.impl;

import org.springframework.stereotype.Repository;

@Repository
public interface CommentMapper {
	void updateComment(int comment_id, String content) throws Exception;

	void updateSongComment(int comment_id, String content) throws Exception;

	void updateSongStoryComment(int comment_id, String content) throws Exception;

	void deleteComment(int comment_id) throws Exception;

	void deleteSongComment(int comment_id) throws Exception;

	void deleteSongStoryComment(int comment_id) throws Exception;

	void updateFallDiagnosisStoryComment(int comment_id, String content) throws Exception;

	void deleteFallDiagnosisStoryComment(int comment_id) throws Exception;
}
