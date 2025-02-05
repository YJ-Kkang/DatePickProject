package jpabasic.datepickproject.controller.post;

import jpabasic.datepickproject.dto.post.request.UpdatePostRequestDto;
import jpabasic.datepickproject.dto.post.response.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jpabasic.datepickproject.dto.post.request.CreatePostRequestDto;
import jpabasic.datepickproject.service.post.PostService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	// post 생성
	@PostMapping
	public ResponseEntity<CreatePostResponseDto> createPostAPI(
		@Valid @RequestBody CreatePostRequestDto requestDto,
		HttpServletRequest request
	) {
		// JWT를 통해 인증된 유저의 ID를 request의 attribute에서 가져옴(추후 JWT 구현 방식에 따라 코드 변경 가능성 있음)
		Long userId = (Long) request.getAttribute("userId");

		// userId를 서비스 단으로 넘겨서 검증 및 post 생성 진행
		CreatePostResponseDto responseDto = postService.createPostService(userId, requestDto);

		// HTTP 상태 코드 201(create)와 함께 responseDto 응답
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	// 게시글 다건 조회
	@GetMapping
	public List<FindAllPostResponseDto> findAllPostAPI() {
		List<FindAllPostResponseDto> findAllPostDto = postService.findAllPost();
		return findAllPostDto;
	}


	// 게시글 단건 조회
	@GetMapping("/{postId}")
	public FindPostResponseDto findPostAPI(@PathVariable Long postId) {
		FindPostResponseDto findPostDto = postService.findPost(postId);
		return findPostDto;
	}


	// 게시글 수정
	@PatchMapping("/{postId}")
	public ResponseEntity<UpdatePostResponseDto> updatePostAPI(
			@PathVariable Long postId,
			@RequestBody UpdatePostRequestDto updatePostRequestDto,
			HttpServletRequest request // 필터에서 저장한 유저 아이디 가져옴
	) {

		// 필터에서 저장한 유저 아이디 가져옴 -> 포스트 수정 서비스 호출 -> 응답 반환
		Long userId = (Long) request.getAttribute("userId");


		UpdatePostResponseDto updatePostDto = postService.updatePost(postId, updatePostRequestDto);
		return ResponseEntity.ok(updatePostDto);

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
		DeletePostResponseDto responseDto = postService.deletePostService(postId, userId);

		// HTTP 상태 코드 200(ok)와 함께 responseDto 응답
		return ResponseEntity.ok(responseDto);
	}

	// post 검색(키워드 다건 조회)
	/**
	 * 포스트맨
	 * GET  http://localhost:8080/api/posts/v1
	 * 헤더 -> Content-Type : application/json;charset=UTF-8
	 * Authorization -> Bearer Token / 로그인 시 나오는 jwt 토큰 값 넣기
	 * Query Params -> (예시) keyword : 서울, page : 1, size : 2
	 */
	@GetMapping("/v1")
	public ResponseEntity<Page<FindPostResponseDto>> searchPostAPI(
		@RequestParam(required = false) String keyword,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		Page<FindPostResponseDto> foundPost = postService.searchPostService(keyword, page, size);
		return ResponseEntity.ok(foundPost);
	}

	// 인기 검색어 기능
	// 키워드 별로 몇 번 호출됐나 기록, 그 개수로 조회


}
