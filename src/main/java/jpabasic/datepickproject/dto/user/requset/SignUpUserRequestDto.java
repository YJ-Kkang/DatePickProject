package jpabasic.datepickproject.dto.user.requset;

import lombok.Getter;

@Getter
public class SignUpUserRequestDto {

	private final String email;

	private final String username;

	private final String password;

	public SignUpUserRequestDto(String email, String password) {
		this.email = email;
		this.username = email;
		this.password = password;
	}
}
