package jpabasic.datepickproject.dto.user.requset;

import lombok.Getter;

@Getter
public class SignUpUserRequestDto {

	// todo 사용자 아이디는 이메일 형식
	private final String email;

	private final String username;

	// todo 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함, 최소 8글자 이상
	private final String password;

	public SignUpUserRequestDto(String email, String password) {
		this.email = email;
		this.username = email;
		this.password = password;
	}
}
