package jpabasic.datepickproject.controller.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jpabasic.datepickproject.common.entity.user.User;
import jpabasic.datepickproject.common.filter.JwtFilter;
import jpabasic.datepickproject.common.utils.JwtUtil;
import jpabasic.datepickproject.dto.user.requset.UpdateUserRequestDto;
import jpabasic.datepickproject.dto.user.response.UpdateUserResponseDto;
import jpabasic.datepickproject.service.user.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	// 유저 다건 조회 (이메일과 사용자이름 페이징 처리로 검색한 로직)
	@GetMapping("/search")
	public Page<User> searchUsers(
		@RequestParam(required = false, defaultValue = "") String email,
		@RequestParam(required = false, defaultValue = "") String username,
		Pageable pageable) {
		return userService.searchMatchedUsers(email, username, pageable);
	}

	// 유저 수정
	@PatchMapping("/{userId}")
	public UpdateUserResponseDto updateUser(
		@PathVariable Long userId,
		@RequestBody UpdateUserRequestDto updateUserRequestDto,
		HttpServletRequest request
	) {
		// request헤더에 담긴 토큰을 추출해서 담기
		String token = JwtFilter.extractToken(request);

		// 그렇게 담은 추출한 토큰으로부터 유저아이디를 찾기
		Long userIdFromToken = JwtUtil.getUserIdFromToken(token);

		return userService.updateUser(userId, updateUserRequestDto,userIdFromToken);
	}
}
