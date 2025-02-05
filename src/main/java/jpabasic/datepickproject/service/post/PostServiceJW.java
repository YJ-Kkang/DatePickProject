package jpabasic.datepickproject.service.post;

import jpabasic.datepickproject.common.entity.post.Post;
import jpabasic.datepickproject.common.entity.user.User;
import jpabasic.datepickproject.dto.post.request.UpdatePostRequestDto;
import jpabasic.datepickproject.dto.post.response.FindAllPostResponseDto;
import jpabasic.datepickproject.dto.post.response.FindPostResponseDto;
import jpabasic.datepickproject.dto.post.response.UpdatePostResponseDto;
import jpabasic.datepickproject.repository.like.LikeRepository;
import jpabasic.datepickproject.repository.post.PostRepository;
import jpabasic.datepickproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceJW {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    // 게시글 다건 조회 (좋아요 내림차순)
    public List<FindAllPostResponseDto> findAllPost() {
        // 모든 게시글 조회
        List<Post> postList = postRepository.findAll();
        List<FindAllPostResponseDto> findAllPostResponseDtoList = new ArrayList<>();

        for (Post post : postList) {

            // 게시글의 좋아요 개수 조회
            Long likeCount = post.getLikeCount();
            findAllPostResponseDtoList.add(new FindAllPostResponseDto(post, likeCount));
        }

        // 좋아요 개수 내림차순 정렬
        findAllPostResponseDtoList.sort((p1, p2) -> Long.compare(p2.getLikeCount(), p1.getLikeCount()));

        return findAllPostResponseDtoList;
    }


    // 게시글 단건 조회
    public FindPostResponseDto findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 게시글의 좋아요 개수 조회
        Long likeCount = post.getLikeCount();

        FindPostResponseDto findPostResponseDto = new FindPostResponseDto(post, likeCount);
        return findPostResponseDto;
    }

    // 게시글 수정
    @Transactional
    public UpdatePostResponseDto updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto) {

        // 저장한 유저아이디 가져오기
        Long userId = updatePostRequestDto.getUserId();

        // 추출한 이메일로 유저를 조회 -> 없으면 예외 발생
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 포스트를 조회 -> 없으면 예외 발생
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));


        // 게시글 제목, 내용 수정 후 반환
        post.updatePost(updatePostRequestDto.getTitle(), updatePostRequestDto.getContent());

        return new UpdatePostResponseDto(post);
    }
}
