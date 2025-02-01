package jpabasic.datepickproject.dto.user.response;

import jpabasic.datepickproject.common.entity.user.User;
import lombok.Getter;

@Getter
public class SignUpUserResponseDto {

		private final String message;

	public SignUpUserResponseDto(User savedUser) {
		this.message = "회원가입이 완료 되었습니다.";
	}
}
