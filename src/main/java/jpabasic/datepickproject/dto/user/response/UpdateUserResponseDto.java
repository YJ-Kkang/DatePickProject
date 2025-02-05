package jpabasic.datepickproject.dto.user.response;

import jpabasic.datepickproject.common.entity.user.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateUserResponseDto {
	private final Long id;
	private final String email;
	private final String username;

	public UpdateUserResponseDto(User savedUser) {
		this.id = savedUser.getId();
		this.email = savedUser.getEmail();
		this.username = savedUser.getUserName();
	}
}
