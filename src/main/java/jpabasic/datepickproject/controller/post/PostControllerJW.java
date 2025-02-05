package jpabasic.datepickproject.controller.post;

import jakarta.servlet.http.HttpServletRequest;
import jpabasic.datepickproject.dto.post.request.UpdatePostRequestDto;
import jpabasic.datepickproject.dto.post.response.FindAllPostResponseDto;
import jpabasic.datepickproject.dto.post.response.FindPostResponseDto;
import jpabasic.datepickproject.dto.post.response.UpdatePostResponseDto;
import jpabasic.datepickproject.service.post.PostServiceJW;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostControllerJW {

    private final PostServiceJW postServiceJW;

    // 게시글 다건 조회
    @GetMapping
    public List<FindAllPostResponseDto> findAllPostAPI() {
        List<FindAllPostResponseDto> findAllPostDto = postServiceJW.findAllPost();
        return findAllPostDto;
    }

    // 게시글 단건 조회
    @GetMapping("/{postId}")
    public FindPostResponseDto findPostAPI(@PathVariable Long postId) {
        FindPostResponseDto findPostDto = postServiceJW.findPost(postId);
        return findPostDto;
    }


    // 게시글 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<UpdatePostResponseDto> updatePostAPI(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequestDto updatePostRequestDto,
            HttpServletRequest request // 필터에서 저장한 유저 아이디 가져옴
    ) {

        // 필터에서 저장한 유저 아이디 가져옴 -> 포스트 수정 서비스 호출 -> 응답 반환
        Long userId = (Long) request.getAttribute("userId");


        UpdatePostResponseDto updatePostDto = postServiceJW.updatePost(postId, updatePostRequestDto);
        return ResponseEntity.ok(updatePostDto);

    }
}
