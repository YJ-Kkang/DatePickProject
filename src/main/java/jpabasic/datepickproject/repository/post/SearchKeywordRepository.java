package jpabasic.datepickproject.repository.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jpabasic.datepickproject.common.entity.post.SearchKeyword;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {

	// 검색어가 DB에 존재하는지 확인
	Optional<SearchKeyword> findByKeyword(String keyword);

	// 가장 많이 검색된 10개 키워드 리스트
	List<SearchKeyword> findTop10ByOrderByCountDesc();
}
