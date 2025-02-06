package jpabasic.datepickproject.service.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jpabasic.datepickproject.common.entity.user.User;
import jpabasic.datepickproject.common.exception.CustomException;
import jpabasic.datepickproject.common.exception.ErrorCode;
import jpabasic.datepickproject.dto.user.requset.UpdateUserRequestDto;
import jpabasic.datepickproject.dto.user.response.UpdateUserResponseDto;
import jpabasic.datepickproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

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

	// 이메일 또는 사용자명으로 부분일치 검색 후 페이징 처리 된 값 반환
	public Page<User> searchMatchedUsers(String username, String email, Pageable pageable) {
		return userRepository.findByEmailContainingOrUserNameContaining(username, email, pageable);
	}

	// 유저 수정
	public UpdateUserResponseDto updateUser(Long userId, UpdateUserRequestDto updateUserRequestDto, Long signInUserId) {
		User user = findUserById(userId);

		if (!user.getId().equals(signInUserId)) {
			throw new CustomException(ErrorCode.ID_MISMATCH); // 사용자 본인만 접근 가능하도록 예외 처리
		}

		user.updateUser(updateUserRequestDto);
		User savedUser = userRepository.save(user);

		return new UpdateUserResponseDto(savedUser);
	}
}
