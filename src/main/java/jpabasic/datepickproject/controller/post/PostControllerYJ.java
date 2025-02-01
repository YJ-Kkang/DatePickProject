package jpabasic.datepickproject.controller.post;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabasic.datepickproject.service.post.PostServiceYJ;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostControllerYJ {
	private final PostServiceYJ postServiceYJ;

}
