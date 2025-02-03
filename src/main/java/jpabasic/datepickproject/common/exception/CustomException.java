package jpabasic.datepickproject.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());  // Exception 메시지를 ErrorCode 메시지로 설정
		this.errorCode = errorCode;
	}
}