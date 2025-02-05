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
@Transactional(readOnly = true)
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepositoryYJ postRepositoryYJ;
	private final UserRepository userRepository;

	// 댓글 생성
	@Transactional
	public CommentResponse createComment(Long postId, Long userId, CommentRequest request) {
		Post post = postRepositoryYJ.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException("포스트를 찾을 수 없습니다."));
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
		Comment comment = new Comment(user, post, request.getContent());
		commentRepository.save(comment);
		return CommentResponse.from(comment);
	}

	// 댓글 조회 (삭제된 댓글 제외)
	public List<CommentResponse> getCommentsByPost(Long postId) {
		List<Comment> comments = commentRepository.findByPostIdAndIsDeletedFalse(postId);
		if (comments.isEmpty()) {
			throw new EntityNotFoundException("댓글을 찾을 수 없습니다.");
		}
		return comments.stream()
			.map(CommentResponse::from)
			.collect(Collectors.toList());
	}

	// 댓글 수정 (본인 댓글만 수정 가능)
	@Transactional
	public void updateComment(Long commentId, Long userId, String content) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));
		if (!comment.getUser().getId().equals(userId)) {
			throw new IllegalStateException("본인 댓글만 수정할 수 있습니다.");
		}
		comment.updateContent(content);
	}

	// 댓글 삭제 (본인 댓글만 삭제 가능, 이미 삭제된 댓글 삭제 불가)
	@Transactional
	public void deleteComment(Long commentId, Long userId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));
		if (comment.isDeleted()) {
			throw new IllegalStateException("이미 삭제된 댓글은 다시 삭제할 수 없습니다.");
		}
		if (!comment.getUser().getId().equals(userId)) {
			throw new IllegalStateException("본인 댓글만 삭제할 수 있습니다.");
		}
		comment.markAsDeleted();
	}
}