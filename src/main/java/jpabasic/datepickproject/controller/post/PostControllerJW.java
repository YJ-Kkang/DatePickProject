package jpabasic.datepickproject.controller.post;

import jakarta.servlet.http.HttpServletRequest;
import jpabasic.datepickproject.common.exception.CustomException;
import jpabasic.datepickproject.common.exception.ErrorCode;
import jpabasic.datepickproject.dto.post.request.UpdatePostRequestDto;
import jpabasic.datepickproject.dto.post.response.FindAllPostResponseDto;
import jpabasic.datepickproject.dto.post.response.FindPostResponseDto;
import jpabasic.datepickproject.dto.post.response.UpdatePostResponseDto;
import jpabasic.datepickproject.service.post.PostServiceJW;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        List<FindAllPostResponseDto> postListResponseDtoList = postServiceJW.findAllPost();
        return postListResponseDtoList;
    }

    // 게시글 단건 조회
    @GetMapping("/{postId}")
    public FindPostResponseDto findPostAPI(@PathVariable Long postId) {
        FindPostResponseDto postDetailResponseDto = postServiceJW.findPost(postId);
        return postDetailResponseDto;
    }


    // 게시글 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<UpdatePostResponseDto> updatePostAPI(
            @PathVariable("postId") Long postId,
            @RequestBody UpdatePostRequestDto updatePostRequestDto,
            HttpServletRequest request
    ) {
        // 1. Authorization 헤더에서 JWT 토큰 추출
        String token = request.getHeader("Authorization");

        // JWT 토큰이 없거나, 접두사 "Bearer "로 시작하지 않으면
        if (token == null || !token.startsWith("Bearer ")) {
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND); // 토큰 존재하지 않는다는 예외 처리
        }
        token = token.substring(7); // "Bearer " 부분 제외

        // 게시글 수정 서비스 호출
        UpdatePostResponseDto updatePost = postServiceJW.updatePost(token, postId, updatePostRequestDto);

        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

}
