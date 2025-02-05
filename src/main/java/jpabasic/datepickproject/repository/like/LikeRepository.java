package jpabasic.datepickproject.repository.like;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.lettuce.core.dynamic.annotation.Param;
import jpabasic.datepickproject.common.entity.like.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

	Optional<Like> findByPostIdAndUserId(@Param("post_id") Long postId, @Param("userId") Long userId);

	// 게시글의 좋아요 개수 조회 (status가 true인 경우만)
	Long countByPostIdAndLikeStatusTrue(Long postId);
}
