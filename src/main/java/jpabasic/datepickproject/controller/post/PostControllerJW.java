package jpabasic.datepickproject.controller.post;

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
    public List<FindAllPostResponseDto> findAllPostServiceAPI() {
        List<FindAllPostResponseDto> postListResponseDtoList = postServiceJW.findAllPostService();
        return postListResponseDtoList;
    }

    // 게시글 단건 조회
    @GetMapping("/{postId}")
    public FindPostResponseDto findPostServiceAPI(@PathVariable Long postId) {
        FindPostResponseDto postDetailResponseDto = postServiceJW.findPostService(postId);
        return postDetailResponseDto;
    }



}

