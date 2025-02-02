package jpabasic.datepickproject.dto.user.response;

import jpabasic.datepickproject.common.entity.user.User;
import lombok.Getter;

@Getter
public class SignUpUserResponseDto {
	private Long id;
	private final String email;
	private final String username;

	public SignUpUserResponseDto(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.username = user.getUserName();
	}
}
