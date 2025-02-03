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
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

	private final jpabasic.datepickproject.service.comment.CommentService commentService;

	@PostMapping
	public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request) {
		return ResponseEntity.ok(commentService.createComment(request));
	}

	@GetMapping("/{postId}")
	public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
		return ResponseEntity.ok(commentService.getCommentsByPost(postId));
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<CommentResponse> updateComment(
		@PathVariable Long commentId,
		@RequestBody CommentUpdateRequest request) {
		return ResponseEntity.ok(commentService.updateComment(commentId, request.getContent()));
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
		commentService.deleteComment(commentId);
		return ResponseEntity.noContent().build();
	}
}