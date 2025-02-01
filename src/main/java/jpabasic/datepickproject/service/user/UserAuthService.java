package jpabasic.datepickproject.service.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jpabasic.datepickproject.common.entity.user.User;
import jpabasic.datepickproject.common.exception.CustomException;
import jpabasic.datepickproject.common.exception.ErrorCode;
import jpabasic.datepickproject.common.utils.JwtUtil;
import jpabasic.datepickproject.common.utils.PasswordEncoder;
import jpabasic.datepickproject.dto.user.requset.SignInUserRequestDto;
import jpabasic.datepickproject.dto.user.requset.ResignUserRequestDto;
import jpabasic.datepickproject.dto.user.requset.SignUpUserRequestDto;
import jpabasic.datepickproject.dto.user.response.SignInUserResponseDto;
import jpabasic.datepickproject.dto.user.response.ResignUserResponseDto;
import jpabasic.datepickproject.dto.user.response.SignUpUserResponseDto;
import jpabasic.datepickproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	PasswordEncoder bcrypt = new PasswordEncoder();

	public SignUpUserResponseDto signUp(SignUpUserRequestDto requestDto) {

		// 등록된 이메일 여부 확인
		Optional<User> existingUser = userRepository.findByEmail(requestDto.getEmail());

		// 존재하면 예외 처리
		if (existingUser.isPresent()) {
			log.info("이미 존재하는 이메일입니다. 이메일: {}", requestDto.getEmail());
			throw new CustomException(ErrorCode.EMAIL_EXISTS);
		}

		// 사용자가 입력한 비밀번호를 암호화
		String encryptedPassword = bcrypt.encode(requestDto.getPassword());

		// User 객체 생성, 이메일과 암호화된 비밀번호를 설정 -> DB에 저장할 이메일과 비밀번호
		User newUser = new User(requestDto.getEmail(), encryptedPassword);

		// DB에 User 저장
		User savedUser = userRepository.save(newUser);

		// savedUser 반환
		return new SignUpUserResponseDto(savedUser);

	}
	public SignInUserResponseDto signIn(SignInUserRequestDto requestDto) {

	}

	public void resign(String password, String token) {

	}

	public JwtUtil getJwtUtil() {
		return jwtUtil;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}
}