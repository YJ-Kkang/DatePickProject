package jpabasic.datepickproject.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
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
	public ResponseEntity<SignUpUserResponseDto> signUp(
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
		String token = request.getHeader("Authorization");

		userAuthService.resign(token, password);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
