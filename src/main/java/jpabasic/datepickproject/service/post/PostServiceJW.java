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

import java.util.ArrayList;
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
        // 게시글 조회하고
        List<Post> postList = postRepositoryYJ.findAll();
        List<FindAllPostResponseDto> findAllPostResponseDtoList = new ArrayList<>();

        // for 문을 사용하여 변환
        for (Post post : postList) {
            findAllPostResponseDtoList.add(new FindAllPostResponseDto(post));
        }

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
    public UpdatePostResponseDto updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto) {
        // 토큰에서 유저 아이디 가져옴 -> 유저아이디로 유저 조회 -> 똑같?

        // 토큰에서 추출한 유저아이디 가져오기
        Long userId = updatePostRequestDto.getUserId();

        // 추출한 이메일로 유저를 조회 -> 없으면 예외 발생
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 포스트를 조회 -> 없으면 예외 발생
        Post post = postRepositoryYJ.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));


        // 게시글 제목, 내용 수정 후 반환
        post.updatePost(updatePostRequestDto.getTitle(), updatePostRequestDto.getContent());

        return new UpdatePostResponseDto(post);
    }
}
