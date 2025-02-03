package jpabasic.datepickproject.dto.comment.request;

import jpabasic.datepickproject.common.entity.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentRequest {
	private Long postId;
	private Long userId;
	private String content;
}