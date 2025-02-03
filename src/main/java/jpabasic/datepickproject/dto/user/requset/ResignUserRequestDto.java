package jpabasic.datepickproject.dto.user.requset;

import lombok.Getter;

@Getter
public class ResignUserRequestDto {

	private final String email;

	private final String username;

	private final String password;

	public ResignUserRequestDto(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
}
