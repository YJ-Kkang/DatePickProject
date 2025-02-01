package jpabasic.datepickproject.service.post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabasic.datepickproject.common.entity.post.Post;
import jpabasic.datepickproject.dto.post.request.CreatePostRequestDto;
import jpabasic.datepickproject.dto.post.response.CreatePostResponseDto;
import jpabasic.datepickproject.dto.post.response.DeletePostResponseDto;
import jpabasic.datepickproject.repository.post.PostRepositoryYJ;
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
		// 유저 id 검증(todo 추후 필터 단에서 로그인할 때 유저 id 검증이 되는 구조라면 아래 로직 삭제할 것 -> 바로 post 생성)
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다.")); // todo 추후 커스텀 예외 처리로 변경

		// post 생성
		Post newPost = new Post(requestDto.getTitle(), requestDto.getContent());

		// repository에 post 저장
		Post savedPost = postRepositoryYJ.save(newPost);

		// 컨트롤러 단으로 dto 넘기기
		return new CreatePostResponseDto(savedPost);
	}

	// post 삭제(소프트 딜리트 사용)
	@Transactional
	public DeletePostResponseDto deletePostService(Long postId) {
		// 포스트 id 검증
		Post findPost = postRepositoryYJ.findPostById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")); // todo 추후 커스텀 예외 처리로 변경

		// 이미 삭제된 게시글에 삭제 요청 들어올 시 예외 처리
		// todo 추후 커스텀 예외 처리로 변경

		// 포스트 삭제 시 포스트 삭제 상태를 true로 변경(BaseEntity의 markAsDeleted() 활용)
		findPost.markAsDeleted();

		// 레포지토리에 포스트 삭제된 상태 저장
		postRepositoryYJ.save(findPost);

		// 컨트롤러 단으로 dto 넘기기
		return new DeletePostResponseDto(findPost);
	}
}
