package jpabasic.datepickproject.service.post;

import jpabasic.datepickproject.common.entity.post.Post;
import jpabasic.datepickproject.common.entity.user.User;
import jpabasic.datepickproject.common.utils.JwtUtil;
import jpabasic.datepickproject.dto.post.request.UpdatePostRequestDto;
import jpabasic.datepickproject.dto.post.response.FindAllPostResponseDto;
import jpabasic.datepickproject.dto.post.response.FindPostResponseDto;
import jpabasic.datepickproject.dto.post.response.UpdatePostResponseDto;
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
public class PostServiceJW {

    private final PostRepositoryYJ postRepositoryYJ;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 게시글 다건 조회
    public List<FindAllPostResponseDto> findAllPost() {
        List<Post> postList = postRepositoryYJ.findAll();
        List<FindAllPostResponseDto> findAllPostResponseDtoList = postList
                .stream()
                .map(FindAllPostResponseDto::new)
                .collect(Collectors.toList());
        return findAllPostResponseDtoList;
    }

    // 게시글 단건 조회
    public FindPostResponseDto findPost(Long postId) {
        Post post = postRepositoryYJ.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        FindPostResponseDto findPostResponseDto = new FindPostResponseDto(post);
        return findPostResponseDto;
    }


    @Transactional
    public UpdatePostResponseDto updatePost(String token, Long postId, UpdatePostRequestDto updatePostRequestDto) {
        // 1. JWT 토큰에서 이메일 추출
        String email = jwtUtil.getEmailFromToken(token);

        // 유저이메일을 조회 -> 없으면 예외 발생
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 포스트를 조회 -> 없으면 예외 발생
        Post post = postRepositoryYJ.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 게시글 수정
        post.updatePost(updatePostRequestDto.getTitle(), updatePostRequestDto.getContent());
        return new UpdatePostResponseDto(post);
    }


}
