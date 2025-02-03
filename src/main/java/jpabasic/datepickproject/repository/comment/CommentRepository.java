package jpabasic.datepickproject.repository.comment;

import jpabasic.datepickproject.common.entity.comment.Comment;
import jpabasic.datepickproject.common.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPost(Post post);
}