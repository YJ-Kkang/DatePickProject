package jpabasic.datepickproject.dto.comment.response;

import jpabasic.datepickproject.common.entity.comment.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {
	private String userName;
	private String content;

	public CommentResponse(String userName, String content) {
		this.userName = userName;
		this.content = content;
	}

	public static CommentResponse from(Comment comment) {
		return new CommentResponse(
			comment.getUser().getUserName(),
			comment.getContent()
		);
	}
}