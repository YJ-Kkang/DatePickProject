package jpabasic.datepickproject.dto.post.response;

import jpabasic.datepickproject.common.entity.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FindAllPostResponseDto {
    private final String userName;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final Long likeCount;

    public FindAllPostResponseDto(Post post, Long likeCount) {
        this.userName = post.getUser().getUserName();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.likeCount = likeCount;
    }
}
