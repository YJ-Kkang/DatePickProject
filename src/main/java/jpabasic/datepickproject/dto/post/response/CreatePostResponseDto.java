package jpabasic.datepickproject.dto.post.response;

import java.time.LocalDateTime;

import jpabasic.datepickproject.common.entity.post.Post;
import lombok.Getter;

@Getter
public class CreatePostResponseDto {
	private String message;
	private String title;
	private String content;
	private LocalDateTime createdAt;

	public CreatePostResponseDto(String message, Post savedPost) {
		this.message = message;
		this.title = savedPost.getTitle();
		this.content = savedPost.getContent();
		this.createdAt = savedPost.getCreatedAt();
	}
}
