package com.demand.server.well_family_house.comment.service;

public interface CommentService {

	void updateComment(int flag,int comment_id, String content) throws Exception;
	
	void deleteComment(int flag,int comment_id) throws Exception;
}
