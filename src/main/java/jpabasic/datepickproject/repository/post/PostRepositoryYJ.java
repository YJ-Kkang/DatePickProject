package jpabasic.datepickproject.repository.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jpabasic.datepickproject.common.entity.post.Post;

public interface PostRepositoryYJ extends JpaRepository<Post, Long> {

	// post 검색(키워드 다건 조회)
	@Query("""
		      SELECT p FROM Post p
		      LEFT JOIN FETCH p.user u
		      WHERE (:keyword IS NULL OR p.title LIKE %:keyword% OR p.content LIKE %:keyword%)
		      ORDER BY p.likeCount DESC, p.createdAt DESC
		""") // 좋아요 수 기준 내림차순 정렬, 같은 좋아요 수라면 작성일 기준 내림차순 정렬
	Page<Post> searchPosts(@Param("keyword") String keyword, Pageable pageable); // page로 뱉으려면 Pageable을 줘야 한다

}
