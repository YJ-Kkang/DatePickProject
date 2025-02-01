package jpabasic.datepickproject.controller.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jpabasic.datepickproject.dto.post.request.CreatePostRequestDto;
import jpabasic.datepickproject.dto.post.response.CreatePostResponseDto;
import jpabasic.datepickproject.service.post.PostServiceYJ;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostControllerYJ {
	private final PostServiceYJ postServiceYJ;

	// post 생성
	@PostMapping
	public ResponseEntity<CreatePostResponseDto> createPostAPI(
		@RequestBody CreatePostRequestDto requestDto,
		HttpServletRequest request
	) {
		// JWT 를 통해 인증된 유저의 ID를 request 의 attribute 에서 가져옴
		Long userId = (Long) request.getAttribute("userId");

		// userId를 서비스 단으로 넘겨서 검증 및 post 생성 진행
		CreatePostResponseDto responseDto = postServiceYJ.createPostService(userId, requestDto);

		// HTTP 상태 코드 201(create)와 함께 responseDto 응답
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

}
