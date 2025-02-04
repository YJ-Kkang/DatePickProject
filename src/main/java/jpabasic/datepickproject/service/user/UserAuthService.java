package jpabasic.datepickproject.service.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jpabasic.datepickproject.common.entity.user.User;
import jpabasic.datepickproject.common.exception.CustomException;
import jpabasic.datepickproject.common.exception.ErrorCode;
import jpabasic.datepickproject.common.utils.JwtUtil;
import jpabasic.datepickproject.common.utils.PasswordEncoder;
import jpabasic.datepickproject.dto.user.requset.SignInUserRequestDto;
import jpabasic.datepickproject.dto.user.requset.SignUpUserRequestDto;
import jpabasic.datepickproject.dto.user.response.SignInUserResponseDto;
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

	// 유저 회원가입 로직
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

		// User 객체 생성, 이메일과 암호화된 비밀번호를 설정
		User newUser = new User(requestDto.getEmail(), requestDto.getUsername(), encryptedPassword);

		// DB에 User 저장
		User savedUser = userRepository.save(newUser);

		// savedUser 반환
		return new SignUpUserResponseDto(savedUser);
	}

	public SignInUserResponseDto signIn(SignInUserRequestDto requestDto) {

		// 1. 이메일을 기반으로 사용자 찾기
		User user = userRepository.findByEmail(requestDto.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));  // 이메일로 유저 찾고 없으면 예외 발생

		// 2. 비밀번호 일치 여부 확인
		boolean passwordMatches = bcrypt.matches(requestDto.getPassword(), user.getPassword());
		if (!passwordMatches) {
			throw new CustomException(ErrorCode.INVALID_PASSWORD);  // 비밀번호 불일치 시 예외 처리
		}

		// 3. JWT 토큰 생성 (user.getId()는 Long, user.getEmail()은 String)
		String token = jwtUtil.createToken(user.getId(), user.getEmail());  // 아이디와 이메일을 기반으로 JWT 토큰 생성

		// 4. JWT 토큰을 포함한 응답 반환 (응답에 토큰을 반환)
		return new SignInUserResponseDto(token);  // 생성된 토큰을 응답에 포함시킴
	}

	public void resign(String token, String password) {

		// 1. JWT 토큰에서 userId 추출
		Long userId = jwtUtil.getUserIdFromToken(token);  // userId로 변경

		// 2. 비밀번호 확인
		User user = findUserById(userId);  // 이제 이메일이 아니라 userId로 사용자 찾기
		boolean passwordMatches = bcrypt.matches(password, user.getPassword());
		if (!passwordMatches) {
			throw new CustomException(ErrorCode.INVALID_PASSWORD);  // 비밀번호가 일치하지 않으면 예외 발생
		}

		// 3. 탈퇴 처리: 사용자의 상태를 "탈퇴"로 변경
		user.inActivate();  // 'active' 플래그를 false로 설정하여 탈퇴 처리
		userRepository.save(user);  // DB에 반영

		log.info("사용자가 탈퇴했습니다. userId: {}", userId);
	}

	public User findUserById(Long userId) {

		// userId로 사용자 존재 여부 확인
		Optional<User> existingUser = userRepository.findById(userId);

		// userId가 존재하지 않으면 예외 처리
		if (existingUser.isEmpty()) {
			log.info("사용자를 찾을 수 없습니다. userId: {}", userId);
			throw new CustomException(ErrorCode.USER_NOT_FOUND);  // 사용자를 찾을 수 없으면 예외 처리
		}
		return existingUser.get();
	}

}