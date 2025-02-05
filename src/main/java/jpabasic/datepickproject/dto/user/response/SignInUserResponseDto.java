package jpabasic.datepickproject.dto.user.response;

import lombok.Getter;

@Getter
public class SignInUserResponseDto {
		private final String token;

		public SignInUserResponseDto(String token) {
			this.token = token;
		}
	}
