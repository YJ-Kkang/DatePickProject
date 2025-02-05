package jpabasic.datepickproject.dto.user.requset;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateUserRequestDto {
	private final String email;
	private final String username;
}
