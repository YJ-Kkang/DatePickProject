package jpabasic.datepickproject.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다. 다시 입력해주세요."),
	URL_NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 경로입니다."),
	EMAIL_EXISTS(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
	USER_ALREADY_DELETE(HttpStatus.BAD_REQUEST, "이미 탈퇴 처리 된 회원입니다."),
	USER_DELETE(HttpStatus.BAD_REQUEST, "탈퇴한 회원은 로그인 할 수 없습니다."),
	PASSWORD_SAME_AS_BEFORE(HttpStatus.BAD_REQUEST, "바꾸려는 비밀번호가 이전과 동일하거나, 입력한 비밀번호가 서로 다릅니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
	EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "입력하신 아이디를 찾을 수 없습니다. 다시 확인해주세요."),
	ID_MISMATCH(HttpStatus.UNAUTHORIZED, "권한이 존재하지 않습니다."),

	COMMENT_NOT_FOUND(HttpStatus.NO_CONTENT, "부모 댓글이 존재하지 않습니다."),
	UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "인증되지 않은 회원입니다. 로그인 후 이용해주세요."),
	USER_CANNOT_FRIEND_SELF(HttpStatus.BAD_REQUEST, "자기 자신에게 친구 요청을 할 수 없습니다."),
	FRIEND_CANNOT_BE_DELETED(HttpStatus.BAD_REQUEST, "삭제된 친구는 조회할 수 없습니다."),
	FRIEND_NOT_FOUND(HttpStatus.BAD_REQUEST, "친구를 찾을 수 없습니다."),
	FRIEND_REQUEST_NOT_AUTHORIZED(HttpStatus.BAD_REQUEST, "요청을 수락하거나 거절할 권한이 없습니다."),
	UNAUTHORIZED_NOT_ACCESS(HttpStatus.UNAUTHORIZED, "자신의 댓글만 수정할 수 있습니다."),
	TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰이 존재하지 않습니다."),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호를 잘못 입력하였습니다." ),

	// Post
	POST_NOT_FOUND(HttpStatus.NO_CONTENT, "게시물이 존재하지 않습니다."),
	POST_DELETION_NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, "본인이 작성하지 않은 게시글은 삭제할 수 없습니다."),
	POST_IS_DELETE(HttpStatus.BAD_REQUEST, "삭제된 게시글은 조회/삭제 할 수 없습니다.");


	private final HttpStatus status;
	private final String message;

	ErrorCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}

