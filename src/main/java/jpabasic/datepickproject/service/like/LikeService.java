package jpabasic.datepickproject.service.like;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabasic.datepickproject.common.entity.like.Like;
import jpabasic.datepickproject.common.entity.post.Post;
import jpabasic.datepickproject.common.entity.user.User;
import jpabasic.datepickproject.dto.like.LikeStatusResponseDto;
import jpabasic.datepickproject.repository.like.LikeRepository;
import jpabasic.datepickproject.repository.post.PostRepositoryYJ;
import jpabasic.datepickproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;
	private final PostRepositoryYJ postRepositoryYJ;
	private final UserRepository userRepository;

	// 좋아요 상태 변경
	@Transactional
	public LikeStatusResponseDto changeLikeStatus(Long postId, Long userId) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));
		Post post = postRepositoryYJ.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
		// 기존의 좋아요 정보를 확인

		Optional<Like> optionalLike = likeRepository.findByPostIdAndUserId(postId, userId);

		// isPresent 는 값이 있을 때 true isEmpty 는 값이 없을 때
		if (optionalLike.isPresent()) {
			// 이미 좋아요가 있다면 -> 상태 변경 (좋아요 취소 혹은 다시 좋아요로 상태 변경이 가능함)
			Like changeLike = optionalLike.get();
			changeLike.switchLike();

			/**
			 * 좋아요 상태 변경에 따라 게시글 좋아요 개수 조정
			 * likeStatus 의 상태가 true 면 ?뒤의 코드가 작동하고 false 면 : 뒤에 코드가 작동하는 방식의 삼항 연산자임.
			 * 코드가 간결해서 좋지만 좀 풀어서 리팩토링 해보고 싶다
			 */
			post.changePostLike(changeLike.isLikeStatus() ? post.getLikeCount() + 1 : post.getLikeCount() - 1);
		} else {
			// 좋아요를 누른 적이 없었다면 새롭게 추가
			Like newLike = new Like(user, post);
			likeRepository.save(newLike);

			// 좋아요 수 증가
			post.changePostLike(post.getLikeCount() + 1);
		}

		// 좋아요 개수를 다시 조회
		Long updatedLikeCount = likeRepository.countByPostIdAndLikeStatusTrue(postId);
		// 조회한 변경된 좋아요 개수 업데이트
		post.changePostLike(updatedLikeCount);
		// 변경된 좋아요 개수가 업데이트된 게시글 저장
		postRepositoryYJ.save(post);

		return new LikeStatusResponseDto(optionalLike.get().checkLike(), post);
	}
}
