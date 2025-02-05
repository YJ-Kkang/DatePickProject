package jpabasic.datepickproject.service.post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabasic.datepickproject.common.entity.post.Post;
import jpabasic.datepickproject.common.entity.user.User;
import jpabasic.datepickproject.common.exception.CustomException;
import jpabasic.datepickproject.common.exception.ErrorCode;
import jpabasic.datepickproject.dto.post.request.CreatePostRequestDto;
import jpabasic.datepickproject.dto.post.response.CreatePostResponseDto;
import jpabasic.datepickproject.dto.post.response.DeletePostResponseDto;
import jpabasic.datepickproject.repository.post.PostRepositoryYJ;
import jpabasic.datepickproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceYJ {

	private final PostRepositoryYJ postRepositoryYJ;
	private final UserRepository userRepository;

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
		Post savedPost = postRepositoryYJ.save(newPost);

		// 컨트롤러 단으로 dto 넘기기
		return new CreatePostResponseDto(savedPost);
	}

	// post 삭제(소프트 딜리트 사용)
	@Transactional
	public DeletePostResponseDto deletePostService(Long postId, Long userId) {
		// 포스트 id 검증
		Post findPost = postRepositoryYJ.findById(postId)
			.orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

		// 작성자 검증(post 작성한 유저와 삭제하려는 유저가 동일인인지 확인)
		boolean isNotAuthor = !findPost.getUser().getId().equals(userId);
		if(isNotAuthor) {
			throw new CustomException(ErrorCode.POST_DELETION_NOT_AUTHORIZED);
		}

		// 이미 삭제된 게시글 예외 처리 (isDeleted = false가 기본 값, true면 이미 삭제된 게시글)
		if (findPost.isDeleted()) {
			throw new CustomException(ErrorCode.POST_IS_DELETE);
		}

		// 소프트 딜리트 처리
		// 포스트 삭제 시 isDeleted를 true로 변경(BaseEntity의 markAsDeleted() 활용) 후 저장
		findPost.markAsDeleted();
		postRepositoryYJ.save(findPost);

		// 컨트롤러 단으로 dto 넘기기
		return new DeletePostResponseDto(findPost);
	}
}
