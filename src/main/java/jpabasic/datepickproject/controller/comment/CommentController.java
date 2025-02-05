package jpabasic.datepickproject.controller.comment;

import jakarta.servlet.http.HttpServletRequest;
import jpabasic.datepickproject.dto.comment.request.CommentRequest;
import jpabasic.datepickproject.dto.comment.request.CommentUpdateRequest;
import jpabasic.datepickproject.dto.comment.response.CommentResponse;
import jpabasic.datepickproject.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	// 댓글 생성
	@PostMapping
	public ResponseEntity<CommentResponse> createComment(@PathVariable Long postId, @RequestBody CommentRequest request, HttpServletRequest httpRequest) {
		Long userId = (Long) httpRequest.getAttribute("userId");
		if (userId == null) {
			return ResponseEntity.status(401).build();
		}
		return ResponseEntity.ok(commentService.createComment(postId, userId, request));
	}

	// 댓글 조회
	@GetMapping
	public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
		return ResponseEntity.ok(commentService.getCommentsByPost(postId));
	}

	// 댓글 수정
	@PatchMapping("/{commentId}")
	public ResponseEntity<String> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentUpdateRequest request, HttpServletRequest httpRequest) {
		Long userId = (Long) httpRequest.getAttribute("userId");
		if (userId == null) {
			return ResponseEntity.status(401).build();
		}
		commentService.updateComment(commentId, userId, request.getContent());
		return ResponseEntity.ok("댓글이 수정되었습니다.");
	}

	// 댓글 삭제
	@DeleteMapping("/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest httpRequest) {
		Long userId = (Long) httpRequest.getAttribute("userId");
		if (userId == null) {
			return ResponseEntity.status(401).build();
		}
		commentService.deleteComment(commentId, userId);
		return ResponseEntity.ok("댓글이 삭제되었습니다.");
	}
}