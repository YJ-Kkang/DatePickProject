package jpabasic.datepickproject.controller.like;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jpabasic.datepickproject.common.entity.like.Like;
import jpabasic.datepickproject.common.entity.post.Post;
import jpabasic.datepickproject.common.utils.JwtUtil;
import jpabasic.datepickproject.dto.like.LikeStatusResponseDto;
import jpabasic.datepickproject.service.like.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts/{post_id}/likes") // userId 는 JWT 를 통해 받아옴 JWT 가 없을 땐 /{post_id}/{user_id}/likes 의 URL 구조가 됨
public class LikeController {

	private final LikeService likeService;
	private final JwtUtil jwtUtil;

	@PatchMapping
	public ResponseEntity<LikeStatusResponseDto> likeStatusAPI(
		@PathVariable("post_id") Long postId,
		HttpServletRequest request) {

		// 헤더에서 토큰값을 가져옴
		String getToken = request.getHeader("Authorization");
		// 만약에 토큰 값이 null 이라면 혹은 앞에 "Bearer "가 붙어 있지 않다면 던지는 예외
		if(getToken == null || !getToken.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		String token = getToken.substring(7);
		Long userId = jwtUtil.getUserIdFromToken(token);

		LikeStatusResponseDto likeStatusResponseDto = likeService.changeLikeStatus(postId, userId);

		// ResponseDto를 반환 및 HttpStatus 반환
		return new ResponseEntity<>(likeStatusResponseDto, HttpStatus.OK);
	}
}
