package jpabasic.datepickproject.service.like;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabasic.datepickproject.common.entity.like.Like;
import jpabasic.datepickproject.common.entity.post.Post;
import jpabasic.datepickproject.common.entity.user.User;
import jpabasic.datepickproject.common.exception.CustomException;
import jpabasic.datepickproject.common.exception.ErrorCode;
import jpabasic.datepickproject.dto.like.LikeStatusResponseDto;
import jpabasic.datepickproject.repository.like.LikeRepository;
import jpabasic.datepickproject.repository.post.PostRepositoryYJ;
import jpabasic.datepickproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
		Post post = postRepositoryYJ.findById(postId)
			.orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

		// 기존의 좋아요 정보를 확인
		Optional<Like> optionalLike = likeRepository.findByPostIdAndUserId(postId, userId);

		// 게시글에 좋아요를 처음 눌렀을 때 라이크를 새로 생성함.
		Like like = optionalLike.orElseGet(() -> {
			// 새로운 좋아요 데이터를 생성
			Like newLike = new Like(user, post);
			// 새롭게 생성된 좋아요 데이터를 레포지토리에 저장
			likeRepository.save(newLike);
			// 게시글의 좋아요 수를 증가 시킴.
			post.changePostLike(post.getLikeCount() + 1);
			return newLike;
		});

		// isPresent 는 값이 있을 때 true isEmpty 는 값이 없을 때
		if (optionalLike.isPresent()) {
			// 이미 좋아요가 있다면 -> 상태 변경 (좋아요 취소 혹은 다시 좋아요로 상태 변경이 가능함)
			like.switchLike();
			/**
			 * 좋아요 상태 변경에 따라 게시글 좋아요 개수 조정
			 * likeStatus 의 상태가 true 면 ?뒤의 코드가 작동하고 false 면 : 뒤에 코드가 작동하는 방식의 삼항 연산자임.
			 * 코드가 간결해서 좋지만 좀 풀어서 리팩토링 해보고 싶다
			 */
			post.changePostLike(like.isLikeStatus() ? post.getLikeCount() + 1 : post.getLikeCount() - 1);
		}

		return new LikeStatusResponseDto(like.checkLike(), post);
	}
}
