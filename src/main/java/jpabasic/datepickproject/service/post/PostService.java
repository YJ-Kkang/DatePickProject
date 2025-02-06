package jpabasic.datepickproject.service.post;

import jpabasic.datepickproject.dto.post.request.UpdatePostRequestDto;
import jpabasic.datepickproject.dto.post.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabasic.datepickproject.common.entity.post.Post;
import jpabasic.datepickproject.common.entity.user.User;
import jpabasic.datepickproject.common.exception.CustomException;
import jpabasic.datepickproject.common.exception.ErrorCode;
import jpabasic.datepickproject.dto.post.request.CreatePostRequestDto;
import jpabasic.datepickproject.repository.post.PostRepository;
import jpabasic.datepickproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final SearchKeywordService searchKeywordService;

	// post 생성
	@Transactional
	public CreatePostResponseDto createPostService(
		Long userId,
		CreatePostRequestDto requestDto
	) {
		// 유저 id 검증(todo 추후 필터 단에서 로그인할 때 유저 id 검증이 되는 구조라면 아래 로직 삭제할 것 -> 바로 post 생성 로직 시작)
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		// post 생성
		Post newPost = new Post(user, requestDto.getTitle(), requestDto.getContent());

		// repository에 post 저장
		Post savedPost = postRepository.save(newPost);

		// 컨트롤러 단으로 dto 넘기기
		return new CreatePostResponseDto(savedPost);
	}

	// 게시글 다건 조회 (좋아요 내림차순)
	public List<FindAllPostResponseDto> findAllPost() {
		// 모든 게시글 조회
		List<Post> postList = postRepository.findAll();
		List<FindAllPostResponseDto> findAllPostResponseDtoList = new ArrayList<>();

		for (Post post : postList) {

			// 게시글의 좋아요 개수 조회
			Long likeCount = post.getLikeCount();
			findAllPostResponseDtoList.add(new FindAllPostResponseDto(post, likeCount));
		}

		// 좋아요 개수 내림차순 정렬
		findAllPostResponseDtoList.sort((p1, p2) -> Long.compare(p2.getLikeCount(), p1.getLikeCount()));

		return findAllPostResponseDtoList;
	}


	// 게시글 단건 조회
	public FindPostResponseDto findPost(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

		// 게시글의 좋아요 개수 조회
		Long likeCount = post.getLikeCount();
		FindPostResponseDto findPostResponseDto = new FindPostResponseDto(post, likeCount);

		return findPostResponseDto;
	}

	// 게시글 수정
	@Transactional
	public UpdatePostResponseDto updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto) {

		// 저장한 유저아이디 가져오기
		Long userId = updatePostRequestDto.getUserId();

		// 추출한 이메일로 유저를 조회 -> 없으면 예외 발생
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

		// 포스트를 조회 -> 없으면 예외 발생
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));


		// 게시글 제목, 내용 수정 후 반환
		post.updatePost(updatePostRequestDto.getTitle(), updatePostRequestDto.getContent());

		return new UpdatePostResponseDto(post);
	}

	// post 삭제(소프트 딜리트 사용)
	@Transactional
	public DeletePostResponseDto deletePostService(Long postId, Long userId) {
		// 포스트 id 검증
		Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

		// 작성자 검증(post 작성한 유저와 삭제하려는 유저가 동일인인지 확인)
		boolean isNotAuthor = !findPost.getUser().getId().equals(userId);
		if (isNotAuthor) {
			throw new CustomException(ErrorCode.POST_DELETION_NOT_AUTHORIZED);
		}

		// 이미 삭제된 게시글 예외 처리 (isDeleted = false가 기본 값, true면 이미 삭제된 게시글)
		if (findPost.isDeleted()) {
			throw new CustomException(ErrorCode.POST_IS_DELETE);
		}

		// 소프트 딜리트 처리
		// 포스트 삭제 시 isDeleted를 true로 변경(BaseEntity의 markAsDeleted() 활용) 후 저장
		findPost.markAsDeleted();
		postRepository.save(findPost);

		// 컨트롤러 단으로 dto 넘기기
		return new DeletePostResponseDto(findPost);
	}

	// post 검색(키워드 다건 조회)
	public Page<FindPostResponseDto> searchPostService(
		String keyword,
		int page,
		int size
	) {
		// 1부터 시작하게 만듦 (정적 팩토리 메서드)
		Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size);

		// 검색된 키워드가 이미 존재하는지 확인 후, 키워드 저장(또는 키워드 검색 횟수 증가)
		if (keyword != null && !keyword.isBlank()) {
			searchKeywordService.saveOrUpdateSearchKeyword(keyword);
		}

		// 좋아요 수 기준으로 정렬된 게시글 검색
		Page<Post> posts = postRepository.searchPosts(keyword, pageable);

		// Entity → DTO 변환
		return posts.map(post -> new FindPostResponseDto(post, post.getLikeCount())); // == new FindPostResponseDto(posts.map());
	}

}
