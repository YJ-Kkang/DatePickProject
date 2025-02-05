package jpabasic.datepickproject.dto.post.response;

import jpabasic.datepickproject.common.entity.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdatePostResponseDto {
    private final String message;
    private final Long userId;
    private final String title;
    private final String content;
    private final LocalDateTime updatedAt;

    public UpdatePostResponseDto(Post post) {
        this.message = "게시글이 수정되었습니다.";
        this.userId = post.getUser().getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.updatedAt = post.getUpdatedAt();
    }
}
