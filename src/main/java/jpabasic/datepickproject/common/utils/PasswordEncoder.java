package jpabasic.datepickproject.common.utils;

import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jpabasic.datepickproject.common.exception.CustomException;
import jpabasic.datepickproject.common.exception.ErrorCode;

@Component
public class PasswordEncoder {

	// Bcrypt : 단방향 해시 알고리즘

	// 일반 문자열을 암호화
	public String encode(String rawPassword) {
		return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
	}

	// 로그인 시 사용자가 입력한 비밀번호와 DB에 저장된 암호화된 비밀번호가 일치여부 확인
	public void matches(String rawPassword, String encodedPassword) {
		BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);

		if (result.verified) {
			throw new CustomException(ErrorCode.PASSWORD_MISMATCH);  // 비밀번호가 일치하지 않으면 예외 처리
		}
	}
}
