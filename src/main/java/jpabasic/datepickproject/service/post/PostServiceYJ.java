package jpabasic.datepickproject.service.post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabasic.datepickproject.common.entity.post.Post;
import jpabasic.datepickproject.dto.post.request.CreatePostRequestDto;
import jpabasic.datepickproject.dto.post.response.CreatePostResponseDto;
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
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을수 없습니다.")); // todo 추후 커스텀 예외 처리로 변경

		// post 생성
		Post newPost = new Post(requestDto.getTitle(), requestDto.getContent());

		// repository에 post 저장
		Post savedPost = postRepositoryYJ.save(newPost);

		// 컨트롤러 단으로 dto 넘기기
		return new CreatePostResponseDto(savedPost);
	}

}
