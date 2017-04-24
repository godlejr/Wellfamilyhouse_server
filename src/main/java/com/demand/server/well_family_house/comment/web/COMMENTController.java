package com.demand.server.well_family_house.comment.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demand.server.well_family_house.comment.service.CommentService;
import com.demand.server.well_family_house.comment.service.impl.CommentServiceImpl;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/comments")
public class COMMENTController {

	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "/{comment_id}", method = RequestMethod.PUT)
	public void update_comment(HttpServletRequest request, @PathVariable int comment_id)
			throws NumberFormatException, Exception {
		commentService.updateComment(Integer.parseInt(request.getParameter("flag")), comment_id,
				request.getParameter("content"));
	}

	@RequestMapping(value = "/{comment_id}", method = RequestMethod.DELETE)
	public void delete_comment(HttpServletRequest request, @PathVariable int comment_id)
			throws NumberFormatException, Exception {
		commentService.deleteComment(Integer.parseInt(request.getParameter("flag")), comment_id);
	}

}
