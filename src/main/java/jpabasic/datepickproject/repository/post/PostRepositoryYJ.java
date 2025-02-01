package jpabasic.datepickproject.repository.post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jpabasic.datepickproject.common.entity.post.Post;

public interface PostRepositoryYJ extends JpaRepository<Post, Long> {

	Optional<Post> findPostById(Long postId);
}
