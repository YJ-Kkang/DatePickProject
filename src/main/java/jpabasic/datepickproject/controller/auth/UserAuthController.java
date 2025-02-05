package jpabasic.datepickproject.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jpabasic.datepickproject.common.filter.JwtFilter;
import jpabasic.datepickproject.dto.user.requset.ResignUserRequestDto;
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
		@RequestBody ResignUserRequestDto requestDto,
		HttpServletRequest request
	) {
		// 1. Authorization 헤더에서 JWT 토큰 추출
		String token = JwtFilter.extractToken(request);

		// 2. 유저 탈퇴 서비스 호출
		userAuthService.resign(token, requestDto.getPassword());

		// 탈퇴 완료 메시지와 함께 200 OK 응답 반환
		ResignUserResponseDto responseDto = new ResignUserResponseDto();

		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
}

