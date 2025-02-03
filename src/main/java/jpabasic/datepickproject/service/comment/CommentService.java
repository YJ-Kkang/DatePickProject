package jpabasic.datepickproject.service.comment;

import jakarta.persistence.EntityNotFoundException;
import jpabasic.datepickproject.dto.comment.request.CommentRequest;
import jpabasic.datepickproject.dto.comment.response.CommentResponse;
import jpabasic.datepickproject.common.entity.comment.Comment;
import jpabasic.datepickproject.common.entity.post.Post;
import jpabasic.datepickproject.common.entity.user.User;
import jpabasic.datepickproject.repository.comment.CommentRepository;
import jpabasic.datepickproject.repository.post.PostRepositoryYJ;
import jpabasic.datepickproject.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepositoryYJ postRepository;

	private final UserRepository userRepository;

	@Transactional
	public CommentResponse createComment(CommentRequest request) {
		Post post = postRepository.findById(request.getPostId())
			.orElseThrow(() -> new EntityNotFoundException("Post not found"));
		User user = userRepository.findById(request.getUserId())
			.orElseThrow(() -> new EntityNotFoundException("User not found"));

		Comment comment = new Comment(user, post, request.getContent());

		commentRepository.save(comment);
		return CommentResponse.from(comment);
	}

	@Transactional(readOnly = true)
	public List<CommentResponse> getCommentsByPost(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException("Post not found"));

		return commentRepository.findByPost(post).stream()
			.map(CommentResponse::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public CommentResponse updateComment(Long commentId, String content) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException("Comment not found"));

		comment.updateContent(content);
		return CommentResponse.from(comment);
	}

	@Transactional
	public void deleteComment(Long commentId) {
		if (!commentRepository.existsById(commentId)) {
			throw new EntityNotFoundException("Comment not found");
		}
		commentRepository.deleteById(commentId);
	}
}