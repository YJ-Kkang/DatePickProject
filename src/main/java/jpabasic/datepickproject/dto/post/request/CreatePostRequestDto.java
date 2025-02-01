package jpabasic.datepickproject.dto.post.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreatePostRequestDto {
	@NotBlank(message = "제목은 필수입니다.")
	@Size(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
	private String title;

	@NotBlank(message = "내용은 필수입니다.")
	@Size(max = 2000, message = "내용은 2000자를 초과할 수 없습니다.")
	private String content;
}
