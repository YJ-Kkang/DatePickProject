package jpabasic.datepickproject.service.post;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabasic.datepickproject.common.entity.post.SearchKeyword;
import jpabasic.datepickproject.repository.post.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchKeywordService {

	private final SearchKeywordRepository searchKeywordRepository;

	// 키워드 저장 or 검색 횟수 증가
	@Transactional
	public void saveOrUpdateSearchKeyword(String keyword) {
		searchKeywordRepository.findByKeyword(keyword)
			.ifPresentOrElse(
				SearchKeyword::increaseCount, // 존재하면 count 증가
				() -> searchKeywordRepository.save(new SearchKeyword(keyword)) // 없으면 새로 저장
			);
	}

	// 인기 키워드 TOP 10
	public List<SearchKeyword> getTop10SearchKeywords() {
		return searchKeywordRepository.findTop10ByOrderByCountDesc();
	}

}
