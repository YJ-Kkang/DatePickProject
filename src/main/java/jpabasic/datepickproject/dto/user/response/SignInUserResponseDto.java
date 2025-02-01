package jpabasic.datepickproject.dto.user.response;

import jpabasic.datepickproject.common.entity.user.User;
import lombok.Getter;

@Getter
public class SignInUserResponseDto {

		private final String token;

		public SignInUserResponseDto(String token) {
			this.token = token;
		}
	}
