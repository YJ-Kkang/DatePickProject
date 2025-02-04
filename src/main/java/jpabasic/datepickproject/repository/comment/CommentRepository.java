package jpabasic.datepickproject.repository.comment;

import jpabasic.datepickproject.common.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPostId(Long postId);
	// 삭제되지 않은 댓글만 조회
	List<Comment> findByPostIdAndIsDeletedFalse(Long postId);

	Optional<Comment> findById(Long commentId);
}
