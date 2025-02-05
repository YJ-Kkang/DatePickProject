package jpabasic.datepickproject.controller.post;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jpabasic.datepickproject.dto.post.request.CreatePostRequestDto;
import jpabasic.datepickproject.dto.post.response.CreatePostResponseDto;
import jpabasic.datepickproject.dto.post.response.DeletePostResponseDto;
import jpabasic.datepickproject.dto.post.response.FindPostResponseDto;
import jpabasic.datepickproject.service.post.PostServiceYJ;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostControllerYJ {
	private final PostServiceYJ postServiceYJ;

	// post 생성
	@PostMapping
	public ResponseEntity<CreatePostResponseDto> createPostAPI(
		@Valid @RequestBody CreatePostRequestDto requestDto,
		HttpServletRequest request
	) {
		// JWT를 통해 인증된 유저의 ID를 request의 attribute에서 가져옴(추후 JWT 구현 방식에 따라 코드 변경 가능성 있음)
		Long userId = (Long) request.getAttribute("userId");

		// userId를 서비스 단으로 넘겨서 검증 및 post 생성 진행
		CreatePostResponseDto responseDto = postServiceYJ.createPostService(userId, requestDto);

		// HTTP 상태 코드 201(create)와 함께 responseDto 응답
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	// post 삭제(소프트 딜리트 사용)
	@DeleteMapping("/{post_id}")
	public ResponseEntity<DeletePostResponseDto> deletePostAPI(
		@PathVariable(name = "post_id") Long postId,
		HttpServletRequest request
	) {
		// JWT를 통해 인증된 유저의 ID를 request의 attribute에서 가져옴(추후 JWT 구현 방식에 따라 코드 변경 가능성 있음)
		Long userId = (Long) request.getAttribute("userId");

		// postId를 서비스 단으로 넘겨서 검증 및 post 삭제 진행
		DeletePostResponseDto responseDto = postServiceYJ.deletePostService(postId, userId);

		// HTTP 상태 코드 200(ok)와 함께 responseDto 응답
		return ResponseEntity.ok(responseDto);
	}

	// post 검색(키워드 다건 조회)
	@GetMapping("/v1")
	public ResponseEntity<Page<FindPostResponseDto>> searchPostAPI(
		@RequestParam(required = false) String keyword,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		Page<FindPostResponseDto> foundPost = postServiceYJ.searchPostService(keyword, page, size);
		return ResponseEntity.ok(foundPost);
	}

	// 인기 검색어 기능
	// 키워드 별로 몇 번 호출됐나 기록, 그 개수로 조회


}
