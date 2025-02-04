package jpabasic.datepickproject.dto.post.request;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UpdatePostRequestDto {
    private Long userId;
    private String title;
    private String content;

    public UpdatePostRequestDto(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

}
