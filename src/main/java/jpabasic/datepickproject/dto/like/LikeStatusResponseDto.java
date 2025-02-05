package jpabasic.datepickproject.dto.like;

import jpabasic.datepickproject.common.entity.post.Post;
import lombok.Getter;

@Getter
public class LikeStatusResponseDto {

	private final String message;
	private final Long likeCount;

	public LikeStatusResponseDto(String message, Post post) {
		this.message = message;
		this.likeCount = post.getLikeCount();
	}
}
