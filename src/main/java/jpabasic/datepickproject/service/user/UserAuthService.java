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

		// User 객체 생성, 이메일과 암호화된 비밀번호를 설정 -> DB에 저장할 이메일과 비밀번호
		User newUser = new User(requestDto.getEmail(), encryptedPassword);

		// DB에 User 저장
		User savedUser = userRepository.save(newUser);

		// JWT 토큰 생성 (JwtUtil 사용)
		String token = jwtUtil.createToken(savedUser.getEmail());

		// savedUser와 token을 반환
		return new SignUpUserResponseDto(savedUser, token);
	}

	// 유저 로그인 로직
	public SignInUserResponseDto signIn(SignInUserRequestDto requestDto) {

			// 1. 이메일을 기반으로 사용자 찾기
			User user = findUserByEmail(requestDto.getEmail());

			// 2. 비밀번호 일치 여부 확인
			if (!bcrypt.matches(requestDto.getPassword(), user.getPassword())) {
				// 비밀번호가 일치하지 않으면 예외 처리
				throw new CustomException(ErrorCode.INVALID_PASSWORD);  // 예시: 비밀번호가 잘못된 경우
			}

			// 3. JWT 토큰 생성
			String token = jwtUtil.createToken(user.getEmail());  // 이메일을 기반으로 JWT 토큰 생성

			// 4. JWT 토큰을 포함한 응답 반환 (응답에 토큰을 반환)
			return new SignInUserResponseDto(token);  // 생성된 토큰을 응답에 포함시킴
		}


		// 유저 탈퇴 로직
	public void resign(String token, String password) {

			// 1. JWT 토큰에서 이메일 추출
			String email = jwtUtil.getEmailFromToken(token);

			// 2. 비밀번호 확인
			User user = findUserByEmail(email);
			bcrypt.matches(password,user.getPassword());

			// 3. 탈퇴 처리: 사용자의 상태를 "탈퇴"로 변경
			user.inActivate();  // 'active' 플래그를 false로 설정하여 탈퇴 처리
			userRepository.save(user);  // DB에 반영

			log.info("사용자가 탈퇴했습니다. 이메일: {}", email);
		}

		// 중복되는 로직 추출 (로그인 및 회원 탈퇴)
		public User findUserByEmail(String email) {

			// 이메일로 사용자 존재 여부 확인
			Optional<User> existingUser = userRepository.findByEmail(email);

			// 이메일이 존재하지 않으면 예외 처리
			if (existingUser.isEmpty()) {
				log.info("이메일을 찾을 수 없습니다. 이메일: {}", email);
				throw new CustomException(ErrorCode.USER_NOT_FOUND);  // 이메일을 찾을 수 없으면 예외 처리
			}
			return existingUser.get();
		}

	}