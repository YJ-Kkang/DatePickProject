package jpabasic.datepickproject.dto.user.response;

import lombok.Getter;

@Getter
public class ResignUserResponseDto {

	private final String message;

	public ResignUserResponseDto() {
		this.message = "회원 탈퇴 처리되었습니다.";
	}
}
