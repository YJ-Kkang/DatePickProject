package jpabasic.datepickproject.controller.comment;

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
	public ResponseEntity<CommentResponse> createComment(@PathVariable Long postId, @RequestBody CommentRequest request) {
		return ResponseEntity.ok(commentService.createComment(postId, request));
	}
	// 댓글 조회
	@GetMapping
	public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
		return ResponseEntity.ok(commentService.getCommentsByPost(postId));
	}
	// 댓글 수정
	@PatchMapping("/{commentId}")
	public ResponseEntity<String> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
		commentService.updateComment(commentId, request.getContent());
		return ResponseEntity.ok("댓글이 수정 되었습니다.");
	}
	// 댓글 삭제
	@DeleteMapping("/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
		commentService.deleteComment(commentId);
		return ResponseEntity.ok("댓글이 삭제 되었습니다.");
	}
}
