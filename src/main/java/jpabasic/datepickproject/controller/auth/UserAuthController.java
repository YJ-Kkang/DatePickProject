package jpabasic.datepickproject.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jpabasic.datepickproject.common.exception.CustomException;
import jpabasic.datepickproject.common.exception.ErrorCode;
import jpabasic.datepickproject.dto.user.requset.SignInUserRequestDto;
import jpabasic.datepickproject.dto.user.requset.SignUpUserRequestDto;
import jpabasic.datepickproject.dto.user.response.ResignUserResponseDto;
import jpabasic.datepickproject.dto.user.response.SignInUserResponseDto;
import jpabasic.datepickproject.dto.user.response.SignUpUserResponseDto;
import jpabasic.datepickproject.service.user.UserAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth/users")
@RequiredArgsConstructor

public class UserAuthController {
	private final UserAuthService userAuthService;

	// 유저 회원가입
	@PostMapping("/signup")
	public ResponseEntity<SignUpUserResponseDto> signUpUser(
		@RequestBody SignUpUserRequestDto requestDto
	) {
		SignUpUserResponseDto responseDto = userAuthService.signUp(requestDto);

		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	// 유저 로그인
	@PostMapping("/signin")
	public ResponseEntity<SignInUserResponseDto> SignInUser(
		@RequestBody SignInUserRequestDto requestDto
	) {
		SignInUserResponseDto responseDto = userAuthService.signIn(requestDto);

		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	// 유저 탈퇴
	@DeleteMapping("/resign")
	public ResponseEntity<ResignUserResponseDto> ResignUser(
		@RequestBody String password,
		HttpServletRequest request
	) {
		// 1. Authorization 헤더에서 JWT 토큰 추출
		String token = request.getHeader("Authorization");

		// JWT 토큰이 없거나, 접두사 "Bearer "로 시작하지 않으면
		if (token == null || !token.startsWith("Bearer ")) {
			throw new CustomException(ErrorCode.TOKEN_NOT_FOUND); // 토큰 존재하지 않는다는 예외 처리
		}
		token = token.substring(7); // "Bearer " 부분 제외

		// 2. 유저 탈퇴 서비스 호출
		userAuthService.resign(token, password);

		// 탈퇴 완료 메시지와 함께 200 OK 응답 반환
		ResignUserResponseDto responseDto = new ResignUserResponseDto("탈퇴가 완료되었습니다.");

		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
}

