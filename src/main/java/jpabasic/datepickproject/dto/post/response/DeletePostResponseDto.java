package jpabasic.datepickproject.dto.post.response;

import java.time.LocalDateTime;

import jpabasic.datepickproject.common.entity.post.Post;
import lombok.Getter;

@Getter
public class DeletePostResponseDto {
	private final String message;
	private final LocalDateTime deletedAt;

	public DeletePostResponseDto(Post post) {
		this.message = "게시글이 삭제되었습니다.";
		this.deletedAt = post.getDeletedAt();
	}
}
