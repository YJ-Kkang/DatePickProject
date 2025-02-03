package jpabasic.datepickproject.dto.comment.response;

import jpabasic.datepickproject.common.entity.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponse {
	private Long id;
	private Long postId;
	private Long userId;
	private String content;

	public CommentResponse(Long id, Long postId, Long userId, String content) {
		this.id = id;
		this.postId = postId;
		this.userId = userId;
		this.content = content;
	}
	public static CommentResponse from(Comment comment) {
		return new CommentResponse(
			comment.getId(),
			comment.getPost().getId(),
			comment.getUser().getId(),
			comment.getContent()
		);
	}
}
