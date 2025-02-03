package jpabasic.datepickproject.service.post;

import jpabasic.datepickproject.common.entity.post.Post;
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

    // 게시글 다건 조회
    public List<FindAllPostResponseDto> findAllPostService() {
        List<Post> postList = postRepositoryYJ.findAll();
        List<FindAllPostResponseDto> findAllPostResponseDtoList = postList
                .stream()
                .map(FindAllPostResponseDto::new)
                .collect(Collectors.toList());
        return findAllPostResponseDtoList;
    }

    // 게시글 단건 조회
    public FindPostResponseDto findPostService(Long postId) {
        Post post = postRepositoryYJ.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        FindPostResponseDto findPostResponseDto = new FindPostResponseDto(post);
        return findPostResponseDto;
    }



}
