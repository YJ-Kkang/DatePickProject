package jpabasic.datepickproject.dto.post.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreatePostRequestDto {
	// todo @NotNull, @Length는 제목과 내용에 대한 유효성 검사를 위해 추가한 사항들입니다. 추후 수정 및 삭제해도 무방합니다.
	@NotNull(message = "제목은 필수입니다.")
	@Length(max = 100, message = "제목은 100자를 초과할 수 없습니다.")
	private String title;

	@NotNull(message = "내용은 필수입니다.")
	@Length(max = 2000, message = "내용은 2000자를 초과할 수 없습니다.")
	private String content;
}
