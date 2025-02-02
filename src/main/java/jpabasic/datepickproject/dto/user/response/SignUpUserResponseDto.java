package jpabasic.datepickproject.dto.user.response;

import jpabasic.datepickproject.common.entity.user.User;
import lombok.Getter;

@Getter
public class SignUpUserResponseDto {
	private final String email;
	private final String token;

	public SignUpUserResponseDto(User user, String token) {
		this.email = user.getEmail();
		this.token = token;
	}
}
